package InferenceNetwork;

import java.util.ArrayList;
import java.util.List;

public class WeightedSum extends BeliefNode {
    List<Double> weight;
    public WeightedSum(ArrayList<? extends QueryNode> c, List<Double> weight) {
        super(c);
        this.weight = weight;
    }

    @Override
    public Double score(Integer docID) {
        Double sum = 0.0;
        int count = 0;
        double wsum = weight.stream().mapToDouble(c -> c).sum();
        for(QueryNode q: children){
            sum += weight.get(count) * Math.exp(q.score(docID));
            count++;
        }
        return sum / wsum;
    }
}