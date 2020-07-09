import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private boolean[][] grid; //false - closed, true - open
	private final int size;
	
	//indexes of virtual top and bottom site
	private static final int topSite = 0;
	private final int bottomSite;
	private int numberOpen = 0;
	
	
	private final WeightedQuickUnionUF uf;
	
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	
    	if(n <= 0)
    		throw new IllegalArgumentException();
    	
    	grid = new boolean[n][n];
    	
    	size = n;
    	bottomSite = n * n + 1;
    	
    	//Adding 2 virtual sites top and bottom
    	uf = new WeightedQuickUnionUF(n * n + 2);
    }
    
    // obtains site index for the uf from row and col values
    private int getSiteIndex(int row, int col)
    {
    	return (row - 1) * size + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
    	
    	
    	if(row <= 0 || col <= 0 || row > size || col > size)
    		throw new IllegalArgumentException();
    	
    	if(isOpen(row, col))
    		return;
    	
    	grid[row - 1][col - 1] = true;
    	numberOpen++;
    	
    	int siteIndex = getSiteIndex(row, col);
    	
    	if(row == 1)
    		uf.union(siteIndex, topSite);
    	else if(row == size)
    		uf.union(siteIndex, bottomSite);
    	
    	if(row != 1){
    		if(isOpen(row - 1, col))
    			uf.union(siteIndex, getSiteIndex(row - 1, col));
    	}
    	
    	if(row != size){
    		if(isOpen(row + 1, col))
    			uf.union(siteIndex, getSiteIndex(row + 1, col));
    	}
    	
    	if(col != 1) {
    		if(isOpen(row, col - 1))
    			uf.union(siteIndex, getSiteIndex(row, col - 1));
    	}
    	
    	if(col != size) {
    		if(isOpen(row, col + 1))
    			uf.union(siteIndex, getSiteIndex(row, col + 1));
    	}    	
    	
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    
    	if(row <= 0 || col <= 0 || row > size || col > size)
    		throw new IllegalArgumentException();
    	
    	return grid[row - 1][col - 1];
    	
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	
    	if(row <= 0 || col <= 0 || row > size || col > size)
    		throw new IllegalArgumentException();
    	
    	return (uf.find(topSite) == uf.find(getSiteIndex(row, col)));
    
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return numberOpen;
    }

    // does the system percolate?
    public boolean percolates() {
    	
    	return uf.find(topSite) == uf.find(bottomSite);
    
    }

    // test client
    public static void main(String[] args) {
    	
    	Percolation test = new Percolation(3);
    	
    	test.open(1, 1);
    	test.open(2, 1);
    	test.open(2, 2);
    	test.open(3, 3);
    	boolean check = test.percolates();
    	System.out.print(check);
    }
}