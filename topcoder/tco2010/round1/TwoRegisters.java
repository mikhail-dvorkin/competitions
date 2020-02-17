package topcoder.tco2010.round1;
public class TwoRegisters {
	public String minProg(int a) {
		for (int b = 1; b <= a; b++) {
			check(a, b);
		}
		return ans;
	}

	String ans;

	private void check(int a, int b) {
		StringBuilder sb = new StringBuilder();
		while (a + b > 2) {
			if (a > b) {
				sb.append("X");
				a -= b;
			} else if (a < b) {
				sb.append("Y");
				b -= a;
			} else {
				return;
			}
			if (ans != null && sb.length() > ans.length() || sb.length() > 50) {
				return;
			}
		}
		String s = sb.reverse().toString();
		if (ans == null || s.length() < ans.length() || s.length() == ans.length() && s.compareTo(ans) < 0) {
			ans = s;
		}
	}

}
