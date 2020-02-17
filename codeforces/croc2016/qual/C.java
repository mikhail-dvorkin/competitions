package codeforces.croc2016.qual;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class C {
	void run() {
		int n = in.nextInt();
		Map<String, Set<String>> map = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			String s = in.next();
			int slash = s.indexOf('/', "http://".length());
			if (slash == -1) {
				slash = s.length();
			}
			String hostname = s.substring(0, slash);
			String path = s.substring(slash);
			if (!map.containsKey(hostname)) {
				map.put(hostname, new TreeSet<>());
			}
			map.get(hostname).add(path);
		}
		Map<String, List<String>> paths2hostnames = new TreeMap<>();
		for (Entry<String, Set<String>> entry : map.entrySet()) {
			StringBuilder sb = new StringBuilder();
			for (String path : entry.getValue()) {
				sb.append(path).append(" ");
			}
			String paths = sb.toString();
			if (!paths2hostnames.containsKey(paths)) {
				paths2hostnames.put(paths, new ArrayList<>());
			}
			paths2hostnames.get(paths).add(entry.getKey());
		}
		List<String> answer = new ArrayList<>();
		for (Entry<String, List<String>> entry : paths2hostnames.entrySet()) {
			if (entry.getValue().size() > 1) {
				StringBuilder sb = new StringBuilder();
				for (String s : entry.getValue()) {
					sb.append(s).append(" ");
				}
				answer.add(sb.toString().trim());
			}
		}
		System.out.println(answer.size());
		for (String s : answer) {
			System.out.println(s);
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
