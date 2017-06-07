package rcc.y2011.round1;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = in.nextInt();
			System.out.println((n - 1) + n * (n - 1) / 4.0);
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
