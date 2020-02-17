package codeforces.globalround3;
import java.io.*;
import java.util.*;

public class E {
	static final String NO = "NO";

	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		Stone[] stones = new Stone[n];
		long sumA = 0;
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			sumA += a[i];
			stones[i] = new Stone();
			stones[i].id = i;
			stones[i].x = a[i];
		}
		int[] b = new int[n];
		long sumB = 0;
		for (int i = 0; i < n; i++) {
			b[i] = in.nextInt();
			sumB += b[i];
		}
		if (sumA != sumB) {
			out.println(NO);
			return;
		}
		Arrays.sort(stones);
		Arrays.sort(b);
		ArrayList<Stone> inc = new ArrayList<>();
		ArrayList<Stone> dec = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			Stone stone = stones[i];
			stone.dest = b[i];
			(stone.x <= stone.dest ? inc : dec).add(stone);
		}
		ArrayList<String> ans = new ArrayList<>();
		int j = 0;
		for (int i = 0; i < inc.size();) {
			Stone left = inc.get(i);
			if (left.x == left.dest) {
				i++;
				continue;
			}
			if (j == dec.size()) {
				out.println(NO);
				return;
			}
			Stone right = dec.get(j);
			if (right.x == right.dest) {
				j++;
				continue;
			}
			int dist = Math.min(left.dest - left.x, right.x - right.dest);
			dist = Math.min(dist, (right.x - left.x) / 2);
			if (dist <= 0) {
				out.println(NO);
				return;
			}
			left.x += dist;
			right.x -= dist;
			ans.add((left.id + 1) + " " + (right.id + 1) + " " + dist);
		}
		out.println("YES");
		out.println(ans.size());
		for (String s : ans) {
			out.println(s);
		}
	}

	static class Stone implements Comparable<Stone> {
		int id;
		int x;
		int dest;

		@Override
		public int compareTo(Stone that) {
			return x - that.x;
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";

		Locale.setDefault(Locale.US);
		BufferedReader br;
		//noinspection ConstantConditions
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
			new E().run();
		}
		br.close();
		out.close();
	}

	static class MyScanner {
		final BufferedReader br;
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
