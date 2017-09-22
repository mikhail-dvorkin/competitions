package topcoder;
import java.util.*;

public class CartInSupermarket {
	public int calcmin(int[] a, int b) {
		Arrays.sort(a);
		int lowTime = 0;
		int highTime = a[a.length - 1];
		while (lowTime + 1 < highTime) {
			int time = (lowTime + highTime) / 2;
			int two = time < Integer.SIZE ? 1 << (time - 1) : Integer.MAX_VALUE;
			long splits = 0;
			for (int v : a) {
				if (v > two) {
					splits += b + 1;
					break;
				}
				int low = -1;
				int high = Math.min(b + 2, two - 1);
				while (low + 1 < high) {
					int s0 = (low + high) / 2;
					int s = s0;
					int cur = 1;
					int t = 0;
					while (cur <= s) {
						s -= cur;
						cur *= 2;
						t++;
					}
					long max = 1L * (cur - s) * (time - t) + 2L * s * (time - t - 1);
					if (v <= max) {
						high = s0;
					} else {
						low = s0;
					}
				}
				splits += high;
				if (splits > b) {
					break;
				}
			}
			if (splits > b) {
				lowTime = time;
			} else {
				highTime = time;
			}
		}
		return highTime;
	}
}
