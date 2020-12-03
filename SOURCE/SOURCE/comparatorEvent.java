package project;
import java.util.Comparator;

public class comparatorEvent implements Comparator<Event>{
	@Override
	public int compare(Event e1, Event e2)
	{
		Double e1_time, e2_time;
		e1_time = (Double) e1.getTime();
		e2_time = (Double) e2.getTime();
		
		return e1_time.compareTo(e2_time);
	}
}
