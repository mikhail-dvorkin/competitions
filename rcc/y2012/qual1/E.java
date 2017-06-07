package rcc.y2012.qual1;
import java.util.*;

public class E {
	private static Scanner in;

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

	public void run() {
		int n = in.nextInt();
		if (n == 1) {
			System.out.println("2 2 3");
			return;
		}
		if (n == 2) {
			System.out.println("2 2 5");
			return;
		}
//		int[] p = primesUpTo(15000);
//		int cnt = 0;
//		for (int a : p) {
//			for (int b : p) {
//				if (b < a) {
//					continue;
//				}
//				for (int c : p) {
//					if (c < b) {
//						continue;
//					}
//					if (c > a + b + 1) {
//						break;
//					}
//					int r = (a + b) % c;
//					if (r != 1 && r != c - 1) {
//						continue;
//					}
//					r = (a + c) % b;
//					if (r != 1 && r != b - 1) {
//						continue;
//					}
//					r = (b + c) % a;
//					if (r != 1 && r != a - 1) {
//						continue;
//					}
//					System.out.println((++cnt) + ": " + a + " " + b + " " + c);
//					if (a != b) throw new RuntimeException();
//					if (Math.abs(c - 2 * a) != 1) throw new RuntimeException();
//				}
//			}
//		}
		boolean[] p = isPrimeArray(10000000);
		int cnt = 2;
		for (int i = 2; i < p.length; i++) {
			if (!p[i]) {
				continue;
			}
			int j = (i / 2) | 1;
			if (p[j]) {
				cnt++;
				if (cnt == n) {
					System.out.println(j + " " + j + " " + i);
					return;
				}
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new E().run();
		in.close();
	}
}
