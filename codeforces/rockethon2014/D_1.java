package codeforces.rockethon2014;
import java.io.*;
import java.util.*;

public class D_1 {
	void run() {
		int ver = in.nextInt();
		int hor = in.nextInt();
		int[] verX = new int[ver];
		int[] verY = new int[ver];
		int[] verL = new int[ver];
		int[] horX = new int[hor];
		int[] horY = new int[hor];
		int[] horL = new int[hor];
		for (int i = 0; i < ver; i++) {
			verX[i] = in.nextInt();
			verY[i] = in.nextInt();
			verL[i] = in.nextInt();
		}
		for (int i = 0; i < hor; i++) {
			horX[i] = in.nextInt();
			horY[i] = in.nextInt();
			horL[i] = in.nextInt();
		}
		int ans = 0;
		for (int i = 0; i < ver; i++) {
			for (int j = 0; j < hor; j++) {
				int plus = Math.min(
						Math.min(verX[i] - horX[j], horX[j] + horL[j] - verX[i]),
						Math.min(horY[j] - verY[i], verY[i] + verL[i] - horY[j])
						);
				ans = Math.max(ans, plus);
			}
		}
		System.out.println(ans);
	}

	static boolean stdStreams = true;
	static String fileName = D_1.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new D_1().run();
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
