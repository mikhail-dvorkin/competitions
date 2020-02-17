package topcoder.tco2007.round1a;
import java.util.*;

public class Destruction {
	public double minError(int numTests, int numDestroyed) {
		double[][] a = new double[numTests + 1][numDestroyed + 1];
		Arrays.fill(a[0], 1);
		for (int i = 1; i <= numTests; i++) {
			a[i][0] = 1;
			a[1][numDestroyed] = 1;
			for (int j = 1; j <= numDestroyed; j++) {
				a[i][j] = a[i - 1][j - 1] * a[i - 1][j] / (a[i - 1][j] + a[i - 1][j - 1]);
			}
			a[1][numDestroyed] = 1;
		}
		return a[numTests][numDestroyed] * 50;
	}

	public static void main(String[] args) {
		System.out.println(new Destruction().minError(1, 1));
		System.out.println(new Destruction().minError(3, 1));
		System.out.println(new Destruction().minError(20, 3));
	}
}

