package yandex.y2016.round1;
import java.io.*;
import java.util.*;

public class C {
	static final int[] POINTS = new int[]{100, 75, 60, 50, 45, 40, 36, 32, 29, 26, 24, 22, 20, 18, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	static final int N = 10;
	static final int M = 1000;
			
	void run() {
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = in.nextInt();
		}
		boolean[] taken = new boolean[POINTS.length];
		for (int p = M - 1; p >= 0; p--) {
			Arrays.fill(taken, false);
			int myScore = 0;
			if (p < taken.length) {
				myScore = POINTS[p];
				taken[p] = true;
			}
			boolean canLose = true;
			for (int i = 0; i < N; i++) {
				int need = myScore - a[i];
				boolean placed = false;
				for (int j = POINTS.length - 1; j >= 0; j--) {
					if (taken[j]) {
						continue;
					}
					if (POINTS[j] >= need) {
						taken[j] = true;
						placed = true;
						break;
					}
				}
				if (!placed) {
					canLose = false;
					break;
				}
			}
			if (!canLose) {
				out.println(p + 1);
				return;
			}
		}
		out.println(0);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = false;
		String inputFileName = "input.txt";
		String outputFileName = "output.txt";
		
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new C().run();
		}
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
