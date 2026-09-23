import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final boolean[] openSites;
    private final WeightedQuickUnionUF ufPercolates;
    private final WeightedQuickUnionUF ufFull;
    private final int virtualTop;
    private final int virtualBottom;
    private int openSiteCount;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        this.n = n;
        int siteCount = n * n;
        this.openSites = new boolean[siteCount];
        this.virtualTop = siteCount;
        this.virtualBottom = siteCount + 1;
        this.ufPercolates = new WeightedQuickUnionUF(siteCount + 2);
        this.ufFull = new WeightedQuickUnionUF(siteCount + 1);
    }

    public void open(int row, int col) {
        validateCoordinates(row, col);
        int site = toIndex(row, col);

        if (openSites[site]) {
            return;
        }

        openSites[site] = true;
        openSiteCount++;

        if (row == 1) {
            ufPercolates.union(site, virtualTop);
            ufFull.union(site, virtualTop);
        }
        if (row == n) {
            ufPercolates.union(site, virtualBottom);
        }

        int[][] directions = {
            { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }
        };

        for (int[] direction : directions) {
            int neighborRow = row + direction[0];
            int neighborCol = col + direction[1];

            if (neighborRow < 1 || neighborRow > n
                    || neighborCol < 1 || neighborCol > n) {
                continue;
            }

            if (isOpen(neighborRow, neighborCol)) {
                int neighbor = toIndex(neighborRow, neighborCol);
                ufPercolates.union(site, neighbor);
                ufFull.union(site, neighbor);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validateCoordinates(row, col);
        return openSites[toIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        validateCoordinates(row, col);
        int site = toIndex(row, col);
        return openSites[site] && ufFull.find(site) == ufFull.find(virtualTop);
    }

    public int numberOfOpenSites() {
        return openSiteCount;
    }

    public boolean percolates() {
        return ufPercolates.find(virtualTop) == ufPercolates.find(virtualBottom);
    }

    private int toIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void validateCoordinates(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row and col must be between 1 and n");
        }
    }
}
