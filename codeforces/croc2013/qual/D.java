package codeforces.croc2013.qual;
import java.util.*;

public class D {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		for (int t = 1; k-- > 0; t *= 2) {
			for (int i = n - 1; i >= 0; i--) {
				System.out.print(n - Math.min(Math.max(i - t, 0), t) + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new D().run();
		in.close();
	}
}
