package roi.y2019;
import java.io.*;
import java.util.*;

public class F_slow {
	void run() {
		int m = in.nextInt();
		int n = in.nextInt();
		int k = in.nextInt();
		int[] min = new int[k];
		int[] max = new int[k];
		for (int i = 0; i < k; i++) {
			min[i] = in.nextInt();
			max[i] = in.nextInt();
		}
		int[] a = new int[2 * n];
		long totalAnswer = 0;
		while (m-- > 0) {
			for (int i = 0; i < 2 * n; i++) {
				a[i] = in.nextInt();
			}
			Arrays.sort(a);
			long ans = 0;
			for (int i = 0; i < 2 * n; i += 2) {
				int best = Integer.MAX_VALUE;
				for (int j = 0; j < k; j++) {
					int score = score(a[i], min[j], max[j]) + score(a[i + 1], min[j], max[j]);
					best = Math.min(best, score);
				}
				ans += best;
			}
			totalAnswer += ans;
		}
		out.println(totalAnswer);
	}

	private int score(int x, int min, int max) {
		if (x <= min) {
			return min - x;
		}
		if (x >= max) {
			return x - max;
		}
		return 0;
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = F_slow.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";
		
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
			//out.print("Case #" + (test + 1) + ": ");
			new F_slow().run();
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
