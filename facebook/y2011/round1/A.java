package facebook.y2011.round1;
import java.io.*;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	final static long MODULO = 1051962371L;

	private void solve() {
		int n = in.nextInt();
		int m = in.nextInt();
		long[][] a = new long[n + 1][n + 1];
		long[][] cnk = new long[n + 1][n + 1];
		long[] factorial = new long[n + 1];
		a[0][0] = 1;
		cnk[0][0] = 1;
		factorial[0] = 1;
		for (int i = 1; i <= n; i++) {
			cnk[i][0] = cnk[i][i] = 1;
			factorial[i] = (factorial[i - 1] * i) % MODULO;
			for (int j = 1; j < i; j++) {
				cnk[i][j] = (cnk[i - 1][j - 1] + cnk[i - 1][j]) % MODULO;
			}
			for (int j = 1; j <= i; j++) {
				a[i][j] = cnk[i][j] * a[i - j][0] % MODULO;
			}
			a[i][0] = factorial[i];
			for (int j = 1; j <= i; j++) {
				a[i][0] = (a[i][0] - a[i][j] + MODULO) % MODULO;
			}
		}
		long answer = 0;
		for (int i = m; i <= n; i++) {
			answer = (answer + a[n][i]) % MODULO;
		}
		out.println(answer);
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
			new A().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
