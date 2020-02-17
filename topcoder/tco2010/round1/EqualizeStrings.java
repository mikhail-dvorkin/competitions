package topcoder;
public class EqualizeStrings {
	public String getEq(String s, String t) {
		int n = s.length();
		String ans = "";
		for (int i = 0; i < n; i++) {
			int a = s.charAt(i) - 'a';
			int b = t.charAt(i) - 'a';
			int d = Math.abs(a - b);
			int c;
			if (d >= 13) {
				c = 0;
			} else {
				c = Math.min(a, b);
			}
			ans += (char) ('a' + c);
		}
		return ans;
	}

}
