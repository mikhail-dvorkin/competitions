package topcoder.tccc2007.round3;
import java.util.*;

public class LazyCat {
	public int maxMiceCount(int[] pos, int[] speed, int d, int[] rest) {
		Arrays.sort(rest);
		int m = pos.length;
		double[] time = new double[m];
		for (int i = 0; i < m; i++) {
			time[i] = (d - pos[i]) * 1.0 / speed[i];
		}
		Arrays.sort(time);
		int n = rest.length;
		int t = 0;
		int j = 0;
		int ans = 0;
		for (int h = 0; h < n; h++) {
			while (j < m && time[j] < t - 1e-10)
				j++;
			if (j >= m)
				continue;
			ans++;
			j++;
			t += rest[h];
		}
		return ans;
	}
}
