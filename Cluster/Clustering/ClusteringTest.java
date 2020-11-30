package Clustering;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import index.Index;
import index.InvertedIndex;

public class ClusteringTest {

    public static void main(String[] args) {
        Index index = new InvertedIndex();
        index.load(true);
        Linkage linkage = Linkage.MEAN;
        for (double threshold = .05; threshold < 1.0; threshold+=.05) {
            Map<Integer, Cluster> clusters = new HashMap<>();
            int clusterid = 0;
            SimilarityMethod sim = new CosineSimilarity();
            int limit = index.getDocCount();
            for (int docid = 1; docid <= limit; docid++) {
                double score = 0.0;
                int best = -1;
                for (Cluster c : clusters.values()) {
                    int cId = c.getId();
                    double s = c.score(index.getDocumentVector(docid));
                    if (s > score) {
                        score = s;
                        best = cId;
                    }
                }
                if (score > threshold) {
                    clusters.get(best).add(docid);
                } else {
                    clusterid++;
                    Cluster cluster = new Cluster(clusterid, index, linkage, sim);
                    cluster.add(docid);
                    clusters.put(clusterid, cluster);
                }
            }
            String filename = "cluster-" + threshold + ".out";
            try(PrintWriter printWriter = new PrintWriter(filename)){
                for(Map.Entry<Integer, Cluster> entry : clusters.entrySet()){
                    for(int docid : entry.getValue().getMembers()) {
                        printWriter.print("<" + entry.getValue().getId() + "> <" + docid + ">\n");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
