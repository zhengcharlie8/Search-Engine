package cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import index.Index;

/**
 * Basic Cluster API suitable for performing experiments with
 * linking strategies.
 * 
 * @author dfisher
 *
 */
public class Cluster {
	public enum Linkage {
		SINGLE, COMPLETE, AVERAGE, MEAN;
	}
	private List<Integer> documentIds;

	// Maintain the centroid representation of the cluster when linkage == MEAN
	private Map <String, Double> clusterRep;
	private SimilarityMethod sim;
	private Linkage linkage;
	private Index index;
	private int id;

	public Cluster(int id, Index index, Linkage linkage, SimilarityMethod sim) {
		documentIds = new ArrayList<Integer>();
		this.index = index;
		this.sim = sim;
		this.linkage = linkage;
		this.id = id; 
		this.clusterRep = new HashMap<String, Double>();
	}
	
	public List<Integer> getDocumentIds() {
		return documentIds;
	}

	public int getId() { return id; }

	/**
	 * score the document other with respect to this cluster using our
	 * similarity method and linkage strategy
	 * @param other the document to score
	 * @return the similarity score
	 */
	public double score(Map<String, Double> other) {
		switch (linkage) {
		case SINGLE:
			return scoreSingle(other);
		case COMPLETE:
			return scoreComplete(other);
		case AVERAGE:
			return scoreAverage(other);
		case MEAN:
			return scoreMean(other);
		default:
			return 0.0;
		}
	}

	/**
	 * Score using the centroid of the cluster (MEAN linkage)
	 * @param other the document to score
	 * @return the similarity score
	 */
	private double scoreMean(Map<String, Double> other) {
		double score = 0.0;
		score = sim.similarity(clusterRep, other);
		return score;
	}
	/**
	 * Score using the average score of the documents in the cluster (AVERAGE linkage)
	 * @param other the document to score
	 * @return the similarity score
	 */
	private double scoreAverage(Map<String, Double> other) {
		double score = 0.0;
		for (Integer i : documentIds) {
			Map <String, Double> dv = index.getDocumentVector(i);
			score += sim.similarity(dv, other);
		}
		return score/documentIds.size();
	}

	/**
	 * Score using the minimum score of the documents in the cluster (COMPLETE linkage)
	 * @param other the document to score
	 * @return the similarity score
	 */
	private double scoreComplete(Map<String, Double> other) {
		double score = 1.0;
		for (Integer i : documentIds) {
			Map <String, Double> dv = index.getDocumentVector(i);
			double thisScore = sim.similarity(dv, other);
			if (thisScore < score) score = thisScore;
		}
		return score;
	}

	/**
	 * Score using the maximum score of the documents in the cluster (SINGLE linkage)
	 * @param other the document to score
	 * @return the similarity score
	 */
	private double scoreSingle(Map<String, Double> other) {
		double score = 0.0;
		for (Integer i : documentIds) {
			Map <String, Double> dv = index.getDocumentVector(i);
			double thisScore = sim.similarity(dv, other);
			if (thisScore > score) score = thisScore;
		}
		return score;
	}

	/**
	 * Add the document to this cluster. If linkage is MEAN, update the centroid.
	 * @param docId the document to add.
	 */
	public void add(Integer docId) {
		documentIds.add(docId);
		// if we are doing mean, update the centroid the lazy way.
		if (linkage == Linkage.MEAN) {
			clusterRep.clear();
			for (Integer i : documentIds) {
				index.getDocumentVector(i).forEach((k,v) -> clusterRep.put(k, ((v.doubleValue() + clusterRep.getOrDefault(k, 0.0)))));
			}
			clusterRep.forEach((k,v) -> clusterRep.put(k, clusterRep.get(k)/documentIds.size()));
		}
	}
}
