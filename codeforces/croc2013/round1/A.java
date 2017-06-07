package codeforces.croc2013.round1;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int[] time = new int[n];
		int[] c = new int[n];
		for (int i = 0; i < n; i++) {
			time[i] = in.nextInt();
			c[i] = in.nextInt();
		}
		int t = 0;
		int maxQ = 0;
		for (int i = 0; i < n; i++) {
			int q = Math.max(0, t - 1 - time[i]);
			maxQ = Math.max(maxQ, q + c[i]);
			t = Math.max(t, time[i] + 1);
			t += c[i];
		}
		System.out.println((t - 1) + " " + maxQ);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}
