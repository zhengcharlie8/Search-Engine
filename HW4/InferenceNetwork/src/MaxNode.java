package InferenceNetwork;
import java.util.ArrayList;

public class MaxNode extends BeliefNode {
    public MaxNode(ArrayList<? extends QueryNode> c) {
        super(c);
    }

    @Override
    public Double score(Integer docID) {
        Double max = (double) Integer.MIN_VALUE;
        for(QueryNode q: children){
            double score = q.score(docID);
            max = Math.max(score, max);
        }
        return max;
    }
}