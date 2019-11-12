import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class CliqueIdentifier {
	private Graph universe;
	private ArrayList<Integer> currentClique = new ArrayList<Integer>();
	private ArrayList<Integer> maximumClique = new ArrayList<Integer>();
	private ArrayList<Graph> maximalsClique = new ArrayList<Graph>();
	private HashMap<Integer,Integer> s = new HashMap<Integer,Integer>();
	private HashMap<Integer,Integer> sOld = new HashMap<Integer,Integer>();

	private int maxQtnCliques;
	private int minWeight;
	private int minQtnNodes;
	private int allSteps = 1;

	private double Tlimit = 0.002;

	public CliqueIdentifier(Graph u, int mqc,int mw,int mqn ) {
		this.universe = u;
		this.maxQtnCliques = mqc;
		this.minWeight = mw;
		this.minQtnNodes = mqn;
		
		// VAMOS RETIRAR TODAS AS ARESTAS COM PESO MENOR QUE minWeight E RETIRAR OS V�RTICES DESCONEXOS
		removeBottomEdges();
		removeDisconnectedNodes();
		this.s.put(0,0);
		this.s.put(1,0);
		this.sOld.put(0,0);
		this.sOld.put(1,0);
	}
	

	public HashMap<Integer,ArrayList<Integer>> coloringGraph(Set<Integer> currentUniverseNodes){
		
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
			
			Set<Integer> nodeadjac = getAdjacents(node);
			Set<Integer> nodeadjac_aux = getAdjacents(node);
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
	

	public void maximalsIdentifier(Set <Integer> currentUniverse,HashMap<Integer,ArrayList<Integer>> colorSet, int level ) {
		if(!this.s.containsKey(level))	this.s.put(level, 0);
		if(!this.sOld.containsKey(level))this.sOld.put(level, 0);
		

		this.s.put(level,this.s.get(level)+this.s.get(level-1) - this.sOld.get(level));
		
		while(!currentUniverse.isEmpty())
		{
			ArrayList<Integer> maximumClass = colorSet.get(colorSet.size());
			int maximumColor = (int) colorSet.keySet().toArray()[colorSet.size()-1];
			int choosenNode  = maximumClass.get(maximumClass.size()-1);
			currentUniverse.remove(choosenNode);
	
			if(currentClique.size()+ maximumColor > maximumClique.size() )
			{
				currentClique.add(choosenNode);
				Set<Integer> nodeAdjac = getAdjacents(choosenNode);
				Set<Integer> intersection = new HashSet<Integer>(currentUniverse);
				intersection.retainAll(nodeAdjac);
				//System.out.println(choosenNode+" cc "+ currentUniverse + " adj  "+ nodeAdjac + " itr "+ intersection);
				
				if(!intersection.isEmpty()) {
					if(this.s.get(level)/this.allSteps < this.Tlimit)
					{
						
					}
					colorSet = coloringGraph(intersection);
					this.s.put(level,this.s.get(level)+1);
						
					maximalsIdentifier(intersection,colorSet,level+1);
					
				}
				else if(currentClique.size()> maximumClique.size())
				{
					maximumClique = currentClique;
					System.out.println("maximum" + maximumClique);
				}
				
				currentClique.removeIf(node -> node == choosenNode);
				
				
			}
			else
			{
				break;
			}
			
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
	

	public Set<Integer> getAdjacents(int node){
		
		Set<Integer> adjacents = new HashSet<Integer>();
		for(ArrayList<Integer> relation : this.universe.getEdges().keySet()) {
			if(relation.contains(node)) {
				int idx = (relation.indexOf(node)!=0) ? 0 : 1;
				adjacents.add(relation.get(idx));
			}
		}
		//System.out.println("adjacents : "+" node "+ node+" "+adjacents);
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
