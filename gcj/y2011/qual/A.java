package gcj.y2011.qual;
import java.io.*;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		int[] who = new int[n];
		int[] where = new int[n];
		for (int i = 0; i < n; i++) {
			who[i] = in.next().equals("B") ? 0 : 1;
			where[i] = in.nextInt();
		}
		int[] x = new int[]{1, 1};
		int[] y = new int[2];
		int t = 0;
		for (int i = 0; i < n; i++) {
			y[0] = y[1] = 1;
			for (int j = n - 1; j >= i; j--) {
				y[who[j]] = where[j];
			}
			while (x[who[i]] != where[i]) {
				for (int j = 0; j < 2; j++) {
					x[j] += Math.signum(y[j] - x[j]);
				}
				t++;
			}
			for (int j = 0; j < 2; j++) {
				x[j] += Math.signum(y[j] - x[j]);
			}
			t++;
		}
		out.println(t);
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
