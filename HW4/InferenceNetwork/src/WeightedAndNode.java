package InferenceNetwork;

import java.util.ArrayList;
import java.util.List;

public class WeightedAndNode extends BeliefNode {
    List<Double> weight;
    public WeightedAndNode(ArrayList<? extends QueryNode> c, List<Double> weight) {
        super(c);
        this.weight = weight;
    }
    @Override
    public Double score(Integer docID) {
        double score = 0.0;
        int count = 0;
        for(QueryNode q: children){
            score += weight.get(count) * q.score(docID);
            count++;
        }
        return score;
    }
}
