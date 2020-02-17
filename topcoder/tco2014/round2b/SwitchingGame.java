package topcoder;
import java.util.*;

public class SwitchingGame {
	class Segment {
		int from, to;

		public Segment(int from, int to) {
			this.from = from;
			this.to = to;
		}
	}
	
    public int timeToWin(String[] states) {
    	int n = states[0].length();
    	int levels = states.length;
    	List<Segment> a = new ArrayList<>();
    	List<Segment> b = new ArrayList<>();
    	for (int i = 0; i < n; i++) {
    		boolean on = false;
    		int last = -1;
    		for (int j = 0; j < levels; j++) {
    			char c = states[j].charAt(i);
    			if (c == '?') {
    				continue;
    			}
    			boolean now = (c == '+');
    			if (now == on) {
    				last = j;
    			} else {
    				Segment s = new Segment(last, j);
    				if (on) {
    					a.add(s);
    				} else {
    					b.add(s);
    				}
    				on = now;
    				last = j;
    			}
    		}
    	}
    	return levels + solve(a) + solve(b);
    }

    int solve(List<Segment> segments) {
    	int last = -2;
    	int result = 0;
    	for (;;) {
    		int next = Integer.MAX_VALUE;
    		for (Segment segment : segments) {
    			if (segment.from <= last) {
    				continue;
    			}
    			next = Math.min(next, segment.to - 1);
    		}
    		if (next == Integer.MAX_VALUE) {
    			break;
    		}
    		result++;
    		last = next;
    	}
		return result;
	}

}
