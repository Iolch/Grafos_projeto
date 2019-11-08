import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class CliqueIdentifier {
	private Graph universe;
	private ArrayList<Integer> current_clique = new ArrayList<Integer>();
	private ArrayList<Integer> maximum_clique = new ArrayList<Integer>();
	private int maxQtnCliques;
	private int minWeight;
	private int minQtnNodes;
	
	public CliqueIdentifier(Graph u, int mqc,int mw,int mqn ) {
		this.universe = u;
		this.maxQtnCliques = mqc;
		this.minWeight = mw;
		this.minQtnNodes = mqn;
		
		// VAMOS RETIRAR TODAS AS ARESTAS COM PESO MENOR QUE minWeight E RETIRAR OS VÉRTICES DESCONEXOS
		removeBottonEdges();
		removeDisconnectedNodes();
		System.out.println(this.universe.getNodes());
	}
	public HashMap<Integer,ArrayList<Integer>> coloringGraph(Graph current_universe){
		HashMap<Integer,ArrayList<Integer>> colorset = new HashMap<Integer,ArrayList<Integer>>();
		
		int maxcolor = 1;
		int minclass = maximum_clique.size() - current_clique.size() + 1;
		if (minclass <= 0) minclass = 1;
		int lastindex = 0;
		int currentclass = 1;
		colorset.put(1, new ArrayList<Integer>());
		colorset.put(2, new ArrayList<Integer>());
		
		for (Map.Entry<Integer,Integer> node : current_universe.getNodes().entrySet()) {
			
			currentclass = 1;
			
			ArrayList<Integer> nodeadjac = getAdjacents(node.getKey());
			ArrayList<Integer> nodeadjac_aux = getAdjacents(node.getKey());
			//System.out.println("adj"+getAdjacents(node.getKey()));
			nodeadjac.retainAll(colorset.get(currentclass));
			//System.out.println("antes"+colorset+"ponto atual "+node);
			while(!nodeadjac.isEmpty())	{
				currentclass += 1;
				nodeadjac = nodeadjac_aux;
				nodeadjac.retainAll(colorset.get(currentclass));
				
			}
			if(currentclass > maxcolor) {
				maxcolor = currentclass;
				colorset.put(maxcolor+1, new ArrayList<Integer>());
			}
			
			
			//System.out.println("em "+currentclass+ " adiciona "+node.getKey());
			colorset.get(currentclass).add(node.getKey());
			//System.out.println("depois"+colorset.get(3));
			
		}
		//System.out.println("teste");
		colorset.put(lastindex, new ArrayList<Integer>());
		//System.out.println(colorset);
		
				
		
		
		return colorset;
	}
	public void maximalsIdentifier() {
		
	
	}
	public ArrayList<Integer> removeDuplicate(ArrayList<Integer> list){
		Set<Integer> set = new LinkedHashSet<Integer>();
		set.addAll(list);
		list.clear();
		list.addAll(set);
		
		return list;	
	}
	public void removeDisconnectedNodes() {
		ArrayList<Integer> connectedNodes = new ArrayList<Integer>();
		for(ArrayList<Integer> relation : this.universe.getEdges().keySet()) 
		{
			
			connectedNodes.addAll(relation);
		}
		connectedNodes = removeDuplicate(connectedNodes);
		this.universe.getNodes().keySet().retainAll(connectedNodes);
		System.out.println(this.universe.getNodes());
	}
	
	public void removeBottonEdges()
	{
		this.universe.getEdges().entrySet().removeIf(edge -> edge.getValue() <  this.minWeight);
		System.out.println(this.universe.getEdges());
	}
	public ArrayList<Integer> getAdjacents(int node){
		ArrayList<Integer> adjacents = new ArrayList<Integer>();
		for(ArrayList<Integer> relation : this.universe.getEdges().keySet()) {
			if(relation.contains(node)) {
				int idx = (relation.indexOf(node)!=0) ? 0 : 1;
				adjacents.add(relation.get(idx));
			}
		}
		return adjacents;
	}
	public ArrayList<Integer> getCurrent_clique() {
		return current_clique;
	}
	public ArrayList<Integer> getMaximum_clique() {
		return maximum_clique;
	}
	public Graph getUniverse() {
		return universe;
	}
}
