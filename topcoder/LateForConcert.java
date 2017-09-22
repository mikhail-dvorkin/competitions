package topcoder;
import java.util.*;

public class LateForConcert {
	int[] dx = new int[]{1, 0, -1, 0};
	int[] dy = new int[]{0, 1, 0, -1};
	
	public double bestRoute(String[] cityMap, int timeLeft, double speedingTicket, double redLight) {
		int hei = cityMap.length;
		int wid = cityMap[0].length();
		double red = 0.7 * redLight;
		double[][][][] a = new double[hei][wid][8][timeLeft + 2];
		double inf = 1e8;
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				for (int d = 0; d < 8; d++)
					Arrays.fill(a[i][j][d], inf);
				if (cityMap[i].charAt(j) == 'Y') {
					for (int d = 0; d < 4; d++) {
						a[i][j][d][0] = 0;
					}
				}
			}
		}
		double ans = inf;
		for (int t = 0; t <= timeLeft; t++) {
			for (int i = 0; i < hei; i++) {
				for (int j = 0; j < wid; j++) {
					for (int d = 0; d < 8; d++) {
						int dir = d % 4;
						double time = a[i][j][d][t];
						if (time > inf / 2)
							continue;
						if (cityMap[i].charAt(j) == 'T' && d < 4) {
							double cur = time - red;
							a[i][j][d + 4][t + 1] = Math.min(a[i][j][d + 4][t + 1], cur);
						}
						if (cityMap[i].charAt(j) == 'C' && t == timeLeft) {
							ans = Math.min(ans, time);
						}
						int ii = i + dx[dir];
						int jj = j + dy[dir];
						if (ii < 0 || jj < 0 || ii >= hei || jj >= wid)
							continue;
						if (cityMap[ii].charAt(jj) == 'X')
							continue;
						if (cityMap[ii].charAt(jj) == 'C' && t != timeLeft - 1)
							continue;
						double cur = time;
						if (cityMap[ii].charAt(jj) == 'S')
							cur += speedingTicket;
						if (cityMap[ii].charAt(jj) == 'T')
							cur += red;
						for (int dd = 0; dd < 4; dd++) {
							if (dd == (dir ^ 2))
								continue;
							a[ii][jj][dd][t + 1] = Math.min(a[ii][jj][dd][t + 1], cur);
						}
					}
				}
			}
		}
		if (ans == inf)
			return -1;
		return ans;
	}
	
	public static void main(String[] args) {
		double a = new LateForConcert().bestRoute(new String[]{"YT", "XC"}, 3, 8.888291761074557, 611.5452325396611);
		System.out.println(a);
	}
}
