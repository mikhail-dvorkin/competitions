package topcoder.srm748;
import java.util.*;

public class UnreliableRover {
	public static final String DIR = "ENWS?";

	public long getArea(String direction, int[] minSteps, int[] maxSteps) {
		long x = 1, y = 1;
		ArrayList<Integer> growths = new ArrayList<>();
		for (int i = 0; i < direction.length(); i++) {
			int dir = DIR.indexOf(direction.charAt(i));
			int s = maxSteps[i] - minSteps[i];
			if (dir == 4) {
				growths.add(s);
			} else if (dir % 2 == 0) {
				x += s;
			} else {
				y += s;
			}
		}
		int g = growths.size();
		int[] sums = new int[1 << g];
		for (int m = 0; m < sums.length; m++) {
			for (int i = 0; i < g; i++) {
				if (((m >> i) & 1) == 1) {
					sums[m] += growths.get(i);
				}
			}
		}
		Arrays.sort(sums);
		long totalSum = sums[sums.length - 1];
		long triangle = 0;
		int prevSum = 0;
		for (int sum : sums) {
			triangle += (sum - prevSum) * (totalSum - sum);
			prevSum = sum;
		}
		return 4 * triangle + x * y + 2 * (x + y) * totalSum;
	}
}
