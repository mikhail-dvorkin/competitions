package facebook.y2014.round1;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		String letters = in.next();
		int a = letters.length();
		long n = in.nextLong();
		for (int len = 1;; len++) {
			if (BigInteger.valueOf(n).compareTo(BigInteger.valueOf(a).pow(len)) > 0) {
				n -= BigInteger.valueOf(a).pow(len).longValue();
				continue;
			}
			n--;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < len; i++) {
				sb.append(letters.charAt((int) (n % a)));
				n /= a;
			}
			if (n != 0) {
				throw new RuntimeException("" + n);
			}
			out.println(sb.reverse());
			break;
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
			new A().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
