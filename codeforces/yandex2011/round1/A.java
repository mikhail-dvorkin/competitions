package codeforces.yandex2011.round1;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		char[][] a = new char[4][n];
		if (n % 2 == 0) {
			a[1][0] = a[2][0] = 'y';
			a[1][n - 1] = a[2][n - 1] = 'z';
		} else {
			a[0][0] = a[1][0] = 'y';
			a[2][n - 1] = a[3][n - 1] = 'z';
		}
		char c = 'b';
		for (int i = 0; i < 4; i++) {
			boolean f = true;
			for (int j = 0; j < n; j++) {
				if (a[i][j] == 0) {
					a[i][j] = c;
					f ^= true;
					if (f) {
						c ^= 1;
					}
				}
				System.out.print(a[i][j]);
			}
			System.out.println();
			c += 2;
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
