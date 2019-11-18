import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Tester {
	private Graph universe;
	private ArrayList<Integer> currentClique = new ArrayList<Integer>();
	private ArrayList<Integer> maximumClique = new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> maximalsClique = new ArrayList<ArrayList<Integer>>();
	private HashMap<Integer,Integer> s = new HashMap<Integer,Integer>();
	private HashMap<Integer,Integer> sOld = new HashMap<Integer,Integer>();
	public Tester(Graph u,ArrayList<ArrayList<Integer>> mc)
	{
		this.universe = u;
		this.maximalsClique = mc;
		this.s.put(0,0);
		this.s.put(1,0);
		this.sOld.put(0,0);
		this.sOld.put(1,0);
        HashMap<Integer,ArrayList<Integer>> colorSet1 = ColorSort.coloringGraph(this.universe.getNodes().keySet(),this.maximumClique,this.currentClique,this.universe.getEdges());
        System.out.println(colorSet1);
		konkjenezic(this.universe.getNodes().keySet(),colorSet1,1);
		System.out.println("konkkong"+this.maximumClique);
	}

	public void konkjenezic(Set <Integer> currentUniverse,HashMap<Integer,ArrayList<Integer>> colorSet, int level){
		
		if(!this.s.containsKey(level))	this.s.put(level, 0);
		if(!this.sOld.containsKey(level))this.sOld.put(level, 0);
		this.s.put(level,this.s.get(level)+this.s.get(level-1) - this.sOld.get(level));
		for(int i = colorSet.size(); i >= 1 ; i--)
		{	
			ArrayList<Integer> colorClass = colorSet.get(i);
			while(!currentUniverse.isEmpty())
			{
				
				ArrayList<Integer> maximumClass = colorClass;
				maximumClass.retainAll(currentUniverse);
				if(maximumClass.isEmpty()) {
					//System.out.println("level "+ level + " - Não tem mais cores! break");
					break;
					}
				
				int maximumColor = (int) colorClass.get(colorClass.size()-1);
				int choosenNode  = maximumClass.get(maximumClass.size()-1);
				colorSet.remove(maximumClass);
				currentUniverse.remove(choosenNode);
				if(currentClique.size()+ maximumColor > maximumClique.size() )
				{
					currentClique.add(choosenNode);
					Set<Integer> nodeAdjac = ColorSort.getAdjacents(choosenNode,this.universe.getEdges());
					Set<Integer> intersection = new HashSet<Integer>(currentUniverse);
					intersection.retainAll(nodeAdjac);
					if(!intersection.isEmpty()) 
					{
					
						HashMap<Integer,ArrayList<Integer>> colorSubSet = ColorSort.coloringGraph(intersection,this.maximumClique,this.currentClique,this.universe.getEdges());
						this.s.put(level,this.s.get(level)+1);
						//System.out.println("chama recursiva... colorSubSet: "+colorSubSet);
						//if(!currentClique.isEmpty() && currentClique.size() >= minQtnNodes) maximalsClique.add(new ArrayList<Integer>(currentClique));
						konkjenezic(intersection,colorSubSet,level+1);
					}
					else if(currentClique.size()> maximumClique.size())
					{	
						maximumClique.clear();
						maximumClique.addAll(currentClique);
						//if(!maximumClique.isEmpty() && maximumClique.size() >= minQtnNodes) maximalsClique.add(new ArrayList<Integer>(maximumClique));
					}
					currentClique.removeIf(node -> node == choosenNode);
				}
				else break;
				
			}
			
		}	
	}
}
