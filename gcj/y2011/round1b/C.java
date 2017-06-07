package gcj.y2011.round1b;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	int n;
	int m;
	int[] a;
	int[] b;
	int[] color;
	int c;
	boolean[][] same;
	boolean[][] diff;
	int[][] next;
	
	private void solve() {
		n = in.nextInt();
		m = in.nextInt();
		a = new int[m];
		b = new int[m];
		color = new int[n];
		same = new boolean[n][n];
		diff = new boolean[n][n];
		next = new int[n][n];
		boolean[][] conn = new boolean[n][n];
		for (int i = 0; i < m; i++) {
			a[i] = in.nextInt() - 1;
		}
		for (int i = 0; i < m; i++) {
			b[i] = in.nextInt() - 1;
			conn[a[i]][b[i]] = true;
			conn[b[i]][a[i]] = true;
		}
		for (int i = 0; i < n; i++) {
			conn[i][(i + 1) % n] = true;
			conn[(i + 1) % n][i] = true;
			Arrays.fill(next[i], -1);
		}
		for (int i = 0; i < n; i++) {
			int jj = -1;
			for (int j = 1; j < n; j++) {
				if (conn[i][(i + j) % n]) {
					if (jj >= 0) {
						next[(i + j) % n][i] = jj;
					}
					jj = (i + j) % n;
				}
			}
		}
		int left = 1;
		int right = n + 1;
		while (left + 1 < right) {
			c = (left + right) / 2;
			if (color(false)) {
				left = c;
			} else {
				right = c;
			}
		}
		c = left;
		color(true);
		out.println(c);
		for (int i = 0; i < n; i++) {
			if (i > 0) {
				out.print(" ");
			}
			out.print(color[i] + 1);
		}
		out.println();
	}

	boolean color(boolean output) {
		if (c == 1) {
			Arrays.fill(color, 0);
			return true;
		}
		dfs(a[0], b[0], false);
		dfs(b[0], a[0], false);
		if (same[a[0]][b[0]] && same[b[0]][a[0]]) {
			if (output) {
				color[a[0]] = 0;
				color[b[0]] = 0;
				dfs(a[0], b[0], true);
				dfs(b[0], a[0], true);
			}
			return true;
		}
		if (diff[a[0]][b[0]] && diff[b[0]][a[0]]) {
			if (output) {
				color[a[0]] = 0;
				color[b[0]] = 1;
				dfs(a[0], b[0], true);
				dfs(b[0], a[0], true);
			}
			return true;
		}
		return false;
	}

	void dfs(int yy, int xx, boolean output) {
		if (xx == yy + 1 || xx == yy + 1 - n) {
			diff[yy][xx] = true;
			same[yy][xx] = true;
			return;
		}
		int x = xx;
		int y = yy;
		int s1 = -1;
		int s2 = -1;
		int groups = 1;
		for (;;) {
			int z = next[x][y];
			if (!output) {
				dfs(y, z, output);
			}
			if (!diff[y][z] && !same[y][z]) {
				diff[yy][xx] = false;
				same[yy][xx] = false;
				return;
			}
			if (diff[y][z] && same[y][z]) {
				s1 = y;
				s2 = z;
				groups++;
			}
			if (diff[y][z] && !same[y][z]) {
				groups++;
			}
			if (z == xx) {
				break;
			}
			x = y;
			y = z;
		}
		if (c >= 3) {
			diff[yy][xx] = (groups >= c);
			same[yy][xx] = (groups > c);
		} else {
			if (s1 == -1) {
				diff[yy][xx] = (groups % 2 == 0);
				same[yy][xx] = (groups % 2 == 1);
			} else {
				diff[yy][xx] = true;
				same[yy][xx] = true;
				
			}
		}
		if (!output) {
			return;
		}
		x = xx;
		y = yy;
		int start = color[yy];
		int end = color[xx];
		int g = start;
		for (;;) {
			int z = next[x][y];
			color[y] = g;
			int gg = g + 1;
			if (diff[y][z] && same[y][z]) {
				if (s1 == y && s2 == z) {
					color[z] = g;
				} else {
					color[z] = gg;
				}
			}
			if (diff[y][z] && !same[y][z]) {
				color[z] = gg;
			}
			if (!diff[y][z] && same[y][z]) {
				color[z] = g;
			}
			color[xx] = end;
			g = color[z];
			dfs(y, z, output);
			if (z == xx) {
				break;
			}
			x = y;
			y = z;
		}
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		for (int t = 1; t <= tests; t++) {
			out.print("Case #" + t + ": ");
			new C().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
