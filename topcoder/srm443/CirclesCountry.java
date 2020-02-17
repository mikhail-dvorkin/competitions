package topcoder.srm443;
public class CirclesCountry {
    public int leastBorders(int[] X, int[] Y, int[] R, int x1, int y1, int x2, int y2) {
        int res = 0;
        int n = X.length;
        for (int i = 0; i < n; i++) {
        	double r1 = Math.hypot(X[i] - x1, Y[i] - y1);
        	double r2 = Math.hypot(X[i] - x2, Y[i] - y2);
        	if ((r1 > R[i]) ^ (r2 > R[i])) {
        		res++;
        	}
        }
        return res;
    }

}
