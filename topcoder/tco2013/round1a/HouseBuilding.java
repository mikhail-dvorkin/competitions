package topcoder;
public class HouseBuilding {
	public int getMinimum(String[] area) {
		int best = Integer.MAX_VALUE;
		for (int v = 0; v <= 9; v++) {
			int cur = 0;
			for (int i = 0; i < area.length; i++) {
				for (int j = 0; j < area[0].length(); j++) {
					int h = area[i].charAt(j) - '0';
					if (h < v) {
						cur += v - h;
					}
					if (h > v + 1) {
						cur += h - v - 1;
					}
				}
			}
			best = Math.min(best, cur);
		}
		return best;
	}

}
