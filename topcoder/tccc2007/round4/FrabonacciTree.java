package topcoder.tccc2007.round4;
public class FrabonacciTree {
	long[] size;

	public String shortestPath(int n, int startIndex, int finishIndex) {
		size = new long[n + 5];
		size[0] = size[1] = 1;
		for (int i = 2; i < n; i++)
			size[i] = size[i - 1] + size[i - 2] + 1;
		return answer(n, 1, startIndex, finishIndex);
	}

	private String answer(int n, long first, int a, int b) {
		if (a == first && b == first)
			return "";
		if (a == first) {
			return way(n, first, b);
		}
		if (b == first) {
			return up(way(n, first, a));
		}
		boolean la = (a <= first + size[n - 2]);
		boolean lb = (b <= first + size[n - 2]);
		if (la && lb)
			return answer(n - 2, first + 1, a, b);
		if (!la && !lb)
			return answer(n - 1, first + size[n - 2] + 1, a, b);
		return up(way(n, first, a)) + way(n, first, b);
	}

	private String up(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			sb.append("U");
		}
		return sb.toString();
	}

	private String way(int n, long first, int a) {
		StringBuilder sb = new StringBuilder();
		while (first != a) {
			boolean la = (a <= first + size[n - 2]);
			if (la) {
				sb.append("L");
				first++;
				n -= 2;
			} else {
				sb.append("R");
				first += size[n - 2] + 1;
				n--;
			}
		}
		return sb.toString();
	}
}
