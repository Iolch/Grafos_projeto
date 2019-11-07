import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class CliqueIdentifier {
	private Graph universe;
	private ArrayList<Integer> current_clique = new ArrayList<Integer>();
	private ArrayList<Integer> maximum_clique = new ArrayList<Integer>();
	
	public CliqueIdentifier(Graph u) {
		this.universe = u;
	}
	public HashMap<Integer,ArrayList<Integer>> coloringGraph(Graph current_universe){
		HashMap<Integer,ArrayList<Integer>> colorset = new HashMap<Integer,ArrayList<Integer>>();
		
		int maxcolor = 1;
		int minclass = maximum_clique.size() - current_clique.size() + 1;
		if (minclass <= 0) minclass = 1;
		int lastindex = 0;
		
		colorset.put(1, new ArrayList<Integer>());
		colorset.put(2, new ArrayList<Integer>());
		
		for (Map.Entry<Integer,Integer> node : current_universe.getNodes().entrySet()) {
			
			int currentclass = 1;
			
			ArrayList<Integer> nodeadjac = getAdjacents(node.getKey());
			nodeadjac.retainAll(colorset.get(currentclass));
			while(!nodeadjac.isEmpty())	currentclass += 1;
			if(currentclass > maxcolor) {
				maxcolor = currentclass;
				colorset.put(maxcolor, new ArrayList<Integer>());
			}
			colorset.get(currentclass).add(node.getKey());
			//if(currentclass < minclass) {}
		}
		System.out.println("teste");
		colorset.put(lastindex, new ArrayList<Integer>());
		System.out.println(colorset);
		return colorset;
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
