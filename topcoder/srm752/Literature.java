package topcoder.srm752;
import java.util.*;

public class Literature {
	public double expectation(int n, int[] hand, int[] history) {
		boolean[] mine = new boolean[3 * n];
		for (int i = 0; i < n; i++) {
			hand[i] -= 1;
			mine[hand[i]] = true;
		}
		TreeSet<Integer> setA = new TreeSet<>();
		TreeSet<Integer> setB = new TreeSet<>();
		for (int i = 0; i < history.length; i++) {
			history[i] -= 1;
			if (i % 3 == 0 || mine[history[i]]) {
				continue;
			}
			TreeSet<Integer> set = (i % 3 == 1) ? setA : setB;
			set.add(history[i]);
			if (set.size() == n) {
				return i + 1;
			}
		}
		int a = n - setA.size();
		int b = n - setB.size();
		double[][][] e = new double[a + 1][b + 1][3];
		for (int i = 1; i <= a; i++) {
			for (int j = 1; j <= b; j++) {
				double pA = i / (2.0 * n);
				double pB = j / (2.0 * n);
				double pNone = (1 - pA) * (1 - pB);
				double pACond = pA / (1 - pNone);
				e[i][j][0] = 3 / (1 - pNone) - 3 + pACond * (2 + e[i - 1][j][2]) + (1 - pACond) * (3 + e[i][j - 1][0]);
				e[i][j][1] = e[i][j][0] - 1;
				e[i][j][2] = 1 + pB * e[i][j - 1][0] + (1 - pB) * e[i][j][0];
			}
		}
		return history.length + e[a][b][history.length % 3];
	}
}
