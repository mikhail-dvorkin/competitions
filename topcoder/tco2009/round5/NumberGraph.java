package topcoder.tco2009.round5;
import java.util.*;

@SuppressWarnings("unused")
public class NumberGraph {
	public int largestSet(String[] graphSet, int[] joiningSet) {
		StringBuilder sb = new StringBuilder();
		for (String s : graphSet) {
			sb.append(s);
		}
		String[] ss = sb.toString().split(" ");
		int n = ss.length;
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(ss[i]);
		}
		int two = 1;
		while ((joiningSet[0] & two) == 0)
			two *= 2;
		for (int i = 0; i < joiningSet.length; i++) {
			joiningSet[i] /= two;
		}
		int ans = 0;
		for (int x : a) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int v : a) {
				if (v % two == x % two) {
					list.add(v / two);
				}
			}
			ans = Math.max(ans, solve(list, joiningSet));
		}
		return ans;
	}

	private int solve(ArrayList<Integer> list, int[] joiningSet) {
		return 0;
	}
}
