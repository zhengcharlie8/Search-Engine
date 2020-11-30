package InferenceNetwork;

import java.util.ArrayList;

import index.Index;
import retrieval.RetrievalModel;

public class BandNode extends UnorderedWindow{

	public BandNode(ArrayList<? extends ProximityNode> termNodes, Index ind, RetrievalModel mod) {
		super(0, termNodes, ind, mod);
	}
}
