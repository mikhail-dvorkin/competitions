package facebook.y2011.round2;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int p = in.nextInt();
		int m = in.nextInt();
		int[] a = read(p);
		int[] b = read(p);
		if (countZeros(a) < countZeros(b)) {
			int[] c = a;
			a = b;
			b = c;
		}
		long ans = a[0] * b[0];
		int sumb = 0;
		for (int i = 1; i < p; i++) {
			ans += a[0] * b[i] + a[i] * b[0];
			sumb += b[i];
		}
		BigInteger pp = BigInteger.valueOf(p);
		boolean forward = 2 * m < p;
		for (int i = 1; i < p; i++) {
			if (a[i] == 0) {
				continue;
			}
			int ii = BigInteger.valueOf(i).modInverse(pp).intValue();
			int j = 0;
			int sum = 0;
			if (forward) {
				for (int k = 1; k < m; k++) {
					j += ii;
					if (j >= p) {
						j -= p;
					}
					sum += b[j];
				}
				ans += sum * (long) a[i];
			} else {
				for (int k = p - 1; k >= m; k--) {
					j -= ii;
					if (j < 0) {
						j += p;
					}
					sum += b[j];
				}
				ans += (sumb - sum) * (long) a[i];
			}
		}
//		out.println(Arrays.toString(a));
//		out.println(Arrays.toString(b));
		out.println(ans);
	}

	private int countZeros(int[] a) {
		int res = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				res++;
			}
		}
		return res;
	}

	private int[] read(int p) {
		int[] a = new int[p];
		int n = in.nextInt();
		int x = in.nextInt();
		int y = in.nextInt();
		long a3 = in.nextInt();
		long a4 = in.nextInt();
		long a5 = in.nextInt();
		a[x]++;
		a[y]++;
		for (int i = 2; i < n; i++) {
			int z = (int) ((x * a3 + y * a4 + a5) % p);
			a[z]++;
			x = y;
			y = z;
		}
		return a;
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		for (int t = 1; t <= tests; t++) {
			new A().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
