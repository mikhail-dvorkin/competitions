package topcoder;
public class StoneGame {
	public int getScore(int[] t, int[] s) {
		int n = t.length;
		for (int i = 0; i < n; i++) {
			if (s[i] == 1)
				continue;
			s[i] = (s[i] % 2 == 1) ? 3 : 2;
		}
		int ans = 0;
		boolean me = true;
		while (true) {
			int j = -1;
			for (int i = 0; i < n; i++) {
				if (s[i] > 0) {
					if (j < 0) {
						j = i;
					} else if (s[i] == 1 && s[j] != 1) {
						j = i;
					} else if (s[i] == 1 && s[j] == 1) {
						if (t[i] > t[j]) {
							j = i;
						}
					} else if (s[j] == 1) {
					} else if (s[j] % 2 == 0 && s[i] % 2 == 1) {
						j = i;
					} else if (s[j] % 2 == s[i] % 2) {
						if (s[i] % 2 == 1 && t[i] > t[j])
							j = i;
						if (s[i] % 2 == 0 && t[i] < t[j])
							j = i;
					}
				}
			}
			if (j < 0) {
				return ans;
			}
			s[j]--;
			if (s[j] == 0 && me)
				ans += t[j];
			me = !me;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new StoneGame().getScore(new int[]{1, 3, 2, 1, 1, 3, 3, 1, 1, 3}, new int[]{100, 1, 1, 69, 13, 87, 1, 96, 35, 68}));
	}
}
