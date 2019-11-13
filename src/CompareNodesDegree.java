import java.util.Comparator;

public class CompareNodesDegree implements Comparator<NodeMask> {
	@Override
	public int compare (NodeMask node1, NodeMask node2)
	{
		return node1.getDegree() < node2.getDegree() ? 1:0;
	}
}
