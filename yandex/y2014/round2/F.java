package yandex.y2014.round2;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class F {
	int n;
	
	void run() {
		n = in.nextInt();
		int[][] a = cycleLengths();
		int[][] b = cycleLengths();
		int[][] c = cycleLengths();
		int[] aKey = a[0];
		int[] aValue = a[1];
		int[] bKey = b[0];
		int[] bValue = b[1];
		int[] cKey = c[0];
		int[] cValue = c[1];
		long ans = 0;
		for (int i = 0; i < aKey.length; i++) {
			for (int j = 0; j < bKey.length; j++) {
				long product = aKey[i] * aValue[i] * bKey[j] * bValue[j];
				long lcm = lcm(aKey[i], bKey[j]);
				for (int k = 0; k < cKey.length; k++) {
					ans += product * cKey[k] * cValue[k] / lcm(lcm, cKey[k]);
				}
			}
		}
		out.println(ans);
	}

	int[][] cycleLengths() {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
		}
		boolean[] mark = new boolean[n];
		TreeMap<Integer, Integer> count = new TreeMap<Integer, Integer>();
		for (int i = 0; i < n; i++) {
			if (mark[i]) {
				continue;
			}
			int j = i;
			int len = 0;
			while (!mark[j]) {
				mark[j] = true;
				len++;
				j = a[j];
			}
			if (count.containsKey(len)) {
				count.put(len, count.get(len) + 1);
			} else {
				count.put(len, 1);
			}
		}
		int m = count.size();
		int[][] r = new int[2][m];
		int i = 0;
		for (Entry<Integer, Integer> entry : count.entrySet()) {
			r[0][i] = entry.getKey();
			r[1][i] = entry.getValue();
			i++;
		}
		return r;
	}
	
	public static long gcd(long a, long b) {
		while (a > 0) {
			long t = b % a;
			b = a;
			a = t;
		}
		return b;
	}
	
	public static long lcm(long a, long b) {
		return (a / gcd(a, b)) * b;
	}
	
	static boolean stdStreams = false;
	static String fileName = F.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = "input.txt";
	static String outputFileName = "output.txt";
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
		new F().run();
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
