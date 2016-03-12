import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
	private double[] percentages;
	private double mean = 0;
	private double stddev = 0;
	private double confidenceLo = 0;
	private double confidenceHo = 0;
	
	public PercolationStats(int N, int T) {
		if (T <= 0 || N <= 0)
			throw new IllegalArgumentException();
		
		percentages = new double[T];
		
		for (int i = 1; i <= T; i++) {
			Percolation percolation = new Percolation(N);
			int openSites = 0;
			
			while (!percolation.percolates()) {
				int k = StdRandom.uniform(1, N+1);
				int j = StdRandom.uniform(1, N+1);
				
				if (!percolation.isOpen(k, j)) {
					percolation.open(k, j);
					openSites++;
					percentages[i-1] = (double) openSites / (double) (N*N); 
				}
			}
		}
		mean = StdStats.mean(percentages);
		stddev = StdStats.stddev(percentages);
		
		double partial = ((1.96) * stddev)/(Math.sqrt(percentages.length));
		confidenceLo = mean - partial;
		
		partial = ((1.96) * stddev)/(Math.sqrt(percentages.length));
		confidenceHo = mean + partial;
	}
	
	public static void main(String[] args) {		
		PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		
		System.out.println("Mean obtained in experiments: " + percolationStats.mean());
		System.out.println("Std dev obtained in experiments: " + percolationStats.stddev());
		System.out.println("Confidence Lo obtained in experiments: " + percolationStats.confidenceLo());
		System.out.println("Confidence Ho obtained in experiments: " + percolationStats.confidenceHi());
	}
	
	public double mean() {
		return mean;
	}
	
	public double stddev() {
		return stddev;
	}
	
	public double confidenceLo() {
		return confidenceLo;	
	}
	
	public double confidenceHi() {
		return confidenceHo;	
	}
}
