package topcoder.srm685;
import java.util.*;

public class InverseMatrixTree {
	static final int M = 1_000_000_007;
	static final int N = 300;

	static Map<Integer, int[]> precalc() {
    	Random random = new Random(566);
    	Map<Integer, int[]> map = new HashMap<>();
		for (int i = 0; i < 1000000; i++) {
			int len = 4 + random.nextInt(10 + random.nextInt(40));
    		int[] a = new int[len];
    		int sum0 = N / 2 - 1 - 2 * len - random.nextInt(3);
			int sum = sum0;
    		int p = 1;
    		for (int j = 0; j < len; j++) {
    			int x;
    			if (i % 64 < 16) {
    				x = random.nextInt(sum);
    			} else {
    				x = random.nextInt(16 + 4 * (j + 1) * sum / (len * len));
    			}
    			if (x > sum || j == len - 1 && i % 7 < 4) {
    				x = sum;
    			}
    			sum -= x;
    			a[j] = x;
    			p = (int) (p * (x + 3L) % M);
    		}
    		if (map.containsKey(p)) {
    			i--;
    			continue;
    		}
    		map.put(p, a);
    	}
		return map;
	}

    public int[] constructGraph(int r) {
    	if (r == 0) {
    		return new int[]{2};
    	}
    	Map<Integer, int[]> map = precalc();
    	for (int p : map.keySet()) {
    		int q = (int) (modInverse(p, M) * ((long) r) % M);
    		if (map.containsKey(q)) {
    	    	int[] ans = new int[2 * N + 1];
    	    	int k = 0;
    	    	ans[k++] = N;
    	    	for (int i = 0; i < N - 1; i++) {
    	    		ans[k++] = i * N + i + 1;
    	    	}
    	    	int x = 0;
    	    	for (int[] a : new int[][]{map.get(p), map.get(q)}) {
    	    		for (int i = 0; i < a.length; i++) {
    	    			ans[k++] = x * N + x + a[i] + 2;
    	    			x += a[i] + 2;
    	    		}
    	    	}
    			return Arrays.copyOf(ans, k);
    		}
    	}
    	System.out.println("NO " + r);
    	return new int[1];
    }

	public static int modInverse(int x, int p) {
		int[] xy = new int[2];
		int gcd = gcdExtended(x, p, xy);
		if (gcd != 1) {
			throw new IllegalArgumentException(x + ", " + p);
		}
		int result = xy[0] % p;
		if (result < 0) {
			result += p;
		}
		return result;
	}

	public static int gcdExtended(int a, int b, int[] xy) {
		if (a == 0) {
			xy[0] = 0;
			xy[1] = 1;
			return b;
		}
		int d = gcdExtended(b % a, a, xy);
		int t = xy[0];
		xy[0] = xy[1] - (b / a) * xy[0];
		xy[1] = t;
		return d;
	}

}
