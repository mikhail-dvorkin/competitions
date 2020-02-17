package topcoder.tccc2007.round2;
public class PowerOfInteger {
	public int greatestPower(String left, String right) {
		long lo = Long.parseLong(left);
		long hi = Long.parseLong(right);
		int ans = 1;
		for (long i = 2; i <= 1000000; i++) {
			long j = i;
			int k = 1;
			while (true) {
				j *= i;
				if (j > hi)
					break;
				k++;
				if (j >= lo)
					ans = Math.max(ans, k);
			}
		}
		return ans;
	}
}
