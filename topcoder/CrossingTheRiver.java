package topcoder;
import java.util.*;

public class CrossingTheRiver {
	public String isItEvenPossible(int waterWidth, int landWidth, int[] b, int depth) {
		Arrays.sort(b);
		int M = b[b.length - 1];
		int[] count = new int[M + 1];
		for (int h : b) {
			count[h]++;
		}
		iloop:
		for (int x : b) {
			int[] a = count.clone();
			for (int i = depth + 1; i <= x; i++) {
				if (a[i] == 0) {
					continue iloop;
				}
				a[i]--;
			}
			int n1 = waterWidth - (x - depth);
			if (n1 < 0) {
				continue iloop;
			}
			if (x == depth) {
				int p1 = 0;
				for (int i = depth; i <= M; i++) {
					if (i <= x) {
						p1 += a[i];
					}
				}
				if (p1 >= waterWidth) {
					return "POSSIBLE";
				}
				continue iloop;
			}
			if (x - depth < depth - 1) {
				continue iloop;
			}
			jloop:
			for (int y = x - depth; y <= M; y++) {
				int[] aa = a.clone();
				for (int i = x - depth + 1; i <= y; i++) {
					if (aa[i] == 0) {
						continue jloop;
					}
					aa[i]--;					
				}
				int n2 = landWidth - (y - (x - depth));
				if (n2 < 0) {
					continue jloop;
				}
				int p1 = 0;
				int p2 = 0;
				int p12 = 0;
				for (int i = depth; i <= M; i++) {
					if (i <= x) {
						p1 += aa[i];
					}
					if ((i >= x - depth) && (i <= y)) {
						p2 += aa[i];
						if (i <= x) {
							p12 += aa[i];
						}
					}
				}
				p1 -= p12;
				p2 -= p12;
				int m1 = Math.max(n1 - p1, 0);
				int m2 = Math.max(n2 - p2, 0);
				if (m1 + m2 <= p12) {
					return "POSSIBLE";
				}
			}
		}
		return "IMPOSSIBLE";
	}

}
