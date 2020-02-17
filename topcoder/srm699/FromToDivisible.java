package topcoder;
public class FromToDivisible {
    public int shortest(int n, int s, int t, int[] a, int[] b) {
    	int m = a.length;
    	int[] queue = new int[m];
    	int low = 0;
    	int high = 0;
    	int[] dist = new int[m];
    	for (int i = 0; i < m; i++) {
    		if (s % a[i] == 0) {
    			queue[high] = i;
    			high++;
    			dist[i] = 1;
    		}
    	}
    	while (low < high) {
    		int v = queue[low];
    		low++;
			if (t % b[v] == 0) {
				return dist[v];
			}
    		for (int u = 0; u < m; u++) {
    			if (dist[u] > 0) {
    				continue;
    			}
    			if (lcm(b[v], a[u]) <= n) {
    				queue[high] = u;
    				high++;
    				dist[u] = dist[v] + 1;
    			}
    		}
    	}
    	return -1;
    }
    
	public static long gcd(long a, long b) {
		while (a > 0) {
			long t = b % a;
			b = a;
			a = t;
		}
		return b;
	}
	
	public static long lcm(long a, long b) {
		return (a / gcd(a, b)) * b;
	}

}
