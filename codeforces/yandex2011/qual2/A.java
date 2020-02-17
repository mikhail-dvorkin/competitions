package codeforces.yandex2011.qual2;
import java.util.*;

public class A {
	private static Scanner in;
	final String[] names = {"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"};

	public void run() {
		long n = in.nextLong();
		for (long m = 1;; m *= 2) {
			for (String name : names) {
				n -= m;
				if (n <= 0) {
					System.out.println(name);
					return;
				}
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
