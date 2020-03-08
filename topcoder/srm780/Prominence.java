package topcoder.srm780;

public class Prominence {
	public static final int M = 1_000_000_007;

	public long sumOfProminences(int n, int[] k, int[] idx, int[] val) {
		int[] h = new int[n + 2];
		for (int i = 0; i < n; i++) {
			int parity = i % 2;
			int a = k[3 * parity];
			int b = k[3 * parity + 1];
			int c = k[3 * parity + 2];
			h[i + 1] = (int) (((((long) a * i + b) % M) * i + c) % M);
		}
		for (int j = 0; j < idx.length; j++) {
			h[idx[j] + 1] = val[j];
		}
		h[0] = h[n + 1] = -1;
		int[] p = prominencesLeft(h);
		int[] hRev = new int[h.length];
		for (int i = 1; i <= n; i++) {
			hRev[i] = h[n + 1 - i];
		}
		int[] pRev = prominencesLeft(hRev);
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += Math.min(p[i], pRev[n + 1 - i]);
		}
		return ans;
	}

	int[] prominencesLeft(int[] h) {
		int n = h.length;
		int[] high = new int[n];
		high[0] = Integer.MAX_VALUE;
		int[] low = new int[n];
		int[] p = new int[n];
		int size = 1;
		for (int i = 1; i + 1 < n; i++) {
			if (h[i] <= h[i - 1]) {
				low[size - 1] = Math.min(low[size - 1], h[i]);
				continue;
			}
			while (high[size - 1] <= h[i]) {
				low[size - 2] = Math.min(low[size - 2], low[size - 1]);
				size--;
			}
			if (h[i] > h[i + 1]) {
				p[i] = h[i] - low[size - 1];
			}
			high[size] = low[size] = h[i];
			size++;
		}
		return p;
	}
}
