package topcoder;
public class SimilarPairs {
	int n;
	int[][][] dif;
	
	public int howManyElements(String[] text, int K) {
		StringBuilder sb = new StringBuilder();
		for (String s : text)
			sb.append(s);
		String s = sb.toString();
		n = s.length();
		dif = new int[n][n][15];
		for (int t = 0; t < 15; t++) {
			int len = 1 << t;
			int two = len / 2;
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					if (j - i < len || n - j < len)
						continue;
					if (t == 0) {
						dif[i][j][t] = (s.charAt(i) == s.charAt(j)) ? 0 : 1;
					} else {
						dif[i][j][t] = dif[i][j][t - 1] + dif[i + two][j + two][t - 1];
					}
				}
			}
		}
		int[] a = new int[n];
		int[] were = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int lo = 0; // diff <= K
				int hi = Math.min(j - i, n - j); // diff > K
				int max;
				if (diff(i, j, hi) <= K) {
					max = hi;
				} else {
					while (lo + 1 < hi) {
						int mi = (lo + hi) >> 1;
						int d = diff(i, j, mi);
						if (d > K)
							hi = mi;
						else
							lo = mi;
					}
					max = lo;
				}
				a[i] = Math.max(a[i], max);
				a[j] = Math.max(a[j], max);

				lo = 0; // diff <= 0
				hi = Math.min(j - i, n - j); // diff > 0
				if (diff(i, j, hi) <= 0) {
					max = hi;
				} else {
					while (lo + 1 < hi) {
						int mi = (lo + hi) >> 1;
						int d = diff(i, j, mi);
						if (d > 0)
							hi = mi;
						else
							lo = mi;
					}
					max = lo;
				}
				were[j] = Math.max(were[j], max);
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++)
			ans += a[i] - were[i];
		return ans;
	}

	private int diff(int i, int j, int len) {
		int res = 0;
		for (int t = 0; t < 15; t++) {
			int two = 1 << t;
			if ((len & two) != 0) {
				res += dif[i][j][t];
				i += two;
				j += two;
				len -= two;
			}
			if (len == 0)
				return res;
		}
		return res;
	}
	
	public static void main(String[] args) {
		int a =new SimilarPairs().howManyElements(new String[]{"aaaaaa"}, 1);
		System.out.println(a);
	}
}
