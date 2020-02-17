package topcoder;
import java.util.*;

public class ReconstructNumber {
	static final int T = 10;
	
	public String smallest(String comparisons) {
		int n = comparisons.length() + 1;
		boolean[][] p = new boolean[n][T];
		int[][] how = new int[n][T];
		for (int i = 0; i < how.length; i++) {
			Arrays.fill(how[i], T);
		}
		for (int d = 0; d < T; d++) {
			p[n - 1][d] = true;
		}
		for (int i = n - 2; i >= 0; i--) {
			for (int d = 0; d < T; d++) {
				for (int e = 0; e < T; e++) {
					boolean nice;
					switch (comparisons.charAt(i)) {
					case '<': nice = d < e; break;
					case '>': nice = d > e; break;
					case '=': nice = d == e; break;
					case '!': nice = d != e; break;
					default: throw new RuntimeException();
					}
					if (!nice || !p[i + 1][e]) {
						continue;
					}
					p[i][d] = true;
					how[i][d] = Math.min(how[i][d], e);
				}
			}
		}
		for (int d = 1; d < T; d++) {
			if (!p[0][d]) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n; i++) {
				sb.append("" + d);
				d = how[i][d];
			}
			return sb.toString();
		}
		return "";
	}
}
