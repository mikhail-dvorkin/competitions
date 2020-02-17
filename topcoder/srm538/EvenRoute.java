package topcoder.srm538;
public class EvenRoute {
	public String isItPossible(int[] x, int[] y, int wantedParity) {
		int n = x.length;
		for (int i = 0; i < n; i++) {
			if (Math.abs(x[i] + y[i]) % 2 == wantedParity) {
				return "CAN";
			}
		}
		return "CANNOT";
	}

}
