package project;
import org.xml.sax.*; 
import org.xml.sax.helpers.*; 

public class UserHandler extends DefaultHandler{
	Simulation sim = null;
	double finalInst, alpha, beta, delta, eta, rho, pLevel;
	int antColSize, nbNodes, nestNode, nodeidx, targetnode;
	
	boolean bGraph = false;
	boolean bWeight = false;
	boolean bEvent = false;
	
	public Simulation getSimulation() {
		return sim;
	}
	
	@Override
	public void startElement(String uri, String name, String tag, Attributes attributes){
		if (tag.equals("simulation")) {
			finalInst = Double.parseDouble(attributes.getValue("finalinst"));
	        antColSize = Integer.parseInt(attributes.getValue("antcolsize"));
	        pLevel = Double.parseDouble(attributes.getValue("plevel"));
	        sim = new Simulation(finalInst, antColSize, pLevel);
		} else if (tag.equals("graph")) {
			nbNodes = Integer.parseInt(attributes.getValue("nbnodes"));
	        nestNode = Integer.parseInt(attributes.getValue("nestnode"));
	        sim.createGraph(nbNodes, nestNode-1);
	        bGraph = true;
		} else if (tag.equals("node")) {
			nodeidx = Integer.parseInt(attributes.getValue("nodeidx"));
	    } else if (tag.equalsIgnoreCase("weight")) {
	    	targetnode = Integer.parseInt(attributes.getValue("targetnode"));
	    	bWeight = true;
	    } else if (tag.equalsIgnoreCase("move")) {
	    	alpha = Double.parseDouble(attributes.getValue("alpha"));
	    	beta = Double.parseDouble(attributes.getValue("beta"));
	    	delta = Double.parseDouble(attributes.getValue("delta"));
	    } else if (tag.equalsIgnoreCase("evaporation")) {
	    	eta = Double.parseDouble(attributes.getValue("eta"));
	    	rho = Double.parseDouble(attributes.getValue("rho"));
	    	bEvent = true;
	    } 
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (bEvent) {
			sim.setParameters(alpha, beta, delta, eta, rho, pLevel);
			bEvent = false;
		} 
	}
	
	public void characters(char[]ch, int start, int length){
		if(bWeight) {
			int weight = Integer.parseInt(new String(ch,start,length));
			sim.graph.setNodeEdges(nodeidx - 1, targetnode - 1, weight);
			bWeight = false;
		}
	}
}
