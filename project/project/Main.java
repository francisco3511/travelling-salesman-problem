package project;

import java.io.File;
import javax.xml.parsers.*; // SAX parser

public class Main{
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		File inputFile = new File("./TESTS/" + args[0]); // Read from command line arguments
		SAXParserFactory fact = SAXParserFactory.newInstance();
		fact.setValidating(true);
	
		// Builds the SAX parser	
		SAXParser saxParser = fact.newSAXParser();
		// Parse the XML document with this handler
		UserHandler handler = new UserHandler();
		saxParser.parse(inputFile, handler);
		Simulation sim = handler.getSimulation();
		
		sim.initAntColony();
		sim.beginSimulation(); //Begin simulation (Launch initial moves and observation events)

		//Get first event and its corresponding time
		sim.currentEvent = sim.pec.getNextEvent();
		
		if(sim.currentEvent == null)
		{
			System.out.println("Empty simulation.\n");
			System.exit(-1);
		}
		sim.currentTime = sim.currentEvent.getTime();

		// Main Simulation Cycle
		while (sim.currentTime <= sim.finalInst)
		{
			sim.currentEvent.simulateEvent();
			
			if(sim.pec.isEmpty()==true)
				break;
			
			sim.currentEvent = sim.pec.getNextEvent();
			sim.currentTime = sim.currentEvent.getTime();
		}
		
	}
	
}
