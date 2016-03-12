import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item> {
	private Node first, last;
	
	private class Node {
		private Item item;
		private Node next, prev;
		
		Node(Item item) {
			this.item = item;
		}
	}
	
	private class DequeIterator implements Iterator<Item> {
		private Node current;

		DequeIterator() {
			current = first;
		}

		public boolean hasNext() {
			return (current != null);
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			Node punt = current;
			current = current.next;	
			return punt.item;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public Deque() {
		first = null;
		last = null;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	
	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();
		Node node = new Node(item);
		
		Node punt = first;
		if (!isEmpty())
			punt.prev = node;
		else 
			last = node;
		
		first = node;
		first.next = punt;
		first.prev = null;
	}
	
	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException();
		Node node = new Node(item);
		
		Node punt = last;
		if (!isEmpty())
			punt.next = node;
		else 
			first = node;
		
		last = node;
		last.next = null;
		last.prev = punt;
	}
	
	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		Node punt = first;
		first = first.next;
		if (isEmpty()) {
			last = null;
		} else {
			first.prev = null;			
		}
		
		return punt.item;
	}
	
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		Node punt = last;
		last = last.prev;
		if (last == null) {
			first = null;
		} else {
			last.next = null;			
		}
		
		return punt.item;
	}
	
	public boolean isEmpty() {
		return (first == null);
	}
	
	public int size() {
		Node punt;
		int size = 0;
		
		for (punt = first; punt != null; punt = punt.next)
			size++;
		return size;
	}
	

	public static void main(String[] args) {
		Deque<String> deque = new Deque<String>();
		//deque.removeFirst();
		deque.addFirst("prima");
		deque.removeLast();
/*
		deque.addFirst("seconda");
		deque.addLast("ultima");
		deque.addLast("ultimissima"); */
		
		StdOut.println("Prima tranche:");
		for (String stringa : deque)
			StdOut.println(stringa);
		StdOut.print("\n\n");
		
		deque.removeFirst();
		deque.removeLast();
		
		StdOut.println("Seconda tranche:");
		for (String stringa : deque)
			StdOut.println(stringa);
		StdOut.print("\n\n");

		Iterator<String> it = deque.iterator();
		StdOut.println("Terza tranche:");
		while (it.hasNext())
			StdOut.println(it.next());

		deque.addFirst("ulteriore");
		deque.addLast("ultima ulteriore");
		StdOut.println("\nQuarta tranche:");
		it = deque.iterator();
		while (it.hasNext())
			StdOut.println(it.next());

		//it.remove();
		//it.next();
		deque.addFirst(null);
	}
	
}

