package codeforces.yandex2011.qual2;
import java.util.*;

public class D {
	private static Scanner in;
	
	int inf;
	int n;
	int[] a;
	int[][] d, how1, how2;

	public void run() {
		n = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		d = new int[n][n];
		how1 = new int[n][n];
		how2 = new int[n][n];
		inf = Integer.MAX_VALUE / 2;
		for (int i = 0; i < n; i++) {
			Arrays.fill(d[i], -1);
		}
		int ans = solve(0, 0);
		System.out.println(ans);
		print(0, 0);
	}

	private int solve(int x, int p) {
		if (d[x][p] >= 0) {
			return d[x][p];
		}
		int[] v = new int[3];
		int len = 0;
		v[len++] = p;
		if (x + 1 < n) {
			v[len++] = x + 1;
		}
		if (x + 2 < n) {
			v[len++] = x + 2;
		}
		if (len == 1) {
			d[x][p] = a[v[0]];
			how1[x][p] = v[0];
			return d[x][p];
		}
		if (len == 2) {
			d[x][p] = Math.max(a[v[0]], a[v[1]]);
			how1[x][p] = v[0];
			how2[x][p] = v[1];
			return d[x][p];
		}
		int best = inf;
		int sumv = p + 2 * x + 3; //v[0] + v[1] + v[2];
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				int cur = solve(x + 2, sumv - v[i] - v[j]) + Math.max(a[v[i]], a[v[j]]);
				if (cur < best) {
					best = cur;
					how1[x][p] = v[i];
					how2[x][p] = v[j];
				}
			}
		}
		d[x][p] = best;
		return d[x][p];
	}

	private void print(int x, int p) {
		if (x + 2 >= n) {
			if (x + 1 >= n) {
				System.out.println(how1[x][p] + 1);
				return;
			}
			System.out.println((how1[x][p] + 1) + " " + (how2[x][p] + 1));
			return;
		}
		System.out.println((how1[x][p] + 1) + " " + (how2[x][p] + 1));
		print(x + 2, p + 2 * x + 3 - how1[x][p] - how2[x][p]);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new D().run();
		in.close();
	}
}
