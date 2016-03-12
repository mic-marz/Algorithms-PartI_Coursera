import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;


public class Board {

	private int[][] board;
	private int dimension;
	private int[][] goalBoard;
	private int hamming = 0;
	private int manhattan = 0;
	private int whiteI, whiteJ;


	public Board(int[][] blocks) {
		dimension = blocks.length;
		board = new int[dimension][dimension];
		goalBoard = new int[dimension][dimension];

		int wanI, wanJ;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {	
				board[i][j] = blocks[i][j];

				if (board[i][j] != 0) {
					wanI = (board[i][j] - 1) / dimension;
					wanJ = (board[i][j] - 1) % dimension;
					int deltaI = Math.abs(wanI - i);
					int deltaJ = Math.abs(wanJ - j);
					//StdOut.println("Valori per l'elemento " + board[i][j] + " di deltaI " + deltaI + " e di deltaJ " + deltaJ);

					if ((deltaI != 0) || (deltaJ != 0)) {
						//StdOut.println("Incremento di hamming per l'elemento " + board[i][j] + " con indici " + i + j);
						hamming++;
						manhattan += deltaI + deltaJ;
					}
				} else {
					whiteI = i;
					whiteJ = j;
					wanI = dimension - 1;
					wanJ = dimension - 1;
				}
				goalBoard[wanI][wanJ] = board[i][j]; 
			}
		}   
	}

	public int dimension() {
		return dimension;
	}

	public int hamming() {
		return hamming;
	}

	public int manhattan() {
		return manhattan;
	}

	public boolean isGoal() {
		return (hamming == 0);
	}

	public Board twin() {
		Board twin = new Board(board);
		twin.board[0][0] = board[0][1];
		twin.board[0][1] = board[0][0];
		return twin;
	}

	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null)
			return false;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (this.board[i][j] != ((Board)y).board[i][j])
					return false;
			}
		}
		return true;
	}

	public Iterable<Board> neighbors() {
		int[][][] neighBoards = new int[4][dimension][];  
		List<Board> neighs = new ArrayList<>();
		
		for (int i = 0; i < dimension; i++) {
			for (int k = 0; k < 4; k++) {
				neighBoards[k][i] = Arrays.copyOf(board[i], board[i].length);
			}
		}

		if (whiteI > 0) {
			neighBoards[0][whiteI - 1][whiteJ] = 0;
			neighBoards[0][whiteI][whiteJ] = board[whiteI - 1][whiteJ];
			neighs.add(new Board(neighBoards[0]));
		}
		if (whiteI < dimension - 1) {
			neighBoards[1][whiteI + 1][whiteJ] = 0;
			neighBoards[1][whiteI][whiteJ] = board[whiteI + 1][whiteJ];
			neighs.add(new Board(neighBoards[1]));
		}

		if (whiteJ > 0) {
			neighBoards[2][whiteI][whiteJ - 1] = 0;
			neighBoards[2][whiteI][whiteJ] = board[whiteI][whiteJ - 1];
			neighs.add(new Board(neighBoards[2]));
		}
		if (whiteJ < dimension - 1) {
			neighBoards[3][whiteI][whiteJ + 1] = 0;
			neighBoards[3][whiteI][whiteJ] = board[whiteI][whiteJ + 1];
			neighs.add(new Board(neighBoards[3]));
		}
		return neighs;
	}

	public String toString() {
		StringBuilder output = new StringBuilder("\n" + Integer.valueOf(dimension).toString());
		for (int i = 0; i < dimension; i++) {
			output.append("\n");
			for (int j = 0; j < dimension; j++) {
				output.append(String.format("%3d", board[i][j]) + " ");
			}
		}
		return output.toString();
	}
	
	public static void main(String[] args) {
		int[][] boardArray2 = {{1,2}, {3,0}};
		Board board3 = new Board(boardArray2);
		if (board3.isGoal())
			StdOut.println("Board3 is goal");
		else
			StdOut.println("Board3 is not goal");
		
		int[][] boardArray = {{1,2,3},{4,0,6},{7,8,5}};
		Board board = new Board(boardArray);
		StdOut.println("Dimension of Board: " + board.dimension);
		StdOut.println("Board:" + board.toString());
		StdOut.println("Goal:" + new Board(board.goalBoard).toString());
		
		StdOut.println("Hamming distance of Board: " + board.hamming());
		StdOut.println("Manhattan distance of Board: " + board.manhattan());

		if (board.isGoal())
			StdOut.println("Board is goal!");
		else
			StdOut.println("Board is not goal!");
		Board twin = board.twin();
		StdOut.println("Twin of board: " + twin.toString());
		if (board.equals(twin))
			StdOut.println("Twin equals to board");
		else
			StdOut.println("Twin not equals to board");
		
		Board board2 = new Board(boardArray);
		if (board.equals(board2))
			StdOut.println("Board2 equals to board");
		else
			StdOut.println("Board2 not equals to board");
		
		StdOut.println("Neighbors of board:");
		for (Board neigh : board.neighbors()) {
			StdOut.println("Neighbor: " + neigh.toString());
		}
	}
}
