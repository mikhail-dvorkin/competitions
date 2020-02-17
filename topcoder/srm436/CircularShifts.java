package topcoder;
import java.util.*;

public class CircularShifts {
	public int maxScore(int n, int Z0, int A, int B, int M) {
		long[] Z = new long[2 * n];
		Z[0] = Z0 % M;
		for (int i = 1; i < 2 * n; i++) {
			Z[i] = (Z[i - 1] * A + B) % M;
		}
		int[] x = new int[n];
		int[] y = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = (int) (Z[i] % 100);
			y[i] = (int) (Z[i + n] % 100);
		}
		int lim = 200;
		ArrayList<Integer> lx = new ArrayList<Integer>();
		ArrayList<Integer> ly = new ArrayList<Integer>();
		for (int d = 99; d >= 0; d--) {
			for (int i = 0; i < n; i++) {
				if (x[i] == d && lx.size() < lim)
					lx.add(i);
				if (y[i] == d && ly.size() < lim)
					ly.add(i);
			}
		}
		boolean[] check = new boolean[n];
		for (int i = -5; i <= 5; i++) {
			check[(i + 64 * n) % n] = true;
		}
		int[] lix = new int[lx.size()];
		for (int i = 0; i < lx.size(); i++) lix[i] = lx.get(i);
		int[] liy = new int[ly.size()];
		for (int i = 0; i < ly.size(); i++) liy[i] = ly.get(i);
		for (int i : lix) {
			for (int j : liy) {
				int k = i + n - j;
				if (k >= n)
					k -= n;
				if (!check[k]) {
					check[k] = true;
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (!check[i])
				continue;
			int cur = 0;
			int k = i;
			for (int j = 0; j < n; j++) {
				cur += x[k] * y[j];
				k++;
				if (k == n)
					k = 0;
			}
			ans = Math.max(ans, cur);
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(148261627 - new CircularShifts().maxScore(60000, 3045743, 454373, 23945734, 566457379)); // 148261627
	}
}
