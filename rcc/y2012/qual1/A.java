package rcc.y2012.qual1;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		for (;;) {
			int[] a = new int[12];
			int sum = 0;
			for (int i = 0; i < 12; i++) {
				a[i] = in.nextInt();
				sum += a[i];
			}
			if (sum == 0) {
				return;
			}
			Arrays.sort(a);
			if (a[0] == a[3] && a[4] == a[7] && a[8] == a[11]) {
				System.out.println("yes");
			} else {
				System.out.println("no");
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
