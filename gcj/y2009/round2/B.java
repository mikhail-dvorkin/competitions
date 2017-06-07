package gcj.y2009.round2;
import java.io.*;
import java.util.*;

public class B implements Runnable {
	private static final int infty = Integer.MAX_VALUE / 2;
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		@SuppressWarnings("unused")
		int fall = in.nextInt();
		boolean[][] o = new boolean[hei][wid];
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				o[i][j] = s.charAt(j) == '#';
			}
		}
		int[][][] a = new int[hei][wid][wid + 1];
		for (int h = 0; h < hei; h++) {
			for (int le = 0; le < wid; le++) {
				for (int ri = le; ri < wid; ri++) {
					a[h][le][ri] = infty;
				}
			}
		}
		{
			int i = 0;
			do {
				i++;
			} while (i < wid && !o[0][i]);
			a[0][0][i - 1] = 0;
		}
		for (int h = 0; h < hei - 1; h++) {
			for (int le = 0; le < wid; le++) {
				for (int ri = le; ri < wid; ri++) {
					if (a[h][le][ri] == infty) {
						continue;
					}
				}
			}
		}
	}

	@Override
	public void run() {
		int tests = in.nextInt();
		in.nextLine();
		for (int t = 1; t <= tests; t++) {
			out.print("Case #" + t + ": ");
			solve();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		Thread thread = new Thread(new B());
		thread.start();
		thread.join();
		in.close();
		out.close();
	}
}
