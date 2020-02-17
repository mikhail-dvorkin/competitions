package topcoder;
public class DriveFailures {
	public double[] failureChances(double[] failureProb) {
		int n = failureProb.length;
		double[][] p = new double[n + 1][n + 1];
		p[0][0] = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				p[i + 1][j] += p[i][j] * (1 - failureProb[i]);
				p[i + 1][j + 1] += p[i][j] * (failureProb[i]);
			}
		}
		return p[n];
	}
}
