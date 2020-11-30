package Clustering;


import index.Index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster {

    private List<Integer> members;
    private Linkage linkage;
    private SimilarityMethod sim;
    private Index index;
    private int id;

    public Cluster(int id, Index index, Linkage linkage, SimilarityMethod sim){

        this.id = id;
        this.members = new ArrayList<>();
        this.sim = sim;
        this.index = index;
        this.linkage = linkage;
    }


    public void add(int docid){
        this.members.add(docid);
    }
    public int getId(){
        return this.id;
    }

    public List<Integer> getMembers(){
        return this.members;
    }

    public double score(DocumentVector vector){
        switch(linkage){
            case COMPLETE:
                return scoreComplete(vector);

            case AVERAGE:
                return scoreAverage(vector);
                
            case MEAN:
            	return scoreMean(vector);
            	
            default:
                return scoreSingle(vector);
        }
    }


    private double scoreSingle(DocumentVector vec){
        double maxScore = 0;
        for(int member : members){
            maxScore = Math.max(sim.similarity(vec, index.getDocumentVector(member)), maxScore);
        }

        return maxScore;
    }


    private double scoreComplete(DocumentVector vec){
        double minScore = Double.MAX_VALUE;
        for(int member : members){
            minScore = Math.min(sim.similarity(vec, index.getDocumentVector(member)), minScore);
        }
        return minScore;
    }


    private double scoreAverage(DocumentVector vec){
        double score = 0;
        for(int member : members){
            score += sim.similarity(vec, index.getDocumentVector(member));
        }
        return score/members.size();
    }


    private double scoreMean(DocumentVector vec){
        Map<String, Double> centroidVector = new HashMap<>();
        for(int member : members){
            DocumentVector memberVector = index.getDocumentVector(member);
            for(String term : memberVector.getKeys()){
                double centroidValue = centroidVector.getOrDefault(term, 0.0);
                double value = memberVector.get(term);
                centroidValue += value / members.size();
                centroidVector.put(term, centroidValue);
            }
        }
        return sim.similarity(vec, new DocumentVector(0, centroidVector));
    }
}
