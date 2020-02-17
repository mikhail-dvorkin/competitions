package topcoder.srm466;
public class LotteryCheating {
	public int minimalChange(String ID) {
		int len = ID.length();
		int ans = len;
		for (long x = 0;; x++) {
			int cur = 0;
			String s = Long.toString(x * x);
			if (s.length() > len) {
				break;
			}
			while (s.length() < len) {
				s = "0" + s;
			}
			for (int i = 0; i < ID.length(); i++) {
				if (s.charAt(i) != ID.charAt(i)) {
					cur++;
				}
			}
			ans = Math.min(ans, cur);
		}
		return ans;
	}

// 7590620582

}
