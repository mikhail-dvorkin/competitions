package hackerrank._101hack43;
import java.util.*;

public class E_heuristic_tl {
	static final int M = 1_000_000_007;

	static Map<Long, Integer> precalc() {
		return solve(100_000, 100_000, 1000, 4);
	}

	static Map<Long, Integer> solve(int n, int k, int lastN, int lastK) {
		Map<Long, Integer> result = new TreeMap<>();
		int[] s = new int[k + 2];
		int[] a = new int[k + 1];
		int[] b = new int[k + 1];
		a[0] = 1;
		for (int i = 2; i <= n; i++) {
			for (int j = 0; j <= k; j++) {
				s[j + 1] = (s[j] + a[j]) % M;
				b[j] = (s[j + 1] - s[Math.max(j + 1 - i, 0)] + M) % M;
			}
			int[] t = a; a = b; b = t;
			if (i > n - lastN) {
				for (int j = k - lastK + 1; j <= k; j++) {
					result.put((((long) i) << 32) + j, a[j]);
				}
			}
		}
		System.out.println(a[k]);
		return result;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		in.close();
		Integer ans = new E_heuristic_tl().new EGenerated().map.get((((long) n) << 32) + k);
		if (ans != null) {
			System.out.println(ans);
			return;
		}
		solve(n, k, 0, 0);
	}

	// REPLACE WITH GENERATED
	public class EGenerated {
		Map<Long, Integer> map;
	}
}