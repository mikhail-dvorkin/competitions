package rcc.y2011.round1;
import java.util.*;

public class C {
	private static Scanner in;

	public void run() {
		int[] a = new int[2];
		a[1] = in.nextInt();
		a[0] = in.nextInt();
		int n = Math.min(a[1], a[0]);
		a[1] -= n;
		a[0] -= n;
		n *= 2;
		int t = 0;
		StringBuilder sb = new StringBuilder();
		if (a[0] > 0) {
			n++;
			a[0]--;
		}
		if (a[1] > 0) {
			sb.append("W");
			n++;
			a[1]--;
			t = 1;
		}
		for (int i = 0; i < n; i++) {
			if (i > 0) {
				sb.append("E");
			}
			t ^= 1;
			if (a[t] > 0) {
				a[t]--;
				sb.append("NS");
			}
			if (a[t] > 0) {
				a[t]--;
				sb.append("SN");
			}
		}
		System.out.println(sb.length());
		System.out.println(sb);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}
