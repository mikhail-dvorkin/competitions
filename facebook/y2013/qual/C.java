package facebook.y2013.qual;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt() - 1;
		int k = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();
		int c = in.nextInt();
		int r = in.nextInt();

		int[] m = new int[3 * k + 2];
		m[0] = a;
		for (int i = 1; i < k; i++) {
			m[i] = (int) ((b * 1L * m[i - 1] + c) % r);
		}
		TreeSet<Integer> present = new TreeSet<Integer>();
		for (int i = k - 1; i >= 0; i--) {
			if (present.contains(m[i])) {
				m[i] = Integer.MAX_VALUE;
			}
			present.add(m[i]);
		}

		TreeSet<Integer> absent = new TreeSet<Integer>();
		for (int i = 0; i <= k; i++) {
			absent.add(i);
		}
		for (int i = 0; i < k; i++) {
			absent.remove(m[i]);
		}

		for (int i = k; i < 3 * k + 2; i++) {
			m[i] = absent.first();
			absent.remove(m[i]);
			absent.add(m[i - k]);
			if (i > 2 * k && m[i] != m[i - k - 1]) {
				throw new RuntimeException();
			}
		}
		out.println(m[((n - k) % (k + 1)) + k]);
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
