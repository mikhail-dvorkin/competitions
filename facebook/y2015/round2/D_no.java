package facebook.y2015.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class D_no {
	int n, m;
	long[] param;
	long T = 1000000000;
	int SZ = 2;
	
	String solve() {
		long[] s = generate(Arrays.copyOfRange(param, 0, param.length), T, 0, n);
		long[] o = generate(Arrays.copyOfRange(param, 5, param.length), 4, 1, m);
		long[] a = generate(Arrays.copyOfRange(param, 10, param.length), n, 1, m);
		long[] b = generate(Arrays.copyOfRange(param, 15, param.length), n, 1, m);
		long[] c = generate(Arrays.copyOfRange(param, 20, param.length), T, 0, m);
		long[] d = generate(Arrays.copyOfRange(param, 25, param.length), T, 0, m);
		for (int i = 0; i < m; i++) {
			o[i]--;
			a[i]--;
		}
//		int blocks = (n + SZ - 1) / SZ;
//		long[] addX = new long[blocks];
//		long[] addY = new long[blocks];
//		long[] force = new long[blocks];
		long ans = 0;
		for (int op = 0; op < m; op++) {
			int k = (int) a[op];
			int more = (int) b[op];
			int index = 0;
			int oper = (int) o[op];
			long cc = c[op];
			long dd = d[op];
			while (more > 0) {
				if (k % SZ == 0 && more >= SZ && k + SZ <= n) {
					// HERE
					ans %= T;
					k += SZ;
					if (k == n) {
						k = 0;
					}
					more -= SZ;
					index += SZ;
					continue;
				}
				switch (oper) {
				case 0:
					s[k] += cc + index * dd;
					s[k] %= T;
					ans += cc + index * dd;
					break;
				case 1:
					ans += s[k];
					ans += cc;
					s[k] = cc;
					break;
				case 2:
					ans += s[k];
					break;
				case 3:
					if (s[k] % 2 != 0) {
						ans++;
					}
					break;
				}
				ans %= T;
				k++;
				if (k == n) {
					k = 0;
				}
				more--;
				index++;
			}
		}
		return "" + ans;
	}
	
	private long[] generate(long[] p, long mod, int shift, int length) {
		long[] a = new long[length];
		a[0] = p[0];
		a[1] = p[1];
		for (int i = 2; i < length; i++) {
			a[i] = (p[2] * a[i - 2] + p[3] * a[i - 1] + p[4]) % mod + shift;
		}
		return a;
	}

	public D_no(Scanner in) {
		n = in.nextInt();
		m = in.nextInt();
		param = new long[30];
		for (int i = 0; i < param.length; i++) {
			param[i] = in.nextLong();
		}
	}
	
	private static String fileName = D_no.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final D_no testCase = new D_no(in);
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
