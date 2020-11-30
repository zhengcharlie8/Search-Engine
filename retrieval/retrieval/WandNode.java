package retrieval;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class WandNode extends BeliefNode{

	ArrayList<Double> wts = null;
	
	public WandNode(ArrayList<? extends QueryNode> c, ArrayList<Double> weights) {
		super(c);
		wts = weights;
	}
	
	@Override
	public Double score(Integer docId) {
		return IntStream.range(0, children.size()).mapToDouble(i -> wts.get(i) * children.get(i).score(docId)).sum();	
		}
}
