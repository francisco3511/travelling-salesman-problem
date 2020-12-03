package project;

public class Simulation {
	double finalInst;
	int antColSize;
	double pLevel;
	Parameters parameters; //Parameters
	Graph graph; //Graph
	Event currentEvent; //Current event
	double currentTime; //Current Time
	Ant[] antColony; 
	PEC pec;  //PEC
	ExpRandom rand; //Random number generator
	
	//Constructor
	public Simulation (double _finalInst, int _antColSize, double _pLevel)
	{
		//Set 3 main parameters
		finalInst = _finalInst;
		antColSize = _antColSize;
		pLevel = _pLevel;
		antColony = new Ant[antColSize]; //Create ant colony
		rand = new ExpRandom(); //Random number generator
		pec = new PEC(_finalInst); //Create PEC object
	}
	
	// Create Graph
	public void createGraph(int nbNodes, int nestNode)
	{
		graph = new Graph(nbNodes, nestNode);
	}
	
	// Set move parameters
	public void setParameters (double alpha, double beta, double delta, double eta, double rho, double pLevel)
	{
		parameters = new Parameters(alpha, beta, delta, eta, rho, pLevel);
	}
	
	public void printGraph()
	{
		System.out.println(graph.toString());
	}
	
	// Initialize antColony
	public void initAntColony()
	{
		for(int i=0; i<antColSize; i++)
			antColony[i] = new Ant(graph.nbNodes,graph.nestNode,graph, parameters);
		
	}
	
	// Begin simulation
	public void beginSimulation()
	{
		// Schedules an initial move for each ant
		int i;
		double eventTime;
		for(i=0;i<antColSize;i++)
		{
			//Make ant next node decision
			int nextNode = antColony[i].decideNextNode();
			//Get corresponding edge
			Edge edge = graph.getEdge(antColony[i].getCurrentNode(), nextNode);
			//Compute event time (weight of edge * delta)
			eventTime = rand.getExpRandom(edge.getWeight()*parameters.getDelta());
			//Schedule move event
			if(eventTime <= pec.getFinalInst()) {
				if(!pec.addEvent(new Move(eventTime, nextNode, pec, antColony[i], graph, parameters)))
					System.out.println("Impossible to add move event.");
			}
		}
		
		//Schedules 20 observation events
		for(i=1;i<=20;i++)
		{
			//Schedules single observation event
			eventTime = (i*(finalInst/20));
			if(!pec.addEvent(new Observation(eventTime, i, pec, graph, parameters)))
				System.out.println("Impossible to add observation event.");
		}
	}
	
}
