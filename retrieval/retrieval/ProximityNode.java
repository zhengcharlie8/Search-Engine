package retrieval;

import index.Index;
import index.Posting;
import index.PostingList;

public abstract class ProximityNode implements QueryNode {
	protected int ctf = 0;
	protected PostingList postingList = null;
	protected Index index;
	protected RetrievalModel model;

	public ProximityNode(Index ind, RetrievalModel mod){
		index = ind;
		this.model = mod;
	}
	protected abstract void generatePostings();

	protected Posting getCurrentPosting() {
		return postingList.getCurrentPosting();
	}

	@Override
	public Integer nextCandidate() {
		if (postingList.hasMore()) {
			return postingList.getCurrentPosting().getDocId();
		}
		return index.getDocCount() + 1; // no more documents
	}

	@Override
	public Double score(Integer docId) {
		int tf = 0;
		if (postingList.hasMore() && postingList.getCurrentPosting().getDocId().equals(docId)) {
			tf = postingList.getCurrentPosting().getTermFreq();
		}
		return model.scoreOccurrence(tf, ctf, index.getDocLength(docId));	
	}

	@Override
	public void skipTo(int docId) {
		postingList.skipTo(docId);
	}

	@Override
	public boolean hasMore() {
		return postingList.hasMore();
	}
}