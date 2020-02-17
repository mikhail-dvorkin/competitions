package topcoder.pilot_round2;
public class TwistedMatrix {
	public String[] solve(String[] a, String[] b) {
		int hei = a.length;
		int wid = a[0].length();
		char[][] c = new char[hei][];
		for (int i = 0; i < hei; i++) {
			c[i] = a[i].toCharArray();
		}
		char[] aa = new char[hei * wid];
		String ans = null;
		for (int i = 0; i + 1 < hei; i++) {
			for (int j = 0; j + 1 < wid; j++) {
				for (int d = 0; d < 2; d++) {
					turn(c, i, j, d);
					boolean ok = true;
					int z = -1;
					for (int x = 0; x < hei; x++) {
						for (int y = 0; y < wid; y++) {
							z++;
							char p = c[x][y];
							char q = b[x].charAt(y);
							if (p == '?' && q == '?') {
								aa[z] = '0';
								continue;
							}
							if (p == '?' || q == '?') {
								aa[z] = (char) (p ^ q ^ '?');
								continue;
							}
							if (p == q) {
								aa[z] = p;
								continue;
							}
							ok = false;
						}
					}
					turn(c, i, j, d ^ 1);
					if (!ok) {
						continue;
					}
					String s = new String(aa);
					if (ans == null || s.compareTo(ans) < 0) {
						ans = s;
					}
				}
			}
		}
		if (ans == null) {
			return new String[0];
		}
		String[] answer = new String[hei];
		for (int i = 0; i < hei; i++) {
			answer[i] = ans.substring(0, wid);
			ans = ans.substring(wid);
		}
		return answer;
	}

	int[] dx = new int[]{0, 0, 1, 1};
	int[] dy = new int[]{0, 1, 1, 0};
	char[] t = new char[4];

	private void turn(char[][] c, int i, int j, int d) {
		for (int k = 0; k < 4; k++) {
			t[k] = c[i + dx[k]][j + dy[k]];
		}
		for (int k = 0; k < 4; k++) {
			c[i + dx[k]][j + dy[k]] = t[(k + (1 + 2 * d)) % 4];
		}
	}

}
