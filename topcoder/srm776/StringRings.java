package topcoder.srm776;

public class StringRings {
	public double expectedRings(int a, int b) {
		double ans = 0;
		while (b > 0) {
			ans += 1.0 / (2 * a + b);
			b--;
		}
		while (a > 0) {
			ans += 1.0 / (2 * a - 1);
			a--;
		}
		return ans;
	}
}
