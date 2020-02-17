package topcoder.tco2009.round2;
public class PlaneFractal {
	public String[] getPattern(int s, int n, int k, int r1, int r2, int c1, int c2) {
		int z = (n - k) / 2;
		String[] answer = new String[r2 - r1 + 1];
		long a = 1;
		for (int i = 0; i < s; i++) {
			a = a * n;
		}
		for (int i = r1; i <= r2; i++) {
			answer[i - r1] = "";
			for (int j = c1; j <= c2; j++) {
				long x = i;
				long y = j;
				long b = a;
				int col = 0;
				for (int t = 0; t < s; t++) {
					long bb = b / n;
					long i1 = x / bb;
					long j1 = y / bb;
					if (i1 >= z && i1 < z + k && j1 >= z && j1 < z + k) {
						col = 1;
						break;
					}
					x = x % bb;
					y = y % bb;
					b = bb;
				}
				answer[i - r1] = answer[i - r1] + col;
			}
		}
		return answer;
	}
}
