package topcoder.srm436;
import java.util.*;

public class DoNotTurn {
	final int[] dx = new int[]{1, 0, -1, 0};
	final int[] dy = new int[]{0, 1, 0, -1};

	public int minimumTurns(int n, int X0, int A, int B, int Y0, int C, int D, int P, int M) {
		long[] X = new long[M];
		long[] Y = new long[M];
		if (M > 0)
			X[0] = X0 % P;
		for (int i = 1; i < M; i++) {
			X[i] = (X[i-1]*A+B) % P;
		}
		if (M > 0)
			Y[0] = Y0 % P;
		for (int i = 1; i < M; i++) {
			Y[i] = (Y[i-1]*C+D) % P;
		}
		boolean[][] wall = new boolean[n][n];
		for (int i = 0; i < M; i++) {
			wall[(int) (X[i] % n)][(int) (Y[i] % n)] = true;
		}
		wall[0][0] = wall[n - 1][n - 1] = false;
		int z = n * n * 4;
		int[] queue = new int[3 * z];
		int high = 3 * z / 2;
		int low = high;
		int[] dist = new int[z];
		boolean[] done = new boolean[z];
		int inf = Integer.MAX_VALUE / 3;
		Arrays.fill(dist, inf);
		for (int d = 0; d < 4; d++) {
			queue[high++] = d;
			dist[d] = 0;
		}
		while (low < high) {
			int s = queue[low];
			low++;
			if (done[s])
				continue;
			int dd = s % 4;
			int x = (s / 4) / n;
			int y = (s / 4) % n;
			if (x == n - 1 && y == n - 1) {
				return dist[s];
			}
			{
				int xx = x + dx[dd];
				int yy = y + dy[dd];
				if (xx < 0 || yy < 0 || xx >= n || yy >= n || wall[xx][yy]) {

				} else {
					int ss = (xx * n + yy) * 4 + dd;
					if (dist[ss] > dist[s]) {
						dist[ss] = dist[s];
						queue[--low] = ss;
					}
				}
			}
			for (int d = 0; d < 4; d++) {
				if (d % 2 == dd % 2)
					continue;
				int ss = (x * n + y) * 4 + d;
				if (dist[ss] > dist[s] + 1) {
					dist[ss] = dist[s] + 1;
					queue[high++] = ss;
				}
			}
			done[s] = true;
		}
		return -1;
	}
}
