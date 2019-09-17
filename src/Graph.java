import java.util.HashMap;
import java.util.ArrayList;
public class Graph{

	HashMap <String, Integer> nodes;
	HashMap <ArrayList<String>, Integer> edges;
	public Graph(HashMap <String, Integer> N, HashMap <ArrayList<String>, Integer> E) {
		this.nodes = N;
		this.edges = E;
	}
	public HashMap <ArrayList<String>, Integer> getEdges() {return edges;}
	public static Graph generateStandardGraph() 
	{
		HashMap <String, Integer> nodes = new HashMap<String, Integer>();
		HashMap <ArrayList<String>, Integer> edges = new HashMap<ArrayList<String>, Integer>();
		nodes.put("a", 0);
		nodes.put("b", 0);
		ArrayList<String> relations = new ArrayList<String>();
		relations.add("a"); 
		relations.add("b"); //[a, b]
		edges.put(relations, 8);
		
		Graph graph = new Graph(nodes, edges);
		return graph;	
	}

}
