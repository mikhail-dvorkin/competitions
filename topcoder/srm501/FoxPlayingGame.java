package topcoder;
public class FoxPlayingGame {
	public double theMax(int nA, int nB, int paramA, int paramB) {
		int n = nA + nB;
		double scoreA = paramA / 1000.0;
		double scoreB = paramB / 1000.0;
		double[][] min = new double[n + 1][nA + 1];
		double[][] max = new double[n + 1][nA + 1];
		for (int i = 0; i <= n; i++) {
			for (int a = 0; a <= nA; a++) {
				min[i][a] = 1e200;
				max[i][a] = -1e200;
			}
		}
		min[0][0] = 0;
		max[0][0] = 0;
		for (int i = 0; i < n; i++) {
			for (int a = 0; a <= nA && a <= i; a++) {
				if (a < nA) {
					min[i + 1][a + 1] = Math.min(min[i + 1][a + 1], min[i][a] + scoreA);
					max[i + 1][a + 1] = Math.max(max[i + 1][a + 1], max[i][a] + scoreA);
				}
				if (i - a < nB) {
					min[i + 1][a] = Math.min(min[i + 1][a], min[i][a] * scoreB);
					min[i + 1][a] = Math.min(min[i + 1][a], max[i][a] * scoreB);
					max[i + 1][a] = Math.max(max[i + 1][a], min[i][a] * scoreB);
					max[i + 1][a] = Math.max(max[i + 1][a], max[i][a] * scoreB);
				}
			}
		}
		return max[n][nA];
	}

}
