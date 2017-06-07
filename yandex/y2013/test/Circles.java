package yandex.y2013.test;
import java.io.*;
import java.util.*;

public class Circles {
	private static String fileName = Circles.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	public void run() {
		int n = in.nextInt();
		double[] r = new double[n];
		for (int i = 0; i < n; i++) {
			int x1 = in.nextInt();
			int y1 = in.nextInt();
			int x2 = in.nextInt();
			int y2 = in.nextInt();
			int x3 = in.nextInt();
			int y3 = in.nextInt();
			int area = x1 * y2 - x2 * y1 +
					   x2 * y3 - x3 * y2 +
					   x3 * y1 - x1 * y3;
			r[i] = Math.hypot(x1 - x2, y1 - y2) *
					Math.hypot(x3 - x2, y3 - y2) *
					Math.hypot(x1 - x3, y1 - y3) / Math.abs(area);
		}
		Arrays.sort(r);
		double rr = -1;
		int count = 0;
		int ans = 1;
		for (int i = 0; i < n; i++) {
			if (r[i] - rr > r[i] * 1e-9) {
				count = 1;
			} else {
				count++;
			}
			ans = Math.max(ans, count);
			rr = r[i];
		}
		System.out.println(Arrays.toString(r));
		out.println(ans);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		new Circles().run();
		in.close();
		out.close();
	}
}
