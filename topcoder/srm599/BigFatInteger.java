package topcoder.srm599;
public class BigFatInteger {
    public int minOperations(int a, int b) {
    	int n = a;
    	int primes = 0;
    	int max = 0;
		for (int i = 2; i <= n; i++) {
			if (n % i == 0) {
				int count = 0;
				while (n % i == 0) {
					n /= i;
					count++;
				}
				max = Math.max(count, max);
				primes++;
			}
		}
		return primes + log(max * b);
    }

    int log(int n) {
    	if (n == 0) {
    		return 0;
    	}
		return 32 - Integer.numberOfLeadingZeros(n - 1);
	}

}
