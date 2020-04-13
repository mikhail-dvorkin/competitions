package topcoder.srm783;

public class ABBAReplace {
	public int countSteps(String prefix, int n, int seed, int threshold) {
		int state = seed;
		boolean[] s = new boolean[n];
		for (int i = 0; i < prefix.length(); i++) {
			s[i] = prefix.charAt(i) == 'A';
		}
		for (int i = prefix.length(); i < n; i++) {
			state = (int) ((state * 1103515245L + 12345) & Integer.MAX_VALUE);
			s[i] = state < threshold;
		}
		int ans = 0;
		int zeros = 0;
		for (int i = 0; i < n; i++) {
			if (s[i]) continue;
			int move = i - zeros;
			if (move > 0) ans++;
			ans = Math.max(ans, move);
			zeros++;
		}
		return ans;
	}
}
