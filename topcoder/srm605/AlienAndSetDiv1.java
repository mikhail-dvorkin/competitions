package topcoder.srm605;
public class AlienAndSetDiv1 {
	final static int M = 1000000007;

    public int getNumber(int n, int k) {
    	n *= 2;
    	k--;
    	int masks = 1 << k;
    	int[][][] p = new int[n + 1][n + 1][masks];
    	p[0][0][0] = 1;
    	for (int i = 0; i < n; i++) {
    		for (int as = 0; as <= i; as++) {
    			int bs = i - as;
    			for (int mask = 0; mask < masks; mask++) {
    				int pCur = p[i][as][mask];
    				if (pCur == 0) {
    					continue;
    				}
    				for (int x = 0; x < 2; x++) {
    					int newMask = ((mask * 2 + x) & (masks - 1));
    					int nas = as;
    					int nbs = bs;
    					if (x == 0) {
    						nas++;
    					} else {
    						nbs++;
    					}
    					boolean ok = true;
    					if (x == 0 && nas <= nbs) {
    						int t = nbs;
    						for (int j = i - 1, m = mask; j >= 0 && j >= i - k; j--, m /= 2) {
    							if ((m & 1) != 0) {
    								if (t == nas) {
    									ok = false;
    									break;
    								}
    								t--;
    							}
    						}
    					}
    					if (x == 1 && nas >= nbs) {
    						int t = nas;
    						for (int j = i - 1, m = mask; j >= 0 && j >= i - k; j--, m /= 2) {
    							if ((m & 1) == 0) {
    								if (t == nbs) {
    									ok = false;
    									break;
    								}
    								t--;
    							}
    						}
    					}
    					if (!ok) {
    						continue;
    					}
    					p[i + 1][nas][newMask] = (p[i + 1][nas][newMask] + pCur) % M;
    				}
    			}
    		}
    	}
    	int ans = 0;
		for (int mask = 0; mask < masks; mask++) {
			ans += p[n][n / 2][mask];
			ans %= M;
		}
		return ans;
    }

}
