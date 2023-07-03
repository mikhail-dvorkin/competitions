package marathons.topcoder.happyGrid;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class Research extends HappyGrid {
	void deprecated() {
		prettyPrintDesired();
		assignDestinations();
		movePebblesByAssignment();
	}

	int[][] pebble;

	public void assignDestinations() {
		pebble = new int[n][n];
		for (int c = 0; c < colors; c++) {
			assignDestinations(c);
		}
	}

	void assignDestinations(int c) {
		int count = colorCount[c];
		ArrayList<Integer> from = new ArrayList<>();
		ArrayList<Integer> to = new ArrayList<>();
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if (field[y][x] == c) from.add(encode(y, x));
			if (desired[y][x] == c) to.add(encode(y, x));
		}
		int[][] e = new int[count][count];
		for (int i = 0; i < count; i++) {
			int y1 = decodeY(from.get(i)), x1 = decodeX(from.get(i));
			for (int j = 0; j < count; j++) {
				int y2 = decodeY(to.get(j)), x2 = decodeX(to.get(j));
				e[i][j] = dist[y1][x1][y2][x2];
			}
		}
		int[] assignment = hungarian(e);
		for (int i = 0; i < count; i++) {
			int y1 = decodeY(from.get(i)), x1 = decodeX(from.get(i));
			pebble[y1][x1] = to.get(assignment[i]);
		}
	}

	void movePebblesByAssignment() {
		while (true) {
			boolean improved = false;
			for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) for (int d = 0; d < 2; d++) {
				if (wall[y][x]) continue;
				int yy = y + DY[d], xx = x + DX[d];
				if (yy < 0 || yy >= n || xx < 0 || xx >= n || wall[yy][xx]) continue;
				int p = pebble[y][x];
				int pp = pebble[yy][xx];
				int before = dist[y][x][decodeY(p)][decodeX(p)] + dist[yy][xx][decodeY(pp)][decodeX(pp)];
				int after = dist[y][x][decodeY(pp)][decodeX(pp)] + dist[yy][xx][decodeY(p)][decodeX(p)];
				assert Math.abs(before - after) <= 2;
				if (after < before) {
					ans.add(move(y, x, yy, xx));
					int t = pebble[y][x]; pebble[y][x] = pebble[yy][xx]; pebble[yy][xx] = t;
					improved = true;
				}
			}
			if (!improved) break;
		}
	}

	private void improveColoringForFailed(TreeSet<Integer>[] nei) {
		int m = nei.length;
		while (failedColoring > 0) {
			boolean improve = false;
			for (int v = 0; v < m; v++) {
				for (int u = 0; u < m; u++) {
					if (colorAssignment[u] == colorAssignment[v]) continue;
					int badV = 0, newBadV = 0;
					for (int w : nei[v]) {
						if (colorAssignment[w] == colorAssignment[v]) badV++;
						if (colorAssignment[w] == colorAssignment[u]) newBadV++;
					}
					int badU = 0, newBadU = 0;
					for (int w : nei[u]) {
						if (colorAssignment[w] == colorAssignment[u]) badU++;
						if (colorAssignment[w] == colorAssignment[v]) newBadU++;
					}
					boolean better = newBadV + newBadU < badV + badU;
					boolean notWorse = (newBadV + newBadU == badV + badU);
					if (better) {
						int t = colorAssignment[v]; colorAssignment[v] = colorAssignment[u]; colorAssignment[u] = t;
						improve = true;
					}
				}
			}
			failedColoring = 0;
			for (int v = 0; v < m; v++) {
				int badV = 0;
				for (int w : nei[v]) {
					if (colorAssignment[w] == colorAssignment[v]) badV++;
				}
				if (badV > 0) failedColoring++;
			}
			if (!improve) break;
		}
	}

	void improveColoring(TreeSet<Integer>[] nei) {
		int m = nei.length;
		int[][] quality = new int[m][colors + 1];
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if (wall[y][x] || groupId[y][x] < 0) continue;
			quality[groupId[y][x]][field[y][x]] += 3;
			for (int d = 0; d < 4; d++) {
				int yy = y + DY[d], xx = x + DX[d];
				if (yy < 0 || yy >= n || xx < 0 || xx >= n || wall[yy][xx]) continue;
				quality[groupId[y][x]][field[yy][xx]] += 1;
			}
		}
		while (true) {
			boolean improve = false;
			for (int v = 0; v < m; v++) {
				for (int u = 0; u < m; u++) {
					if (colorAssignment[u] == colorAssignment[v]) continue;
					int badV = 0, newBadV = 0;
					for (int w : nei[v]) {
						if (colorAssignment[w] == colorAssignment[v]) badV++;
						if (colorAssignment[w] == colorAssignment[u]) newBadV++;
					}
					int badU = 0, newBadU = 0;
					for (int w : nei[u]) {
						if (colorAssignment[w] == colorAssignment[u]) badU++;
						if (colorAssignment[w] == colorAssignment[v]) newBadU++;
					}
					int old = (badV + badU) * 1024 - quality[v][colorAssignment[v]] - quality[u][colorAssignment[u]];
					int nov = (newBadV + newBadU) * 1024 - quality[v][colorAssignment[u]] - quality[u][colorAssignment[v]];
					if (nov < old) {
						int t = colorAssignment[v];
						colorAssignment[v] = colorAssignment[u];
						colorAssignment[u] = t;
						improve = true;
					}
				}
			}
			if (!improve) break;
		}
	}

	void pw(Throwable ignored) {
		try {
			PrintWriter pw = new PrintWriter("deb" + System.currentTimeMillis() + ".txt");
			ignored.printStackTrace(pw);
			pw.close();
		} catch (FileNotFoundException ignored1) {
		}
	}
}
/*
== MyScore ==

488.7
521.44
606.99
627.66
627.83
628.5
658.86
630.4 :) 590.72
664.19
665.75
669.48
703.77, 912 ms, 918 ms → 821 ms
707.51
704.25, 638 ms, 3544 ms
707.81  ===== 81.75311257525055
707.36
714.49
715.15

hardcore
717.95
726.77
757.86 ===== 85.05323320374153 | first10: 759.38→765.9
762.55 ===== 87.60024637678795
770.91 ===== 88.25068395013892

c=2
hei=4
784.82, 1373 ms
hei=5
804.97, 8344 ms
hei=4
764.23, 319 ms | 512→763.17
hei=5
785.49

c=2 n=19 w=0 k=2..8
hei=4
847.07, 1975ms
849.64, 5773 ms
851.94, 1597 ms

c=3
613.13
684.58, 770 ms
 */

