package topcoder.srm582;
public class ColorfulBuilding {
	final int M = 1000000009;

	public int count(String[] color1, String[] color2, int L) {
		int n = 0;
		for (String s : color1) {
			n += s.length();
		}
		int[] c = new int[n];
		for (int i = 0, j = 0; j < color1.length; j++) {
			for (int k = 0; k < color1[j].length(); k++) {
				c[i] = color1[j].charAt(k) * 256 + color2[j].charAt(k);
				i++;
			}
		}
		long[][] a = new long[n][n];
		a[n - 1][0] = 1;
		for (int i = n - 2; i >= 0; i--) {
			long[] ai = a[i];
			int ii = n - i - 1;
			for (int f = i + 1; f < n; f++) {
				long[] af = a[f];
				int s = (c[i] == c[f]) ? 0 : 1;
				for (int d = n - f - 1; d >= 0; d--) {
					long b = af[d];
					ai[d + s] += b;
					b = (b * ii) % M;
					af[d] = b;
				}
			}
		}
		long ans = 0;
		for (int i = 0; i < n; i++) {
			ans += a[i][L - 1];
		}
		return (int) (ans % M);
	}
}
