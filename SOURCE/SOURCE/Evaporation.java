package project;

public class Evaporation extends Event {
	Edge edge;
	ExpRandom rand;
	
	public Evaporation(double _time, Edge _edge, PEC _pec, Graph _graph, Parameters _parameters)
	{
		time = _time;
		edge = _edge;
		pec = _pec;
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
		//Update number of pheromones according to evaporation rules
		double pheromone = edge.getPheromone() - parameters.getRho();
		
		// Make sure pheromone level doesn't go below zero
		if(pheromone < 0)
			pheromone = 0;
		
		// Push update
		edge.updatePheromone(pheromone);
		
		if(edge.getPheromone() > 0)
		{
			//Schedules Evaporation Event
			double eventTime = time + rand.getExpRandom(parameters.getEta());
			
			if(eventTime <= pec.getFinalInst()) {
				if(!pec.addEvent(new Evaporation(eventTime, edge, pec, graph, parameters)))
					System.out.println("Impossible to add evaporation event.");
			}
		}
	}

	@Override
	public String toString() {
		return "Evaporation [edge=" + edge + ", rand=" + rand + "]";
	}
}
