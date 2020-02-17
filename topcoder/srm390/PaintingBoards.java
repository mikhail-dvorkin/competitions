package topcoder.srm390;
public class PaintingBoards {
	public double minimalTime(int[] boardLength, int[] painterSpeed) {
		int len = boardLength.length;
		int n = painterSpeed.length;
		int max = 1 << n;
		double le = 0;
		double ri = 500000;
		double mi = 0;
		long[] ml = new long[n];
		while (le + 1e-10 < ri) {
			mi = (le + ri) * 0.5;
			for (int i = 0; i < n; i++) {
				ml[i] = (long) Math.floor(painterSpeed[i] * mi);
			}
			int[] a = new int[max];
			for (int m = 0; m < max; m++) {
				for (int p = 0; p < n; p++) {
					if ((m & (1 << p)) != 0)
						continue;
					int mm = m | (1 << p);
					int f = a[m];
					a[mm] = Math.max(a[mm], f);
					int length = 0;
					for (int i = f; i < len; i++) {
						length += boardLength[i];
						if (length <= ml[p]) {
							a[mm] = Math.max(a[mm], i + 1);
						} else
							break;
					}
				}
			}
			if (a[max - 1] == len)
				ri = mi;
			else
				le = mi;
		}
		return mi;
	}

	public static void main(String[] args) {
		System.out.println(new PaintingBoards().minimalTime(
				new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				new int[]{3, 1, 2, 1, 1, 1, 2, 1, 1, 4, 1, 1, 1, 1, 1})
			);
	}
}
//{10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000}
