/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */


import java.util.Arrays;

public class BruteCollinearPoints {
    private int count = 0;
    private LineSegment[] segs = new LineSegment[4];

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        Point[] sorted = Arrays.copyOf(points, points.length);

        for (Point p : sorted) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        Arrays.sort(sorted);

        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i].compareTo(sorted[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    for (int m = k + 1; m < points.length; m++) {
                        Point s = points[m];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            Point[] tmpArr = { p, q, r, s };
                            Arrays.sort(tmpArr);
                            if (count == segs.length) {
                                segs = Arrays.copyOf(segs, segs.length * 2);
                            }
                            segs[count++] = new LineSegment(tmpArr[0], tmpArr[3]);
                        }
                    }
                }
            }
        }

    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return count;
    }        // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(segs, count);
    }                // the line segments
}
