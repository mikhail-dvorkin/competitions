package petrozavodsk.y2018.day7;

import java.io.*;
import java.util.*;

public class J_small_slow {
	void run() {
		int n = in.nextInt();
		int[] check = new int[n];
		for (int i = 0; i < n; i++) {
			String s = in.next();
			for (int j = 0; j < n; j++) {
				if (s.charAt(j) == '1') {
					check[i] += 1 << j;
				}
			}
		}
		int[] shoot = new int[1 << n];
		int inf = Integer.MAX_VALUE;
		Arrays.fill(shoot, inf);
		int ansDays = 0;
		int ansShots = 0;
		for (int day = 1; day <= n; day++) {
			for (int mask = 1; mask < shoot.length; mask++) {
				if (shoot[mask] < day) {
					continue;
				}
				int shots = 0;
				for (int i = 0; i < n; i++) {
					boolean willShoot = true;
					for (int thought = 1; thought < shoot.length; thought++) {
						if (((thought >> i) & 1) != 0) {
							continue;
						}
						if ((thought & check[i]) != (mask & check[i])) {
							continue;
						}
						if (shoot[thought] >= day) {
							willShoot = false;
						}
					}
					if (willShoot) {
						shots++;
					}
				}
				if (shots > 0) {
					shoot[mask] = day;
					ansDays += day;
					ansShots += shots;
				}
			}
		}
		out.println(ansDays + " " + ansShots);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = J_small_slow.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new J_small_slow().run();
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
