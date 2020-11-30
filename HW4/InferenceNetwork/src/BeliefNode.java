package InferenceNetwork;

import java.util.ArrayList;

public abstract class BeliefNode implements QueryNode {
	protected ArrayList<? extends QueryNode> children;
	
	public BeliefNode(ArrayList<? extends QueryNode> c) {
		children = c;
	}
	public Integer nextCandidate() {
		return children.stream().mapToInt(c->c.nextCandidate()).min().getAsInt();	
	}
	
	@Override
	public boolean hasMore() {
		return children.stream().anyMatch(c->c.hasMore());
	}
	
	
	@Override
	public void skipTo(int docId) {
		children.forEach(c->c.skipTo(docId));
	}
}
