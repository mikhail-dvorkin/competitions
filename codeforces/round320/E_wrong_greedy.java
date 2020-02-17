package codeforces.round320;
import java.io.*;
import java.util.*;

public class E_wrong_greedy {
	void run() {
		String s = in.next();
		int n = s.length();
		int[] a = new int[n];
		int sum = 0;
		for (int i = 0; i < n; i++) {
			a[i] = (s.charAt(i) == 'R') ? 1 : 0;
			sum += a[i];
		}
		int best = n;
		int[] ans = new int[0];
		for (int first = 0; first < 2; first++) {
			if (n % 2 == 1 && sum != n / 2 + first) {
				continue;
			}
			int step = 0;
			int back = 0;
			int[] seq = new int[n];
			ArrayList<LinkedList<Integer>> groups = new ArrayList<>();
			int[] color = new int[n];
			int last = -1;
			for (int i = 0; i < n; i++) {
				if (i == n - 1 || a[i] != a[i + 1]) {
					LinkedList<Integer> ll = new LinkedList<>();
					for (int j = last + 1; j <= i; j++) {
						ll.add(j);
					}
					groups.add(ll);
					color[groups.size() - 1] = a[i];
					last = i;
				}
			}
			int current = first;
			while (!groups.isEmpty()) {
				if (step > 0) {
					back++;
				}
				for (int i = 0; i < groups.size(); i++) {
					if (color[i] != current) {
						continue;
					}
					int x = groups.get(i).removeFirst();
					seq[step++] = x;
					current ^= 1;
				}
				ArrayList<LinkedList<Integer>> groupsNew = new ArrayList<>();
				int[] colorNew = new int[groups.size()];
				for (int i = 0; i < groups.size(); i++) {
					if (!groups.get(i).isEmpty()) {
						if (groupsNew.isEmpty() || color[i] != colorNew[groupsNew.size() - 1]) {
							groupsNew.add(groups.get(i));
							colorNew[groupsNew.size() - 1] = color[i];
						} else {
							groupsNew.get(groupsNew.size() - 1).addAll(groups.get(i));
						}
					}
				}
				color = colorNew;
				groups = groupsNew;
			}
			if (back < best) {
				best = back;
				ans = seq.clone();
			}
		}
		System.out.println(best);
		for (int i = 0; i < n; i++) {
			System.out.print((ans[i] + 1) + " ");
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = E_wrong_greedy.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new E_wrong_greedy().run();
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
