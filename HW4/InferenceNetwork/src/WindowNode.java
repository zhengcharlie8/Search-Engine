package InferenceNetwork;

import java.util.ArrayList;

import index.Index;
import index.Posting;
import index.PostingList;
import retrieval.RetrievalModel;

public abstract class WindowNode  extends ProximityNode{
	ArrayList<? extends ProximityNode> children;
	
	public WindowNode(Index ind, RetrievalModel mod) {
		super(ind, mod);
	}
	
	private boolean allHaveMore() {
		return children.stream().allMatch(c->c.hasMore());
	}
	
	private int candidate() {
		return children.stream().mapToInt(c->c.nextCandidate()).max().getAsInt();
	}
	@Override
	protected void generatePostings() {
		postingList = new PostingList();
		ArrayList<Posting> matchingPostings = new ArrayList<Posting>();
		while(allHaveMore())
		{
			Integer next = candidate();
			children.forEach(c->c.skipTo(next));
			if(children.stream().allMatch(c->next.equals(c.nextCandidate()))) {
				for (ProximityNode child: children) {
					matchingPostings.add(child.getCurrentPosting());
				}
				Posting p = calculateWindows(matchingPostings);
				if (p!=null) {
					postingList.add(p);
					ctf +=p.getTermFreq();
				}
			}
			matchingPostings.clear();
			children.forEach(c->c.skipTo(next+1));
		}
		postingList.startIteration();
	}
	abstract protected Posting calculateWindows(ArrayList<Posting> matchingPostings);
}
