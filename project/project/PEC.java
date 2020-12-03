package project;
import java.util.PriorityQueue;

public class PEC {
	double finalInst;
	int eEvents;
	int mEvents;
	PriorityQueue<Event> events;
	
	//Constructor
	public PEC(double _finalInst)
	{
		finalInst = _finalInst;
		//PriorityQueue uses a comparator defined to sort events by time in ascending order and allows for duplicates
		events = new PriorityQueue<Event>(new comparatorEvent());
		eEvents = 0;
		mEvents = 0;
	}
	
	//Get final instant
	public double getFinalInst()
	{
		return finalInst;
	}

	public boolean addEvaporation(Evaporation evap)
	{
		if(events.add(evap))
		{
			++eEvents;
			return true;
		}
		else
			return false;
	}

	public boolean addMove(Move move)
	{
		if(events.add(move))
		{
			++mEvents;
			return true;
		}
		else
			return false;
	}
	
	//Add event
	public boolean addEvent(Event e)
	{
		if(e instanceof Evaporation){
			return addEvaporation((Evaporation)e);
		}
		if(e instanceof Move){
			return addMove((Move)e);
		}
		if(e instanceof Observation){
			return events.add(e);
		}
		else {
			return false;
		}
	}
	
	//Get event
	public Event getNextEvent()
	{
		Event next = events.peek();
		events.remove(next);
		return next;
	}
	
	public int getMEvents()
	{
		return mEvents;
	}
	
	public int getEEvents()
	{
		return eEvents;
	}
	
	//Is empty
	public boolean isEmpty()
	{
		return events.isEmpty();
	}

	@Override
	public String toString() {
		return "PEC [finalInst=" + finalInst + ", eEvents=" + eEvents + ", mEvents=" + mEvents + ", events=" + events
				+ "]";
	}
	
}
