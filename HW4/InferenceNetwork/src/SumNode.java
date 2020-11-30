package InferenceNetwork;

import java.util.ArrayList;

public class SumNode extends BeliefNode {
    public SumNode(ArrayList<? extends QueryNode> c) {
        super(c);
    }
    @Override
    public Double score(Integer docID) {
        Double scores = (double) 0;
        for(QueryNode q: children){
            scores+=Math.exp(q.score(docID));
        }
        return Math.log(scores/children.size());
    }
}