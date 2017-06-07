package facebook.y2013.round1;
import java.io.*;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	private static final int M = 1000000007;

	int[][] c = new int[10000][10000];

	private void solve() {
		int n = in.nextInt();
		int k = in.nextInt();
		
		for (int i = 0; i < n; i++) {
			c[i][0] = c[i][i] = 1;
			for (int j = 1; j < i; j++) {
				c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % M;
			}
		}
		
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() % M;
		}
		Arrays.sort(a);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans = (ans + (int) (a[i] * 1L * c[i][k - 1] % M)) % M;
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
			new A().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
