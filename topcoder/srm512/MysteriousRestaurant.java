package topcoder.srm512;
import java.util.*;

public class MysteriousRestaurant {
	public int maxDays(String[] prices, int budget) {
		int days = prices.length;
		int dishes = prices[0].length();
		int ans = 0;
		int[][] p = new int[days][dishes];
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < dishes; j++) {
				char c = prices[i].charAt(j);
				int pr;
				if (c >= '0' && c <= '9') {
					pr = c - '0';
				} else if (c >= 'A' && c <= 'Z') {
					pr = c - 'A' + 10;
				} else {
					pr = c - 'a' + 36;
				}
				p[i][j] = pr;
			}
		}
		for (int d = 1; d <= days; d++) {
			int[][] a = new int[7][dishes];
			for (int i = 0; i < d; i++) {
				for (int j = 0; j < dishes; j++) {
					a[i % 7][j] += p[i][j];
				}
			}
			int pr = 0;
			for (int i = 0; i < 7; i++) {
				Arrays.sort(a[i]);
				pr += a[i][0];
			}
			if (pr <= budget) {
				ans = d;
			} else {
				break;
			}
		}
		return ans;
	}

}
