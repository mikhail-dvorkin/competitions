package gcj.y2017.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	static final String NO = "IMPOSSIBLE";
	static final String YES = "POSSIBLE";
	static final int[] DX = new int[]{1, 0, -1, 0};
	static final int[] DY = new int[]{0, 1, 0, -1};

	String solve() {
		boolean[][][] forbidden = new boolean[h][w][2];
		@SuppressWarnings("unchecked")
		TreeSet<Integer>[][][] cells = new TreeSet[h][w][2];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (f[i][j] == '|') {
					f[i][j] = '-';
				}
				cells[i][j][0] = new TreeSet<>();
				cells[i][j][1] = new TreeSet<>();
			}
		}
		ArrayList<int[]> twosat = new ArrayList<>();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (f[i][j] != '-') {
					continue;
				}
				for (int dir = 0; dir < 4; dir++) {
					int x = i;
					int y = j;
					int d = dir;
					for (;;) {
						x += DX[d];
						y += DY[d];
						if (x >= h || y >= w || x < 0 || y < 0) {
							break;
						}
						if (f[x][y] == '#') {
							break;
						}
						if (f[x][y] == '/') {
							d ^= 3;
							continue;
						}
						if (f[x][y] == '\\') {
							d ^= 1;
							continue;
						}
						if (f[x][y] == '-') {
							forbidden[i][j][dir % 2] = true;
							break;
						}
						cells[i][j][dir % 2].add(x * w + y);
					}
				}
				for (int d = 0; d < 2; d++) {
					int var = (i * w + j) * 2 + d;
					if (forbidden[i][j][d]) {
						twosat.add(new int[]{var ^ 1, var ^ 1});
						continue;
					}
					for (int z : cells[i][j][d]) {
						int y = z % w;
						int x = z / w;
						cells[x][y][0].add(var);
					}
				}
			}
		}
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (f[i][j] != '.') {
					continue;
				}
				TreeSet<Integer> good = cells[i][j][0];
				if (good.isEmpty()) {
					return NO;
				}
				twosat.add(new int[]{good.first(), good.last()});
			}
		}
		boolean[] ans = TwoSat.solve(twosat.toArray(new int[twosat.size()][]), h * w);
		if (ans == null) {
			return NO;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(YES);
		for (int i = 0; i < h; i++) {
			sb.append('\n');
			for (int j = 0; j < w; j++) {
				if (f[i][j] != '-') {
					sb.append(f[i][j]);
					continue;
				}
				sb.append(ans[i * w + j] ? '-' : '|');
			}
		}
		return sb.toString();
	}
	
	static class TwoSat {
		static boolean[] solve(int[][] data, int n) {
			boolean[] ans = new boolean[n];
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] g = new ArrayList[2 * n];
			for (int i = 0; i < 2 * n; i++) {
				g[i] = new ArrayList<Integer>();
			}
			for (int[] clause : data) {
				g[clause[0] ^ 1].add(clause[1]);
				g[clause[1] ^ 1].add(clause[0]);
			}
			boolean[] mark = new boolean[2 * n];
			ArrayList<Integer> order = new ArrayList<Integer>();
			for (int i = 0; i < 2 * n; i++) {
				if (!mark[i]) {
					dfs1(i, mark, order, g);
				}
			}
			int[] comp = new int[2 * n];
			Arrays.fill(comp, -1);
			for (int i = 2 * n - 1, compNum = 0; i >= 0; i--) {
				int v = order.get(i);
				if (comp[v] == -1) {
					dfs2(v, compNum++, comp, g);
				}
			}
			for (int i = 0; i < n; i++) {
				if (comp[2 * i] == comp[2 * i + 1]) {
					return null;
				}
				ans[i] = comp[2 * i + 1] > comp[2 * i];
			}
			return ans;
		}
		
		static void dfs1(int v, boolean[] mark, ArrayList<Integer> order, ArrayList<Integer>[] g) {
			mark[v] = true;
			for (int u : g[v]) {
				if (!mark[u]) {
					dfs1(u, mark, order, g);
				}
			}
			order.add(v);
		}
		
		static void dfs2(int v, int compNum, int[] comp, ArrayList<Integer>[] g) {
			comp[v] = compNum;
			for (int u : g[v ^ 1]) {
				u ^= 1;
				if (comp[u] == -1) {
					dfs2(u, compNum, comp, g);
				}
			}
		}
	}

	int h, w;
	char[][] f;
	
	public C(Scanner in) {
		h = in.nextInt();
		w = in.nextInt();
		f = new char[h][];
		for (int i = 0; i < h; i++) {
			f[i] = in.next().toCharArray();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		/* "Google Code Jam, Facebook Hacker Cup" */
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
//		/* "IPSC" */
//		String fileNameSuffix = "0";
//		String formatOut = "%s";
//		String formatSystemOut = "Case #%2$d solved.";//"Case #%2$d: %1$s";
		
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";
		
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt(); in.nextLine();//1;
		nThreads = Math.min(nThreads, tests);
		@SuppressWarnings("unchecked")
		Callable<String>[] callables = new Callable[tests];
		for (int t = 0; t < tests; t++) {
			final C testCase = new C(in);
			final int testCaseNumber = t + 1;
			callables[t] = new Callable<String>() {
				@Override
				public String call() {
					String answer = testCase.solve();
					System.out.println(String.format(formatSystemOut, answer, testCaseNumber));
					return String.format(formatOut, answer, testCaseNumber);
				}
			};
		}
		try {
			if (nThreads > 1) {
				ExecutorService executor = Executors.newFixedThreadPool(4);
				@SuppressWarnings("unchecked")
				Future<String>[] outputs = new Future[tests];
				for (int t = 0; t < tests; t++) {
					outputs[t] = executor.submit(callables[t]);
				}
				for (int t = 0; t < tests; t++) {
					out.println(outputs[t].get());
				}
				executor.shutdown();
			} else {
				for (int t = 0; t < tests; t++) {
					out.println(callables[t].call());
				}
			}
		} catch (Exception e) {
			System.out.flush();
			System.err.flush();
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("COMPLETE");
		in.close();
		out.close();
	}
}
