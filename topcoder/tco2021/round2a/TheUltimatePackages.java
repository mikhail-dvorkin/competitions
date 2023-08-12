import java.util.*;

public class TheUltimatePackages {
	public int[] count(int n, int D, int[] Xprefix, int[] Yprefix, int L, int seed) {
		ArrayList<Integer>[] nei = new ArrayList[n];
		ArrayList<Integer>[] neiRev = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
			neiRev[i] = new ArrayList<>();
		}
		{
			int[] X = new int[D];
			int[] Y = new int[D];
			int XL = Xprefix.length;
			for (int i=0; i<XL; ++i) {
				X[i] = Xprefix[i];
				Y[i] = Yprefix[i];
			}
			long state = seed;
			for (int i=XL; i<D; ++i) {
				state = (state * 1103515245 + 12345) % (1L << 31);
				int elen = (int)(1 + state%L);
				state = (state * 1103515245 + 12345) % (1L << 31);
				Y[i] = (int)(state % (n - elen));
				X[i] = Y[i] + elen;
			}
			for (int i = 0; i < D; i++) {
				nei[Y[i]].add(X[i]);
				neiRev[n - 1 - X[i]].add(n - 1 - Y[i]);
			}
		}
		boolean[] forward = ultimateForward(nei);
		boolean[] backward = ultimateForward(neiRev);
		int num = 0;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			if (forward[i] && backward[n - 1 - i]) {
				num++;
				sum += i;
			}
		}
		return new int[] {num, sum};
	}

	boolean[] ultimateForward(ArrayList<Integer>[] nei) {
		int n = nei.length;
		BitSet[] needed = new BitSet[n];
		for (int i = 0; i < n; i++) {
			needed[i] = new BitSet();
		}
		boolean[] res = new boolean[n];
		for (int i = 0; i < n; i++) {
			needed[i].set(i);
			for (int j : nei[i]) {
				needed[j].or(needed[i]);
			}
			res[i] = needed[i].cardinality() == i + 1;
		}
		return res;
	}
}
