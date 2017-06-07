package codeforces.round200;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		long a = in.nextLong();
		long b = in.nextLong();
		long ans = 0;
		while (a > 0 && b > 0) {
			if (a >= b) {
				ans += a / b;
				a %= b;
				continue;
			}
			ans += b / a;
			b %= a;
		}
		System.out.println(ans);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
