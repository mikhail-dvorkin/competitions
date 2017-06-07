package yandex.y2013.test;
import java.io.*;
import java.util.*;

public class Proddiff {
	private static String fileName = Proddiff.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	int[] best = new int[0];
	int[] a = new int[32];
	
	public void run() {
		int n = in.nextInt();
		search(n, 0, 1);
		out.println(best.length);
		for (int b : best) {
			out.print(b + " ");
		}
	}

	void search(int n, int i, int m) {
		if (m > n) {
			return;
		}
		for (; m * (m + 1L) <= n; m++) {
			if (n % m != 0) {
				continue;
			}
			a[i] = m;
			search(n / m, i + 1, m + 1);
		}
		a[i] = n;
		if (i + 1 > best.length) {
			best = Arrays.copyOf(a, i + 1);
		}
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		new Proddiff().run();
		in.close();
		out.close();
	}
}
