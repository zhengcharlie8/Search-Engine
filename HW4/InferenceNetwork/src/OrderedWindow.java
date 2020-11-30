package InferenceNetwork;

import java.util.ArrayList;
import java.util.Arrays;

import index.Index;
import index.Posting;
import retrieval.RetrievalModel;

public class OrderedWindow extends WindowNode{

	private Integer distance;

	public OrderedWindow(int d, ArrayList<? extends ProximityNode> children, 
			Index ind, RetrievalModel mod) {
		super(ind, mod);
		this.children = children;
		this.distance = d;
		generatePostings();
	}

	@Override
	protected Posting calculateWindows(ArrayList<Posting> postings) {
		int prev;
		boolean found = false;
		Posting postlst = null; 
		if (postings.size() == 1) {
			return postings.get(0);
		}
		Integer[] p0 = postings.get(0).getPositionsArray();
		for(int i = 0; i < p0.length; i++){
			prev = p0[i];
			for (int j = 1; j < postings.size(); j++){
				found = false;
				ArrayList<Integer> p = new ArrayList<>(Arrays.asList( postings.get(j).getPositionsArray()));
				for (int k = 0; k < p.size(); k++) {
					int cur = p.get(k);
					if (prev < cur && cur <= prev + distance) {
						found = true;
						prev = cur;
						break;
					}
				}
				if (!found)
					break;
			}
			if (found) {
				if( postlst == null)
					postlst =  new Posting(postings.get(0).getDocId(), p0[i] );
				else
					postlst.add(p0[i]);
			}
		}
		return postlst;
	}
}