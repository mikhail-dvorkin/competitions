package topcoder;
public class TheSwapsDivOne {
	public double find(String[] sequence, int k) {
		StringBuilder sb = new StringBuilder();
		for (String s : sequence) {
			sb.append(s);
		}
		String s = sb.toString();
		int n = s.length();
		
		double a = 1;
		for (int i = 0; i < k; i++) {
			double b = 1 - a;
			double aa = a * (1 - 2.0 / n) + b * (2.0 / (n * (n - 1)));
			a = aa;
		}
		
		double[] coef = new double[n];
		double sum = 0;
		for (int i = 0; i < n; i++) {
			coef[i] = (i + 1) * (n - i) * 2.0 / (n * (n + 1));
			sum += coef[i];
		}
		
		double ans = 0;
		for (int i = 0; i < n; i++) {
			int v = s.charAt(i) - '0';
			ans += v * (a * coef[i] + (1 - a) * (sum - coef[i]) / (n - 1)); 
		}
		return ans;
	}

}
