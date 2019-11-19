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
		//Graph graph = new Graph();
		//graph.generateStandardGraph();
		Graph graph = FileReaderController.readFile("assets/c-fat200-1.txt");
		
		Graph graphTester = new Graph(graph);
		int qntCLiques = 5;
		int minWeight = 1;
		int minQntNodes = 12;
		CliqueIdentifier controller = new CliqueIdentifier(graph,qntCLiques,minWeight,minQntNodes);
		
		System.out.println("In�cio Execu��o " + System.currentTimeMillis());
		System.out.println("Par�metros Inciais - Quantidade M�xima Cliques = "+qntCLiques+ " - Peso M�nimo = "+ minWeight + " - Quantidade M�nima Nodes = "+ minQntNodes);
		HashMap<Integer,ArrayList<Integer>> colorSet = ColorSort.coloringGraph(controller.getUniverse().getNodes().keySet(),controller.getMaximumClique(),controller.getcurrentClique(),controller.getUniverse().getEdges());

		System.out.println("In�cio Execu��o Maximais " + System.currentTimeMillis());
		controller.maximalsIdentifier(controller.getUniverse().getNodes().keySet(),colorSet,1);

		System.out.println("In�cio Execu��o Filtro " + System.currentTimeMillis());
        controller.filterMaximals();
        
		System.out.println("MaximalsClique Final: "+controller.getMaximalsClique());
        System.out.println("MaximumClique Final: "+controller.getMaximumClique());    
     
        // Inicia testes
       System.out.println("In�cio Execu��o Testes " + System.currentTimeMillis());
       tester(graphTester, controller.getMaximalsClique());
       
       System.out.println("Finaliza Execu��o " + System.currentTimeMillis());
       
	}
	public static ArrayList<Integer> getNodesWithAdjacents(Graph currentUniverse){
		ArrayList<Integer> nodes = new ArrayList<Integer>();
		for(int node : currentUniverse.getNodes().keySet()) {
			Set<Integer> aux = ColorSort.getAdjacents(node, currentUniverse.getEdges());
			if(!aux.isEmpty()) nodes.add(node);
		}
		return nodes;
	}
	 public static void tester(Graph currentUniverse, ArrayList<ArrayList<Integer>> maximalsClique) {
		ArrayList<ArrayList<Integer>> koncJanezicMaximals = new ArrayList<ArrayList<Integer>>();
		
		while(!getNodesWithAdjacents(currentUniverse).isEmpty()) {
 			Tester tester = new Tester(currentUniverse);
 			
 			HashMap<Integer,ArrayList<Integer>> colorSet = ColorSort.coloringGraph(new HashSet<Integer>(getNodesWithAdjacents(currentUniverse)),tester.getMaximumClique(), tester.getCurrentClique(), currentUniverse.getEdges());
 			
 			tester.konkjenezic(new HashSet<Integer>(getNodesWithAdjacents(currentUniverse)),colorSet,1);
 			
 			koncJanezicMaximals.add(new ArrayList<Integer>(tester.getMaximumClique()));
 			
 			for(int node : tester.getMaximumClique()) {
 				currentUniverse.getEdges().entrySet().removeIf(edge -> tester.getMaximumClique().contains(edge.getKey().get(0)) || tester.getMaximumClique().contains(edge.getKey().get(1)));
 			}
 		}
 		Boolean testSuccess = true;
 		System.out.println("Konc e Janezic Maximals" + koncJanezicMaximals);
 		for(ArrayList<Integer> maximal : maximalsClique) {
 			Boolean innerTest = false;
 			for(ArrayList<Integer> kjmaximal : koncJanezicMaximals) {
 				ArrayList<Integer> aux = new ArrayList<Integer>(maximal);
 				aux.retainAll(kjmaximal);
 				if(aux.equals(maximal)) {
 					innerTest = true;
 					break;
 				}
 			}
 			testSuccess = innerTest;
 			if(!testSuccess) break;
 		}
 		System.out.println("Resultado: " + testSuccess);
 	}
	

}
