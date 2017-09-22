package topcoder;
public class RemissiveSwaps {
	boolean[] w = new boolean[1000000];
	public int findMaximum(int N) {
		if (N == 1000000)
			return N;
		dfs(N);
		return ans;
	}
	
	int ans = -1;
	StringBuilder sb = new StringBuilder();
	int[] ten = new int[]{1, 10, 100, 1000, 10000, 100000};
	
	private void dfs(int n) {
		ans = Math.max(ans, n);
		w[n] = true;
		String s = "" + n;
		int l = s.length();
		for (int i = 0; i < s.length(); i++) {
			for (int j = i + 1; j < s.length(); j++) {
				if (s.charAt(i) > '0' && s.charAt(j) > '0') {
					int m = n;
					int ti = ten[l - 1 - i];
					int tj = ten[l - 1 - j];
					int ci = s.charAt(i) - '0';
					int cj = s.charAt(j) - '0';
					m -= ci * ti + cj * tj;
					m += (cj - 1) * ti + (ci - 1) * tj;
					if (!w[m])
						dfs(m);
				}
			}
		}
	}
}
