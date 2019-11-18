import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ColorSort {
public static HashMap<Integer,ArrayList<Integer>> coloringGraph(Set<Integer> currentUniverseNodes,ArrayList<Integer>maximumClique,
		ArrayList<Integer>currentClique,HashMap <ArrayList<Integer>,Integer> edges){
		
		HashMap<Integer,ArrayList<Integer>> colorset = new HashMap<Integer,ArrayList<Integer>>();		
		int maxColor = 1;
		int minClass = maximumClique.size() - currentClique.size() + 1;
		if (minClass <= 0) minClass = 1;
		int lastIndex = 0;
		int currentClass = 1;
		colorset.put(1, new ArrayList<Integer>());
		colorset.put(2, new ArrayList<Integer>());
		for (Integer node : currentUniverseNodes) {
			
			currentClass = 1;
			
			Set<Integer> nodeadjac = getAdjacents(node,edges);
			Set<Integer> nodeadjac_aux = getAdjacents(node,edges);
			nodeadjac.retainAll(colorset.get(currentClass));
			while(!nodeadjac.isEmpty())	{
				currentClass += 1;
				nodeadjac = nodeadjac_aux;
				nodeadjac.retainAll(colorset.get(currentClass));
				
			}
			if(currentClass > maxColor) {
				maxColor = currentClass;
				colorset.put(maxColor+1, new ArrayList<Integer>());
			}
			
			colorset.get(currentClass).add(node);
		}
		colorset.put(lastIndex, new ArrayList<Integer>());
		
		colorset.entrySet().removeIf(c -> c.getValue().isEmpty());
		
		return colorset;
	}
public static Set<Integer> getAdjacents(int node,HashMap <ArrayList<Integer>,Integer> edges){
	
	Set<Integer> adjacents = new HashSet<Integer>();
	for(ArrayList<Integer> relation : edges.keySet()) {
		if(relation.contains(node)) {
			int idx = (relation.indexOf(node)!=0) ? 0 : 1;
			adjacents.add(relation.get(idx));
		}
	}
	//System.out.println("adjacents : "+" node "+ node+" "+adjacents);
	return adjacents;
}
}
