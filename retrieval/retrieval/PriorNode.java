package retrieval;

import index.Index;

public class PriorNode implements QueryNode {

	private String name;
	private Index index;

	public PriorNode(String priorName, Index ind) {
		this.name = priorName;
		this.index = ind;
	}
	@Override
	public Integer nextCandidate() {
		// we never provide a candidate, but always score another's
		return index.getDocCount() + 1;
	}

	@Override
	public Double score(Integer docId) {
		// read the prior value from the Index
		return index.getPrior(name, docId);
	}

	@Override
	public boolean hasMore() {
		// we never have more, but always are willing to score a document
		return false;
	}

	@Override
	public void skipTo(int docId) {
		// soon you'll see that
		// I will never do anything...
	}
}
