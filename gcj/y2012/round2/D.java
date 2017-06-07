package gcj.y2012.round2;
import java.io.*;
import java.util.*;

public class D {
	private static String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		boolean[][] o = new boolean[hei][wid];
		int[] cavei = new int[10];
		int[] cavej = new int[10];
		int caves = 0;
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				o[i][j] = s.charAt(j) == '#';
				int n = s.charAt(j) - '0';
				if (n >= 0 && n <= 9) {
					cavei[n] = i;
					cavej[n] = j;
					caves = Math.max(caves, n + 1);
				}
			}
		}
		int[] x = new int[hei * wid]; 
		int[] y = new int[hei * wid]; 
		for (int c = 0; c < caves; c++) {
			int ci = cavei[c];
			int cj = cavej[c];
			boolean[] access = new boolean[wid];
			access[cj] = true;
			int n = 0;
			for (int i = ci; i > 0; i--) {
				for (int j = 0; j + 1 < wid; j++) {
					if (access[j] && !o[i][j + 1]) {
						access[j + 1] = true;
					}
				}
				for (int j = wid - 1; j > 0; j--) {
					if (access[j] && !o[i][j - 1]) {
						access[j - 1] = true;
					}
					if (access[j]) {
						x[n] = i;
						y[n] = j;
						n++;
						if (o[i - 1][j]) {
							access[j] = false;
						}
					}
				}
			}
			out.print(c + ": " + n + " ");
			
		}
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
			new D().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
