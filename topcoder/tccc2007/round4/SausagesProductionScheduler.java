package topcoder;
import java.util.*;

public class SausagesProductionScheduler {
	public int maxCount(String[] lowerPercentage, String[] upperPercentage, int[] limits) {
		int n = lowerPercentage.length;
		int[] la = new int[n];
		int[] ha = new int[n];
		int[][] a = new int[n + 1][6000];
		for (int i = 0; i < n; i++) {
			Scanner sc = new Scanner(lowerPercentage[i]);
			int lla = sc.nextInt();
			int llb = sc.nextInt();
			sc.close();
			sc = new Scanner(upperPercentage[i]);
			int hha = sc.nextInt();
			int hhb = sc.nextInt();
			la[i] = Math.max(lla, 100 - hhb);
			ha[i] = Math.min(hha, 100 - llb);
			sc.close();
		}
		for (int i = 0; i <= n; i++)
			Arrays.fill(a[i], -1);
		a[0][0] = 0;
		for (int s = 0; s < n; s++) {
			for (int sa = 0; sa <= 100 * s; sa++) {
				if (a[s][sa] == -1)
					continue;
				int cur = a[s][sa];
				int sb = 100 * cur - sa;
				a[s + 1][sa] = Math.max(a[s + 1][sa], cur);
				for (int v = la[s]; v <= ha[s]; v++) {
					if (sa + v <= limits[0] && sb + 100 - v <= limits[1]) {
						a[s + 1][sa + v] = Math.max(a[s + 1][sa + v], cur + 1);
					}
				}
			}
		}
		Arrays.sort(a[n]);
		return Math.max(a[n][5999], 0);
	}
}
