package topcoder;
public class SumAndProductPuzzle {
    public long getSum(int from, int to) {
    	boolean[] isPrime = isPrimeArray(to);
    	boolean[] nice = new boolean[to];
    	for (int m = 2; m < to; m++) {
    		if (!isPrime[m]) {
    			nice[m] = true;
    		}
    	}
    	for (int x = 2; x * x < to; x++) {
    		for (int y = x; y * x < to; y++) {
    			if (!isPrime[x + y - 1]) {
    				nice[x * y] = false;
    			}
    		}
    	}
    	long ans = 0;
    	for (int i = from; i <= to; i++) {
    		if (nice[i - 1]) {
    			ans += i;
    		}
    	}
    	return ans;
    }
    
	public static boolean[] isPrimeArray(int n) {
		boolean[] isPrime = new boolean[n + 1];
		for (int i = 2; i <= n; i++)
			isPrime[i] = true;
		for (int i = 2, j; (j = i * i) <= n; i++) {
			if (!isPrime[i])
				continue;
			do {
				isPrime[j] = false;
				j += i;
			} while (j <= n);
		}
		return isPrime;
	}

}
