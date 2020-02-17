package topcoder.srm345;
public class Pathfinding {
	public int getDirections(int x, int y) {
		if (x >= 0 && y >= 0)
			return go(x, y);
		if (x < 0 && y < 0)
			return 4 + go(-1 - x, -1 - y);
		if (x >= 0) {
			int t = x; x = y; y = t;
		}
		if (y >= 1)
			return 1 + go(-x, y - 1);
		x = -x;
		if (x % 2 == 1)
			return x + 2;
		return x + 4;
	}

	int go(int x, int y) {
		if (x % 2 == 0 || y % 2 == 0)
			return x + y;
		return x + y + 2;
	}
}
