package codeforces.yandex2011.qual2;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int max = 201;
		int[] count = new int[max];
		int[][] together = new int[max][max];
		int[] a = new int[max];
		int k = -1;
		for (int s = 0; s < n * (n - 1) / 2; s++) {
			k = in.nextInt();
			for (int i = 0; i < k; i++) {
				a[i] = in.nextInt();
				count[a[i]]++;
				for (int j = 0; j < i; j++) {
					together[a[i]][a[j]]++;
					together[a[j]][a[i]]++;
				}
			}
		}
		if (n == 2) {
			System.out.println(1 + " " + a[0]);
			System.out.print(k - 1);
			for (int i = 1; i < k; i++) {
				System.out.print(" " + a[i]);
			}
			System.out.println();
			return;
		}
		for (int i = 0; i < max; i++) {
			if (count[i] == 0) {
				continue;
			}
			Set<Integer> ans = new HashSet<Integer>();
			ans.add(i);
			for (int j = i + 1; j < max; j++) {
				if (together[i][j] == count[i]) {
					count[j] = 0;
					ans.add(j);
				}
			}
			count[i] = 0;
			System.out.print(ans.size());
			for (int x : ans) {
				System.out.print(" " + x);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}
