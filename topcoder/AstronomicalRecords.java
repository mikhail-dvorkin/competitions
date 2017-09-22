package topcoder;
public class AstronomicalRecords {
    public int minimalPlanets(int[] a, int[] b) {
    	int ans = a.length + b.length - 1;
    	for (int i = 0; i < a.length; i++) {
    		for (int j = 0; j < b.length; j++) {
    			int[][] d = new int[i + 2][j + 2];
    			for (int ii = 0; ii <= i; ii++) {
        			for (int jj = 0; jj <= j; jj++) {
        				d[ii + 1][jj + 1] = Math.max(d[ii + 0][jj + 1], d[ii + 1][jj + 0]);
        				if (a[ii] * 1L * b[j] == b[jj] * 1L * a[i]) {
        					d[ii + 1][jj + 1] = Math.max(d[ii + 1][jj + 1], d[ii + 0][jj + 0] + 1);
        				}
        			}
    			}
    			ans = Math.min(ans, a.length + b.length - d[i + 1][j + 1]);
    		}
    	}
    	return ans;
    }

}
