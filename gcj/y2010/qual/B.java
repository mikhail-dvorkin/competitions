package gcj.y2010.qual;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		BigInteger[] a = new BigInteger[n];
		for (int i = 0; i < n; i++) {
			a[i] = new BigInteger(in.next());
		}
		BigInteger g = BigInteger.ZERO;
		for (int i = 1; i < n; i++) {
			g = g.gcd(a[i].subtract(a[i - 1]));
		}
		BigInteger m = a[0].mod(g);
		if (m.signum() > 0) {
			m = g.subtract(m);
		}
		out.println(m);
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
		}
		in.close();
		out.close();
	}
}
