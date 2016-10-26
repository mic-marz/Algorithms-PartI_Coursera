public class LinkedBagwithMergeSort<Item extends Comparable<Item>> {
	private Node first;    // beginning of bag
	private int N;         // number of elements in bag
		
	// helper linked list class
	private class Node {
		private Item item;
		private Node next;
	}

	/**
	 * Initializes an empty bag.
	 */
	public LinkedBagwithMergeSort() {
		first = null;
		N = 0;
	}

	/**
	 * Is this bag empty?
	 * @return true if this bag is empty; false otherwise
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Returns the number of items in this bag.
	 * @return the number of items in this bag
	 */
	public int size() {
		return N;
	}

	/**
	 * Adds the item to this bag.
	 * @param item the item to add to this bag
	 */
	public void add(Item item) {
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
	}
	
	// Go on of exactly "int steps" steps in the list  
	private Node navigate(Node ref, int steps) {
		if (ref == null)
			return null;
		while (steps - 1 > 0) {
			if (ref.next != null)
				ref = ref.next;
			else 
				return null;
			steps--;
		}
		return ref;
	}

	// Try to go on of "int steps" steps in the list, until a null is encountered  
	private Node navigateUntilNull(Node ref, int steps) {
		if (ref == null)
			return null;
		while (steps - 1 > 0) {
			if (ref.next != null)
				ref = ref.next;
			else 
				return ref;
			steps--;
		}
		return ref;
	}
	
	public void bottomUpSort() {
		System.out.println("Bottom Up MergeSort for list");
		for(int size = 1; size < N; size += size) {
			System.out.println("Sublists size : " + size);
			Node ref = null;
			Node lo = first;
			while (true) {
				if (lo == null)
					break;
				Node mid = navigate(lo, size);
				Node midNext;
				if (mid == null)
					break;
				midNext = mid.next;
				if (midNext == null)
					break;
				Node hi = navigateUntilNull(midNext, size);
				System.out.println("lo item = " + lo.item.toString());
				System.out.println("mid item = " + mid.item.toString());
				System.out.println("hi item = " + hi.item.toString());
				
				ref = merge(ref, lo, mid, hi);
				lo = ref.next;
			}
		}
	}
	
	private Node updateRef(Node ref, Node toAppend) {
		if (ref == null) {
			ref = toAppend;
			first = ref;
		}
		else {
			ref.next = toAppend;
			ref = ref.next;
		}
		return ref;
	}
	
	private Node merge(Node ref, Node lo, Node mid, Node hi) {
		System.out.println("Inside merge method");
		Node left = lo;
		Node rigth = mid.next;
		Node leftEdge = mid.next;
		Node rigthEdge = hi.next;
		
		System.out.print("left item = ");
		System.out.println(left != null ? left.item.toString() : "null");
		System.out.print("rigth item = ");
		System.out.println(rigth != null ? rigth.item.toString() : "null");
		System.out.print("leftEdge item = ");
		System.out.println(leftEdge != null ? leftEdge.item.toString() : "null");
		System.out.print("rigthEdge item = ");
		System.out.println(rigthEdge != null ? rigthEdge.item.toString() : "null");

		while(left != leftEdge && rigth != rigthEdge) {
			if (rigth.item.compareTo(left.item) < 0) {
				ref = updateRef(ref, rigth);
				rigth = rigth.next;
			} else {
				ref = updateRef(ref, left);
				left = left.next;
			}
		}
		while(left != leftEdge) {
			ref = updateRef(ref, left);
			left = left.next;
		}
		while(rigth != rigthEdge) {
			ref = updateRef(ref, rigth);
			rigth = rigth.next;
		}
		ref.next = rigthEdge;
		return ref;
	}
		
	public void displayContent() {
		Node curr = first;
		while(curr != null) {
			System.out.println("Item: " + curr.item.toString());
			curr = curr.next;
		}
	}

	public static void main(String[] args) {
		
		LinkedBagwithMergeSort<Integer> list = new LinkedBagwithMergeSort<Integer>();
		list.add(13);
		list.add(1);
		list.add(17);
		list.add(8);
		list.add(9);
		list.add(0);
		list.add(26);
		list.add(11);
		list.add(7);
		list.add(81);
		list.add(92);
		list.add(29);
		list.displayContent();
		
		list.bottomUpSort();
		list.displayContent();
	}
}
