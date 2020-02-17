package topcoder;
import java.util.*;

public class TrianglesContainOrigin {
	class Point implements Comparable<Point> {
		int x, y;
		double atan2;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
			atan2 = Math.atan2(y, x);
		}

		@Override
		public int compareTo(Point o) {
			return Double.compare(atan2, o.atan2);
		}
	}
	
	int orient(Point a, Point b) {
		return Integer.signum(a.x * b.y - a.y * b.x);
	}
	
    public long count(int[] x, int[] y) {
    	int n = x.length;
    	Point[] points = new Point[n];
    	for (int i = 0; i < n; i++) {
    		points[i] = new Point(x[i], y[i]);
    	}
    	Arrays.sort(points);
    	long ans = 0;
    	for (int i = 0; i < n; i++) {
    		int k0 = i + 1;
    		while (k0 < n && orient(points[i], points[k0]) > 0) {
    			k0++;
    		}
    		if (k0 == n) {
    			break;
    		}
    		int k = k0;
    		for (int j = i + 1; j < k0; j++) {
    			if (orient(points[j], points[k0]) != 1) {
    				continue;
    			}
    			while (k + 1 < n && orient(points[j], points[k + 1]) > 0) {
    				k++;
    			}
    			ans += k - k0 + 1;
    		}
    	}
    	return ans;
    }

}
