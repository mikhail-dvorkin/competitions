package topcoder.srm364;
public class YankeeSwap {
	public String sequenceOfSwaps(String[] p) {
		int n = p.length;
		boolean[] taken = new boolean['Z'];
		char[] takes = new char[n];
		iloop:
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j < n; j++) {
				char c = p[i].charAt(j);
				if (!taken[c]) {
					takes[i] = c;
					taken[c] = true;
					continue iloop;
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++)
			sb.append((char)('A' + i));
		String ans = "";
		for (int i = 0; i < n; i++) {
			StringBuilder s = new StringBuilder(sb);
			for (int j = n - 1; j > i; j--) {
				int k = s.indexOf("" + takes[j]);
				char t = s.charAt(j);
				s.setCharAt(j, sb.charAt(k));
				s.setCharAt(k, t);
			}
			int get = s.indexOf("" + takes[i]);
			if (get == i) {
				ans += '-';
				continue;
			}
			ans += (char) ('a' + get);
			char t = sb.charAt(i);
			sb.setCharAt(i, sb.charAt(get));
			sb.setCharAt(get, t);
		}
		return ans;
	}

	public static void main(String[] args) {
		String a = new YankeeSwap().sequenceOfSwaps(new String[]{"BAC",
				 "ACB",
				 "BCA"});
		System.out.println(a);
	}
}
