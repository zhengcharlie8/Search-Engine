package index;

import java.util.Set;

/**
 * Abstract interface of an information retrieval inverted index
 */
public interface Index {
	/**
	 * @param term the term to lookup
	 * @return the posting list for the given term
	 */
	public PostingList getPostings(String term);
	/**
	 * @param term the term to lookup
	 * @return the frequency of the term in the collection
	 */
	public int getTermFreq(String term);
	/**
	 * Get the document frequency of a word
	 * @return number of documents containing the word
	 * @param term the word
	 */
	public int getDocFreq(String term);

	/**
	 * @return the size of the collection in terms
	 */
	public long getCollectionSize();
	/**
	 * @param docId the document id to look up
	 * @return the length of the document
	 */
	public int getDocLength(int docId);
	/**
	 * @return the size of the collection in documents
	 */
	public int getDocCount();

	/**
	 * 
	 * @return the set of terms in the vocabulary, unordered.
	 */
	public Set<String> getVocabulary();

	/**
	 *  Load an index into memory to use.
	 *  NB: this really ought not to have the parameter...
	 * @param compress whether or not the index is compressed
	 */
	public void load(boolean compress);

	/**
	 * @param key document id number
	 * @return the external document id associated with that number.
	 */
	public String getDocName(int key);

	/**
	 * Open the named prior file, read in the data for the specific document
	 * @param name The name of the prior
	 * @param docId the document id to read in
	 * @return
	 */
	public Double getPrior(String name, Integer docId);
}
