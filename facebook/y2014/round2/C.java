package facebook.y2014.round2;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	final static int M = 1000000007;

	private void solve() {
		int n = in.nextInt();
		int[] two = new int[n + 1];
		int[] two1 = new int[n + 1];
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] fw = new ArrayList[n];
		two[0] = 1;
		for (int i = 1; i <= n; i++) {
			two[i] = (2 * two[i - 1]) % M;
			two1[i] = two[i] - 1;
			if (two1[i] < 0) {
				two1[i] += M;
			}
		}
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			fw[i] = new ArrayList<Integer>();
		}
		for (int i = 1; i < n; i++) {
			a[i] = in.nextInt();
			fw[a[i]].add(i);
		}
		for (int i = 0; i < n; i++) {
			Collections.sort(fw[i]);
		}
		boolean[][] f = new boolean[n][n];
		f[0][0] = true;
		int ans = 1;
		for (int i = 1; i < n; i++) {
			int ai = a[i];
			for (int j = 0; j < ai; j++) {
				if (f[j][ai]) {
					f[j][i] = true;
				}
			}
			f[ai][i] = true;
			f[i][i] = true;
			int[] size = new int[i];
			for (int j = i - 1; j >= ai; j--) {
				if (!f[ai][j]) {
					continue;
				}
				size[j] = 1;
				for (int k : fw[j]) {
					if (k >= i) {
						break;
					}
					size[j] += size[k];
				}
			}
			int cur = two1[size[ai]];
			for (int k : fw[ai]) {
				if (k >= i) {
					break;
				}
				cur -= two1[size[k]];
				if (cur < 0) {
					cur += M;
				}
			}
			ans = (int) ((ans * (long) cur) % M);
		}
		out.println(ans);
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
