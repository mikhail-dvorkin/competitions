package topcoder.tco2021.round3;
import java.util.Arrays;

public class TellBagsApart {
	public int solve(int[] a) {
		int same1 = a[0] + a[3];
		int diff1 = a[1] + a[2];
		int same2 = a[4] + a[7];
		int diff2 = a[5] + a[6];
		if (same1 + diff1 > same2 + diff2) {
			return solve(same1, diff1);
		} else {
			return 3 - solve(same2, diff2);
		}
	}

	private int solve(int same, int diff) {
		double t = (3.0 / 7 + 19.0 / 39) / 2;
		double f = same * 1.0 / (same + diff);
		return  (f > t ? 2 : 1);
	}

	public String whichBagIsSmaller(int[] records) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < records.length; i += 8) {
			sb.append(solve(Arrays.copyOfRange(records, i, i + 8)));
		}
		return sb.toString();
	}
}
