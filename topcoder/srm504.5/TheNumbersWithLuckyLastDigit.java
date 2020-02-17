package topcoder;

public class TheNumbersWithLuckyLastDigit {
	public int find(int n) {
		for (int a = 1; a <= 100; a++) {
			for (int i = 0; i <= a; i++) {
				int m = 4 * i + 7 * (a - i);
				if ((n % 10 == m % 10) && (m <= n)) {
					return a;
				}
			}
		}
		return -1;
	}
}
