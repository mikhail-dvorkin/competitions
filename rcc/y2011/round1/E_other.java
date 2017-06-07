package rcc.y2011.round1;
import java.util.*;

public class E_other {
	private static Scanner in;

	public void run() {
		long n = in.nextLong();
		int s = in.nextInt();
		int m = ("" + n).length();
		int[] a = new int[m];
		long nn = n;
		for (int i = 0; i < m; i++) {
			a[i] = (int) (nn % 10);
			s -= a[i];
			nn /= 10;
		}
		if (s < 0 || s % 9 != 0) {
			System.out.println("Impossible");
			return;
		}
		s /= 9;
		while (s > 0) {
			int p = -1;
			for (int i = 0; i + 1 < m; i++) {
				if (a[i] < a[i + 1]) {
					p = i;
					break;
				}
			}
			if (p == -1) {
				break;
			}
			a[p] += 10;
			a[p + 1]--;
			s--;
		}
		for (int i = 0; i + 1 < m; i++) {
			if (a[i] < a[i + 1]) {
				System.out.println("Impossible");
				return;
			}
		}
		for (;;) {
			dfs(a, s, 1);
			if (s == 0) {
				break;
			}
			a[0] += 10;
			a[1]--;
			s--;
		}
		System.out.println("Impossible");
	}
	
	private boolean dfs(int[] aa, int s, int x) {
		int[] a = aa.clone();
		while (a[x] < 0) {
			a[x] += 10;
			a[x + 1]--;
			s--;
		}
		if (s < 0) {
			return false;
		}
		if (s == 0)
		for (;;) {
			dfs(a, s, x + 1);
			if (s == 0) {
				break;
			}
			a[x] += 10;
			a[x + 1]--;
			s--;
		}
		return false;
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new E_other().run();
		in.close();
	}
}
