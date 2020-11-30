package retrieval;

import java.util.ArrayList;

public class MaxNode extends BeliefNode{

	public MaxNode(ArrayList<? extends QueryNode> c) {
		super(c);
	}
	
	@Override
	public Double score(Integer docId) {
		return children.stream().mapToDouble(c -> c.score(docId)).max().getAsDouble();
	}
}
