package codeforces.croc2013.round2;
import java.util.*;

public class A {
	private static Scanner in;
	
	String[] res = new String[]{"First", "Second", "Draw"};

	public void run() {
		int n = in.nextInt();
		String s = in.next();
		String t = in.next();
		int[][] a = new int[2][2];
		for (int i = 0; i < 2 * n; i++) {
			int x = s.charAt(i) - '0';
			int y = t.charAt(i) - '0';
			a[x][y]++;
		}
		for (int i = 0; i < n; i++) {
			int x, y;
			if (a[1][1] > 0) {
				a[1][1]--;
				x = 1;
			} else if (a[1][0] > 0) {
				a[1][0]--;
				x = 1;
			} else if (a[0][1] > 0) {
				a[0][1]--;
				x = 0;
			} else {
				a[0][0]--;
				x = 0;
			}
			if (a[1][1] > 0) {
				a[1][1]--;
				y = 1;
			} else if (a[0][1] > 0) {
				a[0][1]--;
				y = 1;
			} else if (a[1][0] > 0) {
				a[1][0]--;
				y = 0;
			} else {
				a[0][0]--;
				y = 0;
			}
			if (x > y) {
				System.out.println(res[0]);
				return;
			}
			if (x < y) {
				System.out.println(res[1]);
				return;
			}
		}
		System.out.println(res[2]);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
