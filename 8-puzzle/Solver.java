import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solver {

    private final SearchNode goal;

    private static class SearchNode
            implements Comparable<SearchNode> {

        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int priority;

        SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            int cmp = Integer.compare(
                    this.priority, that.priority
            );

            if (cmp != 0) return cmp;


            return Integer.compare(
                    this.board.manhattan(),
                    that.board.manhattan()
            );
        }
    }
    private static void expand(MinPQ<SearchNode> pq,
                               SearchNode current) {

        for (Board neighbor : current.board.neighbors()) {


            if (current.previous != null &&
                    neighbor.equals(current.previous.board)) {
                continue;
            }

            pq.insert(new SearchNode(
                    neighbor,
                    current.moves + 1,
                    current
            ));
        }
    }


    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();


        MinPQ<SearchNode> pq = new MinPQ<>();


        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        pq.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode found = null;

        while (true) {


            SearchNode current = pq.delMin();

            if (current.board.isGoal()) {
                found = current;
                break;
            }

            expand(pq, current);


            SearchNode twinCurrent = twinPQ.delMin();

            if (twinCurrent.board.isGoal()) {
                break;
            }

            expand(twinPQ, twinCurrent);
        }

        goal = found;
    }


    public boolean isSolvable() {
        return goal != null;
    }


    public int moves() {
        return goal == null ? -1 : goal.moves;
    }


    public Iterable<Board> solution() {
        if (goal == null) return null;

        Deque<Board> path = new ArrayDeque<>();

        for (SearchNode node = goal;
             node != null;
             node = node.previous) {

            path.push(node.board);
        }

        return path;
    }


    public static void main(String[] args) {

        In in = new In(args[0]);

        int n = in.readInt();
        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println(
                    "Minimum number of moves = " + solver.moves()
            );

            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
