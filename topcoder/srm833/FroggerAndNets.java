package topcoder.srm833;

public class FroggerAndNets {
	public int jump(String stones, int C, int minW, int seed) {
		int[] L = new int[C];
		int[] R = new int[C];
		int state = seed;
		int N     = stones.length();
		int maxW  = N-1;
		for (int i = 0; i < C; i++) {
			state = (int) ((state * 1103515245L + 12345) % (1 << 31));
			int w = minW + (state % (maxW - minW + 1));
			state = (int) ((state * 1103515245L + 12345) % (1 << 31));
			L[i] = state % (N - w);
			R[i] = L[i] + w;
		}
		int[] leftStone = new int[N];
		int stone = -1;
		for (int i = 0; i < N; i++) {
			if (stones.charAt(i) == 'O') {
				stone = i;
			}
			leftStone[i] = stone;
		}
		int[] rightStone = new int[N];
		stone = N;
		for (int i = N - 1; i >= 0; i--) {
			if (stones.charAt(i) == 'O') {
				stone = i;
			}
			rightStone[i] = stone;
		}
		int[] pos = new int[] {-1, -1};
		int[] score = new int[] {0, 0};
		for (int i = 0; i < C; i++) {
			int p1 = rightStone[L[i]];
			if (p1 > R[i]) {
				return -1;
			}
			int p2 = leftStone[R[i]];
			if (i > 0) {
				int s1 = Math.max(score[0] + Math.abs(pos[0] - p1), score[1] + Math.abs(pos[1] - p1));
				int s2 = Math.max(score[0] + Math.abs(pos[0] - p2), score[1] + Math.abs(pos[1] - p2));
				score[0] = s1;
				score[1] = s2;
			}
			pos[0] = p1;
			pos[1] = p2;
		}
		return Math.max(score[0], score[1]);
	}
}
