package codeforces.round207;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class B {
	private static BufferedReader in;

	public void run() throws IOException {
		long n = Long.parseLong(new StringTokenizer(in.readLine()).nextToken());
		String x = in.readLine();
		String y = in.readLine();
		int g = BigInteger.valueOf(x.length()).gcd(BigInteger.valueOf(y.length())).intValue();
		int[][] count = new int[g][26];
		for (int i = 0; i < y.length(); i++) {
			count[i % g][y.charAt(i) - 'a']++;
		}
		int t = y.length() / g;
		long ans = 0;
		for (int i = 0; i < x.length(); i++) {
			ans += n * (t - count[i % g][x.charAt(i) - 'a']) / t;
		}
		System.out.println(ans);
	}
	
	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new B().run();
		in.close();
	}
}
