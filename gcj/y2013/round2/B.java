package gcj.y2013.round2;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		long p = in.nextLong();
		out.println(must(n, p) + " " + could(n, p));
	}

	long must(int n, long p) {
		if (p == (1L << n)) {
			return p - 1;
		}
		long c = could(n, (1L << n) - p);
		return (1L << n) - c - 2;
	}

	long could(int n, long p) {
		long lo = 0;
		long hi = 1L << n;
		while (lo + 1 < hi) {
			long m = (lo + hi) / 2;
			if (best(m, n) < p) {
				lo = m;
			} else {
				hi = m;
			}
		}
		return lo;
	}
	
	long best(long m, int n) {
		if (m == ((1L << n) - 1)) {
			return m;
		}
		if (m == 0) {
			return m;
		}
		long better = (m + 1) / 2;
		return best(better, n - 1);
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
			new B().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
