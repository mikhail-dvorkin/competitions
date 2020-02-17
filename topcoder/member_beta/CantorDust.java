package topcoder;
import java.util.*;

public class CantorDust {
    public int occurrencesNumber(String[] pattern, int time) {
    	String s = get(time);
    	int hei = pattern.length;
    	int wid = pattern[0].length();
    	boolean[] x = new boolean[wid];
    	boolean[] y = new boolean[hei];
    	boolean xs = false;
    	for (int i = 0; i < hei; i++) {
    		for (int j = 0; j < wid; j++) {
    			if (pattern[i].charAt(j) == 'X') {
    				xs = x[j] = y[i] = true;
    			}
    		}
    	}
    	for (int i = 0; i < hei; i++) {
    		for (int j = 0; j < wid; j++) {
    			if ((pattern[i].charAt(j) == 'X') ^ (x[j] && y[i])) {
    				return 0;
    			}
    		}
    	}
    	if (xs) {
        	return find(x, s) * find(y, s);
    	} else {
    		return (find(x, s) * (s.length() - hei + 1) + find(y, s) * (s.length() - wid + 1) - find(x, s) * find(y, s));
    	}
    }

    private int find(boolean[] x, String s) {
    	int res = 0;
    	for (int i = 0; i <= s.length() - x.length; i++) {
    		boolean good = true;
    		for (int j = 0; j < x.length; j++) {
    			if ((x[j]) ^ (s.charAt(i + j) == 'X')) {
    				good = false;
    			}
    		}
    		if (good) res++;
    	}
		return res;
	}

	private String get(int time) {
    	if (time == 0) {
    		return "X";
    	}
    	String s = get(time - 1);
    	char[] c = new char[s.length()];
    	Arrays.fill(c, '.');
    	String t = new String(c);
    	return s + t + s;
	}

}
