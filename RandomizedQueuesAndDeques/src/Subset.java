import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class Subset {
	
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		int N = 0;
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		
		String[] inputs = StdIn.readAllStrings();
		/*while (StdIn.hasNextLine()) {
			completeText += StdIn.readLine();
		}
		String[] inputs = completeText.split("[\\s,]+"); */
		N = inputs.length;
		boolean[] selectionFlags = new boolean[N];

		//double p = (double) k / (double) N;

		int selected = 0;
		while (selected < k) {
			int rand = StdRandom.uniform(N);
			if (!selectionFlags[rand]) {
				queue.enqueue(inputs[rand]);
				selectionFlags[rand] = true;
				selected++;
			}
		}
			
		for (int i = 0; i < k; i++)
			StdOut.println(queue.dequeue());
	}
}
