package topcoder.tco2007.round4;
import java.util.*;

public class SortingInIterations {
	@SuppressWarnings("unchecked")
	public long sum(int a0, int X, int Y, int M, int n, int start, int finish) {
		int[] a = new int[n];
		a[0] = a0;
		for (int i = 1; i < n; i++) {
			a[i] = (int) ((a[i - 1] * (long) X + Y) % M);
		}
		ArrayList<Integer>[] al = new ArrayList[M];
		for (int i = 0; i < M; i++) {
			al[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < n; i++) {
			al[a[i]].add(i);
		}
		int iter = 1;
		int pos = 0;
		long ans = 0;
		for (int v = 0; v < M; v++) {
			if (al[v].size() == 0)
				continue;
			int p = Collections.binarySearch(al[v], pos);
			if (p < 0)
				p = - 1 - p;
			if (iter >= start) {
				ans += (long) v * (long) (al[v].size() - p);
			}
			if (al[v].get(0) < pos) {
				iter++;
				if (iter > finish)
					break;
				if (iter >= start) {
					ans += (long) v * (long) p;
				}
				pos = al[v].get(p - 1);
			} else {
				pos = al[v].get(al[v].size() - 1);
			}
		}
		if (iter < finish)
			return -1;
		return ans;
	}

	public static void main(String[] args) {
		System.out.println(new SortingInIterations().sum(399999, 398765, 387654, 400000, 400000, 1, 10));
	}
}
