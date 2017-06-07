package yandex.y2016.round2;
import java.io.*;
import java.util.*;

public class C_search {
	int n;
	
	void run() {
		n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		Arrays.sort(a);
		countIsPrimeArray(a[n - 1]);
		out.println(solve(a));
	}

	int solve(int[] a) {
		int best = 0;
		for (int i : a) {
			best += Integer.bitCount(i);
		}
		int[] b = new int[n];
		for (int d = 2; d <= a[n - 1]; d++) {
			if (!isPrime[d]) {
				continue;
			}
			int cost = 0;
			int sure = 0;
			for (int j = 0; j < n; j++) {
				cost += a[j] % d;
				b[j] = a[j] / d;
				if (a[j] < d) {
					sure += a[j];
				}
			}
			if (sure >= best) {
				break;
			}
			if (cost >= best) {
				continue;
			}
			cost += solve(b);
			best = Math.min(best, cost);
		}
		return best;
	}
	
	static boolean[] isPrime;
	
	static void countIsPrimeArray(int n) {
		isPrime = new boolean[n + 1];
		for (int i = 2; i <= n; i++) {
			isPrime[i] = true;
		}
		for (int i = 2, j; (j = i * i) <= n; i++) {
			if (!isPrime[i]) {
				continue;
			}
			do {
				isPrime[j] = false;
				j += i;
			} while (j <= n);
		}
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
			new C_search().run();
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
