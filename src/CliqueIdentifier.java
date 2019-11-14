import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private Set<ArrayList<Integer>> maximalsClique = new HashSet<ArrayList<Integer>>();
	private HashMap<Integer,Integer> s = new HashMap<Integer,Integer>();
	private HashMap<Integer,Integer> sOld = new HashMap<Integer,Integer>();

	private int maxQtnCliques;
	private int minWeight;
	private int minQtnNodes;
	private int allSteps = 1;

	private double Tlimit = 0.002;

	public CliqueIdentifier(Graph u, int mqc,int mw,int mqn ) {
		this.universe = u;
		this.setMaxQtnCliques(mqc);
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
		System.out.println("------------ level "+level+" -----------");
		System.out.println("currentClique inicio da exeucu��o: "+currentClique);
		System.out.println("currentUniverse inicio da execu��o: "+currentUniverse);
		if(!this.s.containsKey(level))	this.s.put(level, 0);
		if(!this.sOld.containsKey(level))this.sOld.put(level, 0);
		
		this.s.put(level,this.s.get(level)+this.s.get(level-1) - this.sOld.get(level));
		for(ArrayList<Integer> colorClass: colorSet.values())
		{			
			while(!currentUniverse.isEmpty())
			{			
				System.out.println("colorClass: "+colorClass);
				
				
				ArrayList<Integer> maximumClass = colorClass;
				maximumClass.retainAll(currentUniverse);
				if(maximumClass.isEmpty()) break;
				
				int maximumColor = (int) colorClass.get(colorClass.size()-1);
				int choosenNode  = maximumClass.get(maximumClass.size()-1);
				colorSet.remove(maximumClass);
				currentUniverse.remove(choosenNode);
				if(currentClique.size()+ maximumColor >= maximumClique.size() )
				{
					currentClique.add(choosenNode);
					Set<Integer> nodeAdjac = getAdjacents(choosenNode);
					Set<Integer> intersection = new HashSet<Integer>(currentUniverse);
					intersection.retainAll(nodeAdjac);
					System.out.println("choosen node: "+choosenNode+" choosen node adjacents "+nodeAdjac);
					System.out.println();
					System.out.println("intersection: "+intersection);
					
					if(!intersection.isEmpty()) {
						if(this.s.get(level)/this.allSteps < this.Tlimit)
						{
							//intersection = sortNodes(intersection);
						}
						HashMap<Integer,ArrayList<Integer>> colorSubSet = coloringGraph(intersection);
						this.s.put(level,this.s.get(level)+1);
						if(!currentClique.isEmpty() && currentClique.size() >= minQtnNodes) maximalsClique.add(new ArrayList<Integer>(currentClique));
						System.out.println("chama recursiva... colorSubSet: "+colorSubSet);
						maximalsIdentifier(intersection,colorSubSet,level+1);
	
					}
					else if(currentClique.size()> maximumClique.size())
					{	
						maximumClique.clear();
						maximumClique.addAll(currentClique);
						if(!maximumClique.isEmpty() && maximumClique.size() >= minQtnNodes) maximalsClique.add(new ArrayList<Integer>(maximumClique));
					}
					currentClique.removeIf(node -> node == choosenNode);
				}
				else
				{
					break;
				}
				System.out.println("currentUniverse fim da execu��o: "+currentUniverse);
				System.out.println("currentClique fim da execu��o: "+currentClique);
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
	
	public Set<Integer> sortNodes(Set<Integer> current)
	{
		
		ArrayList<NodeMask> sortedList = new ArrayList<NodeMask> ();
		Set<Integer> adjac = new HashSet<Integer>();
		for(Integer node: current)
		{
			adjac = getAdjacents(node);
			adjac.retainAll(current);
			NodeMask nodeMask = new NodeMask();
			nodeMask.setNode(node);
			nodeMask.setDegree(adjac.size());
			sortedList.add(nodeMask);
			
			
		}

		quickSort(sortedList,0,sortedList.size()-1);
		
		Set<Integer> newCurrent = new HashSet<Integer>();
		return newCurrent;
	}
	public void removeDisconnectedNodes() {
		ArrayList<Integer> connectedNodes = new ArrayList<Integer>();
		for(ArrayList<Integer> relation : this.universe.getEdges().keySet()) 
		{
			
			connectedNodes.addAll(relation);
		}
		connectedNodes = removeDuplicate(connectedNodes);
		this.universe.getNodes().keySet().retainAll(connectedNodes);
	}
	
	
	public void removeBottomEdges()
	{
		this.universe.getEdges().entrySet().removeIf(edge -> edge.getValue() <  this.minWeight);
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
	public void quickSort(ArrayList<NodeMask> vet, int esq, int dir){
	    int pivo = esq; 
	    int  i;
	    NodeMask ch;
	    int j;   
	    for(i=esq+1;i<=dir;i++){        
	        j = i;                      
	        if(vet.get(j).getDegree() < vet.get(pivo).getDegree()){     
	            ch = vet.get(j);               
	            while(j > pivo){           
	                vet.get(j).setDegree(vet.get( j-1).getDegree());    
	                vet.get(j).setNode(vet.get( j-1).getNode()); 
	                j--;                    
	            }
	            vet.get(j).setDegree(ch.getDegree());    
                vet.get(j).setNode(ch.getNode()); 
	            pivo++;                    
	        }
	    }
	    if(pivo-1 >= esq){              
	    	quickSort(vet,esq,pivo-1);      
	    }
	    if(pivo+1 <= dir){              
	    	quickSort(vet,pivo+1,dir);      
	    }
	 }
	
	public void filterMaximals()
	{
		for(ArrayList<Integer> maximal: maximalsClique)
		{
			
			ArrayList<Integer> maximalAux = new ArrayList<Integer>(maximal);
			for(ArrayList<Integer> currentClique: maximalsClique)
			{
				
				if(currentClique.equals(maximal)) continue;
				
				ArrayList<Integer> currentCliqueAux = new ArrayList<Integer>(currentClique);
				currentCliqueAux.retainAll(maximalAux);
				
				if(!currentCliqueAux.isEmpty())
				{
					
					if(currentClique.size() < maximal.size()) currentClique.removeIf(node -> currentCliqueAux.contains(node));
					
					else maximal.removeIf(node -> currentCliqueAux.contains(node));
				}
				
			}
		}
		Set<ArrayList<Integer>> filterMax = new HashSet<ArrayList<Integer>>();
		for(ArrayList<Integer> maximal: maximalsClique)
		{
			if(!maximal.isEmpty() && maximal.size() >= minQtnNodes ) filterMax.add(maximal);
		}
		maximalsClique.clear();
		maximalsClique.addAll(filterMax);
		
		
		
	}
	public ArrayList<Integer> getcurrentClique() {
		return currentClique;
	}
	
	
	public ArrayList<Integer> getMaximumClique() {
		return maximumClique;
	}
	public Set<ArrayList<Integer>> getMaximalsClique(){
		return maximalsClique;
	}
	
	
	public Graph getUniverse() {
		return universe;
	}


	public int getMaxQtnCliques() {
		return maxQtnCliques;
	}
	public int getMinWeight()
	{
		return minWeight;
	}
	public int getMinQtnNodes()
	{
		return minQtnNodes;
	}
	
	public void setMaxQtnCliques(int maxQtnCliques) {
		this.maxQtnCliques = maxQtnCliques;
	}
}
class NodeMask {
	private int node = 0;
	private int degree = 0;
	public int degree() {
		return node;
	}
	public int getNode() {
		return node;
	}
	public void setNode(int node) {
		this.node = node;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	

	
}

