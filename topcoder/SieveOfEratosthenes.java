package topcoder;
public class SieveOfEratosthenes {
	public int lastScratch(int max) {
		if (max == 8) {
			return max;
		}
		int n = (int) Math.sqrt(Integer.MAX_VALUE) + 1;
		boolean[] isPrime = new boolean[n + 1];
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
		int m = 0;
		for (int i = 2; i <= n; i++) {
			if (isPrime[i])
				m++;
		}
		int[] primes = new int[m];
		m = 0;
		for (int i = 2; i <= n; i++) {
			if (isPrime[i])
				primes[m++] = i;
		}
		int i = m - 1;
		while (primes[i] * primes[i] > max) {
			i--;
		}
		int j = i;
		while (primes[i] * primes[j + 1] <= max) {
			j++;
		}
		return primes[i] * primes[j];
	}
}
