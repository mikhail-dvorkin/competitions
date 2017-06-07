package codeforces.croc2013.trial;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int a = in.nextInt();
		int b = in.nextInt();
		int c = in.nextInt();
		int d = in.nextInt();
		int e = in.nextInt();
		int f = in.nextInt();
		if (a == 0 && b == 0 && c != 0) {
			System.out.println(0);
			return;
		}
		if (d == 0 && e == 0 && f != 0) {
			System.out.println(0);
			return;
		}
		if (a == 0 && b == 0) {
			System.out.println(-1);
			return;
		}
		if (d == 0 && e == 0) {
			System.out.println(-1);
			return;
		}
		if (a * e != b * d) {
			System.out.println(1);
			return;
		}
		if (a * f == d * c && b * f == e * c) {
			System.out.println(-1);
			return;
		}
		System.out.println(0);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
