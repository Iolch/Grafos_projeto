import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		Graph graph = new Graph();
		CliqueIdentifier controller = new CliqueIdentifier(graph,3,1,3);
		System.out.println("MaxQtnCliques: "+controller.getMaxQtnCliques() +" MinWeight: " +controller.getMinWeight()+" MinQtnNodes: " +controller.getMinQtnNodes());
		System.out.println(controller.getUniverse().getEdges());
		HashMap<Integer,ArrayList<Integer>> colorSet = controller.coloringGraph(controller.getUniverse().getNodes().keySet());
		controller.maximalsIdentifier(controller.getUniverse().getNodes().keySet(),colorSet,1);
        controller.filterMaximals();
		System.out.println("MaximalsClique Final: "+controller.getMaximalsClique());
        System.out.println("MaximumClique Final: "+controller.getMaximumClique());
        
        
      
        
        
	}
	

}
