package topcoder.srm772;

import java.util.Arrays;

public class MaxPoints {
	public int findMaxPoints(int n, int[] xg, int[] yg, long k, int seedX, int seedY) {
		int[] a = new int[n];
		int[] b = new int[n];
		a[0] = seedX;
		b[0] = seedY;
		for (int i = 1; i < n; i++) {
			a[i] = (int) ((a[i - 1] * 1103515245L + 12345) % 2147483648L);
			b[i] = (int) ((b[i - 1] * 1103515245L + 12345) % 2147483648L);
		}
		int[] x = Arrays.copyOf(xg, n);
		int[] y = Arrays.copyOf(yg, n);
		for (int i = xg.length; i < n; i++) {
			x[i] = (a[i] % 2000001) - 1000000;
			y[i] = (b[i] % 2000001) - 1000000;
		}
		int[] xs = x.clone();
		int[] ys = y.clone();
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
			dist[i] += yIndex * (long) y[i] - yCum[yIndex];
			dist[i] += xCum[n] - xCum[xIndex] - (n - xIndex) * (long) x[i];
			dist[i] += yCum[n] - yCum[yIndex] - (n - yIndex) * (long) y[i];
			distSum += dist[i];
		}
		Arrays.sort(dist);
		distSum /= 2;
		for (int i = n; i >= 0; i--) {
			if (i < n) distSum -= dist[i];
			if (distSum <= k) return i;
		}
		return -1;
	}
}
