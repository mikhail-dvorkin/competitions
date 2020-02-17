package topcoder.srm589;
public class FlippingBitsDiv1 {
    public int getmin(String[] ss, int m) {
    	StringBuilder sb = new StringBuilder();
    	for (String s : ss) {
    		sb.append(s);
    	}
    	String s = sb.toString();
    	int n = s.length();
    	int[] sa = new int[n];
    	for (int i = 0; i < n; i++) {
    		sa[i] = s.charAt(i) - '0';
    	}
    	int ans = n;
		int t = (n - 1) / m;
    	if (m < t) {
    		for (int mask = 0; mask < (1 << m); mask++) {
    			int a = 0, b = 0;
    			for (int i = 0; i < n; i += m) {
    				int j = Math.min(i + m, n);
    				int c = 0, d = 0;
    				for (int k = i; k < j; k++) {
    					if (sa[k] == ((mask >> (k - i)) & 1)) {
    						d++;
    					} else {
    						c++;
    					}
    				}
    				int aa = Math.min(a + c, b + 1 + d);
    				int bb = Math.min(b + d, a + 1 + c);
    				a = aa;
    				b = bb;
    			}
    			ans = Math.min(ans, a);
    			ans = Math.min(ans, b);
    		}
    	} else {
    		for (int mask = 0; mask < (1 << t); mask++) {
    			int res = Integer.bitCount(mask);
    			for (int i = 0; i < m; i++) {
    				int a = 0, b = 0;
    				for (int j = i, k = 0, f = 0; j < n; j += m, k++) {
    					if ((sa[j] ^ f) == sa[i]) {
    						a++;
    					} else {
    						b++;
    					}
    					if (((mask >> k) & 1) != 0) {
    						f ^= 1;
    					}
    				}
    				res += Math.min(a, b);
    			}
    			ans = Math.min(ans, res);
    		}
    	}
    	return ans;
    }

}
