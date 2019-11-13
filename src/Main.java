import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Graph graph = new Graph();
		
		CliqueIdentifier controller = new CliqueIdentifier(graph,3,5,2);
		System.out.println(controller.getUniverse().getEdges());
		HashMap<Integer,ArrayList<Integer>> colorSet = controller.coloringGraph(controller.getUniverse().getNodes().keySet());
		controller.maximalsIdentifier(controller.getUniverse().getNodes().keySet(),colorSet,1);

		
	}
	

}
