import java.util.NoSuchElementException;


public class IndexMinPQ<Key> {

	// pq array is useful to go from item index to item key 
	private int[] pq;                    // store item indices

	private Key[] keys;                  // store item keys
	// qp array is useful to go from item key to item index 
	private int[] qp;
	private int N;                       // number of items on priority queue

	public IndexMinPQ(int initCapacity) {
		keys = (Key[]) new Object[initCapacity + 1];
		pq = new int[initCapacity + 1];
		qp = new int[initCapacity + 1];
		for (int i = 1; i <= initCapacity; i++) {
			pq[i] = -1;
			qp[i] = -1;			
		}		
		N = 0;
	}

	public IndexMinPQ() {
		this(1);
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	public Key min() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		return keys[1];
	}
	
	public int minIndex() {
		if (!contains(1))
			throw new IllegalArgumentException("No min element present in index priority queue");
		return pq[1];
	}

	// helper function to double the size of the heap array
	private void resize(int capacity) {
		assert capacity > N;
		Key[] temp = (Key[]) new Object[capacity];
		for (int i = 1; i <= N; i++) {
			temp[i] = keys[i];
		}
		keys = temp;
		int[] idxsTmp = new int[capacity];
		for (int i = 1; i <= N; i++) {
			idxsTmp[i] = pq[i];
		}
		pq = idxsTmp;
		idxsTmp = new int[capacity];
		for (int i = 1; i <= N; i++) {
			idxsTmp[i] = qp[i];
		}
		qp = idxsTmp;
		for (int i = N + 1; i < capacity; i++) {
			keys[i] = null;
			pq[i] = -1;
			qp[i] = -1;
		}
	}

	public boolean contains(int idx) {
		return (qp[idx] != -1);
	}

	public void insert(int index, Key x) {
		// double size of array if necessary
		if (N == keys.length - 1) 
			resize(2 * keys.length);
		if (!contains(index)) {
			// add x, and percolate it up to maintain heap invariant
			keys[++N] = x;
			pq[N] = index;
			qp[index] = N;
			swim(N);
		} else 
			throw new IllegalArgumentException("Invalid index argument");
	}

	public void change(int index, Key x) {
		if (!contains(index))
			throw new IllegalArgumentException("Invalid index argument");
		int j = qp[index];
        keys[j] = x;
        // Or swim or sink sets new key in proper head-order
        swim(j);
        sink(j);
	}
	
	public void delete(int index) {
		if (!contains(index))
			throw new IllegalArgumentException("Invalid index argument");
		int j = qp[index];
		
		keys[j] = null;
		pq[j] = -1;
		exch(j,N);
		N--;
		swim(j);
		sink(j);
	}
	
	public int delMin() {
		int minIndex = minIndex();
		delete(minIndex);
		return minIndex;
	}
	
	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/
	private void swim(int k) {
		while (k > 1 && greater(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	private void sink(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && greater(j, j+1)) j++;
			if (!greater(k, j)) break;
			exch(k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for compares and swaps.
	 ***************************************************************************/
	private boolean greater(int i, int j) {
		if ((qp[pq[i]] == -1 ) || (qp[pq[j]] == -1))
			throw new IllegalStateException("Invalid indexes for greater methods");
		return ((Comparable<Key>) keys[i]).compareTo(keys[j]) > 0;
	}

	// Provides exchange even with elements not associated with any indexes in qp array
	private void exch(int i, int j) {
		Key keySwap = keys[i];
		int idxSwap = pq[i];
		keys[i] = keys[j];
		pq[i] = pq[j];
		keys[j] = keySwap;
		pq[j] = idxSwap;
		if (pq[j] != -1)
			qp[pq[j]] = j;
		if (pq[i] != -1)
			qp[pq[i]] = i;
	}
	
	
	public static void main(String[] args) {
		IndexMinPQ<Integer> indexMinPQ = new IndexMinPQ<Integer>();
		indexMinPQ.insert(1, 55);
		indexMinPQ.insert(2, 44);
		indexMinPQ.insert(3, 77);
		indexMinPQ.insert(4, 12);
		System.out.println("Min element in indexMinPQ : " + indexMinPQ.min());
		indexMinPQ.delete(4);
		System.out.println("Min element in indexMinPQ : " + indexMinPQ.min());
		indexMinPQ.delete(1);
		System.out.println("Min element in indexMinPQ : " + indexMinPQ.min());
		System.out.println("Min element index in indexMinPQ : " + indexMinPQ.minIndex());
		indexMinPQ.change(2, new Integer(106));
		System.out.println("Min element in indexMinPQ : " + indexMinPQ.min());
		System.out.println("Min element index in indexMinPQ : " + indexMinPQ.minIndex());
		indexMinPQ.delMin();
		System.out.println("Min element in indexMinPQ : " + indexMinPQ.min());
		System.out.println("Min element index in indexMinPQ : " + indexMinPQ.minIndex());
	}
}
