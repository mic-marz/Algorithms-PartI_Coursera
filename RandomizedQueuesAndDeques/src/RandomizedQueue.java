import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] array;
	private int tail;
	private int capacity;
	
	
	private class RandomizedQueueIterator implements Iterator<Item> {
		private int current;
		private Item[] randomArray;

		RandomizedQueueIterator() {
			current = 0;
			randomArray = (Item[]) new Object[tail];
			for (int i = 0; i < tail; i++)  {
				randomArray[i] = array[i]; 
			}
			StdRandom.shuffle(randomArray);
		}

		public boolean hasNext() {
			return (current < tail);
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			Item toRet = randomArray[current];
			current++;
			return toRet;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public RandomizedQueue() {
		capacity = 1;
		array = (Item[]) new Object[capacity];
		tail = 0;
	}
	
	public boolean isEmpty() {
		return (tail == 0);
	}
	
	public int size() {
		return tail;
	}
	
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();
		
		if (tail == capacity)
			resize(2 * capacity);		
		
		array[tail++] = item;
	}
	
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		int index = (StdRandom.uniform(size()));
		Item returnValue = array[index];

		tail--;
		array[index] = array[tail];
		array[tail] = null;
		
		if (size() > 0 && size() == capacity / 4)
			resize(capacity / 2);
		
		return returnValue;
	}
	
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		int index = (StdRandom.uniform(size()));
		Item returnValue = array[index];
		
		return returnValue;		
	}
	
	private void resize(int length) {
		Item[] newArray = (Item[]) new Object[length];
		
		for (int i = 0; i < tail; i++)  {
			newArray[i] = array[i]; 
		}
		array = newArray;
		capacity = length;
	}

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}
	

	public static void main(String[] args) {
		RandomizedQueue<Integer> ints = new RandomizedQueue<Integer>();
		//ints.dequeue();

		ints.enqueue(Integer.valueOf(5));
		StdOut.println("Queue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);
		
		for (int s = 100; s < 1000; s++)
			ints.enqueue(s);
		
		ints.dequeue();
		StdOut.println("\nQueue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);

		ints.enqueue(Integer.valueOf(10));
		StdOut.println("\nQueue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);

		ints.enqueue(Integer.valueOf(21));
		StdOut.println("\nQueue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);
		
		ints.enqueue(Integer.valueOf(33));
		StdOut.println("\nQueue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);
		
		ints.enqueue(Integer.valueOf(17));
		StdOut.println("\nQueue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);
		
		StdOut.println("Sample: " + ints.sample());
		StdOut.println("Sample: " + ints.sample());
		StdOut.println("Sample: " + ints.sample());
				
		ints.dequeue();
		StdOut.println("\nQueue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);
		ints.dequeue();
		StdOut.println("\nQueue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);
		ints.dequeue();
		StdOut.println("\nQueue size: " + ints.size());
		StdOut.println("Queue capacity: " + ints.capacity);
				
		Iterator<Integer> it = ints.iterator();
		for (Integer integer: ints)
			StdOut.println("Integer: " + integer);
		
		//it.remove();
		//ints.enqueue(null);
	}
}