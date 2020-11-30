package InferenceNetwork;

import index.Index;
import index.Posting;
import index.PostingList;
import retrieval.RetrievalModel;

public abstract class ProximityNode implements QueryNode {
	
	protected int ctf = 0;
	protected Index index;
	protected RetrievalModel model;
	protected PostingList postingList = null; 
	
	public ProximityNode(Index ind, RetrievalModel mod) {
		index = ind;
		this.model = mod;
	}
	protected abstract void generatePostings();
	
	protected Posting getCurrentPosting() {
		return postingList.getCurrentPosting();
	}
	public boolean hasMore() {
		return postingList.hasMore();
	}
	public Integer nextCandidate(){
		if (postingList.hasMore()) {
			return postingList.getCurrentPosting().getDocId();
		}
		return index.getDocCount()+1;
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
}
