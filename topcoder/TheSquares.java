package topcoder;
import java.util.*;

public class TheSquares {
	class Square implements Comparable<Square> {
		int x, y, len;
		String name;
		
		public Square(int x, int y, int len, String name) {
			this.x = x;
			this.y = y;
			this.len = len;
			this.name = name;
		}
		
		@Override
		public int compareTo(Square o) {
			if (len != o.len)
				return o.len - len;
			return name.compareTo(o.name);
		}

		public boolean contains(Square o) {
			return o.x >= x && o.y >= y && (o.x + o.len <= x + len) && (o.y + o.len <= y + len);
		}
	}
	
	public String[] findSequence(String[] X, String[] Y, String[] Lengths, String[] Names, int k) {
		int[] x = readArray(X);
		int[] y = readArray(Y);
		int[] len = readArray(Lengths);
		int n = x.length;
		String[] names = split(Names);
		Square[] sq = new Square[n];
		for (int i = 0; i < n; i++) {
			sq[i] = new Square(x[i], y[i], len[i], names[i]);
		}
		Arrays.sort(sq);
		int[] a = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			a[i] = 1;
			for (int j = i + 1; j < n; j++) {
				if (sq[i].contains(sq[j])) {
					a[i] = Math.max(a[i], 1 + a[j]);
				}
			}
		}
		Square current = null;
		String[] ans = new String[k];
		int mini = 0;
		for (int t = 0; t < k; t++) {
			Square best = null;
			int bi = -1;
			for (int i = mini; i < n; i++) {
				if (a[i] < k - t)
					continue;
				if (current != null && !current.contains(sq[i]))
					continue;
				if (best == null || sq[i].name.compareTo(best.name) < 0) {
					best = sq[i];
					bi = i;
				}
			}
			if (best == null)
				return new String[0];
			ans[t] = best.name;
			current = best;
			mini = bi + 1;
		}
		return ans;
	}

	private String[] split(String[] names) {
		StringBuilder sb = new StringBuilder();
		for (String xx : names)
			sb.append(xx);
		return sb.toString().split(" ");
	}

	private int[] readArray(String[] x) {
		StringBuilder sb = new StringBuilder();
		for (String xx : x)
			sb.append(xx);
		ArrayList<Integer> al = new ArrayList<Integer>();
		Scanner in = new Scanner(sb.toString());
		while (in.hasNextInt())
			al.add(in.nextInt());
		in.close();
		int[] a = new int[al.size()];
		for (int i = 0; i < al.size(); i++)
			a[i] = al.get(i);
		return a;
	}
}
