package topcoder.srm780;

import java.util.ArrayList;

public class Prominence {
	public long sumOfProminences(int n, int[] coef, int[] idx, int[] val) {
		int[] h = new int[n + 2];
		for (int i = 0; i < n; i++) {
			int parity = i % 2;
			int a = coef[3*parity];
			int b = coef[3*parity+1];
			int c = coef[3*parity+2];
			h[i + 1] = (int) (((((long) a * i + b) % 1000000007) * i + c) % 1000000007);
		}
		for (int j = 0; j < idx.length; j++) {
			h[idx[j] + 1] = val[j];
		}
		h[0] = h[n + 1] = -1;
		int[] p = p(h);
		int[] hr = new int[h.length];
		for (int i = 1; i <= n; i++) {
			hr[i] = h[n + 1 - i];
		}
		int[] q = p(hr);
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += Math.min(p[i], q[n + 1 - i]);
		}
		return ans;
	}

	int[] p(int[] h) {
		int n = h.length;
		ArrayList<Integer> high = new ArrayList<>();
		ArrayList<Integer> low = new ArrayList<>();
		high.add(Integer.MAX_VALUE);
		low.add(0);
		int[] p = new int[n];
		for (int i = 1; i + 1 < n; i++) {
			if (h[i] > h[i - 1]) {
				while (high.size() > 0 && high.get(high.size() - 1) <= h[i]) {
					high.remove(high.size() - 1);
					low.set(low.size() - 2, Math.min(low.get(low.size() - 2), low.get(low.size() - 1)));
					low.remove(low.size() - 1);
				}
				if (h[i] > h[i + 1]) {
					p[i] = h[i] - low.get(low.size() - 1);
				}
				high.add(h[i]);
				low.add(h[i]);
			} else {
				low.set(low.size() - 1, Math.min(low.get(low.size() - 1), h[i]));
			}
		}
		return p;
	}

	public static void main(String[] args) {
		System.out.println(new Prominence().sumOfProminences(5, new int[] {1, 2, 3, 4, 5, 6}, new int[] {0, 1, 2, 3, 4}, new int[] {3, 3, 1, 2, 0}));
		System.out.println(new Prominence().sumOfProminences(10, new int[] {0, 0, 0, 0, 0, 0}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 9, 8}, new int[] {500, 300, 400, 200, 400, 700, 100, 300, 300, 100}));
		// [5, {1, 2, 3, 4, 5, 6}, {0, 1, 2, 3, 4}, {3, 3, 1, 2, 0}]:2
	}
}
