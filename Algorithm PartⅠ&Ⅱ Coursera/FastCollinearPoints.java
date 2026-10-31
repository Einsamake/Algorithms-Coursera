/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private int count = 0;
    private LineSegment[] segs = new LineSegment[4];
    private int countSegs = 0;

    private static boolean isSorted(Point[] a, int low, int high, Comparator<Point> cmp) {
        for (int i = low + 1; i <= high; i++) {
            if (cmp.compare((Point) a[i - 1], (Point) a[i]) > 0) {
                return false;
            }// 升序
        }
        return true;
    }

    private static boolean less(Point a, Point b, Comparator<Point> cmp) {
        return cmp.compare((Point) a, (Point) b) < 0;
    }

    private static void merge(Point[] a, Point[] aux, int low, int mid, int high,
                              Comparator<Point> cmp) {
        assert isSorted(a, low, mid, cmp);
        assert isSorted(a, mid + 1, high, cmp);
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }
        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > high) a[k] = aux[i++];
            else if (less(aux[j], aux[i], cmp)) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
        assert isSorted(a, low, high, cmp);
    }

    // private static void sort(Point[] a,Point[] aux,int low,int high){
    //     if(low>=high)return;
    //     int mid= (high+low)/2;
    //     sort(a,aux,low,mid);
    //     sort(a,aux,mid+1,high);
    //     merge(a,aux,low,mid,high);
    // }
    private void addSegment(Point min, Point max) {
        if (count == segs.length) {
            segs = Arrays.copyOf(segs, segs.length * 2);
        }
        segs[count++] = new LineSegment(min, max);
    }

    private static void sort(Point[] a, Comparator<Point> cmp) {
        int N = a.length;
        Point[] aux = new Point[N];
        for (int size = 1; size < N; size = size + size) {
            for (int low = 0; low < N - size; low += size + size) {
                merge(a, aux, low, low + size - 1, Math.min(low + size + size - 1, N - 1), cmp);
            }
        }
    }

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int n = points.length;
        Point[] input = Arrays.copyOf(points, n);
        for (Point p : input) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(input);
        for (int i = 1; i < n; i++) {
            if (input[i].compareTo(input[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        for (Point p : input) {
            Point[] ordered = Arrays.copyOf(input, n);
            sort(ordered, p.slopeOrder());
            int start = 1;

            while (start < n) {
                double slope = p.slopeTo(ordered[start]);
                int end = start + 1;

                while (end < n &&
                        Double.compare(
                                p.slopeTo(ordered[end]),
                                slope) == 0) {
                    end++;
                }
                if (end - start >= 3) {
                    Point min = p;
                    Point max = p;
                    for (int i = start; i < end; i++) {
                        Point q = ordered[i];
                        if (q.compareTo(min) < 0) {
                            min = q;
                        }
                        if (q.compareTo(max) > 0) {
                            max = q;
                        }
                    }
                    if (p.compareTo(min) == 0) {
                        addSegment(min, max);
                    }
                }
                start = end;
            }
        }
    }     // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return count;
    }        // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(segs, count);
    }           // the line segments
}
