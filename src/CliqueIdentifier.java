import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class CliqueIdentifier {
	private Graph universe;
	private ArrayList<Integer> currentClique = new ArrayList<Integer>();
	private ArrayList<Integer> maximumClique = new ArrayList<Integer>();
	private ArrayList<Graph> maximalsClique = new ArrayList<Graph>();
	private ArrayList<Integer> s = new ArrayList<Integer>(Collections.nCopies(2, 0));
	private ArrayList<Integer> sOld = new ArrayList<Integer>(Collections.nCopies(2, 0));

	private int maxQtnCliques;
	private int minWeight;
	private int minQtnNodes;
	private int allSteps = 0;

	private double Tlimit = 0.002;

	public CliqueIdentifier(Graph u, int mqc,int mw,int mqn ) {
		this.universe = u;
		this.maxQtnCliques = mqc;
		this.minWeight = mw;
		this.minQtnNodes = mqn;
		
		// VAMOS RETIRAR TODAS AS ARESTAS COM PESO MENOR QUE minWeight E RETIRAR OS VÉRTICES DESCONEXOS
		removeBottomEdges();
		removeDisconnectedNodes();
		System.out.println(s);
	}
	public HashMap<Integer,ArrayList<Integer>> coloringGraph(Graph currentUniverse){
		HashMap<Integer,ArrayList<Integer>> colorset = new HashMap<Integer,ArrayList<Integer>>();
		
		int maxColor = 1;
		int minClass = maximumClique.size() - currentClique.size() + 1;
		if (minClass <= 0) minClass = 1;
		int lastIndex = 0;
		int currentClass = 1;
		colorset.put(1, new ArrayList<Integer>());
		colorset.put(2, new ArrayList<Integer>());
		for (Map.Entry<Integer,Integer> node : currentUniverse.getNodes().entrySet()) {
			
			currentClass = 1;
			
			ArrayList<Integer> nodeadjac = getAdjacents(node.getKey());
			ArrayList<Integer> nodeadjac_aux = getAdjacents(node.getKey());
			//System.out.println("adj"+getAdjacents(node.getKey()));
			nodeadjac.retainAll(colorset.get(currentClass));
			//System.out.println("antes"+colorset+"ponto atual "+node);
			while(!nodeadjac.isEmpty())	{
				currentClass += 1;
				nodeadjac = nodeadjac_aux;
				nodeadjac.retainAll(colorset.get(currentClass));
				
			}
			if(currentClass > maxColor) {
				maxColor = currentClass;
				colorset.put(maxColor+1, new ArrayList<Integer>());
			}
			
			
			//System.out.println("em "++ " adiciona "+node.getKey());
			colorset.get(currentClass).add(node.getKey());
			//System.out.println("depois"+colorset.get(3));
			
		}
		//System.out.println("teste");
		colorset.put(lastIndex, new ArrayList<Integer>());
		
		colorset.entrySet().removeIf(c -> c.getValue().isEmpty());
		//System.out.println(colorset);
		
		return colorset;
	}
	public void maximalsIdentifier(Graph currentUniverse,HashMap<Integer,ArrayList<Integer>> colorSet, int level ) {
		s.set(level, s.get(level)+s.get(level-1) - sOld.get(level));
		sOld.set(level,s.get(level-1));
		while(!currentUniverse.getNodes().isEmpty())
		{
			ArrayList<Integer> maximumClass = colorSet.get(colorSet.size());
			int maximumColor = (int) colorSet.keySet().toArray()[colorSet.size()-1];
			int choosenNode  = maximumClass.get(maximumClass.size()-1);
			currentUniverse.getNodes().remove(choosenNode);
	
			if(currentClique.size()+ maximumColor > maximumClique.size())
			{
				currentClique.add(choosenNode);
				ArrayList<Integer> nodeAdjac = getAdjacents(choosenNode);
				nodeAdjac.retainAll(currentUniverse.getNodes().keySet());
				System.out.println(currentUniverse.getNodes().keySet());
				if(!nodeAdjac.isEmpty()) {
					if(s.get(level)/allSteps < Tlimit)
					{
						
					}
					colorSet = coloringGraph(currentUniverse);
					maximalsIdentifier(currentUniverse,colorSet,level+1);
					s.set(level,s.get(level)+1);
				}
				else if(currentClique.size()> maximumClique.size()) maximumClique = currentClique;
				
				currentClique.remove(choosenNode);
				
			}
			else break;
			
		}
	
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
		//System.out.println(this.universe.getNodes());
	}
	
	public void removeBottomEdges()
	{
		this.universe.getEdges().entrySet().removeIf(edge -> edge.getValue() <  this.minWeight);
		//System.out.println(this.universe.getEdges());
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
	public ArrayList<Integer> getcurrentClique() {
		return currentClique;
	}
	public ArrayList<Integer> getmaximumClique() {
		return maximumClique;
	}
	public Graph getUniverse() {
		return universe;
	}
}
