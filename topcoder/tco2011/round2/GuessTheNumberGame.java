package topcoder.tco2011.round2;
public class GuessTheNumberGame {
	public int possibleClues(int n) {
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
		long ans = 1;
		for (int i = 2; i <= n; i++) {
			if (!isPrime[i]) {
				continue;
			}
			int p = 2;
			long j = i;
			while (j * i <= n) {
				j *= i;
				p++;
			}
			ans = (ans * p) % 1000000007;
		}
		return (int) ans;
	}

}
