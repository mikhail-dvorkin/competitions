package topcoder.tco2020.round4;
import java.util.Arrays;
import java.util.Random;

public class BigJump {
	int n, m;
	boolean[] flip;

	int next(int x) {
		int xx = x % m;
		if ((x < m) ^ flip[xx]) return (2 * xx) % n; else return (2 * xx + n - 1) % n;
	}

	public int[] construct(int n) {
		this.n = n;
		m = n / 2;
		flip = new boolean[m];
		Random r = new Random(566);
		for (int i = 0; i < m; i++) {
			flip[i] = r.nextBoolean();
		}
		int bestLength = -1;
		while (true) {
			int j = r.nextInt(m);
			flip[j] = !flip[j];
			int[] ans = new int[n];
			boolean[] seen = new boolean[n];
			seen[0] = true;
			int length = n;
			for (int i = 1; i < n; i++) {
				ans[i] = next(ans[i - 1]);
				if (seen[ans[i]]) {
					length = i;
					break;
				}
				seen[ans[i]] = true;
			}
			if (length == n) return ans;
			if (length <= bestLength) {
				flip[j] = !flip[j];
			} else {
				bestLength = length;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(new BigJump().construct(12)));
	}
}
