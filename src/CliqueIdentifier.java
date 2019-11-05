import java.util.ArrayList;

public class CliqueIdentifier {
	private Graph universe;
	private ArrayList<Integer> current_clique;
	private ArrayList<Integer> maximum_clique;
	
	public CliqueIdentifier(Graph u) {
		this.universe = u;
	}
	public ArrayList<Integer> coloringGraph(Graph current_universe){
		ArrayList<Integer> colorset = new ArrayList<Integer>();
		
		int maxcolor = 1;
		int kmin = maximum_clique.size() - current_clique.size() + 1;
		if (kmin <= 0) kmin = 1;
		int lastindex = 0;
		
		for(int i = 0; i <= current_universe.getNodes().size(); i++)
		{
			
		}
		
		
		return colorset;
		
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
