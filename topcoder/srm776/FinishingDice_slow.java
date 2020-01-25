package topcoder.srm776;

import java.util.Arrays;

public class FinishingDice_slow {
	int n;
	int[] die1, die2;
	int[] ans = new int[0];

	public int[] finish(int[] die1, int[] die2) {
		this.die1 = die1;
		this.die2 = die2;
		n = die1.length;
		Arrays.sort(die1);
		Arrays.sort(die2);
		for (int i = 0; i < n; i++) {
			die1[i]--;
			die2[i]--;
		}
		search(new int[n], new int[n], 0, 0, 0, -1);
		return ans;
	}

	boolean search(int[] a, int[] b, int aLen, int bLen, int x, int forbidA) {
		if (aLen == n && bLen == n && x == 2 * n - 1) {
			ans = Arrays.copyOf(a, 2 * n);
			System.arraycopy(b, 0, ans, n, n);
			for (int i = 0; i < 2 * n; i++) {
				ans[i]++;
			}
			return true;
		}
		int haveX = 0;
		for (int i = 0; i < aLen; i++) {
			for (int j = 0; j < bLen; j++) {
				if (a[i] + b[j] == x) {
					haveX++;
				}
			}
		}
		int mustHaveX = Math.min(x + 1, 2 * n - 1 - x);
		if (haveX > mustHaveX) return false;
		if (haveX < mustHaveX) {
			if (aLen < n && x >= die1[aLen] && x != forbidA) {
				a[aLen] = x;
				if (search(a, b, aLen + 1, bLen, x, -1)) return true;
			}
			if (bLen < n && x >= die2[bLen]) {
				b[bLen] = x;
				if (search(a, b, aLen, bLen + 1, x, x)) return true;
			}
			return false;
		}
		return search(a, b, aLen, bLen, x + 1, -1);
	}
}
