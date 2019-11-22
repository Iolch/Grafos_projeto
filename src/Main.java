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
		Graph graph = FileReaderController.readFile("assets/johnson8-4-4.txt");
		
		Graph graphTester = new Graph(graph);
		int qntCLiques = 10;
		int minWeight = 1;
		int minQntNodes = 10;
		CliqueIdentifier controller = new CliqueIdentifier(graph,qntCLiques,minWeight,minQntNodes);
		
		System.out.println("Início Execução " + System.currentTimeMillis());
		System.out.println("Parâmetros Inciais - Quantidade Máxima Cliques = "+qntCLiques+ " - Peso Mínimo = "+ minWeight + " - Quantidade Mínima Nodes = "+ minQntNodes);
		HashMap<Integer,ArrayList<Integer>> colorSet = ColorSort.coloringGraph(controller.getUniverse().getNodes().keySet(),controller.getMaximumClique(),controller.getcurrentClique(),controller.getUniverse().getEdges());

		System.out.println("Início Execução Maximais " + System.currentTimeMillis());
		controller.maximalsIdentifier(controller.getUniverse().getNodes().keySet(),colorSet,1);
		
		System.out.println("Início Execução Filtro " + System.currentTimeMillis());
        controller.filterMaximals();
        
		System.out.println("MaximalsClique Final: "+controller.getMaximalsClique().size() +" - " +controller.getMaximalsClique());
        System.out.println("MaximumClique Final: "+controller.getMaximumClique());    
     
        // Inicia testes
        //System.out.println("Início Execução Testes " + System.currentTimeMillis());
       //tester(graphTester, controller.getMaximalsClique());
       
       System.out.println("Finaliza Execução " + System.currentTimeMillis());
       
	}
	public static boolean filterCLiquesAdjac(ArrayList<Integer> edge, Graph currentUniverse, ArrayList<Integer> maximumClique) {
		Set<Integer> edge0 = ColorSort.getAdjacents(edge.get(0), currentUniverse.getEdges());
		Set<Integer> edge1 = ColorSort.getAdjacents(edge.get(1), currentUniverse.getEdges());
		edge0.removeIf(node -> !edge1.contains(node));
		edge0.removeIf(node -> maximumClique.contains(node));
		return edge0.isEmpty();
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
 				
 				currentUniverse.getEdges().entrySet().removeIf(
 						edge -> filterCLiquesAdjac(edge.getKey(), new Graph(currentUniverse), tester.getMaximumClique())
 				);
 				//currentUniverse.getEdges().entrySet().removeIf(edge -> tester.getMaximumClique().contains(edge.getKey().get(0)) || tester.getMaximumClique().contains(edge.getKey().get(1)));
 			}
 		}
 		int cont = 0;
 		System.out.println("Konc e Janezic Maximals " +koncJanezicMaximals.size()+" - "+koncJanezicMaximals);
 		for(ArrayList<Integer> maximal : maximalsClique) {
 			for(ArrayList<Integer> kjmaximal : koncJanezicMaximals) {
 				ArrayList<Integer> aux = new ArrayList<Integer>(maximal);
 				aux.retainAll(kjmaximal);
 				if(aux.equals(maximal)) {
 					cont ++;
 					break;
 				}
 			}
 		}
 		System.out.println("Encontrou: " + cont + " de " + maximalsClique.size());
 	}
	

}
