package InferenceNetwork;

public interface QueryNode {
	
	public Double score(Integer docId);
    public boolean hasMore();
    public Integer nextCandidate();
    public void skipTo(int docID);
}
