import java.util.Arrays;

public class DoubleWordLadder {
	public String[] transform(String s, String t) {
		int n = s.length();
		int initDiff = 0;
		int x = -1, y = -1;
		char[] third = new char[n];
		int[] todo = new int[n];
		for (int i = 0; i < n; i++) {
			if (s.charAt(i) != t.charAt(i)) {
				initDiff++;
				x = i;
				todo[i] = 2;
			} else {
				if (y == -1 || s.charAt(y) == 'a') {
					y = i;
				}
			}
			third[i] = 'a';
			while (third[i] == s.charAt(i) || third[i] == t.charAt(i)) {
				third[i]++;
			}
		}
		if (initDiff == 0) {
			return new String[] {s};
		}
		if (initDiff == 1) {
			todo[y] = 2;
			initDiff = 2;
		}
		char[] a = s.toCharArray();
		int lastChange = -1;
		String[] ans = new String[2 * initDiff + 1];
		ans[0] = s;
		for (int op = 0; op < 2 * initDiff; op++) {
			int bestI = -1;
			char bestFrom = '.';
			char bestTo = '.';
			for (int i = 0; i < n; i++) {
				if (todo[i] == 0 || i == lastChange) {
					continue;
				}
				char curFrom = a[i];
				char curTo = (a[i] == s.charAt(i)) ? third[i] : t.charAt(i);
				if (bestI == -1 || bestTo > bestFrom) {
					bestI = i; bestFrom = curFrom; bestTo = curTo;
				}
			}
			a[bestI] = bestTo;
			todo[bestI]--;
			ans[op + 1] = new String(a.clone());
			lastChange = bestI;
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println("[ab, aa, ba, bd, cd]\n" +
				"[donation, aonation, aoaation, aoabtion, aolbtion, aolution, solution]");
		String[] ans;
		ans = new DoubleWordLadder().transform("ab", "cd");
		System.out.println(Arrays.toString(ans));
		ans = new DoubleWordLadder().transform("donation", "solution");
		System.out.println(Arrays.toString(ans));
		ans = new DoubleWordLadder().transform("zz", "aa");
		System.out.println(Arrays.toString(ans));
	}
}
