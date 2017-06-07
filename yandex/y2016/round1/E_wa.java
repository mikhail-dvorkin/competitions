package yandex.y2016.round1;
import java.io.*;
import java.util.*;

public class E_wa {
	void run() {
		int n = in.nextInt();
		int len = in.nextInt();
		int[] a = new int[n];
		for (int i = 1; i < n; i++) {
			a[i] = in.nextInt() - 1;
		}
		Arrays.sort(a);
		int[] b = new int[n + 1];
		b[0] = a[0];
		b[n] = len - 1 - a[n - 1];
		for (int i = 1; i < n; i++) {
			b[i] = a[i] - a[i - 1] - 1;
		}
		
		int min = 0;
		int max = b[0];
		int ans = n - 1;
		int pMin = 0;
		int pMax = 1;
		for (int i = 1; i <= n; i++) {
			int min2 = b[i] - max;
			int max2 = b[i] - min;
			min = min2;
			max = max2;
			{int t = pMin; pMin = pMax; pMax = t;}
			if (min < 0) {
				pMin = 0;
				min = 0;
				if (max < 0) {
					ans++;
					ans += pMin + pMax;
					min = 0;
					max = b[i];
					pMin = 0;
					pMax = 1;
				}
			}
		}
		out.println(ans);
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
			new E_wa().run();
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
