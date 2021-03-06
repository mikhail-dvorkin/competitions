package topcoder.srm783;

public class CardboardBoxes {
	public long count(long s) {
		if (s % 2 == 1) return 0;
		s /= 2;
		long ans = 0;
		for (long i = 1; i * i <= s; i++) {
			if (s % i != 0) continue;
			long j = s / i;
			ans += solve(s, i);
			if (j > i) ans += solve(s, j);
		}
		return ans;
	}

	private long solve(long s, long p) {
		return Math.min(p - 1, s / p / 2);
	}
}
