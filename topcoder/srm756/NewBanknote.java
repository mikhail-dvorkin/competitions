package topcoder;
public class NewBanknote {
	int[] a = new int[] {1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000};

	public int[] fewestPieces(int newBanknote, int[] amountsToPay) {
		int largest = a[a.length - 1];
		int[] d = new int[largest + 1];
		for (int i = 1; i < d.length; i++) {
			d[i] = i;
			for (int v : a) {
				if (i >= v) {
					d[i] = Math.min(d[i], d[i - v] + 1);
				}
			}
		}
		int[] ans = new int[amountsToPay.length];
		for (int q = 0; q < amountsToPay.length; q++) {
			int m = ans[q] = amountsToPay[q];
			for (int take = 0; take <= ans[q] && m >= 0; take++, m -= newBanknote) {
				ans[q] = Math.min(ans[q], take + m / largest + d[m % largest]);
			}
		}
		return ans;
	}
}