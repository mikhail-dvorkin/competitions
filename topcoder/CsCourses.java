package topcoder;
public class CsCourses {
	public int[] getOrder(int[] theoreticalValue, int[] practicalValue, int[] expire, int skillBound) {
		int n = theoreticalValue.length;
		int[][][] d = new int[51][51][51];
		int[][][] how = new int[51][51][51];
		int[][][] howi = new int[51][51][51];
		int[][][] howj = new int[51][51][51];
		int infty = 100;
		for (int i = 0; i <= 50; i++) {
			for (int j = 0; j <= 50; j++) {
				for (int k = 0; k <= 50; k++) {
					if (i >= skillBound && j >= skillBound) {
						d[i][j][k] = 0;
					} else {
						d[i][j][k] = infty;
					}
				}
			}
		}
		for (int k = 49; k >= 0; k--) {
			for (int i = 0; i <= 50; i++) {
				for (int j = 0; j <= 50; j++) {
					for (int c = 0; c < n; c++) {
						if (expire[c] <= k)
							continue;
						if (i >= theoreticalValue[c] - 1 && j >= practicalValue[c] - 1) {
							int ii = Math.max(i, theoreticalValue[c]);
							int jj = Math.max(j, practicalValue[c]);
							int dd = 1 + d[ii][jj][k + 1];
							if (dd < d[i][j][k]) {
								d[i][j][k] = dd;
								how[i][j][k] = c;
								howi[i][j][k] = ii;
								howj[i][j][k] = jj;
							}
						}
					}
				}
			}
		}
		if (d[0][0][0] == infty)
			return new int[0];
		int i = 0;
		int j = 0;
		int k = 0;
		int[] a = new int[d[i][j][k]];
		while (d[i][j][k] > 0) {
			a[k] = how[i][j][k];
			int ii = howi[i][j][k];
			int jj = howj[i][j][k];
			k++;
			i = ii;
			j = jj;
		}
		return a;
	}
	
	public static void main(String[] args) {
		int[] a = new CsCourses().getOrder(new int[]{4, 2, 3, 1, 3, 5, 3, 5}, new int[]{0, 0, 0, 0, 1, 1, 2, 2}, new int[]{50, 50, 50, 50, 2, 50, 3, 50}, 2);
		for (int x : a)
			System.out.println(x);
	}
}
