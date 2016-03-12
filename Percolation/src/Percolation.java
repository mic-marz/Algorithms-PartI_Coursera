import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[] openings = null;
	private boolean[] connectedToTop = null;
	private boolean[] connectedToBottom = null;
	private boolean systemPercolates = false;
	
	private WeightedQuickUnionUF uf = null; 

	private int dim;
	
	public Percolation (int N) {
		if (N <= 0)
			throw new IllegalArgumentException();
		dim = N;
		uf = new WeightedQuickUnionUF(dim * dim + 2);

		openings = new boolean[dim * dim];
		connectedToTop = new boolean[dim * dim];
		connectedToBottom = new boolean[dim * dim];

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				openings[i * dim + j] = false;
				connectedToTop[i * dim + j] = false;
				connectedToBottom[i * dim + j] = false;
			}
		}
	}
	
	public void open(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();			

		int index = (i - 1) * dim + (j - 1);
		openings[index] = true;
		if (dim == 1) {
			connectedToTop[0] = true;
			connectedToBottom[0] = true;
			systemPercolates = true;
		}
		else {
			if (i == 1) 
				connectedToTop[index] = true;
			if (i == dim)
				connectedToBottom[index] = true;
			if (i > 1)
				open_internal(index, index - dim);
			if (i < dim)
				open_internal(index, index + dim);
			if (j > 1) 
				open_internal(index, index - 1);
			if (j < dim)
				open_internal(index, index + 1);
		}
	}
	
	private void open_internal(int index, int secondSiteIndex) {
		boolean newConnectedToTop = false;
		boolean newConnectedToBottom = false;
		if (openings[secondSiteIndex]) {
			int secondSiteRootIndex = uf.find(secondSiteIndex);
			int rootIndex = uf.find(index);
			if (connectedToTop[secondSiteRootIndex] || connectedToTop[rootIndex])
				newConnectedToTop = true;
			if (connectedToBottom[secondSiteRootIndex] || connectedToBottom[rootIndex])
				newConnectedToBottom = true;
			if (newConnectedToTop && newConnectedToBottom)
				systemPercolates = true;
			uf.union(index, secondSiteIndex);
			rootIndex = uf.find(secondSiteIndex);
			connectedToTop[rootIndex] = newConnectedToTop;
			connectedToBottom[rootIndex] = newConnectedToBottom;
		}
	}
	
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();
		int index = (i - 1) * dim + (j - 1);
		return openings[index];
	}

	public boolean isFull(int i, int j) {
		if (i < 1 || i > dim || j < 1 || j > dim)
			throw new IndexOutOfBoundsException();
		int index = (i - 1) * dim + (j - 1);
		return connectedToTop[uf.find(index)];
	}

	public boolean percolates() {
		return systemPercolates;
	}
}