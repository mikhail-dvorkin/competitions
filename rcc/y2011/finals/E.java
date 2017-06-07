package rcc.y2011.finals;

import java.util.*;

public class E {
	private static Scanner in;

	public void run() {
		int[] spec = new int[]{11, 13, 17, 19};
		int main = spec.length;
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] decks = new ArrayList[5];
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] poss = new ArrayList[5];
		@SuppressWarnings("unchecked")
		ArrayList<String>[] howr = new ArrayList[5];
		for (int i = 0; i < decks.length; i++) {
			decks[i] = new ArrayList<Integer>();
			poss[i] = new ArrayList<Integer>();
			howr[i] = new ArrayList<String>();
		}
//		int D = 16 * 9 * 5 * 7 * 11 * 13 * 17 * 19;
		int DD = 16 * 9 * 5 * 7;
		int n = in.nextInt();
		int[] p = new int[n];
		int[] q = new int[n];
		int[] v = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = in.nextInt();
			q[i] = in.nextInt();
			int s = Arrays.binarySearch(spec, q[i]);
			if (s >= 0) {
				v[i] = p[i];
				decks[s].add(i);
			} else {
				v[i] = p[i] * DD / q[i];
				decks[main].add(i);
			}
		}
		for (int d = 0; d < decks.length; d++) {
			int mins = 0;
			int maxs = 0;
			for (int y : decks[d]) {
				int x = v[y];
				if (x < 0) {
					mins -= x;
				} else {
					maxs += x;
				}
			}
			boolean[] a = new boolean[maxs + mins + 1];
			int[] how = new int[maxs + mins + 1];
			a[mins] = true;
			how[mins] = -1;
			int minc = mins;
			int maxc = mins;
			for (int y : decks[d]) {
				int x = v[y];
				if (x == 0) {
					continue;
				}
				if (x < 0) {
					for (int i = minc; i <= maxc; i++) {
						if (!a[i]) {
							continue;
						}
						if (!a[i + x]) {
							a[i + x] = true;
							how[i + x] = y;
						}
					}
					minc += x;
				} else {
					for (int i = maxc; i >= minc; i--) {
						if (!a[i]) {
							continue;
						}
						if (!a[i + x]) {
							a[i + x] = true;
							how[i + x] = y;
						}
					}
					maxc += x;
				}
			}
			int qq = (d < main) ? spec[d] : DD;
			for (int i = minc; i <= maxc; i++) {
				if (!a[i] || ((i - mins) % qq != 0)) {
					continue;
				}
				int vv = (i - mins) / qq;
				String s = "";
				int j = i;
				while (how[j] >= 0) {
					s += (how[j] + 1) + " ";
					j -= v[how[j]];
				}
				poss[d].add(vv);
				howr[d].add(s);
//				System.out.println(vv + "<" + s + ">");
			}
//			System.out.println();
		}

	
		int maxs = 500;
		int mins = 500;
		boolean[] a = new boolean[maxs + mins + 1];
		String[] ahow = new String[maxs + mins + 1];
		a[mins] = true;
		ahow[mins] = "";
		for (int d = 0; d < decks.length; d++) {
			boolean[] b = a.clone();
			String[] bhow = ahow.clone();
			for (int j = 0; j < poss[d].size(); j++) {
				int x = poss[d].get(j);
				String howrs = howr[d].get(j);
				for (int i = 0; i < a.length; i++) {
					if (!a[i]) {
						continue;
					}
					if (!b[i + x]) {
						b[i + x] = true;
						bhow[i + x] = ahow[i] + howrs;
					}
				}
			}
			a = b;
			ahow = bhow;
		}
		if (!a[mins + 1]) {
			System.out.println("NO");
			return;
		}
		System.out.println("YES");
		String s = ahow[mins + 1].trim();
		System.out.println(s.split(" ").length);
		System.out.println(s);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new E().run();
		in.close();
	}
}