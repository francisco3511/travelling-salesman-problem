package project;

public abstract class Event{
	double time;
	PEC pec;
	Graph graph;
	Parameters parameters;
	
	public abstract double getTime();
	public abstract void simulateEvent();
	
}
