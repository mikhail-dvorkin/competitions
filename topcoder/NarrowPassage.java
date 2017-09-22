package topcoder;
import java.util.*;
import java.util.Map.Entry;

public class NarrowPassage {
    public int minDist(int len, int[] a, int[] b) {
    	int n = a.length + 2;
    	a = Arrays.copyOf(a, n);
    	b = Arrays.copyOf(b, n);
    	a[n - 1] = b[n - 1] = len;
    	int ans = Integer.MAX_VALUE;
    	for (int p = 0; p < n; p++) {
    		loop:
    		for (int q = 0; q < n; q++) {
    			int cur = 0;
    			TreeMap<Integer, Integer> map = new TreeMap<>();
    			int b0 = 0;
    			int b3 = len;
    			for (int i = 0; i < n; i++) {
    				if (a[p] < a[i] && a[i] < a[q]) {
    					cur += Math.abs(b[i] - a[i]);
    					map.put(a[i], b[i]);
    				} else if (a[i] <= a[p]) {
    					b0 = Math.max(b0, b[i]);
    					cur += a[i] + b[i];
    				} else if (a[i] >= a[q]) {
    					b3 = Math.min(b3, b[i]);
    					cur += 2 * len - a[i] - b[i];
    				} else {
    					throw new RuntimeException();
    				}
    			}
    			Entry<Integer, Integer> prev = null;
    			for (Entry<Integer, Integer> entry : map.entrySet()) {
    				if (prev != null) {
    					if (prev.getValue() >= entry.getValue()) {
    						continue loop;
    					}
    				}
    				prev = entry;
    			}
    			if (b0 >= b3) {
    				continue loop;
    			}
    			if (!map.isEmpty()) {
    				int b1 = map.firstEntry().getValue();
    				int b2 = map.lastEntry().getValue();
    				if (b0 >= b1 || b2 >= b3) {
    					continue loop;
    				}
    			}
    			ans = Math.min(ans, cur);
    		}
    	}
    	for (int p = 0; p < n; p++) {
    		for (int q = 0; q < n; q++) {
    			int cur = 0;
    			for (int i = 0; i < n; i++) {
    				if (a[i] <= a[p] && b[i] <= b[q]) {
    					cur += a[i] + b[i];
    				} else if (a[i] > a[p] && b[i] > b[q]) {
    					cur += 2 * len - a[i] - b[i];
    				} else {
    					if (a[i] <= a[p]) {
    						cur += a[i] + 2 * len - b[i];
    					} else {
    						cur += b[i] + 2 * len - a[i];
    					}
    				}
    			}
    			ans = Math.min(ans, cur);
    		}
    	}

    	return ans;
    }

}
