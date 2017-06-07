package gcj.y2013.qual;
import java.io.*;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	int n = 4;
	String X_WON = "X won";
	String O_WON = "O won";
	String DRAW = "Draw";
	String NOT_COMPLETED = "Game has not completed";
	
	private void solve() {
		char[][] f = new char[n][];
		for (int i = 0; i < n; i++) {
			f[i] = in.next().toCharArray();
		}
		boolean completed = true;
		for (int r = 0; r < 2 * n + 2; r++) {
			int x0, y0, dx, dy;
			if (r < n) {
				x0 = r;
				y0 = 0;
				dx = 0;
				dy = 1;
			} else if (r < 2 * n) {
				x0 = 0;
				y0 = r - n;
				dx = 1;
				dy = 0;
			} else if (r == 2 * n) {
				x0 = y0 = 0;
				dx = dy = 1;
			} else {
				x0 = n - 1;
				y0 = 0;
				dx = -1;
				dy = 1;
			}
			int x = x0, y = y0;
			boolean xWin = true, oWin = true;
			for (int i = 0; i < n; i++) {
				char c = f[x][y];
				if (c == '.') {
					xWin = oWin = completed = false;
				} else if (c == 'X') {
					oWin = false;
				} else if (c == 'O') {
					xWin = false;
				}
				x += dx;
				y += dy;
			}
			if (xWin) {
				out.println(X_WON);
				return;
			}
			if (oWin) {
				out.println(O_WON);
				return;
			}
		}
		out.println(completed ? DRAW : NOT_COMPLETED);
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