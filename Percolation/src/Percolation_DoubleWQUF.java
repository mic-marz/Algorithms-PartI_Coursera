import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* This implementation is based on a double WeightedQuickUnionUF data structure */
public class Percolation_DoubleWQUF {
	private boolean[][] openings = null;
	
	/* With virtual top and bottom sites (useful for percolates method) */
	private WeightedQuickUnionUF uf = null; 
	
	/* With only virtual top site (it's used by isFull method, because
       it doesn't return correct values (for some site) with a
       virtual bottom site */
	private WeightedQuickUnionUF uf2 = null; 

	private int dim;
	private int topIdx = 0;
	private int bottomIdx = 0;
	
	public Percolation_DoubleWQUF(int N) {
		if (N <= 0)
			throw new IllegalArgumentException();
		dim = N;
		topIdx = N * N;
		bottomIdx = topIdx + 1;

		/* Added 2 virtual sites (top and bottom) */
		uf = new WeightedQuickUnionUF(dim * dim + 2);
		uf2 = new WeightedQuickUnionUF(dim * dim + 2);

		openings = new boolean[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				openings[i][j] = false;
			}
		}
	}
	
	public void open(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();			

		openings[i - 1][j - 1] = true;

		if (dim == 1) {
			uf.union(0, topIdx);
			uf2.union(0, topIdx);
			uf.union(topIdx, bottomIdx);
		}
		else {
			if (i == 1) {
				uf.union((j - 1), topIdx);
				uf2.union((j - 1), topIdx);
			}
			if (i == dim)
				uf.union((dim - 1) * dim + (j - 1), bottomIdx);
			if (i > 1) {
				if (openings[i - 2][j - 1]) {
					uf.union((i - 2) * dim + (j - 1), (i - 1) * dim + (j - 1));
					uf2.union((i - 2) * dim + (j - 1), (i - 1) * dim + (j - 1));
				}
			}
			if (i < dim) {
				if (openings[i][j - 1]) {
					uf.union(i * dim  + (j - 1), (i - 1) * dim + (j - 1));
					uf2.union(i * dim  + (j - 1), (i - 1) * dim + (j - 1));				
				}
			}
			if (j > 1) {
				if (openings[i - 1][j - 2]) {
					uf.union((i - 1) * dim + (j - 1), (i - 1) * dim + (j - 2));
					uf2.union((i - 1) * dim + (j - 1), (i - 1) * dim + (j - 2));
				}
			}
			if (j < dim) {
				if (openings[i - 1][j]) {
					uf.union((i - 1) * dim + (j - 1), (i - 1) * dim + j);
					uf2.union((i - 1) * dim + (j - 1), (i - 1) * dim + j);
				}
			}
		}
	}
	
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();
		
		return openings[i - 1][j - 1];
	}

	public boolean isFull(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();

		return uf2.connected((i - 1) * dim + (j - 1), topIdx);
	}

	public boolean percolates() {
		return uf.connected(topIdx, bottomIdx);
	}
}