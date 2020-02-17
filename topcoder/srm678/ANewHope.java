package topcoder.srm678;
import java.util.*;

public class ANewHope {
    public int count(int[] firstWeek, int[] lastWeek, int d) {
    	int n = firstWeek.length;
    	int[] a = new int[n];
    	int[] rename = new int[n];
    	for (int i = 0; i < n; i++) {
    		rename[lastWeek[i] - 1] = i;
    	}
    	for (int i = 0; i < n; i++) {
    		a[i] = rename[firstWeek[i] - 1];
    	}
    	int ans = 0;
    	for (;;) {
    		boolean ok = true;
    		for (int i = 0; i < n; i++) {
    			if (a[i] != i) {
    				ok = false;
    			}
    		}
    		if (ok) {
    			return ans + 1;
    		}
    		ans++;
    		TreeSet<Integer> set = new TreeSet<>();
    		int next = n - d + 1;
    		for (int j = 0; j < next; j++) {
    			set.add(a[j]);
    		}
    		int[] b = new int[n];
    		for (int i = 0; i < n; i++) {
    			b[i] = set.pollFirst();
    			if (next < n) {
    				set.add(a[next]);
    				next++;
    			}
    		}
    		a = b;
    	}
    }

}
