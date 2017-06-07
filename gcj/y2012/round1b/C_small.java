package gcj.y2012.round1b;
import java.io.*;
import java.util.*;

public class C_small {
	private static String fileName = C_small.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		Arrays.sort(a);
		Map<Integer, Integer> all = new HashMap<Integer, Integer>();
		for (int mask = 0; mask < (1 << n); mask++) {
			int sum = 0;
			for (int i = 0; i < n; i++) {
				if (((mask >> i) & 1) == 1) {
					sum += a[i];
				}
			}
			if (all.containsKey(sum)) {
				for (int m : new int[]{mask, all.get(sum)}) {
					for (int i = 0; i < n; i++) {
						if (((m >> i) & 1) == 1) {
							out.print(a[i] + " ");
						}
					}
					out.println();
				}
				return;
			}
			all.put(sum, mask);
		}
		out.println("Impossible");
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
			out.println("Case #" + t + ":");
			new C_small().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
