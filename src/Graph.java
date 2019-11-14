import java.util.HashMap;
import java.util.ArrayList;
public class Graph{
	HashMap <Integer, Integer> nodes = new HashMap <Integer, Integer>();
	HashMap <ArrayList<Integer>, Integer> edges = new HashMap <ArrayList<Integer>, Integer>();
	
	public Graph(HashMap <Integer, Integer> N, HashMap <ArrayList<Integer>, Integer> E) {
		this.nodes = N;
		this.edges = E;
	}
	public Graph() {
		this.generateStandardGraph();
	}
	
	public HashMap <Integer, Integer> getNodes() {return nodes;}
	public HashMap <ArrayList<Integer>, Integer> getEdges() {return edges;}
	
	public void addNode(int idx) {
		if(!this.nodes.containsKey(idx)) this.nodes.put(idx, 1);
	}
	public void addEdge(int idx1, int idx2, int w) {
		if(this.getNodes().containsKey(idx1) && this.getNodes().containsKey(idx2)) {
			ArrayList<Integer> relations = new ArrayList<Integer>();
			relations.add(idx1); 
			relations.add(idx2); //[a, b]
			edges.put(relations, w);
		}	
	}
	public void generateStandardGraph() 
	{
		this.addNode(1);
		this.addNode(2);
		this.addNode(3);
		this.addNode(4);
		this.addNode(5);
		this.addNode(6);
		this.addNode(7);
		this.addNode(8);
		this.addNode(9);
		this.addNode(10);
		
		this.addEdge(1, 3, 4);
		this.addEdge(2, 4, 24);
		this.addEdge(2, 5, 25);
		this.addEdge(3, 4, 29);
		this.addEdge(3, 6, 8);
		this.addEdge(3, 7, 26);
		this.addEdge(3, 9, 36);
		this.addEdge(4, 6, 13);
		this.addEdge(4, 7, 339);
		this.addEdge(4, 8, 17);
		this.addEdge(4, 9, 1);
		this.addEdge(5, 7, 13);
		this.addEdge(5, 8, 15);
		this.addEdge(6, 7, 22);
		this.addEdge(6, 9, 12);
		this.addEdge(7, 8, 9);
		this.addEdge(7, 9, 55);
		this.addEdge(7, 10, 21);
		
	}


}
