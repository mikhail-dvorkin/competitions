package topcoder.srm645;
public class JanuszTheBusinessman {
	public int makeGuestsReturn(int[] arrivals, int[] departures) {
		int n = arrivals.length;
		boolean[] happy = new boolean[n];
		int ans = 0;
		for (;;) {
			int a = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				if (happy[i]) {
					continue;
				}
				a = Math.min(a, departures[i]);
			}
			if (a == Integer.MAX_VALUE) {
				return ans;
			}
			int b = -1;
			for (int i = 0; i < n; i++) {
				if (arrivals[i] <= a) {
					b = Math.max(b, departures[i]);
				}
			}
			for (int i = 0; i < n; i++) {
				if (arrivals[i] <= b) {
					happy[i] = true;
				}
			}
			ans++;
		}
	}
}
