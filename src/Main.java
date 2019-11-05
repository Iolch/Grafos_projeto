
public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Graph graph = Graph.generateStandardGraph();
		CliqueIdentifier controller = new CliqueIdentifier(graph);
		
		System.out.println(controller.getUniverse().getEdges());
	}

}
