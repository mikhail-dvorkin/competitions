package topcoder.srm364;
import java.util.*;

public class PowerPlants {
	public int minCost(String[] connectionCost, String plantList, int numPlants) {
		int n = connectionCost.length;
		int init = 0;
		for (int i = 0; i < n; i++) {
			if (plantList.charAt(i) == 'Y')
				init |= 1 << i;
		}
		int MAX = 1 << n;
		int[] cost = new int[MAX];
		Arrays.fill(cost, 1000000);
		cost[init] = 0;
		int ans = 1000000;
		for (int mask = init; mask < MAX; mask++) {
			if (cost[mask] == 1000000)
				continue;
			if (ones(mask) >= numPlants)
				ans = Math.min(ans, cost[mask]);
			for (int i = 0; i < n; i++) {
				if ((mask & (1 << i)) != 0) {
					for (int j = 0; j < n; j++) {
						if ((mask & (1 << j)) == 0) {
							int m2 = mask | (1 << j);
							char c = connectionCost[i].charAt(j);
							int v;
							if (c >= '0' && c <= '9')
								v = c - '0';
							else
								v = c - 'A' + 10;
							cost[m2] = Math.min(cost[m2], cost[mask] + v);
						}
					}
				}
			}
		}
		return ans;
	}

	private int ones(int mask) {
		if (mask == 0)
			return 0;
		return 1 + ones(mask & (mask - 1));
	}
}
