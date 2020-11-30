package retrieval;

import index.Index;

public abstract class RetrievalModel {
	Index index;
	double collSize;

	/**
	 * @param tf -- term frequency
	 * @param ctf -- collection term frequency
	 * @param docLen -- document length
	 * @return score of the occurrences for the given model
	 */

	public abstract double scoreOccurrence(int tf, int ctf, int docLen);
}
