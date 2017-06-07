package yandex.y2014.warmup;
import java.io.*;
import java.util.*;

public class A {
	public static final int[] dx = new int[]{1, 0, -1, 0, 1, -1, -1, 1};
	public static final int[] dy = new int[]{0, 1, 0, -1, 1, 1, -1, -1};

	void run() {
		parse();
		int wx = xy[0];
		int wy = xy[1];
		parse();
		int rx = xy[0];
		int ry = xy[1];
		parse();
		int bx = xy[0];
		int by = xy[1];
		if (Math.abs(wx - bx) <= 1 && Math.abs(wy - by) <= 1) {
			out.println("Strange");
			return;
		}
		boolean attackedByRook = attackedByRook(bx, by, rx, ry, wx, wy);
		boolean canMove = false;
		for (int d = 0; d < 8; d++) {
			int x = bx + dx[d];
			int y = by + dy[d];
			if (x < 0 || x >= 8 || y < 0 || y >= 8) {
				continue;
			}
			if (Math.abs(wx - x) <= 1 && Math.abs(wy - y) <= 1) {
				continue;
			}
			if (x == rx && y == ry) {
				canMove = true;
				break;
			}
			if (attackedByRook(x, y, rx, ry, wx, wy)) {
				continue;
			}
			canMove = true;
			break;
		}
		if (canMove) {
			if (!attackedByRook) {
				out.println("Normal");
			} else {
				out.println("Check");
			}
		} else {
			if (!attackedByRook) {
				out.println("Stalemate");
			} else {
				out.println("Checkmate");
			}
		}
	}
	
	boolean attackedByRook(int x, int y, int rx, int ry, int kx, int ky) {
		if (ry != y) {
			int t = x; x = y; y = t;
			t = rx; rx = ry; ry = t;
			t = kx; kx = ky; ky = t;
		}
		if (ry != y) {
			return false;
		}
		if (ky != y) {
			return true;
		}
		boolean between = (Integer.signum(kx - x) != Integer.signum(kx - rx));
		return !between;
	}

	int[] xy = new int[2];
	
	void parse() {
		String s = in.next();
		xy[0] = s.charAt(0) - 'a';
		xy[1] = s.charAt(1) - '1';
	}

	static boolean stdStreams = true;
	static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		BufferedReader br;
		if (stdStreams) {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			br = new BufferedReader(new FileReader(inputFileName));
			out = new PrintWriter(outputFileName);
		}
		in = new MyScanner(br);
		new A().run();
		br.close();
		out.close();
	}
	
	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		MyScanner(BufferedReader br) {
			this.br = br;
		}
		
		void findToken() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		String next() {
			findToken();
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		double nextDouble() {
			return Double.parseDouble(next());
		}
	}
}
