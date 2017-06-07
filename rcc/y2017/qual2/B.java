package rcc.y2017.qual2;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class B {
	void run() {
		BigInteger a = BigInteger.valueOf(in.nextInt());
		BigInteger b = BigInteger.valueOf(in.nextInt());
		BigInteger c = BigInteger.valueOf(in.nextInt());
		BigInteger d = BigInteger.valueOf(in.nextInt());
		BigInteger k = lcm(b, d);
		BigInteger x = k.divide(b).multiply(a);
		BigInteger y = k.divide(d).multiply(c);
		BigInteger z = lcm(x, y);
		BigInteger g = z.gcd(k);
		out.println(z.divide(g) + " " + k.divide(g));
	}
	
	BigInteger lcm(BigInteger a, BigInteger b) {
		return a.divide(a.gcd(b)).multiply(b);
	}
	
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new B().run();
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
