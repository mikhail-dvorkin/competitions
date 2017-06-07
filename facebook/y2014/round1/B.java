package facebook.y2014.round1;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int jars = in.nextInt();
		int coins = in.nextInt();
		int need = in.nextInt();
		int fails = jars - 1;
		for (int empty = 0; empty < jars; empty++) {
			int filled = jars - empty;
			int each = coins / filled;
			int rest = coins % filled;
			if (need <= each * filled) {
				fails = Math.min(fails, empty);
			} else {
				fails = Math.min(fails, empty + filled - rest);
				// after the contest :(
				if (rest <= empty * each) {
					fails = Math.min(fails, empty);
				}
			}
		}
//		Jury's solution:
//		int x = (int) Math.ceil(need * 1.0 / jars);
//		int m = jars - (int) Math.min(Math.floor(coins / x), jars);
//		out.print(need + m);
		out.println(need + fails);
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
