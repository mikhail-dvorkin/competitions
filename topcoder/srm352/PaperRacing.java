package topcoder.srm352;
import java.util.*;

public class PaperRacing {
	String[] f;
	int hei, wid;

	public int minMoves(String[] track, int vRow, int vCol) {
		f = track;
		hei = track.length;
		wid = track[0].length();
		short[][][][] w = new short[hei][wid][110][110];
		ArrayList<Integer> q1 = new ArrayList<Integer>();
		ArrayList<Integer> q2 = new ArrayList<Integer>();
		ArrayList<Integer> q3 = new ArrayList<Integer>();
		ArrayList<Integer> q4 = new ArrayList<Integer>();
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				for (int k = 0; k < 110; k++) {
					Arrays.fill(w[i][j][k], (short) -1);
				}
				if (track[i].charAt(j) == 'S') {
					w[i][j][vRow + 55][vCol + 55] = 0;
					q1.add(i);
					q2.add(j);
					q3.add(vRow + 55);
					q4.add(vCol + 55);
				}
			}
		}
		int lo = 0;
		while (lo < q1.size()) {
			int x = q1.get(lo);
			int y = q2.get(lo);
			int vx = q3.get(lo) - 55;
			int vy = q4.get(lo) - 55;
			int len = w[x][y][vx + 55][vy + 55];
//			System.out.println(x + " " + y + " " + len);
			lo++;
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					int xx = x + vx + dx;
					int yy = y + vy + dy;
					char cha = getChar(xx, yy);
					if (cha == '.') {
						if (w[xx][yy][vx + dx + 55][vy + dy + 55] != -1)
							continue;
						w[xx][yy][vx + dx + 55][vy + dy + 55] = -2;
					}
					int a = yy - y;
					int b = x - xx;
					int c = 2 * (x * yy - y * xx);
					double odist = 1e100;
					double fdist = 1e200;
					for (int x1 = Math.min(x, xx); x1 <= Math.max(x, xx); x1++) {
						for (int y1 = Math.min(y, yy); y1 <= Math.max(y, yy); y1++) {
							char ch = getChar(x1, y1);
							if (ch != 'F' && ch != 'X')
								continue;
							boolean zer = false, pos = false, neg = false;
							for (int xt = 0; xt < 2; xt++) {
								for (int yt = 0; yt < 2; yt++) {
									int c1 = a * (2 * x1 + 2 * xt - 1) + b * (2 * y1 + 2 * yt - 1) - c;
									if (c1 == 0)
										zer = true;
									else if (c1 > 0)
										pos = true;
									else
										neg = true;
								}
							}
							if (ch == 'F' && zer) {
								pos = neg = true;
							}
							if (pos && neg) {
								double dist = Math.hypot(x - x1, y - y1);
								if (ch == 'F')
									fdist = dist;
								else
									odist = Math.min(odist, dist);
							}
						}
					}
					if (fdist < odist + 1e-8)
						return len + 1;
					if (odist == 1e100 && getChar(xx, yy) == '.') {
						q1.add(xx);
						q2.add(yy);
						q3.add(vx + dx + 55);
						q4.add(vy + dy + 55);
						w[xx][yy][vx + dx + 55][vy + dy + 55] = (short) (len + 1);
					}
				}
			}
		}
		return -1;
	}

	private char getChar(int xx, int yy) {
		if (xx < 0 || yy < 0 || xx >= hei || yy >= wid)
			return '#';
		char c = f[xx].charAt(yy);
		if (c == 'S')
			c = '.';
		return c;
	}

	public static void main(String[] args) {
		int a = new PaperRacing().minMoves(
				new String[]
				           {"F.................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.", "..................................................", ".XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "..................................................", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXS"}, 0, 0
		);
		System.out.println(a);
	}
}

