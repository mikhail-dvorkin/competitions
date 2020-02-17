package topcoder.srm635;
import java.util.*;

public class StoryFromTCO {
    public int minimumChanges(int[] places, int[] cutoff) {
    	int n = places.length;
    	boolean[] selected = new boolean[n];
    	List<Integer> as = new ArrayList<Integer>();
    	List<Integer> bs = new ArrayList<Integer>();
    	int ans = 0;
    	for (int i = 0; i < n; i++) {
    		if (places[i] > cutoff[i]) {
    			selected[i] = true;
    			as.add(places[i]);
    			bs.add(cutoff[i]);
    			ans++;
    		}
    	}
    	Collections.sort(as);
    	Collections.sort(bs);
    	while (!bs.isEmpty()) {
    		int b = bs.get(0);
    		if (as.get(0) <= b) {
    			bs.remove(0);
    			as.remove(0);
    			continue;
    		}
    		int j = -1;
    		for (int i = 0; i < n; i++) {
    			if (selected[i]) {
    				continue;
    			}
    			if (places[i] > b) {
    				continue;
    			}
    			if (j == -1 || cutoff[i] > cutoff[j]) {
    				j = i;
    			}
    		}
    		if (j == -1) {
    			return -1;
    		}
    		bs.remove(0);
    		bs.add(cutoff[j]);
    		Collections.sort(bs);
    		selected[j] = true;
    		ans++;
    	}
    	return ans;
    }

}
