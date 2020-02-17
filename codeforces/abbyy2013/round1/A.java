package codeforces.abbyy2013.round1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class A {
	private static BufferedReader in;

	public void run() throws IOException {
		String s = in.readLine().trim();
		Set<Character> letters = new TreeSet<>();
		int tens = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				continue;
			}
			if (c == '?') {
				tens++;
				continue;
			}
			letters.add(c);
		}
		long ans = 1;
		for (int i = 0; i < letters.size(); i++) {
			ans *= 10 - i;
		}
		if (tens > 0) {
			ans *= 10;
			tens--;
		}
		char c = s.charAt(0);
		if (c == '?' || (c >= 'A' && c <= 'J')) {
			ans = ans * 9 / 10;
		}
		System.out.print(ans);
		for (int i = 0; i < tens; i++) {
			System.out.print("0");
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new A().run();
		in.close();
	}
}
