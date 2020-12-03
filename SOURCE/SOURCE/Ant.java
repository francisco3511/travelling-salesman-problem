package project;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Ant {
	int currentNd;
	LinkedList<Edge> path;
	int pathWeight;
	int[] visited;
	int visitCount;
	int nbNodes;
	int nestNode;
	Graph graph;
	Random rand;
	Parameters parameters;
	
	public Ant(int _nbNodes, int _nestNode, Graph _graph, Parameters _parameters)
	{
		nbNodes = _nbNodes;
		nestNode = _nestNode;
		currentNd = _nestNode;
		path = new LinkedList<Edge>();
		visited = new int[_nbNodes];
		visited[nestNode] = 1; // Marks nest as visited
		graph = _graph;
		visitCount = 1;
		rand = new Random();
		parameters = _parameters;
	}
	
	public LinkedList<Edge> getPath()
	{
		return path;
	}
	
	public int getPathLength()
	{
		return pathWeight;
	}
	
	public boolean isFinished()
	{
		int flag = 1;
		
		for(int i = 0; i < nbNodes; i++) {
			if(visited[i] != 1) {
				flag = 0;
				break;
			}
		}
		
		if((visitCount == nbNodes+1) && (currentNd == nestNode) && (flag == 1))
			return true;
		//else
		return false;
	}
	
	public int getCurrentNode()
	{
		return currentNd;
	}
	
	// Picks one node the ant has visited already from an uniform distribution
	public int pickRandomVisitedNd(Node currNode)
	{
		if(currNode.getNrEdges()==0)
		{
			System.out.println("Current node has no edges.");
			System.exit(-1);
		}
		
		//If current node only has one edge
		if(currNode.getNrEdges()==1)
			return currNode.getEdgeFromIndex(0).getTargetNode();
		
		//Else, pick a random node from the edges of current node
		int randomNd = currNode.getEdgeFromIndex(rand.nextInt(currNode.getNrEdges())).getTargetNode();
		
		return randomNd;
	}
	
	// Compute coefficients used to compute probability of choosing a node from set J
	public double[] computeCoefficients(LinkedList<Edge> setJ)
	{
		int sizeSetJ = setJ.size();
		double[] coefficients = new double[sizeSetJ+1];
		Edge edge;
		double ci = 0;
		
		//Fill coefficients array with each C(i)(j)k
		for(int i=0;i<sizeSetJ;i++)
		{
			edge = setJ.get(i);
			coefficients[i] = (parameters.getAlpha()+edge.getPheromone())/(parameters.getBeta()+edge.getWeight());
			ci += coefficients[i];
		}
		
		//Last position of coefficients array is the sum coefficient C(i)
		coefficients[sizeSetJ] = ci;
		
		return coefficients;
	}
	
	// Compute probability of choosing a node j from set J based on coefficient probability
	public double[] computeProbabilities(double[] coefficients)
	{
		double[] probabilities = new double[coefficients.length-1];
		
		//Compute probabilities and fill in array
		for(int i=0;i<coefficients.length-1;i++)
		{
			probabilities[i] = coefficients[i]/coefficients[coefficients.length-1];
		}
		
		//Return array
		return probabilities;
	}
	
	public int decision(double[] probabilities, LinkedList<Edge> setJ)
	{
		//Generate random double from uniform distribution (0.0, 1.0)
		double randNumber = rand.nextDouble();
		
		double total = 0;
		for (int i = 0; i < probabilities.length; i++) 
		{
		    total += probabilities[i];
		    if (total >= randNumber) 
		    {
		    	return setJ.get(i).getTargetNode();
		    }
		}
		//else, error
		System.out.println("Cumulative sum error.");
		return 0;
	}
	
	// Decide to which node the ant will go next and return the node ID
	public int decideNextNode()
	{
		Node currNode = graph.getNode(currentNd);
		Edge edge;
		LinkedList<Edge> setJ = new LinkedList<Edge>();
		
		//Go through all node edges
		for(int i=0;i<currNode.getNrEdges();i++)
		{
			edge = currNode.getEdgeFromIndex(i); // Get edge
			
			// Check if already visited, if not: add edge to setJ
			if(visited[edge.getTargetNode()]==0)
				setJ.add(edge); 
		}
		//Check if set is empty and if yes, pick a random node
		if(setJ.isEmpty())
			return pickRandomVisitedNd(currNode);	
		
		//Next move decision
		else
		{
			double[] coefficients = computeCoefficients(setJ);
			double[] probabilities = computeProbabilities(coefficients);
			return decision(probabilities, setJ);
		}

	}
	
	
	public void moveNextNode(int nextNode)
	{
		// If next node was already visited and we are not done yet we should go through path and remove sub-cycle
		if(visited[nextNode]==1 && visitCount != nbNodes)
		{
			for(int i=path.size()-1;i>=0;i--) // Remove redundant sub-cycle
			{
				Edge edge = path.get(i);
				int nodeDeleted = edge.getTargetNode();
				visited[nodeDeleted]=0;
				visitCount--;
				pathWeight -= edge.getWeight();
				path.remove(i);
				if(edge.getSourceNode()==nextNode)
					break;
			}
		}
		else // Else, update path to include node to which we will move
		{
			Edge edge = graph.getNode(currentNd).getEdgeTargetNd(nextNode);
			//Add edge to path
			path.add(edge);
			//Update pathLength
			pathWeight += edge.getWeight();
			//Increment visit count
			++visitCount;
			//Update visited nodes array
			visited[nextNode]=1;
		}
		currentNd = nextNode; // Updates currentNd of ant
	}
	
	public void finishedCycle()
	{
		if(!isFinished())
		{
			System.out.println("Not a cycle.");
			return;
		}
		
		Edge edge;
		//If the graph has no Hamiltonian cycle yet(cycleLength==-1) or 
		// if the ant has found a shorter length cycle, update
		if(pathWeight < graph.getCycleWeight() || graph.getCycleWeight() == -1)
		{
			int[] cycle = new int[nbNodes];
			cycle[0]=nestNode;
			//Update graph shortest cycle found so far
			for(int i=0;i<path.size()-1;i++)
			{
				edge = path.get(i);
				cycle[i+1]=edge.getTargetNode();
			}
			
			graph.updateCycle(cycle, pathWeight);
		}
		
		//Resets all parameters
		pathWeight = 0;
		path.clear();
		visited = new int[nbNodes];
		visited[nestNode]=1;
		visitCount = 1;
	}

	@Override
	public String toString() {
		return "Ant [currentNd=" + currentNd + ", path=" + path + ", pathLength=" + pathWeight + ", visited="
				+ Arrays.toString(visited) + ", visitCount=" + visitCount + ", nbNodes=" + nbNodes + ", nestNode="
				+ nestNode + "]";
	}
}
