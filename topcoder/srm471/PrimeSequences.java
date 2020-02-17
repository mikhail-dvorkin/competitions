package topcoder.srm471;
public class PrimeSequences {
	public int getLargestGenerator(int n, int d) {
		boolean[] isPrime = new boolean[n + 1];
		int[] a = new int[n + 1];
		for (int i = 2; i <= n; i++)
			isPrime[i] = true;
		for (int i = 2, j; (j = i * i) <= n; i++) {
			if (!isPrime[i])
				continue;
			do {
				isPrime[j] = false;
				j += i;
			} while (j <= n);
		}
		int ans = -1;
		for (int i = 2; i <= n; i++) {
			if (isPrime[i]) {
				a[i] = 1 + a[i / 2];
				if (a[i] >= d) {
					ans = i;
				}
			}
		}
		return ans;
	}

}
