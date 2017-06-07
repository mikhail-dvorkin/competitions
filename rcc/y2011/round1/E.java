package rcc.y2011.round1;
import java.util.*;

public class E {
	private static Scanner in;

	public void run() {
		long n = in.nextLong();
		int s = in.nextInt();
		for (int m = (int) (n % 10); m <= s; m += 10) {
			if (can((9 * n + m) / 10, m, s - m)) {
				System.out.println(m);
				return;
			}
		}
		System.out.println("Impossible");
	}
	
	private boolean can(long n, int m, int s) {
		if (s < 0) {
			return false;
		}
		int k = (int) (n % 10);
		n -= k;
		m -= k;
		if (m < 0) {
			return false;
		}
		if (m == 0) {
			return (n == 0) && (s == 0);
		}
		if (can(n / 10, m, s - m)) {
			return true;
		}
		while (n >= 10 && m >= 10) {
			n -= 10;
			m -= 10;
			if (can(n / 10, m, s - m)) {
				return true;
			}
		}		
		return false;
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new E().run();
		in.close();
	}
}
