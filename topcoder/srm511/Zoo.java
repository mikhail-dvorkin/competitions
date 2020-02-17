package topcoder.srm511;
import java.util.*;

public class Zoo {
	public long theCount(int[] a) {
		int n = a.length;
		Arrays.sort(a);
		int i = 0;
		int v = 0;
		long ans = 1;
		for (;;) {
			if (i + 1 < n && a[i] == v && a[i + 1] == v) {
				ans *= 2;
				i += 2;
				v += 1;
			} else {
				break;
			}
		}
		long t = 1;
		while (i < n) {
			if (a[i] != v) {
				return 0;
			}
			i++;
			v++;
			t = 2;
		}
		return ans * t;
	}

}
