package cluster;

import java.util.Map;

/**
 * @author dfisher
 * Provide an interface for measures of similarity.
 *
 */
public interface SimilarityMethod {
	
	/**
	 * @param repA the sparse vector representation of A
	 * @param repB the sparse vector representation of B
	 * @return the similarity between A and B
	 * NB: wrapping the Maps in a SparseVector class might improve readability
	 */
	public abstract double similarity(Map<String, Double> repA, Map<String, Double> repB);
}
