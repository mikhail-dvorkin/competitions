package topcoder;
// http://mat.fsv.cvut.cz/gcg/sbornik/tomiczkova.pdf
public class TrianglePainting {
    public double expectedArea(int[] x1, int[] y1, int[] x2, int[] y2, int[] pInt) {
    	int n = pInt.length;
    	double[] area = new double[n + 1];
    	double[] p = new double[n + 1];
    	for (int j = 0; j < n; j++) {
    		p[j] = pInt[j] * 0.01;
    		if (x1[j] * y2[j] - x2[j] * y1[j] < 0) {
    			int t = x1[j]; x1[j] = x2[j]; x2[j] = t;
    			t = y1[j]; y1[j] = y2[j]; y2[j] = t;
    		}
    		area[j + 1] = area[j] + (x1[j] * y2[j] - x2[j] * y1[j]) * 0.5 * p[j];
    		int[] x = new int[]{0, x1[j], x2[j], 0};
    		int[] y = new int[]{0, y1[j], y2[j], 0};
    		for (int i = 0; i < 3; i++) {
    			int dx = x[i] - x[i + 1];
				int dy = y[i] - y[i + 1];
				double length = Math.hypot(dx, dy);
				int cx = -dy;
				int cy = dx;
				double z = 0;
				for (int k = 0; k < j; k++) {
					int v = Math.max(0, Math.max(cx * x1[k] + cy * y1[k], cx * x2[k] + cy * y2[k]));
					z += v * p[k];
				}
				z /= Math.hypot(cx, cy);
				area[j + 1] += length * z * p[j];
    		}
    	}
    	return area[n];
    }

}
