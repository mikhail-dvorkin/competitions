package topcoder;
import java.util.Arrays;

public class ProductThreshold {
	public long subarrayCount(int n, int limit, int[] prefix, int spread, int offset) {
		int[] a = Arrays.copyOf(prefix, n);
		int seed = Math.abs(a[prefix.length - 1]);
		for (int i = prefix.length; i < n; i++) {
			seed = (int) ((seed * 1103515245L + 12345) % (1 << 31));
			a[i] = (seed % spread) + offset;
		}
		long ans = 0;
		int prevZero = -1;
		for (int i = 0; i < n; i++) {
			if (a[i] != 0) {
				continue;
			}
			ans += solve(a, prevZero + 1, i, limit);
			ans += (i - prevZero) * (long) (n - i);
			prevZero = i;
		}
		ans += solve(a, prevZero + 1, n, limit);
		return ans;
	}

	long solve(int[] a, int from, int to, int limit) {
		if (from == to) {
			return 0;
		}
		int high = from;
		int p = 1;
		long ans = 0;
		int signLow = 1;
		int signHigh = 1;
		int posHigh = 1;
		int posLow = 1;
		for (int low = from; low < to; low++) {
			while (high < to) {
				long pNew = ((long) p) * a[high];
				if (Math.abs(pNew) > limit && high != low) {
					break;
				}
				p = (int) pNew;
				signHigh *= Integer.signum(a[high]);
				if (signHigh == 1) {
					posHigh++;
				}
				high++;
				if (signHigh == 1) {
					ans += (high - from + 1) - posHigh;
				} else {
					ans += posHigh;
				}
				if (p > limit) {
					ans--;
				}
			}
			
			int pos = posHigh - posLow;
			if (signLow == 1) {
				ans += pos;
			} else {
				ans += (high - low) - pos;
			}
			
			p /= a[low];
			signLow *= Integer.signum(a[low]);
			if (signLow == 1) {
				posLow++;
			}
		}
		return ans;
	}
}
