package retrieval;

import java.util.ArrayList;

public class SumNode extends BeliefNode  {

	public SumNode(ArrayList<? extends QueryNode> c) {
		super(c);
	}
	
	@Override
	public Double score(Integer docId) {
		return Math.log(children.stream().mapToDouble(c -> Math.exp(c.score(docId))).sum()/children.size());
	}
}
