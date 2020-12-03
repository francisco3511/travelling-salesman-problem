package project;

public class Move extends Event {
	Ant ant;
	int nextNode;
	ExpRandom rand;
	
	public Move(double _time, int _nextNode, PEC _pec, Ant _ant, Graph _graph, Parameters _parameters)
	{
		time = _time;
		nextNode = _nextNode;
		pec = _pec;
		ant = _ant;
		graph = _graph;
		parameters = _parameters;
		rand = new ExpRandom();
	}
	
	public double getTime()
	{
		return time;
	}
	
	public void simulateEvent()
	{
		//Move to Next node
		ant.moveNextNode(nextNode);
	
		double eventTime, pheromone, pherVar;
		Edge edge;
		int nextMoveNd;
		
		if(ant.isFinished() == true) //Check if it's finished
		{
			for(int i=0; i<ant.getPath().size(); i++)
			{		
				// Hamiltonian cycle found: lay pheromones
				edge = ant.getPath().get(i);
				if(edge.getPheromone() == 0) { // Only lay pheromones if the edge is empty (see FAQ)
					pherVar = ant.graph.totalWeight * parameters.getPLevel()/ant.getPathLength();
					pheromone = edge.getPheromone()+pherVar;
					edge.updatePheromone(pheromone);
			
					//Schedules Evaporation Event
					eventTime = time + rand.getExpRandom(parameters.getEta());
					if(eventTime <= pec.getFinalInst()) {
						if(!pec.addEvent(new Evaporation(eventTime, edge, pec, graph, parameters)))
							System.out.println("Impossible to add evaporation event.");
					}
				}
			}
			
			//Finished Cycle (Reset parameters)
			ant.finishedCycle();
		}
		
		//Decides nextNode move
		nextMoveNd = ant.decideNextNode();
	
		//Get corresponding edge
		edge = graph.getEdge(ant.getCurrentNode(), nextMoveNd);
		//Compute event time (weight of edge * delta)
		eventTime = time + rand.getExpRandom(edge.getWeight()*parameters.getDelta());
		
		//Schedules next Move
		if(eventTime<=pec.getFinalInst()) {
			if(!pec.addEvent(new Move(eventTime, nextMoveNd,pec, ant, graph, parameters)))
				System.out.println("Impossible to add move event.");
		}
	}

	@Override
	public String toString() {
		return "Move [antCurrNode=" + ant.getCurrentNode() + ", nextNode=" + nextNode + "]";
	}
	
	
}
