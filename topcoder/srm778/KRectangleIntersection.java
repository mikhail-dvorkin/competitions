package topcoder.srm778;

import java.util.*;

public class KRectangleIntersection {
	public long maxIntersection(int[] xl, int[] yl, int[] xh, int[] yh, int k) {
		int n = xl.length;
		long ans = 0;
		for (int x1 : xl) {
			for (int x2 : xh) {
				if (x1 >= x2) continue;
				ArrayList<Event> open = new ArrayList<>();
				for (int i = 0; i < n; i++) {
					if (xl[i] > x1 || xh[i] < x2) continue;
					open.add(new Event(yl[i], i));
				}
				Collections.sort(open);
				TreeSet<Event> close = new TreeSet<>();
				for (Event event : open) {
					close.add(new Event(yh[event.id], event.id));
					if (close.size() > k) {
						close.remove(close.first());
					}
					if (close.size() == k) {
						ans = Math.max(ans, (long) (x2 - x1) * (close.first().y - event.y));
					}
				}
			}
		}
		return ans;
	}

	static class Event implements Comparable<Event> {
		int y;
		int id;

		public Event(int y, int id) {
			this.y = y;
			this.id = id;
		}

		@Override
		public int compareTo(Event that) {
			if (y != that.y) return y - that.y;
			return id - that.id;
		}
	}
}
