import java.util.HashMap;
import java.util.ArrayList;
public class Graph{
	HashMap <Integer, Integer> nodes;
	HashMap <ArrayList<Integer>, Integer> edges;
	
	public Graph(HashMap <Integer, Integer> N, HashMap <ArrayList<Integer>, Integer> E) {
		this.nodes = N;
		this.edges = E;
	}
	
	public HashMap <Integer, Integer> getNodes() {return nodes;}
	public HashMap <ArrayList<Integer>, Integer> getEdges() {return edges;}
	
	public static Graph generateStandardGraph() 
	{
		HashMap <Integer, Integer> nodes = new HashMap<Integer, Integer>();
		HashMap <ArrayList<Integer>, Integer> edges = new HashMap<ArrayList<Integer>, Integer>();
	
		nodes.put(1, 0);
		nodes.put(2, 0);
		
		ArrayList<Integer> relations = new ArrayList<Integer>();
		
		relations.add(1); 
		relations.add(2); //[a, b]
		edges.put(relations, 20);
		
		
		Graph graph = new Graph(nodes, edges);
		return graph;	
	}


}
