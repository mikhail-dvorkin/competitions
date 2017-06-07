package facebook.y2013.round1;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	boolean[] mark;
	int[] left;
	boolean[][] e;
	
	public boolean match() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				e[i][j] = true;
				for (int k = 0; k < len; k++) {
					if (s[i * len + k] == '?') {
						continue;
					}
					if (t[j * len + k] == '?') {
						continue;
					}
					if (s[i * len + k] != t[j * len + k]) {
						e[i][j] = false;
						break;
					}
				}
			}
		}
		Arrays.fill(left, -1);
		for (int i = 0; i < m; i++) {
			Arrays.fill(mark, false);
			if (!dfs(i)) {
				return false;
			}
		}
		return true;
	}

	private boolean dfs(int i) {
		if (mark[i]) {
			return false;
		}
		mark[i] = true;
		for (int j = 0; j < m; j++) {
			if (!e[i][j]) {
				continue;
			}
			if (left[j] == -1 || dfs(left[j])) {
				left[j] = i;
				return true;
			}
		}
		return false;
	}
	
	char min = 'a';
	char max = 'f';
	
	int m, n, len;
	char[] s, t;
	
	private void solve() {
		m = in.nextInt();
		s = in.next().toCharArray();
		t = in.next().toCharArray();
		n = s.length;
		len = n / m;
		mark = new boolean[m];
		left = new int[m];
		e = new boolean[m][m];
		if (!match()) {
			out.println("IMPOSSIBLE");
			return;
		}
		for (int i = 0; i < n; i++) {
			if (s[i] != '?') {
				continue;
			}
			for (char c = min; c <= max; c++) {
				s[i] = c;
				if (match()) {
					break;
				}
			}
		}
		out.println(s);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		for (int t = 1; t <= tests; t++) {
			out.print("Case #" + t + ": ");
			new B().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
