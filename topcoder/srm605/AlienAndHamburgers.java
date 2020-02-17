package topcoder;
import java.util.*;

public class AlienAndHamburgers {
    public int getNumber(int[] type, int[] taste) {
    	int n = type.length;
    	Set<Integer> types = new HashSet<Integer>();
    	for (int t : type) {
    		types.add(t);
    	}
    	List<Integer> totalTaste = new ArrayList<Integer>();
    	for (int t : types) {
    		int sum = 0;
    		int taken = 0;
    		int max = Integer.MIN_VALUE;
    		for (int i = 0; i < n; i++) {
    			if (type[i] != t) {
    				continue;
    			}
    			int v = taste[i];
    			max = Math.max(max, v);
    			if (v >= 0) {
    				sum += v;
    				taken++;
    			}
    		}
    		int total;
    		if (taken > 0) {
    			total = sum;
    		} else {
    			total = max;
    		}
    		totalTaste.add(total);
    	}
    	Collections.sort(totalTaste);
    	int ans = 0;
    	int sum = 0;
    	int taken = 0;
    	for (int i = totalTaste.size() - 1; i >= 0; i--) {
    		sum += totalTaste.get(i);
    		taken++;
    		ans = Math.max(ans, sum * taken);
    	}
    	return ans;
    }

}
