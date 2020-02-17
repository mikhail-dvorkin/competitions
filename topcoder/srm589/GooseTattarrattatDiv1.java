package topcoder.srm589;
public class GooseTattarrattatDiv1 {
    public int getmin(String s) {
    	int[] count = new int[26];
    	int[] comp = new int[26];
    	for (int i = 0; i < comp.length; i++) {
    		comp[i] = i;
    	}
    	for (int i = 0; i < s.length(); i++) {
    		count[s.charAt(i) - 'a']++;
    	}
    	for (int i = 0; i < s.length(); i++) {
    		int x = s.charAt(i) - 'a';
    		int y = s.charAt(s.length() - 1 - i) - 'a';
    		x = comp[x];
    		y = comp[y];
    		for (int j = 0; j < comp.length; j++) {
    			if (comp[j] == x) {
    				comp[j] = y;
    			}
    		}
    	}
    	int ans = 0;
    	for (int c = 0; c < comp.length; c++) {
    		int size = 0;
    		int max = 0;
    		for (int i = 0; i < comp.length; i++) {
    			if (comp[i] != c) {
    				continue;
    			}
    			size += count[i];
    			max = Math.max(max, count[i]);
    		}
    		ans += size - max;
    	}
    	return ans;
    }

}
