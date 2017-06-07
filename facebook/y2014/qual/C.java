package facebook.y2014.qual;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	static final int FACTOR = 1000;
	
	private void solve() {
		int n = in.nextInt();
		double ps = in.nextDouble();
		double pr = in.nextDouble();
		int pi = (int) Math.round(in.nextDouble() * FACTOR);
		int pu = (int) Math.round(in.nextDouble() * FACTOR);
		double pw = in.nextDouble();
		int pd = (int) Math.round(in.nextDouble() * FACTOR);
		double pl = in.nextDouble();
		double[][][] p = new double[n + 1][n + 1][FACTOR + 1];
		p[0][0][pi] = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k <= FACTOR; k++) {
					double pSun = k * 1.0 / FACTOR;
					double pWin = pSun * ps + (1 - pSun) * pr;
					p[i + 1][j][k] += p[i][j][k] * pWin * (1 - pw);
					p[i + 1][j][Math.min(k + pu, FACTOR)] += p[i][j][k] * pWin * pw;
					p[i][j + 1][k] += p[i][j][k] * (1 - pWin) * (1 - pl);
					p[i][j + 1][Math.max(k - pd, 0)] += p[i][j][k] * (1 - pWin) * pl;
				}
			}
		}
		double ans = 0;
		for (int j = 0; j < n; j++) {
			for (int k = 0; k <= FACTOR; k++) {
				ans += p[n][j][k];
			}
		}
		out.printf("%.6f\n", ans);
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
