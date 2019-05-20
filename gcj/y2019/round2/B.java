package gcj.y2019.round2;
import java.io.*;
import java.util.*;

public class B {
	static final int D = 100;
	static final int V = 20;
	static final int ME = D - 1;

	int shitted = 9;
	int shitmoves = shitted * 7;
	int unshitted = V - shitted;

	public void run() throws IOException {
		int[] a = new int[unshitted];
		boolean[] putMe = new boolean[unshitted];
		int best = 0;
		for (int d = 0;; d++) {
			in.readLine();
			if (d == D - 1) {
				forge(best, ME);
				return;
			}
			if (d < shitmoves) {
				int p = (d < shitted) ? ME : rnd.nextInt(ME);
				forge(unshitted + d % shitted, p);
				continue;
			}
			if (d < shitmoves + unshitted) {
				a[d - shitmoves] = askAmount(d - shitmoves);
				continue;
			}
			if (d == shitmoves + unshitted) {
				for (int i = 1; i < unshitted; i++) {
					if (a[i] <= a[best]) {
						best = i;
					}
				}
			}
			int x = -1;
			for (int i = 0; i < unshitted; i++) {
				if (i == best) {
					continue;
				}
				if (x == -1 || a[i] <= a[x]) {
					x = i;
				}
			}
			int p = putMe[x] ? rnd.nextInt(ME) : ME;
			putMe[x] = true;
			a[x]++;
			forge(x, p);
		}
	}
	
	void forge(int v, int p) {
		System.out.println((v + 1) + " " + (p + 1));
		System.out.flush();
	}
	
	int askAmount(int v) throws IOException {
		forge(v, -1);
		String s = in.readLine();
		StringTokenizer st = new StringTokenizer(s);
		return Integer.parseInt(st.nextToken());
	}
	
	static BufferedReader in;
	static Random rnd = new Random(566);

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		int tests = Integer.parseInt(in.readLine());
		for (int test = 0; test < tests; test++) {
			new B().run();
		}
		in.close();
	}
}
