package topcoder.tco2007.round2;
import java.util.*;

public class WordSplit {
	public String[] pieces(String s) {
		int l = s.length();
		String[][][] a = new String[l + 1][l + 1][];
		for (int i = 0; i < l; i++) {
			a[1][i] = new String[]{s.substring(i, i + 1)};
		}
		for (int j = 2; j <= l; j++) {
			for (int i = 0; i + j <= l; i++) {
				String[] best = null;
				String[] cur = null;
				String[] tmp;
				boolean[] were = new boolean['z' + 1];
				for (int k = i; k < i + j; k++) {
					if (were[s.charAt(k)])
						break;
					were[s.charAt(k)] = true;
					tmp = a[j - (k + 1 - i)][k + 1];
					if (tmp == null) {
						cur = new String[1];
					} else {
						cur = new String[tmp.length + 1];
						System.arraycopy(tmp, 0, cur, 0, tmp.length);
					}
					cur[cur.length - 1] = s.substring(i, k + 1);
					Arrays.sort(cur);
					if (better(cur, best)) {
						best = cur;
					}
				}
				a[j][i] = best;
			}
		}
		return a[l][0];
	}

	private boolean better(String[] a, String[] b) {
		if (b == null)
			return true;
		if (a.length < b.length)
			return true;
		if (a.length > b.length)
			return false;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals(b[i]))
				continue;
			if (a[i].compareTo(b[i]) < 0)
				return true;
			return false;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(new WordSplit().pieces("facetiously").length);
	}
}
