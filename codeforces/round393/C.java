package codeforces.round393;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		Node root = new Node(0, n);
		for (int i = 0; i < n; i++) {
			int order = in.nextInt() - 1;
			int x = in.nextInt();
			if (x == 1) {
				x = in.nextInt();
			}
			root.update(order, x);
			out.println(root.top());
		}
	}
	
	class Node {
		int low, high, mid;
		Node left, right;
		int val, up, down;

		Node(int low, int high) {
			this.low = low;
			this.high = high;
			if (low + 1 < high) {
				mid = (low + high) / 2;
				left = new Node(low, mid);
				right = new Node(mid, high);
			}
		}

		void update(int v, int x) {
			if (left == null) {
				if (x > 0) {
					up = 1;
					val = x;
				} else {
					down = 1;
				}
				return;
			}
			if (v < mid) {
				left.update(v, x);
			} else {
				right.update(v, x);
			}
			if (left.up >= right.down) {
				down = left.down;
				up = left.up - right.down + right.up;
			} else {
				down = left.down - left.up + right.down;
				up = right.up;
			}
		}
		
		int top(int pops) {
			if (left == null) {
				if (up > pops) {
					return val;
				}
				return -1;
			}
			if (right.up > pops) {
				return right.top(pops);
			}
			pops += right.down - right.up;
			return left.top(pops);
		}
		
		int top() {
			return top(0);
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
