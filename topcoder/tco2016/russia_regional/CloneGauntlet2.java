package topcoder.tco2016.russia_regional;
public class CloneGauntlet2 {
    public int minClones(int[] parent, int[] connection) {
    	int n = connection.length;
    	int ans = 0;
    	int a = 0;
    	boolean[] open = new boolean[n];
    	open[0] = true;
    	while (!open[n - 1]) {
    		int b = connection[a];
    		if (b == 0 || open[b] || !open[parent[b - 1]]) {
    			return -1;
    		}
    		open[b] = true;
    		ans++;
    		a = b;
    	}
    	while (a != 0) {
    		int b = parent[a - 1];
    		if (connection[b] != a) {
    			ans--;
    		}
    		a = b;
    	}
    	return ans;
    }

}
