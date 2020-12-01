package cluster;

import java.util.Map;

/**
 * @author dfisher
 * Implement cosine similarity with a weighted vector
 *
 */
public class CosSimMethod implements SimilarityMethod {
	@Override
	public double similarity(Map<String, Double> repA, Map<String, Double> repB) {
		double dot = 0.0, norm = 0.0, sumA = 0.0, sumB = 0.0;
		sumA = repA.entrySet().stream().mapToDouble(e -> Math.pow(e.getValue().doubleValue(), 2)).sum();
		sumB = repB.entrySet().stream().mapToDouble(e -> Math.pow(e.getValue().doubleValue(), 2)).sum();
		norm = Math.sqrt(sumA * sumB);
		//NB: this could have been done in a map (zipping the two lists on A's keyset).
		for (String term : repA.keySet()) {
			dot += repA.get(term) * repB.getOrDefault(term, 0.0);
		}
		return dot/norm;
	}
}
