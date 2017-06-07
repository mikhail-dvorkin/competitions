package gcj.y2010.qual;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int runs = in.nextInt();
		int size = in.nextInt();
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		long[] profit = new long[n];
		int[] groups = new int[n];
		for (int x = 0; x < n; x++) {
			while (groups[x] < n && profit[x] + a[(x + groups[x]) % n] <= size) {
				profit[x] += a[(x + groups[x]) % n];
				groups[x]++;
			}
		}
		int x = 0;
		long answer = 0;
		for (int r = 0; r < runs; r++) {
			answer += profit[x];
			x = (x + groups[x]) % n;
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
			out.print("Case #" + t + ": ");
			new C().solve();
		}
		in.close();
		out.close();
	}
}
