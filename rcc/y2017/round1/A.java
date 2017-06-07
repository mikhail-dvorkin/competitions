package rcc.y2017.round1;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int a = in.nextInt();
		int b = in.nextInt();
		ArrayList<Integer> primes = new ArrayList<>();
		primes.addAll(primeFactorization(a));
		primes.addAll(primeFactorization(b));
		Collections.sort(primes);
		for (int i = 0; i + 1 < primes.size(); i++) {
			if (primes.get(i) == primes.get(i + 1)) {
				primes.remove(i);
				primes.remove(i);
				i--;
			}
		}
		int m = primes.size();
		long prod = 1;
		for (int p : primes) {
			prod *= p;
		}
		long best = prod + 2;
		long bestX = -1;
		for (int mask = 0; mask < (1 << m); mask++) {
			long x = 1;
			for (int i = 0; i < m; i++) {
				if (((mask >> i) & 1) != 0) {
					x *= primes.get(i);
				}
			}
			if (x + prod / x < best) {
				best = x + prod / x;
				bestX = x;
			}
		}
		System.out.println(bestX + " " + prod / bestX);
	}
	
	public static ArrayList<Integer> primeFactorization(int n) {
		ArrayList<Integer> res = new ArrayList<>();
		for (int i = 2; n > 1 && i * i <= n; i++) {
			while (n % i == 0) {
				res.add(i);
				n /= i;
			}
		}
		if (n > 1)
			res.add(n);
		return res;
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			new A().run();
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
