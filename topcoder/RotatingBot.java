package topcoder;
public class RotatingBot {
	public int minArea(int[] moves) {
		boolean[][] f = new boolean[5000][5000];
		int x0 = f.length / 2;
		int y0 = f[0].length / 2;
		int x = x0;
		int y = y0;
		int x1 = x;
		int x2 = x;
		int y1 = y;
		int y2 = y;
		int dx = 1;
		int dy = 0;
		f[x][y] = true;
		for (int m : moves) {
			for (int i = 0; i < m; i++) {
				x += dx;
				y += dy;
				if (f[x][y]) {
					return -1;
				}
				f[x][y] = true;
				x1 = Math.min(x, x1);
				x2 = Math.max(x, x2);
				y1 = Math.min(y, y1);
				y2 = Math.max(y, y2);
			}
			int ddx = -dy;
			int ddy = dx;
			dx = ddx;
			dy = ddy;
		}
		x = x0;
		y = y0;
		dx = 1;
		dy = 0;
		f = new boolean[f.length][f[0].length];
		f[x][y] = true;
		for (int j = 0; j < moves.length; j++) {
			for (int i = 0; i < moves[j]; i++) {
				x += dx;
				y += dy;
				f[x][y] = true;
			}
			if (j == moves.length - 1) {
				break;
			}
			int xx = x + dx;
			int yy = y + dy;
			int ddx = -dy;
			int ddy = dx;
			dx = ddx;
			dy = ddy;
			if (xx < x1 || xx > x2 || yy < y1 || yy > y2) {
				continue;
			}
			if (f[xx][yy]) {
				continue;
			}
			return -1;
		}
		return (x2 - x1 + 1) * (y2 - y1 + 1);
	}

}
