package roi.y2017;
import java.io.*;
import java.util.*;

public class E {
	boolean run() {
		String s = in.next();
		String t = in.next();
		int n = s.length();
		for (int from = 0; from < n;) {
			if (from > 0 && !t.substring(from - 1, from + 1).equals(s.substring(from - 1, from + 1))) {
				return false;
			}
			int to = from;
			while (to < n && t.charAt(to) == t.charAt(from)) {
				to++;
			}
			ArrayList<Integer> fragments = new ArrayList<>();
			for (int left = from; left < to;) {
				int right = left;
				while (right < to && s.charAt(right) == s.charAt(left)) {
					right++;
				}
				int f = right - left;
				if (s.charAt(left) != t.charAt(from)) {
					f *= -1;
				}
				fragments.add(f);
				while (fragments.size() > 1) {
					int a = fragments.get(fragments.size() - 1);
					int b = fragments.get(fragments.size() - 2);
					if (a + b <= 0) {
						break;
					}
					fragments.remove(fragments.size() - 1);
					fragments.remove(fragments.size() - 1);
					fragments.add(Math.abs(a) + Math.abs(b));
				}
				left = right;
			}
			if (fragments.size() > 1 || fragments.get(0) < 0) {
				return false;
			}
			from = to;
		}
		return true;
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
			out.println(new E().run() ? "Yes" : "No");
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
