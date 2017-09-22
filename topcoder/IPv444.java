package topcoder;
import java.util.*;

public class IPv444 {
	class Request implements Comparable<Request> {
		int[] mask;
		int price;
		
		public Request(String s, int price) {
			mask = new int[4];
			String[] ss = s.split("[\\.]");
			for (int i = 0; i < 4; i++) {
				if (ss[i].equals("*")) {
					mask[i] = -1;
				} else {
					mask[i] = Integer.parseInt(ss[i]);
				}
			}
			this.price = price;
		}

		@Override
		public int compareTo(Request o) {
			return o.price - price;
		}
	}
	
	public long getMaximumMoney(String[] request, int[] price) {
		int n = request.length;
		Request[] r = new Request[n];
		for (int i = 0; i < n; i++) {
			r[i] = new Request(request[i], price[i]);
		}
		@SuppressWarnings("unchecked")
		TreeSet<Integer>[] ts = new TreeSet[4];
		@SuppressWarnings("unchecked")
		TreeMap<Integer, Integer>[] tm = new TreeMap[4];
		for (int i = 0; i < 4; i++) {
			ts[i] = new TreeSet<Integer>();
			tm[i] = new TreeMap<Integer, Integer>();
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 4; j++) {
				ts[j].add(r[i].mask[j]);
			}
		}
		for (int j = 0; j < 4; j++) {
			int k = 0;
			for (int i : ts[j]) {
				if (i == -1) {
					continue;
				}
				tm[j].put(i, k++);
			}
			tm[j].put(-1, n);
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 4; j++) {
				r[i].mask[j] = tm[j].get(r[i].mask[j]);
			}
		}
		Arrays.sort(r);
		int m = 1000 - n;
		int nn = n + 1;
		long ans = 0;
		boolean[] b = new boolean[nn * nn * nn * nn];
		for (Request rr : r) {
			int low0 = rr.mask[0];
			int high0 = rr.mask[0];
			int low1 = rr.mask[1];
			int high1 = rr.mask[1];
			int low2 = rr.mask[2];
			int high2 = rr.mask[2];
			int low3 = rr.mask[3];
			int high3 = rr.mask[3];
			if (high0 == n) low0 = 0;
			if (high1 == n) low1 = 0;
			if (high2 == n) low2 = 0;
			if (high3 == n) low3 = 0;
			for (int i0 = low0; i0 <= high0; i0++) {
				for (int i1 = low1; i1 <= high1; i1++) {
					for (int i2 = low2; i2 <= high2; i2++) {
						for (int i3 = low3; i3 <= high3; i3++) {
							int index = i0;
							index = index * nn + i1;
							index = index * nn + i2;
							index = index * nn + i3;
							if (!b[index]) {
								long profit = rr.price;
								if (i0 == n) profit *= m;
								if (i1 == n) profit *= m;
								if (i2 == n) profit *= m;
								if (i3 == n) profit *= m;
								ans += profit;
								b[index] = true;
							}
						}
					}
				}
			}
		}
		return ans;
	}

}
