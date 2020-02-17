package topcoder;
public class ModModMod {
    public long findSum(int[] mod, int r) {
    	int n = mod.length;
    	boolean[] actual = new boolean[n];
    	int act = r + 1;
    	for (int i = 0; i < n; i++) {
    		if (mod[i] < act) {
    			actual[i] = true;
    			act = mod[i];
    		}
    	}
    	int[] a = new int[r + 1];
    	int cur = mod[n - 1];
		for (int i = 0; i <= r && i < cur; i++) {
    		a[i] = i;
    	}
		for (int j = n - 2; j >= 0; j--) {
			if (!actual[j]) {
				continue;
			}
			int d = mod[j];
			if (d <= cur) {
				cur = d;
				continue;
			}
			for (int i = cur; i < d && i <= r; i++) {
				a[i] = a[i % cur];
			}
			cur = d;
		}
		long ans = 0;
		for (int i = 1; i <= r; i++) {
			ans += a[i % cur];
		}
		return ans;
    }

}
