import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
	private int moves;
	private List<Board> solution;
	
	private class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private SearchNode previous;
		private int numberOfMoves;
		private int priority;

		@Override
		public int compareTo(SearchNode that) {
			if (this.priority > that.priority)
				return +1;
			else if (this.priority < that.priority)
				return -1;
			else 
				return 0;
		}
		
		private SearchNode(Board board, SearchNode previous) {
			this.board = board;
			this.previous = previous;
			if (previous != null) {
				numberOfMoves = previous.numberOfMoves + 1;				
			} else {
				numberOfMoves = 0;				
			}
			this.priority = numberOfMoves + board.manhattan();
		}
	}
	
	public Solver(Board initial) {
		Board twin = initial.twin();
		SearchNode initialSn = new SearchNode(initial, null);
		SearchNode initialTwinSn = new SearchNode(twin, null);
		
		MinPQ<SearchNode> prioQue = new MinPQ<>();
		MinPQ<SearchNode> prioTwinQue = new MinPQ<>();

		prioQue.insert(initialSn);
		prioTwinQue.insert(initialTwinSn);

		solution = new ArrayList<>();
		SearchNode lastSn = prioQue.delMin();
		SearchNode lastTwinSn = prioTwinQue.delMin();
		solution.add(lastSn.board);

		while (!lastSn.board.isGoal() && !lastTwinSn.board.isGoal()) {	
			for (Board neighBoard : lastSn.board.neighbors()) {
				prioQue.insert(new SearchNode(neighBoard, lastSn));
			}
			for (Board neighBoard : lastTwinSn.board.neighbors()) {
				prioTwinQue.insert(new SearchNode(neighBoard, lastTwinSn));
			}
			lastSn = prioQue.delMin();
			lastTwinSn = prioTwinQue.delMin();
			solution.add(lastSn.board);
		}
		if (!lastSn.board.isGoal()) {
			moves = -1;
		} else {
			moves = lastSn.numberOfMoves;
		}
	}

	public int moves() {
		return moves;
	}
	
	public boolean isSolvable() {
		return (moves != -1);
	}
	
	public Iterable<Board> solution() {
		if (isSolvable()) 
			return solution;
		else 
			return null;
	}
	
		
	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
