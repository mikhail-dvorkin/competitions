package codeforces.croc2013.round1;
import java.util.*;

public class B {
	final String[] NAMES = new String[]{
			"bus topology",
			"ring topology",
			"star topology",
			"unknown topology"
	};

	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		if (m < n - 1 || m > n) {
			System.out.println(NAMES[3]);
			return;
		}
		int[] nei = new int[n];
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			nei[a]++;
			nei[b]++;
		}
		int not2 = 0;
		int not1 = 0;
		int center = -1;
		for (int i = 0; i < n; i++) {
			if (nei[i] != 2) {
				not2++;
			}
			if (nei[i] != 1) {
				not1++;
				center = i;
			}
		}
		if (m == n && not2 == 0) {
			System.out.println(NAMES[1]);
			return;
		}
		if (m == n - 1) {
			if (not2 == 2) {
				System.out.println(NAMES[0]);
				return;
			}
			if (not1 == 1 && nei[center] == m) {
				System.out.println(NAMES[2]);
				return;
			}
		}
		System.out.println(NAMES[3]);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}
