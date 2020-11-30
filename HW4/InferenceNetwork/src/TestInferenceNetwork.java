package InferenceNetwork;

import index.Index;
import index.InvertedIndex;
import retrieval.Dirichlet;
import retrieval.RetrievalModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestInferenceNetwork {
    public static void main(String[] args) {
        int k = 10;
        boolean compressed = true;
        Index invertedIndex = new InvertedIndex();
        invertedIndex.load(compressed);
        
        RetrievalModel model = new Dirichlet(invertedIndex,1500);
        List<Map.Entry<Integer, Double>> results;
        InferenceNetwork network = new InferenceNetwork();
        QueryNode queryNode;
        ArrayList<ProximityNode> children;
		List<String> queries = new ArrayList<String>(Arrays.asList(
				"the king queen royalty",
				"servant guard soldier",
				"hope dream sleep",
				"ghost spirit",
				"fool jester player",
				"to be or not to be",
				"alas",
				"alas poor",
				"alas poor yorick",
				"antony strumpet"
		));

        String outFile, runID, qID;
        int qNum = 0;

        //unordered
        outFile = "uw.trecrun";
        runID = "charliezheng-uw3-dir-1500";
        qNum = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile));){
            for (String query : queries) {
                qNum++;
                children = genTermNodes(query, invertedIndex, model);
                int winSize = 3 * children.size();
                queryNode = new UnorderedWindow(winSize, children, invertedIndex, model);
                results = network.runQuery(queryNode, k);
                qID = "Q" + qNum;
                printResults(results, invertedIndex, writer, runID, qID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        outFile = "od1.trecrun";
        runID = "charliezheng-od1-dir-1500";
        qNum = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile));){
            for (String query : queries) {
                qNum++;
                children = genTermNodes(query, invertedIndex, model);
                int winSize = 1;
                queryNode = new OrderedWindow(winSize, children, invertedIndex, model);
                results = network.runQuery(queryNode, k);
                qID = "Q" + qNum;
                printResults(results, invertedIndex, writer, runID, qID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        outFile = "and.trecrun";
        runID = "charliezheng-and-dir-1500";
        qNum = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile));){
            for (String query : queries) {
                qNum++;
                children = genTermNodes(query, invertedIndex, model);
                queryNode = new AndNode(children);
                results = network.runQuery(queryNode, k);
                qID = "Q" + qNum;
                boolean append = qNum > 1;
                printResults(results, invertedIndex, writer, runID, qID);
                }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        outFile = "or.trecrun";
        runID = "charliezheng-or-dir-1500";
        qNum = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile));){
            for (String query : queries) {
                qNum++;
                children = genTermNodes(query, invertedIndex, model);
                queryNode = new OrNode(children);
                results = network.runQuery(queryNode, k);
                qID = "Q" + qNum;
                printResults(results, invertedIndex, writer, runID, qID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        outFile = "sum.trecrun";
        runID = "charliezheng-sum-dir-1500";
        qNum = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile));){
            for (String query : queries) {
                qNum++;
                children = genTermNodes(query, invertedIndex, model);
                queryNode = new SumNode(children);
                results = network.runQuery(queryNode, k);
                qID = "Q" + qNum;
                printResults(results, invertedIndex, writer, runID, qID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        outFile = "max.trecrun";
        runID = "charliezheng-max-dir-1500";
        qNum = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile));){
            for (String query : queries) {
                qNum++;
                children = genTermNodes(query, invertedIndex, model);
                queryNode = new MaxNode(children);
                results = network.runQuery(queryNode, k);
                qID = "Q" + qNum;
                printResults(results, invertedIndex, writer, runID, qID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static ArrayList<ProximityNode> genTermNodes(String query, Index index, RetrievalModel model){
        String[] terms = query.split("\\s+");
        ArrayList<ProximityNode> children = new ArrayList<ProximityNode>();
        for(String term : terms){
            ProximityNode node = new TermNode(term,index, model);
            children.add(node);
        }
        return children;
    }

    private static void printResults(List<Map.Entry<Integer, Double>> results, Index index, PrintWriter writer,
                                     String runID, String qID){
        int rank = 1;
        for(Map.Entry<Integer, Double> entry : results){
            String sceneID = index.getDocName(entry.getKey());
            String resultLine = qID + " skip " + sceneID + " " + rank + " " + String.format("%.7f", entry.getValue())
                    + " " + runID;
            writer.println(resultLine);
            rank++;
        }
    }
}