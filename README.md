## Introduction
This project implements a search engine using a file of all shakespeare scenes as a corpus. It is able to be modified to be a generic search engine for any provided corpus.

## Files
This project has already been compiled so all the output files are already included in the directory under `src/output`.

The file format for the code is:

1. `src/index/index` contains the code for building the inverted index and the postings list from the provided corpus.

2. `src/index/utilities` contains the code for compressing the index (using VByte Encoding)

3. `src/retrieval/apps` contains the code to build an index, comparing retrieval models (BM-25, Vector Space Model, Jelinik-Mercer and Dirichlet), making and testing a prior operator for querying independent features, creating document vectors, building an online cluster,  and a file to test the inference network.

4. `src/retrieval/retrieval` contains the code for the retrieval models, clustering and belief and query nodes for the inference network.

5. `src/cluster` contains the code for building a cluster. Similararity score is calculated using Cosine Similarity.

## Dependencies

The code uses json-simple-1.1.1.jar	which can be downloaded here <https://code.google.com/archive/p/json-simple/downloads> 

## Building & Running

The code can be compiled, built and run using Eclipse.
## Runnable Applications
This project has 6 runnable apps:
1. `apps/BuildIndex`: This is used to build the inverted index and write it onto disk. The options for this are:
    * `filename` - The absolute path of the input JSON file (in this case - Shakespeare plays).
    * `compress` - Whether to compress the index or not using VByte encoding(true/false).  
    
2. `apps/CompareModel`: This is used to retrieve query results from an index built using `BuildIndex`. The input queries are stored in a file, in which each line contains a set of terms separated by whitespace. It prints the time taken to perform the retrieval on the output screen, which can be used to compare the query retrieval times for different indices. It also saves the output from the following retrieval models into a file:
    * Vector Space Model with `log TF` and `log IDF` weights. Saved in `vs.trecrun`.
    * BM25 model with `k1 = 1.5`, `k2 = 500` and `b = 0.75`. Saved in `bm25.trecrun`.
    * Query Likelihood Model with Jelinik-Mercer smoothing and `lambda = 0.2`. Saved in `ql-jm.trecrun`.
    * Query Likelihood Model with Dirchlet smoothing and `mu = 1200`. Saved in `ql-dir.trecrun`.
    
    This takes the following command line arguments:
    * `filename` - This is the absolute/relative path of the file containing the queries. The relative path should be relative to the root of the project.
    * `compress` - Whether the inverted index to query from is compressed or not (true/false).
    * `k` - The top k results to return.
    
3.  `apps/TestInferenceNetwork`: This is used to run the inference network with the following operators:
     * Ordered Window, with the window size of 1 (exact phrase)
     * Unordered Window, with window size 3 * size of the query
     * SUM
     * AND
     * OR
     * MAX
     
     All of the operators are scored with Query Likelihood model, with Dirchlet smoothing, with the value of mu = 1500.
     It takes the following parameters:
     * `k`: The top k results to return.
     * `compressed`:  Whether the inverted index to query from is compressed or not (true/false).
     * `queryFile`: The name of the file containing the query.
     
4.  `apps/OnlineCluster`: This is used to create the document clusters, given the index compression and the linkage. It creates files for thresholds ranging from 0.05 to 0.95.
    The following are the supported linkages:
    * SINGLE_LINK
    * COMPLETE_LINK
    * AVERAGE_LINK
    * MEAN_LINK
    
5.  `api/MakePrior`: This is used to create the priors using `uniform` and `random`, and writes them to a file on disk.

6.   `api/TestPrior`: This is used to run the prior queries using the arguments provided.

## Troubleshooting
1. Make sure Java is installed correctly (Project is currently running on Java 8).
2. Make sure the json-simple dependency is correctly installed and added to the dependencies.
