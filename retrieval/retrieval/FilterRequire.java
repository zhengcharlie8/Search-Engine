package retrieval;

public class FilterRequire extends FilterOperator{

	public FilterRequire(ProximityNode proximityExp, QueryNode q) {
		super(proximityExp, q);
	}
	
	@Override
	public Integer nextCandidate() {
		return  Math.max(filter.nextCandidate(), query.nextCandidate());	
	}
		
	@Override
	public Double score(Integer docId) {
		if (docId.equals(filter.nextCandidate()))
			return query.score(docId);
		else
			return null;
	}
}
