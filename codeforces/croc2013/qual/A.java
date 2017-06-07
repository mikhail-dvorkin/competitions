package codeforces.croc2013.qual;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		TreeMap<Integer, Integer> count = new TreeMap<Integer, Integer>();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			int key = in.nextInt();
			if (key == 0) {
				continue;
			}
			int value = count.containsKey(key) ? count.get(key) : 0;
			value++;
			count.put(key, value);
			if (value == 3) {
				System.out.println(-1);
				return;
			}
			if (value == 2) {
				ans++;
			}
		}
		System.out.println(ans);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}