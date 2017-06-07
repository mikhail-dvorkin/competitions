package gcj.y2009.qual;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class B implements Runnable {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	private int hei;
	private int wid;
	private int[][] a;
	private Point[][] b;
	private final int[] dx = new int[]{-1, 0, 0, 1};
	private final int[] dy = new int[]{0, -1, 1, 0};

	private void solve() {
		hei = in.nextInt();
		wid = in.nextInt();
		a = new int[hei][wid];
		b = new Point[hei][wid];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				a[i][j] = in.nextInt();
			}
		}
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				go(i, j);
			}
		}
		char c = 'a';
		Map<Point, Character> map = new HashMap<Point, Character>();
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (!map.containsKey(b[i][j])) {
					map.put(b[i][j], c++);
				}
			}
		}
		for (int i = 0; i < hei; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < wid; j++) {
				sb.append(map.get(b[i][j])).append(' ');
			}
			out.println(sb.toString().trim());
		}
	}

	private Point go(int i, int j) {
		if (b[i][j] != null)
			return b[i][j];
		int minH = Integer.MAX_VALUE;
		int dir = -1;
		for (int d = 0; d < 4; d++) {
			int ii = i + dx[d];
			int jj = j + dy[d];
			try {
				int h = a[ii][jj];
				if (h < minH) {
					minH = h;
					dir = d;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
		}
		if (minH >= a[i][j]) {
			b[i][j] = new Point(i, j);
			return b[i][j];
		}
		b[i][j] = go(i + dx[dir], j + dy[dir]);
		return b[i][j];
	}

	@Override
	public void run() {
		int tests = in.nextInt();
		for (int t = 1; t <= tests; t++) {
			out.println("Case #" + t + ": ");
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
