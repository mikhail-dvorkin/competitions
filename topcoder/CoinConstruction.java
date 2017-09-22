package topcoder;
public class CoinConstruction {
    public int[] construct(int k) {
    	int n = Integer.SIZE - Integer.numberOfLeadingZeros(k);
    	int[] ans = new int[n];
    	for (int i = 0; i < n; i++) {
    		ans[i] = 1 << i;
    	}
    	ans[n - 1] = k - Integer.highestOneBit(k) + 1;
    	return ans;
    }

}
