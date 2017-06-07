package facebook.y2014.round1;
import java.io.*;
import java.util.*;

public class D {
	private static String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		int gcd = in.nextInt();
		a = new int[n];
		int ansUp = 0;
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			if (a[i] > 0) {
				int aiNew = (a[i] - 1) / gcd + 1;
				ansUp += aiNew * gcd - a[i];
				a[i] = aiNew;
			}
		}
		Arrays.sort(a);
		int ansZero = 0;
		if (a[n - 1] <= 1) {
			for (int i = 1; i < n; i++) {
				ansZero += 1 - a[i];
			}
		} else {
			for (int i = 0; i < n; i++) {
				if (a[i] == 0) {
					ansZero++;
				}
				if (a[i] > 1) {
					a = Arrays.copyOfRange(a, i, n);
					n = a.length;
					break;
				}
			}
			int[] aOrig = a.clone();
			int[][] e = new int[n][primes.length];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < primes.length; j++) {
					int p = primes[j];
					int q = p;
					while (q < a[i]) {
						q *= p;
					}
					e[i][j] = q - a[i];
				}
			}
			int[] aa = hungarian(e);
			int[] b = new int[n];
			for (int i = 0; i < n; i++) {
				b[i] = a[i] + e[i][aa[i]];
				ans += e[i][aa[i]];
			}
			System.out.println(Arrays.toString(aOrig));
			System.out.println(Arrays.toString(b) + " " + ans);
			search(0, n, 50, 0);
		}
		out.println((ans + ansZero) * gcd + ansUp);
	}
	
	int[] a;
	int ans;
	
	private void search(int x, int first, int deep, int sum) {
//		System.out.println(Arrays.toString(a) + x);
		if (sum >= ans) {
			return;
		}
		int n = a.length;
		if (x == n) {
			System.out.println("BETTER: " + Arrays.toString(a) + " " + sum);
			ans = sum;
			return;
		}
		if (x >= first) {
//			System.out.println(sum);
			int aOrig = a[x];
			int ax = aOrig;
			for (;; ax++) {
				boolean ok = true;
				for (int i = 0; i < x; i++) {
					if (gcd(ax, a[i]) > 1) {
						ok = false;
						break;
					}
				}
				if (ok) {
					break;
				}
			}
			a[x] = ax;
			search(x + 1, first, deep, sum + ax - aOrig);
			a[x] = aOrig;
			return;
		}
		boolean tried = false;
		int aOrig = a[x];
		if (x > 0) {
			a[x] = Math.max(a[x], a[x - 1] + 1);
		}
		for (;;) {
			boolean ok = true;
			for (int i = 0; i < x; i++) {
				if (gcd(a[x], a[i]) > 1) {
					ok = false;
					break;
				}
			}
			if (ok) {
				tried = true;
				search(x + 1, first, deep, sum + a[x] - aOrig);
			}
			a[x]++;
			if (a[x] >= aOrig + deep + x && tried) {
				break;
			}
		}
		a[x] = aOrig;
	}

	public static boolean[] isPrimeArray(int n) {
		boolean[] isPrime = new boolean[n + 1];
		for (int i = 2; i <= n; i++)
			isPrime[i] = true;
		for (int i = 2, j; (j = i * i) <= n; i++) {
			if (!isPrime[i])
				continue;
			do {
				isPrime[j] = false;
				j += i;
			} while (j <= n);
		}
		return isPrime;
	}
	
	public static int[] primesUpTo(int n) {
		boolean[] isPrime = isPrimeArray(n);
		int m = 0;
		for (int i = 2; i <= n; i++) {
			if (isPrime[i])
				m++;
		}
		int[] primes = new int[m];
		m = 0;
		for (int i = 2; i <= n; i++) {
			if (isPrime[i])
				primes[m++] = i;
		}
		return primes;
	}
	
	int[] primes = primesUpTo(150);

	public static int[] hungarian(int[][] e) {
		int[] ans = new int[e.length];
		Arrays.fill(ans, -1);
		if (e.length == 0 || e[0].length == 0) {
			return ans;
		}
		int infty = Integer.MAX_VALUE / 3;
		boolean swap = false;
		if (e.length > e[0].length) {
			swap = true;
			int[][] f = new int[e[0].length][e.length];
			for (int i = 0; i < e.length; i++) {
				for (int j = 0; j < e[0].length; j++) {
					f[j][i] = e[i][j];
				}
			}
			e = f;
		}
		int n1 = e.length;
		int n2 = e[0].length;
		int[] u = new int[n1 + 1];
		int[] v = new int[n2 + 1];
		int[] p = new int[n2 + 1];
		int[] way = new int[n2 + 1];
		for (int i = 1; i <= n1; i++) {
			p[0] = i;
			int j0 = 0;
			int[] minv = new int[n2 + 1]; 
			Arrays.fill(minv, infty);
			boolean[] used = new boolean[n2 + 1];
			do {
				used[j0] = true;
				int i0 = p[j0], j1 = 0;
				int delta = infty;
				for (int j = 1; j <= n2; j++) {
					if (!used[j]) {
						int cur = e[i0 - 1][j - 1] - u[i0] - v[j];
						if (cur < minv[j]) {
							minv[j] = cur;
							way[j] = j0;
						}
						if (minv[j] < delta) {
							delta = minv[j];
							j1 = j;
						}
					}
				}
				for (int j = 0; j <= n2; j++) {
					if (used[j]) {
						u[p[j]] += delta;
						v[j] -= delta;
					} else {
						minv[j] -= delta;
					}
				}
				j0 = j1;
			} while (p[j0] != 0);
			do {
				int j1 = way[j0];
				p[j0] = p[j1];
				j0 = j1;
			} while (j0 > 0);
		}
		for (int j = 1; j <= n2; j++) {
			if (p[j] > 0) {
				// if (e[p[j] - 1][j - 1] >= infty) no matching of size n1;
				// sum += e[p[j] - 1][j - 1];
				if (swap) {
					ans[j - 1] = p[j] - 1;
				} else {
					ans[p[j] - 1] = j - 1;
				}
			}
		}
		return ans;
	}
	
	public static int gcd(int a, int b) {
		while (a > 0) {
			int t = b % a;
			b = a;
			a = t;
		}
		return b;
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
			out.print("Case #" + t + ": ");
			new D().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
