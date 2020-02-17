package topcoder.tco2009.round1;

public class KthProbableElement {
	int low;
	int high;
	int m;
	int k;
	double[][] cnk;

	public double probability(int M, int lowerBound, int upperBound, int N, int K) {
		low = lowerBound;
		high = upperBound;
		m = M;
		k = K;
		cnk = new double[m + 1][m + 1];
		for (int i = 0; i <= m; i++) {
			cnk[i][i] = cnk[i][0] = 1;
			for (int j = 1; j < i; j++) {
				cnk[i][j] = cnk[i - 1][j - 1] + cnk[i - 1][j];
			}
		}
		return prob(N) - prob(N - 1);
	}

	private double prob(int n) {
		double p = 0;
		for (int i = k; i <= m; i++) {
			p += prob(n, i);
		}
		return p;
	}

	private double prob(int n, int i) {
		double p1 = (n - low + 1) * 1d / (high - low + 1);
		if (p1 < 0) p1 = 0;
		if (p1 > 1) p1 = 1;
		double p2 = 1 - p1;
		return cnk[m][i] * Math.pow(p1, i) * Math.pow(p2, m - i);
	}
}
