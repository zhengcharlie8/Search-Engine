package retrieval;

import java.util.ArrayList;

import index.Index;

public class BandNode extends UnorderedWindow {

	public BandNode(ArrayList<? extends ProximityNode> termNodes, 
			Index ind, RetrievalModel mod) {
		super(0, termNodes, ind, mod);
	}
}
