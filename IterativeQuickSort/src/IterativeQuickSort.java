import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class IterativeQuickSort<Item extends Comparable<Item>> {
	
    public  void sort(Item[] a) {
    	Stack<Integer> stack = new Stack<Integer>();
        StdRandom.shuffle(a);
        stack.push(a.length - 1);
        stack.push(0);
        
        while(!stack.isEmpty()) {
        	int lo = stack.pop();
        	int hi = stack.pop();
        	if (hi - lo < 2)
        		continue;
        	int j = partition(a, lo, hi);
        	stack.push(j - 1);
        	stack.push(lo);
        	stack.push(hi);
        	stack.push(j + 1);
        }
    }
	
	// partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
	// and return the index j.
	private int partition(Item[] a, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		Item v = a[lo];
		while (true) { 

			// find item on lo to swap
			while (less(a[++i], v))
				if (i == hi) break;

			// find item on hi to swap
			while (less(v, a[--j]))
				if (j == lo) break;      // redundant since a[lo] acts as sentinel

			// check if pointers cross
			if (i >= j) break;

			exch(a, i, j);
		}

		// put partitioning item v at a[j]
		exch(a, lo, j);

		// now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
		return j;
	}
    
	
    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/ 
     // is v < w ?
     private boolean less(Item v, Item w) {
         return v.compareTo(w) < 0;
     }
         
     // exchange a[i] and a[j]
     private static void exch(Object[] a, int i, int j) {
         Object swap = a[i];
         a[i] = a[j];
         a[j] = swap;
     }
     
     
     public static void main(String[] args) {
    	 Integer[] array = new Integer[10];
    	 array[0] = 11;
    	 array[1] = 10;
    	 array[2] = 9;
    	 array[3] = 8;
    	 array[4] = 7;
      	 array[5] = 6;
    	 array[6] = 5;
    	 array[7] = 4;
    	 array[8] = 3;
    	 array[9] = 2;

    	 IterativeQuickSort<Integer> alg = new IterativeQuickSort<Integer>();
    	 alg.sort(array);
    	 
    	 System.out.println("Display of ordered array:");
    	 for (int i = 0; i < array.length; i++) {
    		 System.out.println("Element: " + array[i]);
    	 }
     }
}
