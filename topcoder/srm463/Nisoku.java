package topcoder;
import java.util.Arrays;

public class Nisoku {
	public double theMax(double[] a) {
		Arrays.sort(a);
		int n = a.length;
		double ans = 0;
		for (int k = 0; 2 * k <= n; k++) {
			double r = 1;
			for (int i = 0; i < k; i++) {
				r *= a[i] + a[2 * k - 1 - i];
			}
			for (int i = 2 * k; i < n; i++) {
				r *= a[i];
			}
			ans = Math.max(ans, r);
		}
		return ans;
	}
	
	public static void main(String[] args) {
		double a = new Nisoku().theMax(new double[]{1.56276,3.62065,2.84331,1.82359,2.35135,2.44959,3.18171,2.08659,4.94325,4.62340,8.75337,4.45313,2.15899,1.99331,4.23224,1.79837,4.29835,9.81168,3.27587,2.16238,1.78701,1.54335,1.51930,7.20526,7.66486,1.91206,6.74281,7.15244,3.95024,4.36556,3.04464,3.26337,2.64665,7.47867,1.51522,9.91150,7.99658,1.78729,8.27356,3.22942,4.30267,2.75784,3.25437,2.04638,3.42158,1.81414,1.85491,2.49631,1.81189,7.91752});
		System.out.println(a);
	}
}
