package topcoder.tco2008.round2;
import java.util.*;

public class CreatureTraining {
	public long maximumPower(int[] count, int[] power, int d) {
		int n = count.length;
		long[][][] a = new long[n + 1][d + 1][d + 1];
		long inf = 1000000000000000000L;
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= d; j++) {
				Arrays.fill(a[i][j], -inf);
			}
		}
		a[0][0][0] = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= d; j++) {
				for (int k = 0; k <= d; k++) {
					long v = a[i][j][k];
					if (v < 0)
						continue;
					for (int t = 0; t + j + k <= d; t++) {
						long num = count[i] + j - t;
						if (num < 0)
							continue;
						a[i + 1][t][k + j] = Math.max(a[i + 1][t][k + j], v + num * power[i]);
					}
				}
			}
		}
		long ans = 0;
		for (int k = 0; k <= d; k++) {
			ans = Math.max(ans, a[n][0][k]);
		}
		return ans;
	}
}
