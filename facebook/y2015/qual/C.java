package facebook.y2015.qual;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	public static final int[] di = new int[]{1, 0, -1, 0};
	public static final int[] dj = new int[]{0, 1, 0, -1};
	public static final String laser = "v>^<";
	int hei, wid;
	char[][] f;
	
	String solve() {
		int i0 = -1;
		int j0 = -1;
		int i1 = -1;
		int j1 = -1;
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (f[i][j] == 'S') {
					f[i][j] = '.';
					i0 = i;
					j0 = j;
				} else if (f[i][j] == 'G') {
					f[i][j] = '.';
					i1 = i;
					j1 = j;
				}
			}
		}
		boolean[][] p = new boolean[hei][wid];
		p[i0][j0] = true;
		int m = 0;
		do {
			boolean[][] q = new boolean[hei][wid];
			for (int i = 0; i < hei; i++) {
				for (int j = 0; j < wid; j++) {
					if (!p[i][j]) {
						continue;
					}
					for (int d = 0; d < 4; d++) {
						int ii = i + di[d];
						int jj = j + dj[d];
						if (ii < 0 || ii >= hei || jj < 0 || jj >= wid) {
							continue;
						}
						if (f[ii][jj] != '.') {
							continue;
						}
						q[ii][jj] = true;
					}
				}
			}
			for (int i = 0; i < hei; i++) {
				for (int j = 0; j < wid; j++) {
					int d = laser.indexOf(f[i][j]);
					if (d < 0) {
						continue;
					}
					d = (d + 3) % 4;
					f[i][j] = laser.charAt(d);
					int ii = i;
					int jj = j;
					for (;;) {
						ii += di[d];
						jj += dj[d];
						if (ii < 0 || ii >= hei || jj < 0 || jj >= wid) {
							break;
						}
						if (f[ii][jj] != '.') {
							break;
						}
						q[ii][jj] = false;
					}
				}
			}
			p = q;
			m++;
			if (m > 4 * hei * wid) {
				return "impossible";
			}
		} while (!p[i1][j1]);
		return m + "";
	}
	
	public C(Scanner in) {
		hei = in.nextInt();
		wid = in.nextInt();
		f = new char[hei][];
		for (int i = 0; i < hei; i++) {
			f[i] = in.next().toCharArray();
		}
	}
	
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		@SuppressWarnings("unchecked")
		Future<String>[] outputs = new Future[tests];
		for (int t = 0; t < tests; t++) {
			final C testCase = new C(in);
			outputs[t] = executor.submit(new Callable<String>() {
				@Override
				public String call() {
					return testCase.solve();
				}
			});
		}
		for (int t = 0; t < tests; t++) {
			out.println("Case #" + (t + 1) + ": " + outputs[t].get());
		}
		in.close();
		out.close();
		executor.shutdown();
	}
}
