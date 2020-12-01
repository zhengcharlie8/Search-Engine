package apps;

import java.util.Map;
import java.util.HashMap;

import index.Index;
import index.InvertedIndex;
import cluster.Cluster;
import cluster.Cluster.Linkage;
import cluster.SimilarityMethod;
import cluster.CosSimMethod;


/**
 * @author dfisher
 * Test program for online cluster task.
 * run the experiment with:
 * cp="../../json-simple-1.1.1.jar:../../compsci546/InferenceNetwork/bin"
 * wpa199:test dfisher$ for thresh in `seq 0.05 0.05 1` ; do 
 * java -classpath $cp apps.OnlineCluster false MEAN $thresh &gt; output/cluster-$thresh.out ; 
 * done
 */
public class OnlineCluster {

	public static void main(String[] args) {
		Index index = new InvertedIndex();
		index.load(Boolean.parseBoolean(args[0]));
		Linkage linkage = Linkage.valueOf(args[1]);
		double threshold = Double.valueOf(args[2]);
		
		Map <Integer, Cluster> clusters = new HashMap<Integer, Cluster>();
		int clusterId = 0;
		SimilarityMethod sim = new CosSimMethod();
		int limit = index.getDocCount();
		for (int docId = 1; docId <= limit; docId++) {
			double score = 0.0;
			int best = -1;
			for (Cluster c : clusters.values()) {
				int cId = c.getId();
				double s = c.score(index.getDocumentVector(docId));
				if (s > score ) {
					score = s;
					best = cId;
				}
			}

			if (score > threshold) {
				// add to cluster best
				clusters.get(best).add(docId);
			} else {
				// make a new cluster
				clusterId++;
				Cluster clust = new Cluster(clusterId, index, linkage, sim);
				clust.add(docId);
				clusters.put(clusterId, clust);
			}
		}
		// Dump the full set of clusters, in id order.
		clusters.keySet().stream().sorted().forEach((cId) -> {
			Cluster c = clusters.get(cId);
			c.getDocumentIds().forEach((dId) -> 
				System.out.println(c.getId() + " " + index.getDocName(dId)));
		});
	}
}