/*
== Compared to server ==

Test Case #1:
Score = 311.0
Run Time = 943ms

Test Case #2:
Score = 6709.0
Run Time = 5992ms

Test Case #3:
Score = 5728.0
Run Time = 2808ms

Test Case #4:
Score = 2850.0
Run Time = 3713ms

Test Case #5:
Score = 4863.0
Run Time = 3737ms

Test Case #6:
Score = 1208.0
Run Time = 3632ms

Test Case #7:
Score = 3854.0
Run Time = 2769ms

Test Case #8:
Score = 1041.0
Run Time = 2221ms

Test Case #9:
Score = 2566.0
Run Time = 2395ms

Test Case #10:
Score = 738.0
Run Time = 1746ms

Seed = 1,	Time = 773,	Score = 311.0,	MyScore = 779.45,
Seed = 2,	Time = 3815,	Score = 6709.0,	MyScore = 794.15,
Seed = 3,	Time = 1030,	Score = 5728.0,	MyScore = 817.0,
Seed = 4,	Time = 1447,	Score = 2850.0,	MyScore = 606.64,
Seed = 5,	Time = 1330,	Score = 4863.0,	MyScore = 745.29,
Seed = 6,	Time = 1797,	Score = 1208.0,	MyScore = 642.55,
Seed = 7,	Time = 1104,	Score = 3854.0,	MyScore = 834.2,
Seed = 8,	Time = 912,	Score = 1041.0,	MyScore = 543.89,
Seed = 9,	Time = 856,	Score = 2566.0,	MyScore = 774.76,
Seed = 10,	Time = 343,	Score = 738.0,	MyScore = 569.44,
 */

