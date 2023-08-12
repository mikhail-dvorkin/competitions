import java.util.*;

public class SecondLargestMultiple {
	long n;
	int b;
	int maxLength;
	HashMap[] map;

	void search(int prefix, int length, int used) {
//		System.out.println(prefix);
		{
			HashMap<Integer, Long> thisMap = map[used];
			int remainder = (int) (prefix % n);
			long prev = (long) thisMap.getOrDefault(remainder, 0L);
			int max = (int) (prev << 32 >> 32);
			int max2 = (int) (prev >> 32);
			if (prefix > max) {
				max2 = max; max = prefix;
			} else if (prefix > max2) {
				max2 = prefix;
			}
			long neww = (max2 << 32) | max;
			thisMap.put(remainder, neww);
		}
		if (length == maxLength) {
			return;
		}
		int pb = prefix * b;
		int start = length == 0 ? 1 : 0;
		for (int i = start; i < b; i++) {
			if (((used >> i) & 1) != 0) {
				continue;
			}
			search(pb + i, length + 1, used | (1 << i));
		}
	}

	public long find(long n, int b) {
		this.n = n;
		this.b = b;
		int left = b / 2;
		int right = b - left;
		maxLength = right;
		map = new HashMap[1 << b];
		for (int i = 0; i < map.length; i++) {
			map[i] = new HashMap();
		}
		search(0, 0, 0);
		for (int leftUsed = 0; leftUsed < (1 << b); leftUsed++) {
			if (Integer.bitCount(leftUsed) != left) {
				continue;
			}
			int unused = ((1 << b) - 1) ^ leftUsed;
			int rightUsed = unused;
			while (true) {
				HashMap<Integer, Long> thisMap = map[leftUsed];
				for (Map.Entry<Integer, Long> entry : thisMap.entrySet()) {
					int remainder = entry.getKey();
					long value = entry.getValue();
					int max = (int) (value << 32 >> 32);
					int max2 = (int) (value >> 32);
				}
				if (rightUsed == 0) {
					break;
				}
				rightUsed = (rightUsed - 1) & unused;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
//		System.out.println("9876543201\t" + new SecondLargestMultiple().find(1, 10));
		System.out.println("9876012345\t" + new SecondLargestMultiple().find(12345, 10));
//		System.out.println("-1\t" + new SecondLargestMultiple().find(12345, 3));
//		System.out.println("0\t" + new SecondLargestMultiple().find(2, 2));
//		System.out.println("-1\t" + new SecondLargestMultiple().find(17, 4));
//		System.out.println("800025\t" + new SecondLargestMultiple().find(25, 7));
//		System.out.println("0\t" + new SecondLargestMultiple().find(282458553905L, 11));
		System.out.println("8842413355380\t" + new SecondLargestMultiple().find(239L, 12));
	}
}
