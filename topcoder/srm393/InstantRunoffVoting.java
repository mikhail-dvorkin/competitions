package topcoder.srm393;
public class InstantRunoffVoting {
	public int winner(String[] voters) {
		int n = voters.length;
		int m = voters[0].length();
		boolean[] out = new boolean[m];
		while (true) {
			int[] v = new int[m];
			for (String s : voters) {
				for (int i = 0; i < m; i++) {
					int c = s.charAt(i) - '0';
					if (out[c])
						continue;
					v[c]++;
					break;
				}
			}
			int least = n + 1;
			for (int i = 0; i < m; i++) {
				if (out[i])
					continue;
				if (2 * v[i] > n)
					return i;
				least = Math.min(least, v[i]);
			}
			boolean ok = false;
			for (int i = 0; i < m; i++) {
				if (out[i])
					continue;
				if (v[i] == least)
					out[i] = true;
				if (!out[i])
					ok = true;
			}
			if (!ok)
				return -1;
		}
	}
}
