import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Graph graph = new Graph();
		graph.generateStandardGraph();
		//Graph graph = FileReaderController.readFile("assets/p_hat300_1.txt");
		
		Graph graphTester = new Graph(graph);
		System.out.println("dess1"+graphTester.getNodes());
		CliqueIdentifier controller = new CliqueIdentifier(graph,3,1,3);
		System.out.println("MaxQtnCliques: "+controller.getMaxQtnCliques() +" MinWeight: " +controller.getMinWeight()+" MinQtnNodes: " +controller.getMinQtnNodes());
		System.out.println(controller.getUniverse().getEdges());
		HashMap<Integer,ArrayList<Integer>> colorSet = ColorSort.coloringGraph(controller.getUniverse().getNodes().keySet(),controller.getMaximumClique(),controller.getcurrentClique(),controller.getUniverse().getEdges());
		controller.maximalsIdentifier(controller.getUniverse().getNodes().keySet(),colorSet,1);
        controller.filterMaximals();
		System.out.println("MaximalsClique Final: "+controller.getMaximalsClique());
        System.out.println("MaximumClique Final: "+controller.getMaximumClique());    
     
        // Inicia testes
        System.out.println("dess"+graphTester.getNodes());
       tester(graphTester);
       
	}
	 public static void tester(Graph currentUniverse) {
		ArrayList<ArrayList<Integer>> koncJanezicMaximals = new ArrayList<ArrayList<Integer>>();
 		while(!currentUniverse.getNodes().keySet().isEmpty()) {
 			Tester tester = new Tester(currentUniverse);
 			HashMap<Integer,ArrayList<Integer>> colorSet = ColorSort.coloringGraph(currentUniverse.getNodes().keySet(),tester.getMaximumClique(), tester.getCurrentClique(), currentUniverse.getEdges());
 			tester.konkjenezic(currentUniverse.getNodes().keySet(),colorSet,1);
 			koncJanezicMaximals.add(new ArrayList<Integer>(tester.getMaximumClique()));
 			for(int node : tester.getMaximumClique()) {
 				currentUniverse.getNodes().remove(node);
 			}
 		}
 		System.out.println("Konc e Janezic" + koncJanezicMaximals);
 	}
	

}
