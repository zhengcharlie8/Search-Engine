package retrieval;

import java.util.ArrayList;
import index.Index;
import index.Posting;
import index.PostingList;

public abstract class Window extends ProximityNode {
	ArrayList<? extends ProximityNode> children;

	public Window(Index ind, RetrievalModel mod) {
		super(ind, mod);
	}

	private boolean allHaveMore() {
		return children.stream().allMatch(c -> c.hasMore());
	}

	private int candidate() {
		return children.stream().mapToInt(c -> c.nextCandidate()).max().getAsInt();
	}
	@Override
	protected void generatePostings() {
		//Conjunctive processing of child node postings and 
		// populate the posting list for a window node
		postingList = new PostingList();

		ArrayList<Posting> matchingPostings = new ArrayList<Posting>();

		while (allHaveMore()) {
			Integer next = candidate();
			children.forEach(c -> c.skipTo(next));
			if (children.stream().allMatch(c -> next.equals(c.nextCandidate()))) {
				// everyone is on the document
				for (ProximityNode child : children)
					matchingPostings.add(child.getCurrentPosting());
				Posting p = calculateWindows(matchingPostings);
				if (p != null) {
					postingList.add(p);
					ctf += p.getTermFreq();
				}
			}
			matchingPostings.clear();
			children.forEach(c -> c.skipTo(next + 1));
		}
		//reset to the start state.
		postingList.startIteration();
	}

	/**
	 * @param matchingPostings the list of postings to intersect positions on.
	 * @return the Posting containing the positions of each matching window, or null, if none.
	 */
	abstract protected Posting calculateWindows(ArrayList<Posting> matchingPostings);
}
