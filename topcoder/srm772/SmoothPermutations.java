package topcoder.srm772;

public class SmoothPermutations {
	int M;
	SegmentsTreeSimple st;

	int solve(int n, int k, int x) {
		return (x < k) ? 0 : (int) (st.get(0, n - k) * (long) st.get(x - k, x) % M);
	}

	class SegmentsTreeSimple {
		int n;
		int[] a;
		int size;

		SegmentsTreeSimple(int n) {
			this.n = n;
			size = 1;
			while (size <= n) {
				size *= 2;
			}
			a = new int[2 * size];
			for (int i = 0; i < n; i++) {
				a[size + i] = i + 1;
			}
			for (int i = size - 1; i >= 1; i--) {
				a[i] = (int) (a[2 * i] * (long) a[2 * i + 1] % M);
			}
		}

		int get(int from, int to) {
			from += size;
			to += size;
			int res = 1;
			while (from < to) {
				if (from % 2 == 1) {
					res = (int) (res * (long) a[from] % M);
					from++;
				}
				if (to % 2 == 1) {
					to--;
					res = (int) (res * (long) a[to] % M);
				}
				from /= 2;
				to /= 2;
			}
			return res;
		}
	}

	public long countPermutations(int t, int m, int mx, int seed, int[] prefN, int[] prefK, int[] prefX) {
		this.M = m;
		st = new SegmentsTreeSimple(mx);
		int[] a = new int[3 * t];
		a[0] = seed;
		for (int i = 1; i < a.length; i++) {
			a[i] = (int) ((a[i - 1] * 1103515245L + 12345) % 2147483648L);
		}
		long ans = 0;
		for (int i = 0; i < prefN.length; i++) {
			ans += solve(prefN[i], prefK[i], prefX[i]);
		}
		for (int i = prefN.length; i < t; i++) {
			int n = a[i] % mx + 1;
			ans += solve(n, a[t + i] % n + 1, a[2 * t + i] % n + 1);
		}
		return ans;
	}
}
