package topcoder.srm362;
import java.util.*;

public class PartialSeries {
	int[] av;

	public int[] getFirst(int[] series, int[] available) {
		av = available;
		int n = series.length;
		int MAX = 1 << av.length;
		int[][][] r = new int[n + 1][121][MAX];
		int[][][] how = new int[n + 1][121][MAX];
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j < 121; j++) {
				Arrays.fill(r[i][j], 1000);
			}
		}
		Arrays.sort(av);
		if (n == 1) {
			if (series[0] == -1)
				return new int[]{av[0]};
			return series;
		}
		int m0 = 0;
		for (int a = 0; a <= 10; a++) {
			if (series[n - 2] == -1) {
				boolean found = false;
				for (int i = 0; i < av.length; i++) {
					if (av[i] == a) {
						m0 = 1 << i;
						found = true;
						break;
					}
				}
				if (!found)
					continue;
			} else if (series[n - 2] != a) {
				continue;
			}
			for (int b = 0; b <= 10; b++) {
				int m1 = 0;
				if (series[n - 1] == -1) {
					boolean found = false;
					for (int i = 0; i < av.length; i++) {
						if (av[i] == b) {
							m1 = 1 << i;
							if (m1 == m0)
								continue;
							found = true;
							break;
						}
					}
					if (!found)
						continue;
				} else if (series[n - 1] != b) {
					continue;
				}
				int state = 11 * a + b;
				r[n - 2][state][m0 | m1] = 0;
			}
		}
		for (int i = n - 3; i >= 0; i--) {
			for (int state = 0; state < 121; state++) {
				int a = state / 11;
				int b = state % 11;
				for (int m = 0; m < MAX; m++) {
					if (r[i + 1][state][m] < 1000) {
						if (series[i] >= 0) {
							int z = series[i];
							int loc = local(z, a, b);
							int cur = r[i + 1][state][m] + loc;
							if (cur < r[i][11 * z + a][m] || (cur == r[i][11 * z + a][m] && b < how[i][11 * z + a][m])) {
								r[i][11 * z + a][m] = cur;
								how[i][11 * z + a][m] = b;
							}
						} else {
							for (int j = 0; j < av.length; j++) {
								if ((m & (1 << j)) != 0)
									continue;
								int z = av[j];
								int loc = local(z, a, b);
								int cur = r[i + 1][state][m] + loc;
								int mm = m | (1 << j);
								if (cur < r[i][11 * z + a][mm] || (cur == r[i][11 * z + a][mm] && b < how[i][11 * z + a][mm])) {
									r[i][11 * z + a][mm] = cur;
									how[i][11 * z + a][mm] = b;
								}
							}
						}
					}
				}
			}
		}
		int ans = 1000;
		for (int j = 0; j < 121; j++) {
			for (int m = 0; m < MAX; m++) {
				ans = Math.min(ans, r[0][j][m]);
			}
		}
//		System.out.println(r[0][11 * 1 + 2][28]);
//		System.out.println(r[1][11 * 2 + 3][30]);
//		System.out.println(r[2][11 * 3 + 4][28]);
//		System.out.println(r[3][11 * 4 + 5][24]);
		int[] answer = new int[n];
		for (int a = 0; a <= 10; a++) {
			for (int b = 0; b <= 10; b++) {
				if (series[0] >= 0 && series[0] != a)
					continue;
				if (series[1] >= 0 && series[1] != b)
					continue;
				m0 = 0;
				if (series[0] < 0) {
					boolean found = false;
					for (int i = 0; i < av.length; i++) {
						if (av[i] == a) {
							m0 = 1 << i;
							found = true;
						}
					}
					if (!found)
						continue;
				}
				if (series[1] < 0) {
					boolean found = false;
					for (int i = 0; i < av.length; i++) {
						if (av[i] == b) {
							if (1 << i == m0)
								continue;
							found = true;
						}
					}
					if (!found)
						continue;
				}
				for (int mr = 0; mr < MAX; mr++) {
					if (r[0][11 * a + b][mr] == ans) {
						answer[0] = a;
						answer[1] = b;
						int forbid = m0;
						iloop:
						for (int i = 2; i < n; i++) {
							int aa = answer[i - 1];
							for (int bb = 0; bb <= 10; bb++) {
								if (series[i] >= 0 && series[i] != bb)
									continue;
								if (series[i] < 0) {
									boolean found = false;
									for (int j = 0; j < av.length; j++) {
										if (av[j] == bb) {
											if ((forbid & (1 << j)) != 0)
												continue;
											found = true;
										}
									}
									if (!found)
										continue;
								}
								for (int mm = 0; mm < MAX; mm++) {
									if (((mm & forbid) == 0) && r[i - 1][11 * aa + bb][mm] == ans) {
										answer[i] = bb;
										continue iloop;
									}
								}
							}
						}
						return answer;
					}
				}
			}
		}
		return new int[]{ans};
	}

	private int local(int z, int a, int b) {
		return (a > Math.max(z, b) || a < Math.min(z, b)) ? 1 : 0;
	}

	public static void main(String[] args) {
		new PartialSeries().getFirst(new int[]{-1, -1, -1, -1, -1}, new int[]{1, 2, 3, 4, 5});
//		new PartialSeries().getFirst(new int[]{-1,-1,-1,-1,-1}, new int[]{1, 2, 3, 4, 5});
	}
}
