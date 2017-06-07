package rcc.y2011.round1;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int max = 0;
		int min = 0;
		for (int i = 0; i < n; i++) {
			int a = in.nextInt() - 1 - i;
			max = Math.max(a, max);
			min = Math.min(a, min);
		}
		min *= -1;
		System.out.println(max + min + Math.min(max, min));
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}
