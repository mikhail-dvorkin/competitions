package topcoder.tco2022.round2b;

public class AddFlowers {
	public String[] add(String[] lawn) {
		int hei = lawn.length;
		int wid = lawn[0].length();
		char[][] ans = new char[hei][];
		for (int i = 0; i < hei; i++) {
			ans[i] = lawn[i].toCharArray();
		}
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (lawn[i].charAt(j) != 'F') {
					continue;
				}
				int iStart = i == 0 ? i : i - 1;
				int jStart = j == 0 ? j : j - 1;
				for (int ii = iStart; ii < iStart + 2; ii++) {
					for (int jj = jStart; jj < jStart + 2; jj++) {
						ans[ii][jj] = 'F';
					}
				}
			}
		}
		String[] a = new String[hei];
		for (int i = 0; i < hei; i++) {
			a[i] = new String(ans[i]);
		}
		return a;
	}
}
