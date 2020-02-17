package topcoder;
import java.util.*;

public class RadioRange {
	class Event implements Comparable<Event> {
		double x;
		int inc;
		
		public Event(double x, int inc) {
			this.x = x;
			this.inc = inc;
		}

		@Override
		public int compareTo(Event o) {
			return Double.compare(x, o.x);
		}
	}
	
    public double RadiusProbability(int[] x, int[] y, int[] r, int maxR) {
    	int n = x.length;
    	List<Event> events = new ArrayList<>();
    	events.add(new Event(maxR, 0));
    	for (int i = 0; i < n; i++) {
    		double d = Math.hypot(x[i], y[i]);
    		double maxD = d + r[i];
    		double minD = Math.max(d - r[i], 0);
    		events.add(new Event(minD, 1));
    		events.add(new Event(maxD, -1));
    	}
    	Collections.sort(events);
    	double prevX = 0;
    	int count = 0;
    	double ansLen = 0;
    	for (Event event : events) {
    		if (count == 0) {
    			ansLen += event.x - prevX;
    		}
    		count += event.inc;
    		prevX = event.x;
    		if (event.inc == 0) {
    			break;
    		}
    	}
    	return ansLen / maxR;
    }

}
