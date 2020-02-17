package topcoder.srm669;
public class SubdividedSlimes {
    public int needCut(int s, int m) {
    	for (int t = 1; t <= s; t++) {
    		int score = s * (s - 1) / 2;
    		for (int i = 0; i < t; i++) {
    			int n = s / t;
    			if (i < s % t) {
    				n++;
    			}
    			score -= n * (n - 1) / 2;
    		}
    		if (score >= m) {
    			return t - 1;
    		}
    	}
    	return -1;
    }

}
