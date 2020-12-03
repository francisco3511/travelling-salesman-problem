package project;

public class Observation extends Event {
	int obsNumber;
	public Observation(double _time, int _obsNumber, PEC _pec, Graph _graph, Parameters _parameters)
	{
		obsNumber = _obsNumber;
		time = _time;
		pec = _pec;
		graph = _graph;
		parameters = _parameters;
	}
	
	public double getTime()
	{
		return time;
	}
	
	public void simulateEvent()
	{
		//simulate
		System.out.println("Observation " + obsNumber+":");
		System.out.println("\t Present instant:\t\t "+time);
		System.out.println("\t Number of move events:\t\t "+pec.getMEvents());
		System.out.println("\t Number of evaporation events:\t "+pec.getEEvents());
		if(graph.cycleWeight == -1)
			System.out.println("\t Hamiltonian cycle:\t\t ");
		else System.out.println("\t Hamiltonian cycle:\t\t "+ graph.stringCycle());
	}

	@Override
	public String toString() {
		return "Observation [obsNumber=" + obsNumber + "]";
	}
}
