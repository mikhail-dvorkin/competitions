package gcj.y2012.qual;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int from = in.nextInt();
		int to = in.nextInt();
		int n = ("" + to).length();
		int ten = (int) Math.pow(10, n - 1);
		int ans = 0;
		Set<Integer> recycled = new HashSet<Integer>();
		for (int a = from; a <= to; a++) {
			int b = a;
			for (int j = 1; j < n; j++) {
				int d = b % 10;
				b = (b / 10) + d * ten;
				if (a < b && b <= to) {
					recycled.add(b);
				}
			}
			ans += recycled.size();
			recycled.clear();
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
