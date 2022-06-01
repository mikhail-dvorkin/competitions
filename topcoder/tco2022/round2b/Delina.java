package topcoder.tco2022.round2b;

public class Delina {
	public double getProbability(int threshold, int m) {
		int aLot = 1_000_000;
		double p = 1;
		for (int i = m; i <= aLot; i++) {
			double pLose = Math.pow((threshold - 1.0) / 20, i + 1);
			p *= 1 - pLose;
		}
		return p;
	}
}
