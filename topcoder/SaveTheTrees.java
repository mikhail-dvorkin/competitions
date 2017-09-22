package topcoder;
import java.util.*;

public class SaveTheTrees {
	public int minimumCut(int[] x, int[] y, int[] h) {
		int n = x.length;
		int[] xx = x.clone();
		int[] yy = y.clone();
		boolean[] in = new boolean[n];
		int[] t = new int[n];
		for (int i = 0; i < n; i++) {
			t[i] = i;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - 1; j++) {
				if (h[t[j]] < h[t[j + 1]]) {
					int c = t[j];
					t[j] = t[j + 1];
					t[j + 1] = c;
				}
			}
		}
		Arrays.sort(xx);
		Arrays.sort(yy);
		int ans = n;
		for (int i1 = 0; i1 < n; i1++) {
			for (int i2 = i1; i2 < n; i2++) {
				for (int j1 = 0; j1 < n; j1++) {
					for (int j2 = j1; j2 < n; j2++) {
						int per = 2 * (xx[i2] - xx[i1] + yy[j2] - yy[j1]);
						int cur = n;
						int per1 = 0;
						for (int i = 0; i < n; i++) {
							if (xx[i1] <= x[i] && x[i] <= xx[i2] && yy[j1] <= y[i] && y[i] <= yy[j2]) {
								in[i] = true;
							} else {
								in[i] = false;
								per1 += h[i];
								cur--;
							}
						}
						int i = 0;
						while (i < n && per1 < per) {
							if (in[t[i]]) {
								per1 += h[t[i]];
								cur--;
							}
							i++;
						}
						if (per1 >= per) {
							ans = Math.min(ans, n - cur);
						}
					}
				}
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		int[] b = new int[40];
		int[] c = new int[40];
		for (int i = 0; i < 40; i++) {
			b[i] = 100 * i + 1;
			c[i] = 1;
		}
		int a = new SaveTheTrees().minimumCut(
				new int[]{55, 67, 100, 38, 80, 98, 47, 58, 61, 33},
				new int[]{87, 17, 20, 7, 57, 19, 23, 68, 27, 39},
				new int[]{6, 2, 8, 8, 31, 25, 23, 19, 45, 4});
		a = new SaveTheTrees().minimumCut(b, b, b);
		System.out.println(a);
	}
}
