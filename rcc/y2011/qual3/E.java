package rcc.y2011.qual3;
import java.util.*;

public class E {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		int k = in.nextInt();
		int[] b = new int[k];
		int[] where = new int[n];
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for (int i = 0; i < n; i++) {
			int j = - 2 - Arrays.binarySearch(b, a[i]);
			if (j < 0) {
				System.out.println("NO");
				return;
			}
			b[j] = a[i];
			where[i] = j;
			map.put(a[i], j);
		}
		System.out.println("YES");
		for (int i = 0; i < n; i++) {
			System.out.println("I(" + (where[i] + 1) + ")");
		}
		for (int i : map.values()) {
			System.out.println("R(" + (i + 1) + ")");
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new E().run();
		in.close();
	}
}
