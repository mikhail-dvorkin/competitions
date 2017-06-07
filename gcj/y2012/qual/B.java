package gcj.y2012.qual;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	int couldGetAtLeastP(int total, int p) {
		int best = (total + 2) / 3;
		if (best >= p) {
			return 2;
		}
		int bestSurprising = (total + 4) / 3;
		if (bestSurprising >= p && total >= 2 && total <= 28) {
			return 1;
		}
		return 0;
	}

	private void solve() {
		int n = in.nextInt();
		int surprising = in.nextInt();
		int p = in.nextInt();
		int[] count = new int[3];
		for (int i = 0; i < n; i++) {
			int total = in.nextInt();
			count[couldGetAtLeastP(total, p)]++;
		}
		out.println(count[2] + Math.min(count[1], surprising));
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
