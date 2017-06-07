package rcc.y2012.qual1;
import java.util.*;

public class C {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		double[] log = new double[n + 1];
		for (int i = 1; i <= n; i++) {
			log[i] = Math.log(i);
		}
		double[] b = new double[n + 1];
		Arrays.fill(b, -1);
		b[0] = 0;
		int[] bh = new int[n + 1];
		for (int i = 0; i < n; i++) {
			boolean up = false;
			boolean down = false;
			for (int j = i + 1; j < i + 20; j++) {
				double cur = b[i] + log[j - i];
				if (cur > b[j]) {
					b[j] = cur;
					bh[j] = i;
				}
				if (j == n) {
					break;
				}
				up |= (a[j] > a[j - 1]);
				down |= (a[j] < a[j - 1]);
				if (up && down) {
					break;
				}
			}
		}
		ArrayList<Integer> ans = new ArrayList<Integer>();
		for (int i = n; i > 0;) {
			ans.add(i);
			i = bh[i];
		}
		Collections.sort(ans);
//		while (i < n) {
//			boolean up = false;
//			boolean down = false;
//			int ii = n;
//			for (int j = i + 1; j < n; j++) {
//				up |= (a[j] > a[j - 1]);
//				down |= (a[j] < a[j - 1]);
//				if (up && down) {
//					ii = j;
//					break;
//				}
//			}
//			ans.add(ii);
//			i = ii;
//		}
		System.out.println(ans.size());
		for (int x : ans) {
			System.out.print(x + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}
