
public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Graph graph = Graph.generateStandardGraph();
		CliqueIdentifier controller = new CliqueIdentifier(graph,3,5,2);
		System.out.println(controller.getUniverse().getEdges());
		controller.coloringGraph(graph);
		
	}

}
