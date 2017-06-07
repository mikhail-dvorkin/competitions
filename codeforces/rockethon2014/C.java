package codeforces.rockethon2014;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int place = in.nextInt();
		if (place == n + 1) {
			System.out.println(0);
			return;
		}
		int needBeHigher = n + 1 - place;
		long inf = Long.MAX_VALUE / 3;
		long ans = inf;
//		Map<Integer, List<Integer>> map = new TreeMap<>();
//		Participant[] participants = new Participant[n];
		int[] points = new int[n];
		int[] effort = new int[n];
		for (int i = 0; i < n; i++) {
//			participants[i] = new Participant(in.nextInt(), in.nextInt());
			points[i] = in.nextInt();
			effort[i] = in.nextInt();
//			if (!map.containsKey(points)) {
//				map.put(points, new ArrayList<Integer>());
//			}
//			map.get(points).add(efforts);
		}
		int[] allPoints = points.clone();
		Arrays.sort(allPoints);
		int needPoints = allPoints[n - place];
		for (int gotPoints = needPoints; gotPoints <= needPoints + 2; gotPoints++) {
			List<Integer> a = new ArrayList<>();
			List<Integer> b = new ArrayList<>();
			int c = 0;
			for (int i = 0; i < n; i++) {
				int p = points[i];
				int e = effort[i];
				if (p > gotPoints) {
					a.add(e);
				} else if (p == gotPoints || p == gotPoints - 1) {
					b.add(e);
				} else {
					a.add(e);
					c++;
				}
			}
			Collections.sort(a);
			Collections.sort(b);
			long cur = 0;
			int takeB = needBeHigher - c;
			if (takeB > b.size()) {
				continue;
			}
			if (takeB < 0) {
				takeB = 0;
			}
			int score = 0;
			int ai = 0;
			int bi = 0;
			while (takeB > 0) {
				takeB--;
				cur += b.get(bi);
				bi++;
				score++;
			}
			while (score < gotPoints) {
				if (bi < b.size() && (ai == a.size() || b.get(bi) < a.get(ai))) {
					cur += b.get(bi);
					bi++;
					score++;
				} else if (ai < a.size()) {
					cur += a.get(ai);
					ai++;
					score++;
				} else {
					break;
				}
			}
			if (score >= gotPoints) {
				ans = Math.min(ans, cur);
			}
		}
//		int mustDefeat = n + 1 - place;
//		int defeated = 0;
//		for (int p : map.keySet()) {
//			List<Integer> curList = map.get(p);
//			Collections.sort(curList);
//		}
		System.out.println(ans == inf ? -1 : ans);
	}
	
//	class Participant implements Comparable<Participant> {
//		int points;
//		int effort;
//		
//		public Participant(int points, int effort) {
//			this.points = points;
//			this.effort = effort;
//		}
//
//		@Override
//		public int compareTo(Participant o) {
//			return 0;
//		}
//	}

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
