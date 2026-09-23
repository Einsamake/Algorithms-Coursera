#include <iostream>
#include <vector>
#include <random>
#include <cmath>
#include <stdexcept>
class UnionFind {
private:
    std::vector<int> parent;
    std::vector<int> size;

public:
    explicit UnionFind(int n) : parent(n), size(n, 1) {
        for (int i = 0; i < n; ++i) {
            parent[i] = i;
        }
    }

    int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    void unite(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        }
        if (size[rootP] < size[rootQ]) {
            size[rootQ] += size[rootP];
            parent[rootP] = rootQ;
        }
        else {
            size[rootP] += size[rootQ];
            parent[rootQ] = rootP;
        }
    }

    bool isConnected(int p, int q) {
        return find(p) == find(q);
    }
};

class Percolation {
private:
    int n;
    std::vector<std::vector<int>> grid;
    UnionFind uf;
    int virtualTop;
    int virtualBottom;
    int member=0;

    int unionIndex(int row, int col) const {
        return row * n + col;
    }

    void connectNeighbor(int row, int col) {
        int origin = unionIndex(row, col);
        int directions[4][2] = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (auto& direction : directions) {
            int neighborRow = row + direction[0];
            int neighborCol = col + direction[1];
            if (neighborRow < 0 || neighborCol < 0 || neighborRow >= n || neighborCol >= n) {
                continue;
            }
            if (isOpen(neighborRow, neighborCol)) {
                uf.unite(unionIndex(neighborRow,neighborCol), origin );
            }
        }
        if (row == 0) {
            uf.unite(virtualTop, origin);
        }
        if (row == n - 1) {
            uf.unite(virtualBottom, origin);
        }
    }
    void validate(int row, int col) const {
        if (row<0||col<0||row>=n||col>=n) {
            throw std::invalid_argument("IllegalArgumentException");
        }
    }

public:
    explicit Percolation(int size) :
        n(size),
        grid(size, std::vector<int>(size)),
        uf(size * size + 2),
        virtualTop(size * size ),
        virtualBottom(size * size+1)
    {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = 0;
            }
        }
    }

    void open(int row, int col) {
        validate(row, col);
        if (isOpen(row,col)) {
            return;
        }
        grid[row][col] = 1;
        connectNeighbor(row, col);
        member++;
    }

    bool isOpen(int row, int col)  {
        validate(row,col);
        if (grid[row][col] == 1) {
            return true;
        }
        return false;
    }

    bool isFull(int row, int col)  {
        validate(row,col);

        if (!isOpen(row,col)) {
            return false;
        }
        if (uf.isConnected(virtualTop,unionIndex(row,col))) {
            return true;
        }
        return false;
    }

    int numberOfOpenSites() const {
        return member;
    }

    bool percolates() {
        if (uf.isConnected(virtualTop, virtualBottom)) {
            return true;
        }
        return false;
    }
};
class PercolationStats {
private:
    int n_;
    int trials_;
    std::vector<double> threshold;
    static int validate(int n ,int trials) {
        if (n<=0||trials<=0) {
            throw std::invalid_argument("IllegalArgumentException");
        }
        return n;
    }
public:
    PercolationStats(int n, int trials):
    n_(validate(n,trials)),
    trials_(trials),
    threshold(trials)
    {
        std::mt19937 gen(std::random_device{}());
        std::uniform_int_distribution<int> dist(1, n);
        for (int i = 0; i < trials; i++) {
            Percolation p(n);
            while (!p.percolates()) {
                int row = dist(gen)-1;
                int col = dist(gen)-1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            threshold[i]=static_cast<double> (p.numberOfOpenSites())/(n*n);
        }
    }

    double mean() const {
        double sum = 0;
        for (double i: threshold) {
            sum += i;
        }
        return sum / threshold.size();
    }
    double stddev() const {
        double sum = 0;
        double meanValue = mean();
        for (double i: threshold) {
            double diff = meanValue - i;
            sum += diff * diff;
        }
        return std::sqrt(sum / (threshold.size()-1));
    }
    double confidenceLo() const {
        return mean() - 1.96*stddev()/std::sqrt(threshold.size());
    }
    double confidenceHi() const {
        return mean() + 1.96*stddev()/std::sqrt(threshold.size());
    }
};
int main() {
    int n,trails;
    std::cin >> n >> trails;
    PercolationStats stats(n,trails);

}
