import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] board;
    private final int n;
    private final int hamming;
    private final int manhattan;
    private final int blankRow;
    private final int blankCol;


    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException();

        n = tiles.length;
        if (n < 2)
            throw new IllegalArgumentException();

        board = new int[n][n];

        int h = 0;
        int m = 0;
        int br = -1;
        int bc = -1;

        for (int i = 0; i < n; i++) {
            if (tiles[i] == null || tiles[i].length != n)
                throw new IllegalArgumentException();

            for (int j = 0; j < n; j++) {
                int value = tiles[i][j];
                board[i][j] = value;

                if (value == 0) {
                    br = i;
                    bc = j;
                    continue;
                }

                if (value != i * n + j + 1)
                    h++;

                int targetRow = (value - 1) / n;
                int targetCol = (value - 1) % n;

                m += Math.abs(i - targetRow)
                        + Math.abs(j - targetCol);
            }
        }

        if (br == -1)
            throw new IllegalArgumentException();

        hamming = h;
        manhattan = m;
        blankRow = br;
        blankCol = bc;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append('\n');

        for (int[] row : board) {
            for (int value : row)
                sb.append(String.format("%2d ", value));
            sb.append('\n');
        }

        return sb.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (!(y instanceof Board)) return false;

        Board that = (Board) y;

        return n == that.n &&
                Arrays.deepEquals(board, that.board);
    }

    private Board swapped(int r1, int c1, int r2, int c2) {
        int[][] copy = new int[n][];

        for (int i = 0; i < n; i++)
            copy[i] = board[i].clone();

        int temp = copy[r1][c1];
        copy[r1][c1] = copy[r2][c2];
        copy[r2][c2] = temp;

        return new Board(copy);
    }


    private void addNeighbor(List<Board> result, int r, int c) {
        if (r >= 0 && r < n && c >= 0 && c < n)
            result.add(swapped(blankRow, blankCol, r, c));
    }

    public Iterable<Board> neighbors() {
        List<Board> result = new ArrayList<>();

        addNeighbor(result, blankRow - 1, blankCol);
        addNeighbor(result, blankRow + 1, blankCol);
        addNeighbor(result, blankRow, blankCol - 1);
        addNeighbor(result, blankRow, blankCol + 1);

        return result;
    }

    public Board twin() {
        int row = (board[0][0] == 0 || board[0][1] == 0)
                  ? 1 : 0;

        return swapped(row, 0, row, 1);
    }

    public static void main(String[] args) {
        int[][] tiles = {
                { 1, 2, 3 },
                { 4, 0, 5 },
                { 6, 7, 8 }
        };

        Board b = new Board(tiles);

        System.out.println(b);
        System.out.println("Hamming: " + b.hamming());
        System.out.println("Manhattan: " + b.manhattan());

        for (Board neighbor : b.neighbors())
            System.out.println(neighbor);
    }
}
