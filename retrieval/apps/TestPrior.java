package apps;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import index.Index;
import index.InvertedIndex;
import retrieval.AndNode;
import retrieval.Dirichlet;
import retrieval.InferenceNetwork;

import retrieval.PriorNode;
import retrieval.ProximityNode;
import retrieval.QueryNode;
import retrieval.RetrievalModel;
import retrieval.TermNode;

/*
 * *
Q1: the king queen royalty prior uniform

Q2: the king queen royalty prior random


 Please run these queries with AND. Use dirichlet smoothing with μ=1500.
 */
public class TestPrior {

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		boolean compressed = Boolean.parseBoolean(args[1]);
		Index index = new InvertedIndex();
		index.load(compressed);
		String query = "the king queen royalty";

		RetrievalModel model = new Dirichlet(index, 1500);
		List<Map.Entry<Integer, Double>> results;
		InferenceNetwork network = new InferenceNetwork();
		QueryNode queryNode;
		ArrayList<QueryNode> children;

		String outfile, runId;
		String qId = "Q1";

		outfile = "uniform.trecrun";
		runId = "dafisher-uniform-dir-1500";
		// make each of the required query nodes and run the queries
		children = genTermNodes(query, index, model);
		QueryNode priorNode = new PriorNode("uniform", index);
		children.add(priorNode);
		queryNode = new AndNode(children);
		results = network.runQuery(queryNode, k);
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(outfile));
			printResults(results, index, writer, runId, qId);
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		outfile = "random.trecrun";
		runId = "dafisher-random-dir-1500";
		// make each of the required query nodes and run the queries
		children = genTermNodes(query, index, model);
		priorNode = new PriorNode("random", index);
		children.add(priorNode);
		queryNode = new AndNode(children);
		results = network.runQuery(queryNode, k);
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(outfile));
			printResults(results, index, writer, runId, qId);
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private static void printResults(List<Entry<Integer, Double>> results, 
			Index index, PrintWriter writer, String runId, String qId) {
		int rank = 1;
		for (Map.Entry<Integer, Double> entry : results) {
			String sceneId = index.getDocName(entry.getKey());
			String resultLine = qId + " skip " + sceneId + " " + rank + " " 
					+ entry.getValue() + " " + runId;

			writer.println(resultLine);
			rank++;
		}
	}
	private static ArrayList<QueryNode> genTermNodes(String query, Index index, RetrievalModel model) {
		String [] terms = query.split("\\s+");
		ArrayList<QueryNode> children = new ArrayList<QueryNode>();
		for (String term : terms) {
			ProximityNode node = new TermNode(term, index, model);
			children.add(node);
		}
		return children;
	}
}
