package topcoder.tccc2007.round1a;
public class LetterInterchange {
	public int[] interchangeWhich(String[] s1, String[] s2) {
		StringBuilder sb = new StringBuilder();
		for (String t : s1)
			sb.append(t);
		for (String t : s2)
			sb.append(t);
		String s = sb.toString();
		int n = s.length();
		int[] am = new int['z' + 1];
		for (int i = 0; i < n; i++) {
			am[s.charAt(i)]++;
		}
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			am[c]--;
			for (char d = 'a'; d < c; d++) {
				if (am[d] > 0) {
					for (int j = n - 1;; j--)
						if (s.charAt(j) == d)
							return new int[]{i, j};
				}
			}
		}
		for (int i = 0; i < n; i++) {
			am[s.charAt(i)]++;
		}
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			am[c]--;
			if (am[c] > 0) {
				for (int j = i + 1; j < n; j++)
					if (s.charAt(j) == c)
						return new int[]{i, j};
			}
		}
		return new int[]{n - 2, n - 1};
	}
}
