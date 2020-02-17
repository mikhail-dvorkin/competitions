package topcoder.srm607;
public class PalindromicSubstringsDiv1 {
    public double expectedPalindromes(String[] s1, String[] s2) {
    	StringBuilder sb = new StringBuilder();
    	for (String s : s1) {
    		sb.append(s);
    	}
    	for (String s : s2) {
    		sb.append(s);
    	}
    	String s = sb.toString();
    	int n = s.length();
    	double pEqual = 1.0 / 26;
    	double ans = 0;
    	for (int i = 0; i < n; i++) {
    		ans++;
    		double prob = 1;
    		for (int j = 1;; j++) {
    			if (i - j < 0 || i + j >= n) {
    				break;
    			}
    			char c1 = s.charAt(i - j);
    			char c2 = s.charAt(i + j);
    			if (c1 == '?' || c2 == '?') {
    				prob *= pEqual;
    			} else if (c1 != c2) {
    				break;
    			}
    			ans += prob;
    		}
    		prob = 1;
    		for (int j = 0;; j++) {
    			if (i - j < 0 || i + 1 + j >= n) {
    				break;
    			}
    			char c1 = s.charAt(i - j);
    			char c2 = s.charAt(i + 1 + j);
    			if (c1 == '?' || c2 == '?') {
    				prob *= pEqual;
    			} else if (c1 != c2) {
    				break;
    			}
    			ans += prob;
    		}
    	}
    	return ans;
    }

}
