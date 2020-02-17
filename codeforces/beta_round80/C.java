package codeforces.beta_round80;
import java.util.*;

public class C {
	private static Scanner in;

	public void run() {
		long n = in.nextLong();
		long x = in.nextLong();
		int p = in.nextInt();
		for (int t = 0; t < p; t++) {
			System.out.print(solve(n, x, in.nextLong() - 1) ? '.' : 'X');
		}
		System.out.println();
	}

	private boolean solve(long n, long x, long i) {
		long o = n - x;
		if (o <= x) {
			return (i % 2 == 0) && (i < 2 * o);
		}
		if (x == 0) {
			return true;
		}
		if (n % 2 == 1) {
			if (i == n - 1) {
				return false;
			}
			x--;
		}
		if (x == 0) {
			return true;
		}
		return (i < o - x) || (i % 2 == 0);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}
