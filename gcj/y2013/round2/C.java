package gcj.y2013.round2;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	int n, s;
	int[] a, b, x, y;
	boolean[] mark;
	
	int dist() {
		int[] aa = new int[n];
		int[] bb = new int[n];
		for (int i = 0; i < n; i++) {
			aa[i] = 1;
			for (int j = 0; j < i; j++) {
				if (x[j] < x[i]) {
					aa[i] = Math.max(aa[i], aa[j] + 1);
				}
			}
		}
		for (int i = n - 1; i >= 0; i--) {
			bb[i] = 1;
			for (int j = i + 1; j < n; j++) {
				if (x[i] > x[j]) {
					bb[i] = Math.max(bb[i], bb[j] + 1);
				}
			}
		}
		int dist = 0;
		for (int i = 0; i < n; i++) {
			dist += Math.abs(a[i] - aa[i]) + Math.abs(b[i] - bb[i]);
		}
		return dist;
	}
	
	private void solve() {
		n = in.nextInt();
		a = new int[n];
		b = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			b[i] = in.nextInt();
		}
		x = new int[n];
		mark = new boolean[n];
		s = 0;
		for (int i = n - 1; i >= 0; i--) {
			dfs(i);
		}
		y = new int[n];
		for (int i = 0; i < n; i++) {
			y[x[i]] = i;
		}
		System.out.println(dist());
		for (;;) {
			boolean change = false;
			for (int i = n - 2; i >= 0; i--) {
				int j = y[i];
				int k = y[i + 1];
				if (k < j) {
					if (a[k] < a[j] && b[k] > b[j]) {
						int d1 = dist();
						int t = x[j]; x[j] = x[k]; x[k] = t;
						y[x[j]] = j;
						y[x[k]] = k;
						int d2 = dist();
						if (d2 > d1) {
							t = x[j]; x[j] = x[k]; x[k] = t;
							y[x[j]] = j;
							y[x[k]] = k;
							continue;
						}
						change = true;
					}
				}
			}
			if (!change) {
				break;
			}
		}
		System.out.println(dist());
		for (int i = 0; i < n; i++) {
			out.print(" " + (x[i] + 1));
		}
		out.println();
	}

	void dfs(int v) {
		if (mark[v]) {
			return;
		}
		mark[v] = true;
		for (int u = 0; u < n; u++) {
			if (u == v) {
				continue;
			}
			if (u < v && a[u] < a[v] && b[u] <= b[v]) {
				dfs(u);
				continue;
			}
			if (u > v && b[u] < b[v] && a[u] <= a[v]) {
				dfs(u);
				continue;
			}
		}
		x[v] = s++;
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
			out.print("Case #" + t + ":");
			new C().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
