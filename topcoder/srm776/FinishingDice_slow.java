package topcoder.srm776;

import java.util.Arrays;

public class FinishingDice_slow {
	int n;
	int[] a, mustHave, have, die;
	int aLen = 1, bLen = 1;

	public int[] finish(int[] die1, int[] die2) {
		n = die1.length;
		Arrays.sort(die1);
		Arrays.sort(die2);
		die = new int[2 * n];
		for (int i = 0; i < n; i++) {
			die[i] = die1[i] - 1;
			die[n + i] = die2[i] - 1;
		}
		a = new int[2 * n];
		mustHave = new int[4 * n];
		for (int i = 0; i <= 2 * n - 2; i++) {
			mustHave[i] = Math.min(i + 1, 2 * n - 1 - i);
		}
		have = new int[4 * n];
		have[0] = 1;
		if (die[0] <= 0 && die[n] <= 0 && search(1, 0)) {
			for (int i = 0; i < 2 * n; i++) a[i]++;
			return a;
		}
		return new int[0];
	}

	boolean search(int x, int forbidA) {
		if (aLen == n && bLen == n) return true;
		while (have[x] == mustHave[x]) x++;
		if (x != forbidA && set(false, x)) {
			if (search(x, 0)) return true;
			remove(false, x);
		}
		if (set(true, x)) {
			if (search(x, x)) return true;
			remove(true, x);
		}
		return false;
	}

	boolean set(boolean b, int v) {
		boolean good = true;
		if (b ^ (v % 2 == 0)) {
			if (bLen == n || v < die[n + bLen]) return false;
			a[n + bLen++] = v;
			for (int i = 0; i < aLen; i++) {
				int s = v + a[i];
				if (++have[s] > mustHave[s]) good = false;
			}
		} else {
			if (aLen == n || v < die[aLen]) return false;
			a[aLen++] = v;
			for (int i = 0; i < bLen; i++) {
				int s = v + a[n + i];
				if (++have[s] > mustHave[s]) good = false;
			}
		}
		if (!good) remove(b, v);
		return good;
	}

	void remove(boolean b, int v) {
		if (b ^ (v % 2 == 0)) {
			bLen--;
			for (int i = 0; i < aLen; i++) have[v + a[i]]--;
		} else {
			aLen--;
			for (int i = 0; i < bLen; i++) have[v + a[n + i]]--;
		}
	}
}
