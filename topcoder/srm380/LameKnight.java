package topcoder;
public class LameKnight {
	public int maxCells(int hei, int wid) {
		if (wid >= 7) {
			if (hei >= 3)
				return 5 + wid - 7;
			if (hei == 2)
				return 4;
			return 1;
		}
		if (hei >= 3) {
			return Math.min(wid, 4);
		}
		if (hei == 2) {
			return (wid + 1) / 2;
		}
		return 1;
	}
}
