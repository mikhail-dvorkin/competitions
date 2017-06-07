package codeforces.beta_round80;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		long ans = 0;
		for (int i = 0; i < n; i++) {
			ans += (i + 1L) * a[i] - i;
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
