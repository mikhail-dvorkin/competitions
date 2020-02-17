package topcoder;
import java.util.*;

public class TheEmpireStrikesBack {
	int M = 1_000_000_007;
	
    public int find(int ax, int bx, int cx, int ay, int by, int cy, int n, int m) {
    	int[] x = new int[n];
    	int[] y = new int[n];
    	x[0] = ax;
    	y[0] = ay;
    	for (int i = 1; i < n; i++) {
    		x[i] = (int) ((x[i - 1] * (long) bx + cx) % M);
    		y[i] = (int) ((y[i - 1] * (long) by + cy) % M);
    	}
    	Point[] all = new Point[n];
    	for (int i = 0; i < n; i++) {
    		all[i] = new Point(x[i], y[i]);
    	}
    	Arrays.sort(all);
    	ArrayList<Point> points = new ArrayList<>();
    	for (Point p : all) {
    		while (!points.isEmpty() && points.get(points.size() - 1).y <= p.y) {
    			points.remove(points.size() - 1);
    		}
    		points.add(p);
    	}
    	long low = -1;
    	long high = M;
    	while (low + 1 < high) {
    		long t = (low + high) / 2;
    		int i = 0;
    		int take = 0;
    		for (;;) {
    			if (i == points.size()) {
    				break;
    			}
    			take++;
    			long yMin = points.get(i).y - t;
    			while (i + 1 < points.size() && points.get(i + 1).y >= yMin) {
    				i++;
    			}
    			long xMax = points.get(i).x + t;
    			while (i < points.size() && points.get(i).x <= xMax) {
    				i++;
    			}
    		}
    		if (take <= m) {
    			high = t;
    		} else {
    			low = t;
    		}
    	}
    	return (int) high;
    }
    
    class Point implements Comparable<Point> {
    	int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point that) {
			if (x == that.x) {
				return that.y - y;
			}
			return x - that.x;
		}
    }

}
