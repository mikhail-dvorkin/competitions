package codeforces.round225;
import java.io.*;
import java.util.*;

public class B {
	static class Segment implements Comparable<Segment> {
		int diag;
		int from, to, len;
		boolean left, right;

		public Segment(int diag, int from, int to) {
			this.diag = diag;
			this.from = from;
			this.to = to;
			len = to - from;
			left = (from == 0);
			right = (to == diag + 1);
		}

		@Override
		public int compareTo(Segment that) {
			return from - that.from;
		}

		boolean update(int newDiag) {
			int delta = newDiag - diag;
			if (!left) {
				from += delta;
			}
			if (right) {
				to += delta;
			}
			len = to - from;
			diag = newDiag;
			return len <= 0;
		}

		@Override
		public String toString() {
			return diag + " " + from + "-" + to;
		}
	}

	int n;

	void run() {
		n = in.nextInt();
		int m = in.nextInt();
		Segment[] segments = new Segment[m];
		for (int i = 0; i < m; i++) {
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			segments[i] = new Segment(x + y, x, x + 1);
		}
		Arrays.sort(segments, Comparator.comparingInt((Segment a) -> a.diag).thenComparingInt(a -> a.from));
		TreeSet<Segment> set = new TreeSet<>();
		for (Segment segment : segments) {
			int diag = segment.diag;
			Segment prev = set.floor(segment);
			if(prev != null) {
				if (prev.update(diag)) {
					set.remove(prev);
					prev = null;
				}
			}
			if (prev != null) {
				if (prev.from <= segment.from && segment.to <= prev.to) {
					continue;
				}
				if (segment.from == prev.to) {
					set.remove(prev);
					segment = new Segment(diag, prev.from, segment.to);
				}
			}
			set.add(segment);
			Segment next = set.higher(segment);
			if(next != null) {
				if (next.update(diag)) {
					set.remove(next);
					next = null;
				}
			}
			if (next != null) {
				if (segment.to == next.from) {
					set.remove(segment);
					set.remove(next);
					segment = new Segment(diag, segment.from, next.to);
					set.add(segment);
				}
			}
		}
		Segment finish = new Segment(2 * n - 2, n - 1, n);
		Segment prev = set.floor(finish);
		if(prev != null) {
			if (prev.update(finish.diag)) {
				set.remove(prev);
				prev = null;
			}
		}
		if (prev != null) {
			if (prev.from <= finish.from && finish.to <= prev.to) {
				out.println(-1);
				return;
			}
		}
		out.println(finish.diag);
	}

	static boolean stdStreams = true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new B().run();
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
