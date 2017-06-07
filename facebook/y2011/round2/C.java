package facebook.y2011.round2;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	static final long MODULO = 1000000007;

	private void solve() {
		int n = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt() + 1;
		int c = in.nextInt();
		int d = in.nextInt() + 1;
		long ans = solve(n, a, d) - solve(n, b, d) - solve(n, a, c) + solve(n, b, c);
		ans %= MODULO;
		ans += MODULO;
		ans %= MODULO;
		out.println(ans);
	}

	private long solve(long n, int a, int d) {
		if (a >= d) {
			return 0;
		}
		boolean[] isPrime = new boolean[d + 1];
		int[] p = new int[d + 1];
		for (int i = 2; i <= d; i++)
			isPrime[i] = true;
		for (int i = 2; i <= d; i++) {
			if (!isPrime[i])
				continue;
			p[i] = i;
			int j = 2 * i;
			while (j <= d) {
				isPrime[j] = false;
				p[j] = i;
				j += i;
			}
		}
		long[] s = new long[d + 1];
		long ans = solve1(n, a, d);
		ans %= MODULO;
		s[1] = 1;
		p[1] = 1;
		for (int i = 2; i <= d; i++) {
//			System.out.println(i);
			if (p[i] > p[i / p[i]]) {
				s[i] = -s[i / p[i]];
				ans += s[i] * solve1(n, (a + i - 1) / i, (d + i - 1) / i);
				ans %= MODULO;
			}
		}
		ans += MODULO;
		ans %= MODULO;
		return ans;
	}

	private long solve1(long n, long a, long d) {
		if (a >= d) {
			return 0;
		}
		return pow(d - a, n);
	}

	private long pow(long v, long n) {
		if (n == 1) {
			return v;
		}
		if (n % 2 == 1) {
			return v * pow(v, n - 1) % MODULO;
		}
		long u = pow(v, n / 2);
		return u * u % MODULO;
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		long tests = in.nextInt();
		in.nextLine();
		for (long t = 1; t <= tests; t++) {
			new C().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
