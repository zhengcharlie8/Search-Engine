package InferenceNetwork;

import retrieval.Dirichlet;
import index.InvertedIndex;

public class HW4 {

	public static void main(String[] args) {
		
		String[] query = new String[] {
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
		};
		
		InvertedIndex index = new InvertedIndex();
		Dirichlet diri = new Dirichlet(index,1500.0);
		
	}

}
