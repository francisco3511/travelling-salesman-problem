package project;
import java.util.Random;

public class ExpRandom {
	Random rand;

	public ExpRandom()
	{
		rand = new Random();
	}
	
	//Get random number
	public double getExpRandom(double m) 
	{
		double next = rand.nextDouble();
		return -m*Math.log(1.0-next);
	}

}
