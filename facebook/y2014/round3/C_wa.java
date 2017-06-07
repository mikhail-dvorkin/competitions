package facebook.y2014.round3;
import java.io.*;
import java.util.*;

public class C_wa {
	private static String fileName = C_wa.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		int m = in.nextInt();
		int[] edgeFrom = new int[m];
		int[] edgeTo = new int[m];
		int[] diffs = new int[m];
		int[] deg = new int[n];
		for (int i = 0; i < m; i++) {
			edgeFrom[i] = in.nextInt();
			edgeTo[i] = in.nextInt();
			deg[edgeFrom[i]]++;
			deg[edgeTo[i]]++;
		}
		int[][] nei = new int[n][];
		for (int i = 0; i < n; i++) {
			nei[i] = new int[deg[i]];
		}
		int[] degTemp = deg.clone();
		for (int i = 0; i < m; i++) {
			degTemp[edgeFrom[i]]--;
			nei[edgeFrom[i]][degTemp[edgeFrom[i]]] = edgeTo[i];
			degTemp[edgeTo[i]]--;
			nei[edgeTo[i]][degTemp[edgeTo[i]]] = edgeFrom[i];
		}
		int[] queue = new int[n];
		int[][] d = new int[2][n];
		boolean[] gotLetter = new boolean[n];
		int letters = 0;
		for (int edge = 0; edge < m; edge++) {
			if (diffs[edge] > 1) {
				throw new RuntimeException();
			}
			if (diffs[edge] == 1) {
				continue;
			}
			int a = edgeFrom[edge];
			int b = edgeTo[edge];
			for (int q = 0; q < 2; q++) {
				int s = (q == 0) ? a : b;
				int[] dist = d[q];
				Arrays.fill(dist, Integer.MAX_VALUE);
				int low = 0;
				int high = 1;
				queue[0] = s;
				dist[s] = 0;
				while (low < high) {
					int v = queue[low];
					int dv = dist[v];
					low++;
					for (int u : nei[v]) {
						if (dv + 1 < dist[u]) {
							dist[u] = dv + 1;
							queue[high] = u;
							high++;
						}
						if ((dv % 2) == (dist[u] % 2)) {
							throw new RuntimeException();
						}
					}
				}
			}
			for (int i = 0; i < n; i++) {
				if (d[0][i] == d[1][i]) {
					out.println(-1);
					return;
				}
				gotLetter[i] = d[0][i] > d[1][i];
			}
			for (int e = 0; e < m; e++) {
				int x = edgeFrom[e];
				int y = edgeTo[e];
				if (gotLetter[x] ^ gotLetter[y]) {
					diffs[e]++;
					if (diffs[e] == 2) {
						out.println(-1);
						return;
					}
				}
			}
			letters++;
		}
		out.println(letters);
	}
	
	static int t;

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
		for (t = 1; t <= tests; t++) {
			out.print("Case #" + t + ": ");
			new C_wa().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
