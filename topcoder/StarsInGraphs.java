package topcoder;
import java.util.*;

public class StarsInGraphs {
	public int starryPaths(String[] adjacencyMatrix, int C) {
		n = adjacencyMatrix.length;
		e = new boolean[n][n];
		boolean[] star = new boolean[n];
		deg = new int[n];
		int m = 0;
		for (int i = 0; i < n; i++) {
			deg[i] = 0;
			for (int j = 0; j < n; j++) {
				e[i][j] = adjacencyMatrix[i].charAt(j) == '1';
				if (e[i][j])
					deg[i]++;
			}
			star[i] = (star(deg[i], C) > 0);
			if (star[i])
				m++;
		}
		if (m == 0)
			return 0;
		len = new int[n];
		int ans = 0;
		for (d = 3;; d++) {
			int mx = 0;
			if (star(d, C) == 0)
				break;
			for (int i = 0; i < n; i++) {
				if (deg[i] == d) {
					Arrays.fill(len, -1);
					dfs(i, i);
					mx = Math.max(mx, len[i]);
				}
			}
			ans += mx;
		}
		if (cyc)
			return -1;
		return ans;
	}
	
	int[] len;
	boolean cyc = false;
	
	private void dfs(int v, int proh) {
		if (len[v] >= 0)
			return;
		len[v] = 1;
		for (int i = 0; i < n; i++) {
			if (e[v][i] && deg[i] == d) {
				if (i == proh) {
					cyc = true;
					return;
				}
				dfs(i, proh);
				len[v] = Math.max(len[v], len[i] + 1);
			}
		}
	}

	int n, d;
	boolean[][] e;
	int[] deg;
	
	int star(int t, int C) {
		if (t >= 3) {
			long s = (1L << t);
			s -= 1;
			s -= t;
			s -= t * (t - 1) / 2;
			if (s > C)
				return 0;
			return (int) s;
		}
		return 0;
	}
	
	public static void main(String[] args) {
		int a = new StarsInGraphs().starryPaths(new String[]
		{"0010000000000000000000000001", "0000100110000000000000000000", "0000010100000000000000000000", "0000000000000010000000001000", "0000000000000000100100000000", "0000100000010001010100000000", "0010000000010110000000110100", "0001000000000000000000000000", "0010000000001100001001000000", "0000000000000010001000000000", "0001000001000000000000100000", "1000000000000000000000000000", "1000000000100000000000000000", "0100000110000000000000100000", "0000000010000100001001000000", "0010000100000000000010100000", "0000110000000000000000010000", "0100000000000000000000000000", "0000000000000000010000000000", "0000100000000000000000000001", "0000010000000000000000000000", "0001000000000000000100000000", "0000000000000000000000000000", "0100001000110010000010000000", "0010001000000000000000100000", "0010001000000001000000000000", "0100000000000000000000000100", "0001010000010010000100100000"},
		99);
		System.out.println(a);
	}
}
