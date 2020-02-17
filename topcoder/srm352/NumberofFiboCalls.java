package topcoder.srm352;
public class NumberofFiboCalls {
	public int[] fiboCallsMade(int n) {
		int[] a = new int[n + 5];
		int[] b = new int[n + 5];
		a[0] = 1;
		b[1] = 1;
		for (int i = 2; i <= n; i++) {
			a[i] = a[i - 1] + a[i - 2];
			b[i] = b[i - 1] + b[i - 2];
		}
		return new int[]{a[n], b[n]};
	}
}
