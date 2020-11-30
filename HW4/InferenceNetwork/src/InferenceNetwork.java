package InferenceNetwork;
import java.util.*;

public class InferenceNetwork {
	public List<Map.Entry<Integer, Double>> runQuery(QueryNode qnode, int K){
        PriorityQueue<Map.Entry<Integer, Double>> result = new PriorityQueue<>(Map.Entry.<Integer,Double>comparingByValue());
        while(qnode.hasMore()){
            Integer d = qnode.nextCandidate();
            qnode.skipTo(d);
            Double curScore = qnode.score(d);
            if(curScore != null){
            	result.add(new AbstractMap.SimpleEntry<Integer, Double>(d,curScore));
            	if (result.size()>K) {
                result.poll();
            	}
            }
            
            qnode.skipTo(d+1);
        }

        ArrayList<Map.Entry<Integer, Double>> scores = new ArrayList<Map.Entry<Integer, Double>>();
        scores.addAll(result);
        scores.sort(Map.Entry.<Integer, Double>comparingByValue(Comparator.reverseOrder()));
        return scores;
    }
}
