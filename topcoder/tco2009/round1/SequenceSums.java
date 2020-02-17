package topcoder;

public class SequenceSums {
	public int[] sequence(int n, int L) {
		for (int i = L; i <= 100; i++) {
			if ((2 * n) % i != 0)
				continue;
			int a = (2 * n) / i;
			a -= i + 1;
			if (a < -2 || a % 2 != 0)
				continue;
			int[] ans = new int[i];
			for (int j = 0; j < i; j++) {
				ans[j] = a / 2 + j + 1;
			}
			return ans;
		}
		return new int[0];
	}
}
