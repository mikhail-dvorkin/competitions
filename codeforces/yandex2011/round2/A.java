package codeforces.yandex2011.round2;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		long left = in.nextLong();
		long right = in.nextLong();
		for (long ten = 10; ten <= 10 * right; ten *= 10) {
			long best = ten / 2;
			best = Math.max(best, left);
			best = Math.min(best, right);
			check(best);
		}
		System.out.println(ans);
	}

	long ans = -1;

	private void check(long v) {
		long u = reverse(v);
		ans = Math.max(ans, u * v);
	}

	private long reverse(long v) {
		if (v == 0) {
			return 0;
		}
		return reverse(v / 10) * 10 + (9 - v % 10);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
