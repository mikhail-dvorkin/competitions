package yandex.y2018.round2;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int mana = in.nextInt();
		int damageSingle = in.nextInt();
		int costSingle = in.nextInt();
		int damageAll = in.nextInt();
		int costAll = in.nextInt();
		int[] h = new int[n];
		for (int i = 0; i < n; i++) {
			h[i] = in.nextInt();
		}
		Arrays.sort(h);
		int answer = 0;
		for (int alls = 0; costAll * alls <= mana; alls++) {
			int kills = 0;
			int singles = (mana - alls * costAll) / costSingle;
			for (int i = 0; i < n; i++) {
				int health = h[i] - damageAll * alls;
				if (health > 0) {
					int need = (health + damageSingle - 1) / damageSingle;
					if (need <= singles) {
						singles -= need;
						health = 0;
					}
				}
				if (health <= 0) {
					kills++;
				}
			}
			answer = Math.max(answer, kills);
		}
		out.println(answer);
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
