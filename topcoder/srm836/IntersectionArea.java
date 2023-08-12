public class IntersectionArea {
	public int[] addOne(int[] X1, int[] Y1, int[] X2, int[] Y2, long A) {
		int M = 1_000_000;
		int x1 = 0, y1 = 0, x2 = M, y2 = M;
		for (int i = 0; i < X1.length; i++) {
			x1 = Math.max(x1, X1[i]);
			y1 = Math.max(y1, Y1[i]);
			x2 = Math.min(x2, X2[i]);
			y2 = Math.min(y2, Y2[i]);
		}
		if (A == 0) {
			if (x1 > 0 || y1 > 0) return new int[]{0, 0, 1, 1};
			if (x2 < M || y2 < M) return new int[]{M - 1, M - 1, M, M};
			return new int[0];
		}
		for (int xa = 1; x1 + xa <= x2; xa++) {
			if (A % xa != 0) continue;
			long ya = A / xa;
			if (y1 + ya <= y2) {
				return new int[] {x1, y1, x1 + xa, (int) (y1 + ya)};
			}
		}
		return new int[0];
	}
}
