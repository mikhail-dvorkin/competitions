import java.util.Arrays;

public class MaxPoints {
	public int findMaxPoints(int n, int[] XG, int[] YG, long k, int seedX, int seedY) {
		int[] a = new int[n];
		int[] b = new int[n];
		a[0] = seedX;
		b[0] = seedY;
		for (int i = 1; i < n; i++) {
			a[i] = (int) ((a[i-1]*1103515245L + 12345) % 2147483648L);
			b[i] = (int) ((b[i-1]*1103515245L + 12345) % 2147483648L);
		}
		int[] x = new int[n];
		int[] y = new int[n];
		for (int i = 0; i < XG.length; i++) {
			x[i] = XG[i];
			y[i] = YG[i];
		}
		for (int i = XG.length; i < n; i++) {
			x[i] = (a[i] % 2000001) - 1000000;
			y[i] = (b[i] % 2000001) - 1000000;
		}
		int[] xs = Arrays.copyOf(x, n);
		int[] ys = Arrays.copyOf(y, n);
		Arrays.sort(xs);
		Arrays.sort(ys);
		long[] xCum = new long[n + 1];
		long[] yCum = new long[n + 1];
		for (int i = 0; i < n; i++) {
			xCum[i + 1] = xCum[i] + xs[i];
			yCum[i + 1] = yCum[i] + ys[i];
		}
		long[] dist = new long[n];
		long distSum = 0;
		for (int i = 0; i < n; i++) {
			int xIndex = Arrays.binarySearch(xs, x[i]);
			int yIndex = Arrays.binarySearch(ys, y[i]);
			dist[i] += xIndex * (long) x[i] - xCum[xIndex];
			dist[i] += (xCum[n] - xCum[xIndex]) - (n - xIndex) * (long) x[i];
			dist[i] += yIndex * (long) y[i] - yCum[yIndex];
			dist[i] += (yCum[n] - yCum[yIndex]) - (n - yIndex) * (long) y[i];
			distSum += dist[i];
		}
		Arrays.sort(dist);
		long val = distSum / 2;
		if (val <= k) return n;
		for (int i = n - 1; i >= 0; i--) {
			val -= dist[i];
			if (val <= k) return i;
		}
		return -1;
	}

	public static void main(String[] args) {
//		int x = new MaxPoints().findMaxPoints(4, new int[0], new int[0], -5397718, 1825126330, 704277911);
		int x = new MaxPoints().findMaxPoints(100000, new int[0], new int[0], -3829423936963408L, 1225224201, 441244497);
		System.out.println(x);
	}
}
