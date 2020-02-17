package topcoder;
public class TheChroniclesOfAmber {
	public double minimumTime(int[] princeX, int[] princeY, int[] destinationX, int[] destinationY) {
		int n = princeX.length;
		double ans = 0;
		for (int i = 0; i < n; i++) {
			double d = Math.hypot(princeX[i] - destinationX[i], princeY[i] - destinationY[i]);
			ans = Math.max(ans, d);
		}
		for (int left = 0; left < n; left++) {
			double cur = 0;
			for (int i = 0; i < n; i++) {
				double pr = 1e100;
				for (int j = 0; j < n; j++) {
					if (j == left) {
						continue;
					}
					double c = Math.hypot(destinationX[i] - princeX[j], destinationY[i] - princeY[j]);
					pr = Math.min(pr, c);
				}
				cur = Math.max(cur, pr);
			}
			ans = Math.min(ans, cur);
		}
		return ans;
	}
}
