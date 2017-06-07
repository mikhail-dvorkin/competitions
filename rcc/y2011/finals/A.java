package rcc.y2011.finals;

import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int s = in.nextInt();
		int year = in.nextInt();
		@SuppressWarnings("unchecked")
		Map<Integer, Integer>[] change = new HashMap[n];
		for (int c = 0; c < n; c++) {
			change[c] = new HashMap<Integer, Integer>();
		}
		for (int i = 0; i < m; i++) {
			int day = in.nextInt() - 1;
			int city = in.nextInt() - 1;
			int diff = in.nextInt();
			change[city].put(day, diff);
		}
		int fullyear = year * 2 * s;
		ArrayList<TZEvent> events = new ArrayList<TZEvent>();
		for (int c = 0; c < n; c++) {
			int ot = 0;
			int zone = 0;
			int d = 0;
			while (ot < fullyear) {
				Integer chInteger = change[c].get(d);
				int ch = (chInteger == null) ? 0 : chInteger;
				if (ch != 0) {
					ot += s;
					int nz = zone + ch;
					events.add(new TZEvent(ot, zone, nz));
					zone = nz;
					ot += s - ch;
				} else {
					ot += 2 * s;
				}
				d += 1;
			}
		}
		Collections.sort(events);
		s += 2;
		long ans = 0;
		int[] zones = new int[2 * s + 1];
		zones[s] = n;
		int[] cum = new int[2 * s + 1];
		for (int i = 1; i <= 2 * s; i++) {
			cum[i] = cum[i - 1] + zones[i];
		}
		int e = 0;
		long penalty = 0;
		for (int t = 0; t < fullyear; t++) {
			while (e < events.size() && events.get(e).time == t) {
				int from = events.get(e).from + s;
				int to = events.get(e).to + s;
				e++;
				zones[from]--;
				if (from < to) {
					penalty += (to - from) * cum[from - 1];
					penalty -= (to - from) * (n - cum[to - 1]);
					for (int i = from; i < to; i++) {
						penalty += ((to - i) - (i - from)) * zones[i];
						cum[i]--;
					}
				} else {
					penalty -= (from - to) * cum[to - 1];
					penalty += (from - to) * (n - 1 - cum[from - 1]);
					for (int i = to; i < from; i++) {
						penalty += ((i - to) - (from - i)) * zones[i];
						cum[i]++;
					}
				}
				zones[to]++;
			}
			ans += penalty;
//			System.out.println(t + " " + penalty);
		}
		System.out.println(ans);
	}
	
	class TZEvent implements Comparable<TZEvent> {
		int time;
		int from;
		int to;

		public TZEvent(int time, int from, int to) {
			this.time = time;
			this.from = from;
			this.to = to;
		}

		@Override
		public int compareTo(TZEvent o) {
			return time - o.time;
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}