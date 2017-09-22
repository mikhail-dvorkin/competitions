package topcoder;
public class BattleDice {
	@SuppressWarnings("unused")
	int[] die3(int[] die1, int[] die2, int range) {
		int n = die1.length;
		int nn = n * n;
		boolean[][][][] p = new boolean[n + 1][range + 1][n * n + 1][n * n + 1];
		p[0][range][0][0] = true;
		for (int i = 0; i < n; i++) {
			for (int f = 1; f <= range; f++) {
				for (int a = 0; a <= nn; a++) {
					for (int b = 0; b <= nn; b++) {
						if (!p[i][f][a][b])
							continue;
						for (int e = 1; e <= f; e++) {
							
						}
					}
				}
			}
		}
		return new int[0];
	}
}
