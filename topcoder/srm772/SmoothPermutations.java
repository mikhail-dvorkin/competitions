public class SmoothPermutations {
	private int solve(int n, int k, int x, int M) {
		if (x < k) return 0;
		return (int) (st.get(0, n - k) * (long) st.get(x - k, x) % M);
	}

	class SegmentsTreeSimple {
		int n;
		int M;
		int[] a;
		int size;

		SegmentsTreeSimple(int n, int M) {
			this.n = n;
			this.M = M;
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

	SegmentsTreeSimple st;

	public long countPermutations(int T, int M, int MX, int seed, int[] prefN, int[] prefK, int[] prefX) {
		st = new SegmentsTreeSimple(MX, M);
		int[] a = new int[3 * T];
		a[0] = seed;
		for (int i = 1; i < a.length; i++) {
			a[i] = (int) ((a[i - 1] * 1103515245L + 12345) % 2147483648L);
		}
		int[] n = new int[T];
		int[] k = new int[T];
		int[] x = new int[T];
		for (int i = 0; i < prefN.length; i++) {
			n[i] = prefN[i];
			k[i] = prefK[i];
			x[i] = prefX[i];
		}
		for (int i = prefN.length; i < T; i++) {
			n[i] = (a[i] % MX) + 1;
			k[i] = (a[T+i] % n[i]) + 1;
			x[i] = (a[2*T+i] % n[i]) + 1;
		}
		long ans = 0;
		for (int t = 0; t < T; t++) {
			ans += solve(n[t], k[t], x[t], M);
		}
		return ans;
	}

	public static void main(String[] args) {
//		long x = new SmoothPermutations().countPermutations(3, 100, 5, 4, new int[]{5,4}, new int[]{3,2}, new int[]{5,2});
		long x = new SmoothPermutations().countPermutations(100, 999999937 , 123456, 47, new int[]{}, new int[]{}, new int[]{});
		System.out.println(x);
	}
}
