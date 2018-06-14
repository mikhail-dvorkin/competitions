package topcoder;
public class SubarrayAverages {
	public double[] findBest(int[] arr) {
		int n = arr.length;
		int x = 0;
		double[] ans = new double[n];
		while (x < n) {
			int bestSum = arr[x];
			int bestNum = 1;
			int sum = bestSum;
			int num = bestNum;
			for (int i = x + 1; i < n; i++) {
				sum += arr[i];
				num++;
				if (sum * (long) bestNum < bestSum * (long) num) {
					bestSum = sum;
					bestNum = num;
				}
			}
			for (int i = x; i < x + bestNum; i++) {
				ans[i] = bestSum * 1.0 / bestNum;
			}
			x += bestNum;
		}
		return ans;
	}
}
