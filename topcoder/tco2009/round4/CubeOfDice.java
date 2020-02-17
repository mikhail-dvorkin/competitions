package topcoder.tco2009.round4;
import java.util.*;

public class CubeOfDice {
	public long minimumSum(int N, int[] values) {
		int[][] a = new int[3][2];
		a[0][0] = values[0];
		a[0][1] = values[5];
		a[1][0] = values[1];
		a[1][1] = values[4];
		a[2][0] = values[2];
		a[2][1] = values[3];
		for (int i = 0; i < 3; i++) {
			if (a[i][0] > a[i][1]) {
				int b = a[i][0];
				a[i][0] = a[i][1];
				a[i][1] = b;
			}
		}
		int max = Math.max(a[0][1], Math.max(a[1][1], a[2][1]));
		int sum = 0;
		for (int i = 0; i < 6; i++) {
			sum += values[i];
		}
		if (N == 1) {
			return sum - max;
		}
		int[] b = new int[3];
		b[0] = a[0][0];
		b[1] = a[1][0];
		b[2] = a[2][0];
		Arrays.sort(b);
		int min1 = b[0];
		int min2 = b[0] + b[1];
		int min3 = b[0] + b[1] + b[2];
		long ans = 0;
		long n = N;
		ans += (4 * (n - 2) * (n - 1) + (n - 2) * (n - 2)) * min1;
		ans += (4 * (n - 1) + 4 * (n - 2)) * min2;
		ans += 4 * min3;
		return ans;
	}
}
