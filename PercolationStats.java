import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	
	private final double[] arr;
	private final int T;
	private static final double distr = 1.96;
	
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

    	if(trials <= 0)
    		throw new IllegalArgumentException();
    	if(n <= 0)
    		throw new IllegalArgumentException();
    	
    	T = trials;
    	int num = 0;
    	int i = 0;
    	
    	arr = new double[trials];
    	
    	for(; i < trials; i++) {

    	    Percolation perc = new Percolation(n);
    			
    	    while(!perc.percolates()){
    	    	
    			int siteRow = 1 + StdRandom.uniform(n);
    			int siteCol = 1 + StdRandom.uniform(n);

    			if(!perc.isOpen(siteRow, siteCol))
    			{
    				perc.open(siteRow, siteCol);
    				num++;
    			}
    			
    		}
    		
    		arr[i] = (double)num / (n * n);
    		num = 0;
 
    	}
    }

    // sample mean of percolation threshold
    public double mean() {

    	return StdStats.mean(arr);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	
    	return StdStats.stddev(arr);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	
    	return mean() - distr * stddev() / Math.sqrt(T); 
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	
    	return mean() + distr * stddev() / Math.sqrt(T);
    	
    }

   // test client (see below)
   public static void main(String[] args) {

	   PercolationStats stats = new PercolationStats(20, 10000);
	   System.out.print(stats.mean());
   }
}