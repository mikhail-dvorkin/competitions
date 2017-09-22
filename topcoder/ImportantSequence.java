package topcoder;
public class ImportantSequence {
	public int getCount(int[] b, String operators) {
		int n = b.length;
		long min = 1;
		long inf = Long.MAX_VALUE / 3;
		long max = inf;
		int sign = 1;
		long shift = 0;
		for (int i = 0; i < n; i++) {
			int sign2;
			long shift2;
			if (operators.charAt(i) == '+') {
				shift2 = b[i] - shift;
				sign2 = -sign;
			} else {
				shift2 = shift - b[i];
				sign2 = sign;
			}
			shift = shift2;
			sign = sign2;
			if (sign == 1) {
				min = Math.max(min, 1 - shift);
			} else {
				max = Math.min(max, shift - 1);
			}
		}
		if (max == inf) {
			return -1;
		}
		if (min <= max) {
			return (int) (max - min + 1);
		}
		return 0;
	}

}
