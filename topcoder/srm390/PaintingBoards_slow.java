package topcoder;
import java.util.Arrays;

public class PaintingBoards_slow {
	public double minimalTime(int[] boardLength, int[] painterSpeed) {
		int len = boardLength.length;
		int n = painterSpeed.length;
		double inf = 1e100;
		int max = 1 << n;
		double[][] a = new double[len + 1][max];
		for (int i = 0; i <= len; i++) {
			Arrays.fill(a[i], inf);
		}
		a[0][0] = 0;
		for (int i = 0; i < len; i++) {
			for (int m = 0; m < max; m++) {
				if (a[i][m] >= inf)
					continue;
				for (int p = 0; p < n; p++) {
					if ((m & (1 << p)) != 0)
						continue;
					int mm = m | (1 << p);
					int length = 0;
					for (int j = i + 1; j <= len; j++) {
						length += boardLength[j - 1];
						double time = length * 1.0 / painterSpeed[p];
						double cur = Math.max(a[i][m], time);
						a[j][mm] = Math.min(a[j][mm], cur);
					}
				}
			}
		}
		Arrays.sort(a[len]);
		return a[len][0];
	}
	
	public static void main(String[] args) {
		System.out.println(new PaintingBoards_slow().minimalTime(
				new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				new int[]{9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1})
			);
	}
}
