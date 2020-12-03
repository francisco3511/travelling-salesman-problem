package project;

public class Edge {
	private int sourceNode;
	private int targetNode;
	private int weight;
	private double pheromone;
	
	public Edge(int _sourceNode, int _targetNode, int _weight)
	{
		sourceNode = _sourceNode;
		targetNode = _targetNode;
		weight = _weight;
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public double getPheromone()
	{
		return pheromone;
	}
	
	public int getTargetNode()
	{
		return targetNode;
	}
	
	public int getSourceNode()
	{
		return sourceNode;
	}
	
	public void updatePheromone(double _pheromone)
	{
		pheromone = _pheromone;
	}

	@Override
	public String toString() {
		return "Edge [sourceNode=" + sourceNode + ", targetNode=" + targetNode + ", weight=" + weight + ", pheromone="
				+ pheromone + "]\n";
	}
}
