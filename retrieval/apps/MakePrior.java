package apps;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import index.Index;
import index.InvertedIndex;

public class MakePrior {

	public static void main(String[] args) {
		boolean compressed = Boolean.parseBoolean(args[0]);
		Index index = new InvertedIndex();
		index.load(compressed);
		try {
			// write two files, uniform prior, random prior, both in log probability space.
			String name = "uniform";
			RandomAccessFile writer = new RandomAccessFile(name + ".prior", "rw");
			double uniform = Math.log(1.0/index.getDocCount());
			for (int i = 1; i <= index.getDocCount(); i ++) {
				writer.writeDouble(uniform);
				System.out.println(index.getDocName(i) + "\t" + uniform);
			}
			writer.close();

			name = "random";
			// fixed seed to make it replicable...
			Random rand = new Random(1024);
			writer = new RandomAccessFile(name + ".prior", "rw");
			for (int i = 1; i <= index.getDocCount(); i ++) {
				double prior = Math.log(rand.nextDouble());
				writer.writeDouble(prior);
				System.out.println(index.getDocName(i) + "\t" + prior);
			}
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
