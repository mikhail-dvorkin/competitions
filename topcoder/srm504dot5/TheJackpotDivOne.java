package topcoder.srm504dot5;
import java.util.Arrays;


public class TheJackpotDivOne {
	long sum;
	int s1, n;

	void add(long v) {
		s1 += v % n;
		sum += v / n;
		if (s1 >= n) {
			s1 -= n;
			sum++;
		}
	}

	long avg() {
		return sum + 1;
	}

	public long[] find(long[] money, long jackpot) {
		n = money.length;
		sum = 0;
		s1 = 0;
		for (long x : money) {
			add(x);
		}
		while (jackpot > 0) {
			Arrays.sort(money);
			if (money[n - 1] - money[0] <= 1) {
				long give = jackpot / n;
				for (int i = 0; i < n; i++) {
					money[i] += give;
				}
				add(give * n);
				jackpot -= give * n;
			}
			long give = Math.min(avg() - money[0], jackpot);
			money[0] += give;
			add(give);
			jackpot -= give;
		}
		Arrays.sort(money);
		return money;
	}
}
