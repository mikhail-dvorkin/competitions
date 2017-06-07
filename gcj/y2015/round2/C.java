package gcj.y2015.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	int m;
	String[] text;
	
	String solve() {
		TreeMap<String, Integer> words = new TreeMap<>();
		int[][] ids = new int[m][];
		for (int i = 0; i < m; i++) {
			TreeSet<Integer> set = new TreeSet<>();
			for (String w : text[i].split("\\s+")) {
				if (!words.containsKey(w)) {
					words.put(w, words.size());
				}
				set.add(words.get(w));
			}
			ids[i] = new int[set.size()];
			int k = 0;
			for (int x : set) {
				ids[i][k++] = x;
			}
		}
		int wordsCount = words.size();
		Vertex[] vSentence = new Vertex[m];
		Vertex[] vIn = new Vertex[wordsCount];
		Vertex[] vOut = new Vertex[wordsCount];
		int inf = Integer.MAX_VALUE / 3;
		for (int i = 0; i < wordsCount; i++) {
			vIn[i] = new Vertex();
			vOut[i] = new Vertex();
			addEdge(vIn[i], vOut[i], 1, 0);
		}
		for (int i = 0; i < m; i++) {
			vSentence[i] = new Vertex();
			for (int j : ids[i]) {
				addEdge(vSentence[i], vIn[j], inf, 0);
				addEdge(vOut[j], vSentence[i], inf, 0);
			}
		}
		s = vSentence[0];
		t = vSentence[1];
		return "" + fordFulkerson();
	}
	
	static class Vertex {
		List<Edge> adj = new ArrayList<Edge>();
		int time;
	}
	
	static class Edge {
		Vertex from, to;
		int c, f;
		Edge rev;
		
		public Edge(Vertex from, Vertex to, int c) {
			this.from = from;
			this.to = to;
			this.c = c;
		}
	}
	
	Vertex s, t;
	int curTime;
	
	static void addEdge(Vertex v, Vertex u, int c1, int c2) {
		Edge vu = new Edge(v, u, c1);
		Edge uv = new Edge(u, v, c2);
		vu.rev = uv;
		uv.rev = vu;
		v.adj.add(vu);
		u.adj.add(uv);
	}
	
	public int fordFulkerson() {
		for (int ans = 0;;) {
			curTime++;
			if (dfsFordFulkerson(s)) {
				ans++;
				continue;
			}
			return ans;
		}
	}
	
	public boolean dfsFordFulkerson(Vertex v) {
		if (v == t) {
			return true;
		}
		v.time = curTime;
		for (Edge e : v.adj) {
			Vertex u = e.to;
			if (e.f == e.c || u.time == curTime) {
				continue;
			}
			if (dfsFordFulkerson(u)) {
				e.f++;
				e.rev.f--;
				return true;
			}
		}
		return false;
	}
	
	public C(Scanner in) {
		m = in.nextInt();
		in.nextLine();
		text = new String[m];
		for (int i = 0; i < m; i++) {
			text[i] = in.nextLine();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";
		
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		@SuppressWarnings("unchecked")
		Callable<String>[] callables = new Callable[tests];
		for (int t = 0; t < tests; t++) {
			final C testCase = new C(in);
			final int testCaseNumber = t + 1;
			callables[t] = new Callable<String>() {
				@Override
				public String call() {
					String answer = testCase.solve();
					System.out.println(String.format(formatSystemOut, answer, testCaseNumber));
					return String.format(formatOut, answer, testCaseNumber);
				}
			};
		}
		try {
			if (nThreads > 1) {
				ExecutorService executor = Executors.newFixedThreadPool(4);
				@SuppressWarnings("unchecked")
				Future<String>[] outputs = new Future[tests];
				for (int t = 0; t < tests; t++) {
					outputs[t] = executor.submit(callables[t]);
				}
				for (int t = 0; t < tests; t++) {
					out.println(outputs[t].get());
				}
				executor.shutdown();
			} else {
				for (int t = 0; t < tests; t++) {
					out.println(callables[t].call());
				}
			}
		} catch (Exception e) {
			System.out.flush();
			System.err.flush();
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("COMPLETE");
		in.close();
		out.close();
	}
}
