package topcoder.tco2009.round3;
public class CampaignTrail {
	public double probWin(int[] electors, int[] winCurrent, int[] winIfVisited, int visits) {
		int n = electors.length;
		int sum = 0;
		for (int i : electors) {
			sum += i;
		}
		double[][][] a = new double[n + 1][sum + 1][visits + 1];
		for (int x = 0; x <= sum; x++) {
			if (2 * x > sum) {
				for (int v = 0; v <= visits; v++) {
					a[n][x][v] = 1;
				}
			}
		}
		int ss = sum;
		for (int i = n - 1; i >= 0; i--) {
			ss -= electors[i];
			for (int s = 0; s <= ss; s++) {
				for (int v = 0; v <= visits; v++) {
					double p = 0;
					if (v < visits) {
						p = (winIfVisited[i] * a[i + 1][s + electors[i]][v + 1] + (100 - winIfVisited[i]) * a[i + 1][s][v + 1]);
					}
					p = Math.max(p, winCurrent[i] * a[i + 1][s + electors[i]][v] + (100 - winCurrent[i]) * a[i + 1][s][v]);
					a[i][s][v] = p * 0.01;
				}
			}
		}
		return a[0][0][0];
	}
}
