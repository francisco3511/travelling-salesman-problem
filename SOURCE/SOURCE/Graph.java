package project;

import java.util.Arrays;

public class Graph {
	int nbNodes;
	int nestNode;
	int[] cycle;
	int cycleWeight;
	Node[] nodes;
	int totalWeight; // Sum of all edges weight in graph
	
	public Graph(int _nbNodes, int _nestNode)
	{
		//Set parameters
		nbNodes = _nbNodes;
		nestNode = _nestNode;
		cycle = new int[_nbNodes]; // Hamiltonian cycle
		//Cycle length is initialized to '-1' to signal the lack of a Hamiltonian cycle found so far
		cycleWeight = -1;
		totalWeight = 0;
		nodes = new Node[nbNodes]; // Initialize array of nodes
		//Create each node object
		for(int i=0; i<nbNodes; i++){
			nodes[i] = new Node(i);
		}
	}
	
	public int getNbNodes()
	{
		return nbNodes;
	}
	
	//Set i-th node edge
	public void setNodeEdges(int i, int j, int weight)
	{
		nodes[i].setEdge(j,weight);
		nodes[j].setEdge(i,weight);
		totalWeight += weight;
	}
	
	//Get node
	public Node getNode(int i)
	{
		return nodes[i];
	}
	
	//Get edge
	public Edge getEdge(int i, int j)
	{
		return getNode(i).getEdgeTargetNd(j);
	}
	
	public void updateCycle(int[] _cycle, int weight)
	{ 
		cycle = _cycle;
		cycleWeight = weight;
	}
	
	public int[] getCycle()
	{
		return cycle;
	}
	
	public int getCycleWeight()
	{
		return cycleWeight;
	}
	
	public int getTotalWeight()
	{
		return totalWeight;
	}
	
	public String stringCycle()
	{
		int i;
		String str = "{";
		for(i=0;i<nbNodes;i++)
		{
			if(i!=0)
			{
				str += ", ";
			}
			str += String.valueOf(cycle[i]+1);
		}
		str += "}";
		return str;
	}
	

	@Override
	public String toString() {
		return "Graph [nbNodes=" + nbNodes + ", nestNode=" + nestNode + ", nodes=\n" + Arrays.toString(nodes) + "]";
	}
}
