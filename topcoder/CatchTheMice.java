package topcoder;
public class CatchTheMice {
	private static final double eps = 1e-8;

	public double largestCage(int[] xp, int[] yp, int[] xv, int[] yv) {
		int n = xp.length;
		double t = 0;
		double ans = 1e100;
		
		while (true) {
			double tnext = 1e100;
			int x1 = 0;
			double xmin = xp[0] + t * xv[0];
			int x2 = 0;
			double xmax = xp[0] + t * xv[0];
			int y1 = 0;
			double ymin = yp[0] + t * yv[0];
			int y2 = 0;
			double ymax = yp[0] + t * yv[0];
			for (int i = 0; i < n; i++) {
				double xx = xp[i] + t * xv[i];
				if (xx < xmin || Math.abs(xx - xmin) < eps && xv[i] < xv[x1]) {
					x1 = i;
					xmin = xx;
				}
				if (xx > xmax || Math.abs(xx - xmax) < eps && xv[i] > xv[x2]) {
					x2 = i;
					xmax = xx;
				}
				double yy = yp[i] + t * yv[i];
				if (yy < ymin || Math.abs(yy - ymin) < eps && yv[i] < yv[y1]) {
					y1 = i;
					ymin = yy;
				}
				if (yy > ymax || Math.abs(yy - ymax) < eps && yv[i] > yv[y2]) {
					y2 = i;
					ymax = yy;
				}
				for (int j = 0; j < n; j++) {
					int k;
					k = x1;
					if (xv[k] != xv[j]) {
						double tt = (xp[j] - xp[k]) * 1d / (xv[k] - xv[j]);
						if (tt > t + eps)
							tnext = Math.min(tnext, tt);
					}
					k = x2;
					if (xv[k] != xv[j]) {
						double tt = (xp[j] - xp[k]) * 1d / (xv[k] - xv[j]);
						if (tt > t + eps)
							tnext = Math.min(tnext, tt);
					}
					k = y1;
					if (yv[k] != yv[j]) {
						double tt = (yp[j] - yp[k]) * 1d / (yv[k] - yv[j]);
						if (tt > t + eps)
							tnext = Math.min(tnext, tt);
					}
					k = y2;
					if (yv[k] != yv[j]) {
						double tt = (yp[j] - yp[k]) * 1d / (yv[k] - yv[j]);
						if (tt > t + eps)
							tnext = Math.min(tnext, tt);
					}
				}
			}
			int dv = (xv[x2] - xv[x1]) - (yv[y2] - yv[y1]);
			if (dv != 0) {
	 			double tt = ((ymax - ymin) - (xmax - xmin)) / dv;
				if (tt > t + eps)
					tnext = Math.min(tnext, tt);
			}
			double cur = Math.max(ymax - ymin, xmax - xmin);
			ans = Math.min(ans, cur);
			if (xv[x2] - xv[x1] >= 0 && yv[y2] - yv[y1] >= 0)
				break;
			t = tnext;
		}
		
		for (double tt : new double[]{}) {
			double xmin = 1e100;
			double xmax = -1e100;
			double ymin = 1e100;
			double ymax = -1e100;
			for (int i = 0; i < n; i++) {
				double x = xp[i] + tt * xv[i];
				double y = yp[i] + tt * yv[i];
				xmin = Math.min(xmin, x);
				xmax = Math.max(xmax, x);
				ymin = Math.min(ymin, y);
				ymax = Math.max	(ymax, y);
			}
			double cur = Math.max(xmax - xmin, ymax - ymin);
			ans = Math.min(ans, cur);
		}
		return ans;
	}
	
	public static void main(String[] args) {
		double a = new CatchTheMice().largestCage(
				new int[]{50,10,30,15},
				new int[]{-10,30,20,40},
				new int[]{-5,-10,-15,-5},
				new int[]{40,-10,-1,-50});
		System.out.println(a);
	}
}
