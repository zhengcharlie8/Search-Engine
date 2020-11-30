package retrieval;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class WsumNode extends BeliefNode {

	ArrayList<Double> wts = null;
	
	public WsumNode(ArrayList<? extends QueryNode> c, ArrayList<Double> weights) {
		super(c);
		wts = weights;
	}

	@Override
	public Double score(Integer docId) {
		return Math.log(IntStream.range(0, children.size())
				.mapToDouble(i -> wts.get(i) * Math.exp(children.get(i).score(docId)))
				.sum()/wts.stream().reduce(Double::sum).orElse(1.0));
	}
}
