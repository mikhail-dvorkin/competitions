package topcoder.tco2018.round3a;
import java.util.Arrays;

public class LeftToRightGame {
	static final int T = 10;

	public String whoWins(int length, int divisor) {
		boolean[] a = new boolean[divisor];
		boolean[] b = new boolean[divisor];
		Arrays.fill(a, length % 2 == 0);
		a[0] = !a[0];
		for (int k = 0; k < length; k++) {
			Arrays.fill(b, false);
			for (int i = 0; i < divisor; i++) {
				for (int j = 0; j < T; j++) {
					if (k == length - 1 && j == 0) {
						continue;
					}
					if (!a[(i * T + j) % divisor]) {
						b[i] = true;
					}
				}
			}
			boolean[] t = a; a = b; b = t;
		}
		return a[0] ? "Alice" : "Bob";
	}
}
