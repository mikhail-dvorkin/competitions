package topcoder.srm416;
import java.util.*;

public class CustomDice {
	int mod = 1000000007;

	public int countDice(int m) {
		m *= 6;
		int[] a = new int[m + 1];
		int[] b = new int[m + 1];
		for (int i = 1; i <= m; i++)
			a[i] = 1;
		for (int d = 2; d <= 6; d++) {
			Arrays.fill(b, 0);
			for (int i = 0; i <= m; i++) {
				if (i >= d)
					b[i] = (a[i - d] + b[i - d]) % mod;
			}
			System.arraycopy(b, 0, a, 0, m + 1);
		}
		int ans = 0;
		for (int i = 0; i <= m; i++)
			ans = (ans + a[i]) % mod;
		return (int) ((30L * ans) % mod);
	}

	public static void main(String[] args) {
		System.out.println(new CustomDice().countDice(4));
	}
}
