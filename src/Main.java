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
		Graph graph = FileReaderController.readFile("assets/p_hat300_1.txt");
		
		Graph graphTester = new Graph(graph);
		int qntCLiques = 5;
		int minWeight = 1;
		int minQntNodes = 8;
		CliqueIdentifier controller = new CliqueIdentifier(graph,qntCLiques,minWeight,minQntNodes);
		
		System.out.println("Início Execução " + System.currentTimeMillis());
		System.out.println("Parâmetros Inciais - Quantidade Máxima Cliques = "+qntCLiques+ " - Peso Mínimo = "+ minWeight + " - Quantidade Mínima Nodes = "+ minQntNodes);
		//System.out.println("MaxQtnCliques: "+controller.getMaxQtnCliques() +" MinWeight: " +controller.getMinWeight()+" MinQtnNodes: " +controller.getMinQtnNodes());
		//System.out.println(controller.getUniverse().getEdges());
		HashMap<Integer,ArrayList<Integer>> colorSet = ColorSort.coloringGraph(controller.getUniverse().getNodes().keySet(),controller.getMaximumClique(),controller.getcurrentClique(),controller.getUniverse().getEdges());

		System.out.println("Início Execução Maximais " + System.currentTimeMillis());
		controller.maximalsIdentifier(controller.getUniverse().getNodes().keySet(),colorSet,1);

		System.out.println("Início Execução Filtro " + System.currentTimeMillis());
        controller.filterMaximals();
        
		System.out.println("MaximalsClique Final: "+controller.getMaximalsClique());
        System.out.println("MaximumClique Final: "+controller.getMaximumClique());    
     
        // Inicia testes
       System.out.println("Início Execução Testes " + System.currentTimeMillis());
       tester(graphTester, controller.getMaximalsClique());
       
       System.out.println("Finaliza Execução " + System.currentTimeMillis());
       
	}
	 public static void tester(Graph currentUniverse, ArrayList<ArrayList<Integer>> maximalsClique) {
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
 		Boolean testSuccess = true;
 		System.out.println("Konc e Janezic Maximals" + koncJanezicMaximals);
 		for(ArrayList<Integer> maximal : maximalsClique) {
 			testSuccess = koncJanezicMaximals.contains(maximal);
 			if(!testSuccess) {break;}
 		}
 		System.out.println("Resultado: " + testSuccess);
 	}
	

}
