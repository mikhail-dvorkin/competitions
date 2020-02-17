package topcoder;
import java.util.Arrays;


public class RabbitNumbering {
	int modulo = 1000000007;
	
	public int theCount(int[] a) {
		Arrays.sort(a);
		long ans = 1;
		for (int i = 0; i < a.length; i++) {
			if (a[i] <= i) {
				return 0;
			}
			ans *= (a[i] - i);
			ans %= modulo;
		}
		return (int) ans;
	}
}
