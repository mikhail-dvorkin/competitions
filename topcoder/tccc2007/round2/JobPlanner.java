package topcoder;
public class JobPlanner {
	public int minimalCost(String[] can, int[] cost) {
		int m = can.length;
		int n = can[0].length();
		int[] does = new int[m];
		int[] worker = new int[n];
		iloop:
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (can[j].charAt(i) == 'Y') {
					does[j]++;
					worker[i] = j;
					continue iloop;
				}
			}
			return -1;
		}
		while (true) {
			boolean good = false;
			for (int wi = 0; wi < m; wi++) {
				for (int j = 0; j < n; j++) {
					int wj = worker[j];
					if (wi == wj || can[wi].charAt(j) == 'N')
						continue;
					int v1 = cost[wi] * (2 * does[wi] + 1);
					int v2 = cost[wj] * (2 * does[wj] - 1);
					if (v1 < v2) {
						worker[j] = wi;
						does[wi]++;
						does[wj]--;
						good = true;
					}
				}
			}
			if (!good)
				break;
		}
		int ans = 0;
		for (int i = 0; i < m; i++) {
			ans += cost[i] * does[i] * does[i];
		}
		return ans;
	}
	
	public static void main(String[] args) {
		int a = new JobPlanner().minimalCost(new String[]{"YYYNNNN", "YYYYYYY", "YYYYNNN"}, new int[]{2, 3, 2});
		System.out.println(a);
	}
}
