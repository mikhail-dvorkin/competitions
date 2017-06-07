package codeforces.round283;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		List<Event> events = new ArrayList<>();
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			events.add(new Event(a[i], b[i], i));
		}
		int m = in.nextInt();
		int[] c = new int[m];
		int[] d = new int[m];
		int[] k = new int[m];
		for (int i = 0; i < m; i++) {
			c[i] = in.nextInt();
			d[i] = in.nextInt();
			k[i] = in.nextInt();
			events.add(new Event(c[i], d[i], k[i], i));
		}
		Collections.sort(events);
		TreeMap<Integer, List<Integer>> open = new TreeMap<Integer, List<Integer>>();
		boolean ans = true;
		int[] who = new int[n];
		main:
		for (Event event : events) {
			if (event.type) {
				if (!open.containsKey(event.b)) {
					open.put(event.b, new ArrayList<Integer>());
				}
				open.get(event.b).add(event.id);
				continue;
			}
			for (;;) {
				Integer x = open.ceilingKey(event.b);
				if (x == null) {
					ans = false;
					break main;
				}
				List<Integer> list = open.get(x);
				if (list.isEmpty()) {
					open.remove(x);
					continue;
				}
				int id = list.get(list.size() - 1);
				if (k[id] == 0) {
					list.remove(list.size() - 1);
					continue;
				}
				k[id]--;
				who[event.id] = id;
				break;
			}
		}
		if (!ans) {
			out.println("NO");
			return;
		}
		out.println("YES");
		for (int i = 0; i < n; i++) {
			out.print(who[i] + 1 + " ");
		}
	}
	
	class Event implements Comparable<Event> {
		int x;
		boolean type;
		int a, b, k, id;
		
		public Event(int a, int b, int id) {
			this.x = 2 * a + 1;
			this.type = false;
			this.a = a;
			this.b = b;
			this.id = id;
		}

		public Event(int a, int b, int k, int id) {
			this.x = 2 * a;
			this.type = true;
			this.a = a;
			this.b = b;
			this.k = k;
			this.id = id;
		}
		


		@Override
		public int compareTo(Event o) {
			return Integer.compare(x, o.x);
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
