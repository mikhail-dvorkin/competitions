package topcoder;
public class Decipherability {
	public String check(String s, int k) {
		int n = s.length();
		int best = n;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (s.charAt(i) == s.charAt(j)) {
					best = Math.min(best, j - i);
				}
			}
		}
		return (best <= k && k < n) ? "Uncertain" : "Certain";
	}
}
