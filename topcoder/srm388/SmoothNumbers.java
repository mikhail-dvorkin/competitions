package topcoder.srm388;
public class SmoothNumbers {
	public int countSmoothNumbers(int n, int k) {
		boolean[] isPrime = new boolean[n + 1];
		boolean[] bad = new boolean[n + 1];
		for (int i = 2; i <= n; i++)
			isPrime[i] = true;
		for (int i = 2; i <= n; i++) {
			if (!isPrime[i])
				continue;
			if (i > k)
				bad[i] = true;
			for (int j = 2 * i; j <= n; j += i) {
				isPrime[j] = false;
				if (i > k)
					bad[j] = true;
			}
		}
		int ans = 0;
		for (int i = 1; i <= n; i++)
			if (!bad[i])
				ans++;
		return ans;
	}
}
