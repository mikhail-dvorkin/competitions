package gcj.y2013.qual;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		long a = in.nextLong();
		long b = in.nextLong();
		int ans = 0;
		for (long n : nice) {
			if (a <= n && n <= b) {
				ans++;
			}
		}
		out.println(ans);
	}

	static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}
	
	static boolean isPalindrome(String s) {
		return s.equals(reverse(s));
	}
	
	static ArrayList<Long> nice = new ArrayList<Long>();
	
	public static void main(String[] args) throws IOException {
		for (long n = 0; n <= 10000000; n++) {
			if (!isPalindrome("" + n)) {
				continue;
			}
			if (!isPalindrome("" + n * n)) {
				continue;
			}
			System.out.println(n + " " + n * n);
			nice.add(n * n);
		}
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