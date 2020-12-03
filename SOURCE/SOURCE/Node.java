package project;

import java.util.LinkedList;

public class Node {
	private int nodeIdx;
	LinkedList<Edge> edges;
	
	public Node(int _nodeIdx)
	{
		nodeIdx = _nodeIdx;
		edges = new LinkedList<Edge>();
	}
	
	//Set edge
	public void setEdge(int targetNode, int weight)
	{
		if(getEdgeTargetNd(targetNode) == null)
			edges.add(new Edge(nodeIdx, targetNode, weight));
		else
			System.out.println("Edge already associated.");
	}
	
	//Get edge
	public Edge getEdgeTargetNd (int targetNd)
	{
		for(int i=0;i<edges.size();i++)
		{
			Edge edge = edges.get(i);
			if(edge.getTargetNode()==targetNd)
			{
				return edge;
			}
		}
		return null;
	}
	
	//Get edge list index
	public Edge getEdgeFromIndex(int index)
	{
		if(index < edges.size())
		{
			return edges.get(index);
		}
		//else
		System.out.println("index out of node edges linked list bounds");
		return null;
	}
	
	//Get number of edges
	public int getNrEdges()
	{
		return edges.size();
	}

	//IsConnectedToNestNode
	public boolean hasEdgeToTarget(int targetNode)
	{
		Edge edge = getEdgeTargetNd(targetNode);
		if(edge!=null)
			return true;
		//else
		return false;
	}

	@Override
	public String toString() {
		return "Node [nodeIdx=" + nodeIdx + ", edges=" + edges + "]\n";
	}
}


