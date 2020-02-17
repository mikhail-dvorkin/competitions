package topcoder.tco2014.round2a;
import java.util.*;

public class SixteenBricks {
    public int maximumSurface(int[] a) {
    	Arrays.sort(a);
    	int sum = 0;
    	for (int x : a) {
    		sum += x;
    	}
    	int s = (a[0] + a[1]) * 4 + (a[2] + a[3] + a[4] + a[5]) * 3 + (a[6] + a[7]) * 2;
    	return 16 + 4 * sum - 2 * s;
    }

}
