package codeforces.croc2013.qual;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		String s = in.nextLine().trim();
		while (!s.isEmpty()) {
			if (s.charAt(0) == ' ') {
				s = s.substring(1);
				continue;
			}
			if (s.charAt(0) == '"') {
				int i = s.indexOf("\"", 1);
				System.out.println("<" + s.substring(1, i) + ">");
				s = s.substring(i + 1);
				continue;
			}
			int i = s.indexOf(" ");
			if (i < 0) {
				System.out.println("<" + s + ">");
				break;
			}
			System.out.println("<" + s.substring(0, i) + ">");
			s = s.substring(i + 1);
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}