package codeforces.croc2013.trial;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		System.out.println("ACCCBCABCA".charAt(in.nextInt() - 1));
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}
