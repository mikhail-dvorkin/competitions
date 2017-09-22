package topcoder;
public class ConcatenateNumber {
	public int getSmallest(int number, int k) {
		long rest = 0;
		int ten = ("" + number).length();
		long t = 1;
		for (int i = 0; i < ten; i++) {
			t *= 10;
			t %= k;
		}
		for (int i = 1; i < k + 566; i++) {
			rest = rest * t + number;
			rest %= k;
			if (rest == 0)
				return i;
		}
		return -1;
	}
}
