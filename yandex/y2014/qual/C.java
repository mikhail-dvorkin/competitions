package yandex.y2014.qual;
import java.io.*;
import java.util.*;

public class C {
	static class Paper implements Comparable<Paper> {
		int x, y;

		public Paper(int x, int y) {
			if (x > y) {
				int t = x;
				x = y;
				y = t;
			}
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Paper o) {
			int c = o.x + o.y - x - y;
			if (c != 0) {
				return c;
			}
			return x - o.x;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Paper other = (Paper) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
	
	void run() {
		int n = in.nextInt();
		Paper[] papers = new Paper[n];
		for (int i = 0; i < n; i++) {
			papers[i] = new Paper(in.nextInt(), in.nextInt());
		}
		Arrays.sort(papers);
		boolean bad = false;
		for (int i = 0; i < n; i++) {
			if (i >= 2 && papers[i].equals(papers[i - 1]) && papers[i].equals(papers[i - 2])) {
				bad = true;
			}
			if (i >= 1 && papers[i].equals(papers[i - 1]) && papers[i].x == papers[i].y) {
				bad = true;
			}
		}
		if (bad) {
			out.println("Chaos");
			return;
		}
		for (int i = 0; i < n; i++) {
			if (i >= 1 && papers[i].equals(papers[i - 1])) {
				out.println(papers[i].y + " " + papers[i].x);
			} else {
				out.println(papers[i].x + " " + papers[i].y);
			}
		}
	}

	static boolean stdStreams = true;
	static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new C().run();
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
