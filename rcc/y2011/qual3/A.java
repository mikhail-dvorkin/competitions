package rcc.y2011.qual3;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int d = in.nextInt();
		int[] x = new int[n];
		int[] y = new int[n];
		int[] t = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			t[i] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int dist = (x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]);
				if ((t[i] != t[j]) && (dist == d * d)) {
					System.out.println("Yes");
					return;
				}
			}	
		}
		System.out.println("No");
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
