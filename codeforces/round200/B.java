package codeforces.round200;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		String s = in.nextLine();
		int n = s.length();
		char[] c = new char[n];
		int m = 0;
		for (int i = 0; i < n; i++) {
			c[m] = s.charAt(i);
			m++;
			if (m >= 2 && c[m - 2] == c[m - 1]) {
				m -= 2;
			}
		}
		if (m == 0) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}
