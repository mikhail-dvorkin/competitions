package codeforces.yandex2011.round2;
import java.util.*;

public class A_other {
	private static Scanner in;

	public void run() {
		long left = in.nextLong();
		long right = in.nextLong();
		run(left, right, 1, 0);
		System.out.println(ans);
	}

	private void run(long left, long right, long ten, long rest) {
		while ((left <= right) && (left % 10 != 0)) {
			check(left, ten, rest);
			left++;
		}
		while ((left <= right) && (right % 10 != 9)) {
			check(right, ten, rest);
			right--;
		}
		if (left > right) {
			return;
		}
		run(left / 10, right / 10, 10 * ten, 10 * rest + 4);
	}
	
	long ans = -1;

	private void check(long v, long ten, long rest) {
		v = v * ten + rest;
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
		new A_other().run();
		in.close();
	}
}
