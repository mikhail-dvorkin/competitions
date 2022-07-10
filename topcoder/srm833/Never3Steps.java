package topcoder.srm833;

public class Never3Steps {
	public int count(int hei, int wid) {
		if (hei + wid == 0) return 1;
		int M = 1_000_000_007;
		int[][] endVer = new int[hei + 1][wid + 1];
		int[][] endHor = new int[hei + 1][wid + 1];
		int[] sumVer = new int[hei + 1];
		int[] sumHor = new int[wid + 1];
		for (int i = 0; i <= hei; i++) {
			for (int j = 0; j <= wid; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				if (i == 0) {
					if (j != 3) {
						endHor[i][j] = 1;
					}
					sumHor[j] = (sumHor[j] + endHor[i][j]) % M;
					continue;
				}
				if (j == 0) {
					if (i != 3) {
						endVer[i][j] = 1;
					}
					sumVer[i] = (sumVer[i] + endVer[i][j]) % M;
					continue;
				}
				endVer[i][j] = sumHor[j];
				endHor[i][j] = sumVer[i];
				if (i >= 3) {
					endVer[i][j] = (endVer[i][j] + M - endHor[i - 3][j]) % M;
				}
				if (j >= 3) {
					endHor[i][j] = (endHor[i][j] + M - endVer[i][j - 3]) % M;
				}
				sumVer[i] = (sumVer[i] + endVer[i][j]) % M;
				sumHor[j] = (sumHor[j] + endHor[i][j]) % M;
			}
		}
		return (endVer[hei][wid] + endHor[hei][wid]) % M;
	}
}
