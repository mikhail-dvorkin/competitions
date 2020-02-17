package topcoder.srm393;
public class NSegmentDisplay {
	String[] sym;
	String[] pat;
	int n;

	boolean valid(boolean[] yes) {
		for (String p : pat) {
			boolean ok = false;
			sloop:
			for (String s : sym) {
				for (int i = 0; i < n; i++) if (yes[i]) {
					boolean on = p.charAt(i) == '1';
					boolean sh = s.charAt(i) == '1';
					if (sh != on)
						continue sloop;
				}
				ok = true;
			}
			if (!ok)
				return false;
		}
		return true;
	}

	public String brokenSegments(String[] symbols, String[] patterns) {
		sym = symbols;
		pat = patterns;
		n = symbols[0].length();
		boolean[] yes = new boolean[n];
		for (String p : patterns) {
			for (int i = 0; i < n; i++) {
				if (p.charAt(i) == '1')
					yes[i] = true;
			}
		}
		if (!valid(yes))
			return "INCONSISTENT";
		StringBuilder sb = new StringBuilder();
		sb.setLength(n);
		for (int i = 0; i < n; i++) {
			if (yes[i]) {
				sb.setCharAt(i, 'Y');
				continue;
			}
			yes[i] = true;
			if (valid(yes)) {
				sb.setCharAt(i, '?');
			} else {
				sb.setCharAt(i, 'N');
			}
			yes[i] = false;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		new NSegmentDisplay().brokenSegments(new String[]{"1011111","0000011","1110110","1110011","0101011"
				,"1111001","1111101","1000011","1111111","1111011"}, new String[]{"0111111"});
	}
}
