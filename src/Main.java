import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Graph graph = Graph.generateStandardGraph();
		CliqueIdentifier controller = new CliqueIdentifier(graph,3,5,3);
		System.out.println(controller.getUniverse().getEdges());
		HashMap<Integer,ArrayList<Integer>> colorSet = controller.coloringGraph(controller.getUniverse());
		controller.maximalsIdentifier(controller.getUniverse(),colorSet,1);
		
	}

}
