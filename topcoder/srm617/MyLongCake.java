package topcoder.srm617;
public class MyLongCake {
    public int cut(int n) {
    	int ans = 1;
    	for (int i = 1; i < n; i++) {
    		if (gcd(i, n) > 1) {
    			ans++;
    		}
    	}
    	return ans;
    }

	public static int gcd(int a, int b) {
		while (a > 0) {
			int t = b % a;
			b = a;
			a = t;
		}
		return b;
	}

}
