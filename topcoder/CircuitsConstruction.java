package topcoder;
import java.util.*;

public class CircuitsConstruction {
    public int maximizeResistance(String s_, int[] c) {
    	s = s_;
    	int v = solve();
    	Arrays.sort(c);
    	int ans = 0;
    	for (int i = c.length - v; i < c.length; i++) {
    		ans += c[i];
    	}
    	return ans;
    }
    
    String s;
    int x = 0;

    int solve() {
    	char c = s.charAt(x);
    	x++;
		if (c == 'X') {
    		return 1;
    	}
		int a = solve();
		int b = solve();
		if (c == 'A') {
			return a + b;
		}
		return Math.max(a, b);
	}

}
