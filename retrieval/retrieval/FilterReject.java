package retrieval;

public class FilterReject extends FilterOperator {

	public FilterReject(ProximityNode proximityExp, QueryNode q) {
		super(proximityExp, q);
	}

	@Override
	public Integer nextCandidate() {
		return query.nextCandidate();	
	}
	
	@Override
	public Double score(Integer docId) {
		if (! docId.equals(filter.nextCandidate()))
			return query.score(docId);
		else
			return null;
	}
}
