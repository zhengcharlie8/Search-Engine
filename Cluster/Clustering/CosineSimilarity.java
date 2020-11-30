package Clustering;

public class CosineSimilarity implements SimilarityMethod{

    public CosineSimilarity(){}


    @Override
    public double similarity(DocumentVector rep1, DocumentVector rep2){
        double similarity = 0.0;
        double d1 = 0;
        for(String term : rep1.getKeys()){
            similarity += rep1.get(term) * rep2.get(term);
            d1 += Math.pow(rep1.get(term), 2);
        }
        double d2 = 0;
        for(String term : rep2.getKeys()){
            d2 += Math.pow(rep2.get(term), 2);
        }
        return similarity / Math.sqrt(Math.max(1, d1 * d2));
    }

}