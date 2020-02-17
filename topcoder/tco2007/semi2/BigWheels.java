package topcoder.tco2007.semi2;
import java.util.*;

public class BigWheels {
	public int[] enough(String[] wheels) {
		int n = wheels.length;
		double[][] p = new double[n + 1][110];
		int[] a = new int[50];
		TreeSet<Integer> ts = new TreeSet<Integer>();
		for (int i = n - 1; i >= 0; i--) {
			Scanner sc = new Scanner(wheels[i]);
			int m = 0;
			int max = -1;
			while (sc.hasNext()) {
				a[m] = sc.nextInt();
				max = Math.max(max, a[m]);
				m++;
			}
			sc.close();
			// needed to win
			for (int val = 0; val < 110; val++) {
				for (int j = 0; j < m; j++) {
					int f = a[j];
					double winf = 0;
					double beatf = 0;
					if (f >= val) {
						winf = 1 - p[i + 1][f + 1];
						beatf = 1;
					}
					double wing = 0;
					double beatg = 0;
					for (int k = 0; k < m; k++) {
						int g = a[k];
						if (f + g > max)
							continue;
						if (f + g < val)
							continue;
						wing += (1d / m) * (1 - p[i + 1][f + g + 1]);
						beatg += (1d / m) * (1);
					}
//					double win = winf;
					double beat = beatf;
					if (winf < 1e-10 || wing > winf) {
//						win = wing;
						beat = beatg;
					} else {
						if (i == 0)
							ts.add(f);
					}
					beat += (1 - beat) * p[i + 1][val];
					p[i][val] += beat / m;
				}
			}
		}
		int[] ans = new int[ts.size()];
		int l = 0;
		for (int x : ts) {
			ans[l++] = x;
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] a = new BigWheels().enough(new String[]
{ "4 4 4", "7 6" }
		);
		for (int x : a)
			System.out.println(x);
	}
}
