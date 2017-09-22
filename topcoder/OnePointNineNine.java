package topcoder;
import java.util.*;

public class OnePointNineNine {
	long M = 1000000007;
	
    @SuppressWarnings("unchecked")
	public int countSubsets(int[] x, int[] y, int r) {
    	int n = x.length;
    	double[][] d = new double[n][n];
    	double[][] at = new double[n][n];
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			d[i][j] = Math.hypot(x[i] - x[j], y[i] - y[j]) / r;
    			at[i][j] = Math.atan2(y[j] - y[i], x[j] - x[i]);
    		}
    	}
    	color = new int[n];
    	size = new int[n];
    	spec = new boolean[n];
    	mark = new boolean[n];
    	Arrays.fill(color, -1);
    	nei = new ArrayList[n];
    	for (int i = 0; i < n; i++) {
    		nei[i] = new ArrayList<>();
    	}
    	for (int i = 0; i < n; i++) {
    		int j1 = -1;
    		int j2 = -1;
    		for (int j = 0; j < n; j++) {
    			if (i == j) {
    				 continue;
    			}
    			if (d[i][j] > 0.75 && d[i][j] < 1.25) {
    				if (j1 == -1) {
    					j1 = j;
    					continue;
    				}
    				if (j2 != -1) {
    					continue;
    				}
    				if (d[j1][j] > 1.25) {
    					j2 = j;
    				}
    			}
    		}
    		if (j2 != -1) {
    			int c = -1;
    			for (int j = 0; j < n; j++) {
    				if (d[i][j] < 0.75) {
    					if (c == -1) {
    						c = j;
    					}
    					spec[j] = true;
    				}
    			}
    			c = -1;
    			for (int j = 0; j < n; j++) {
    				if (d[j1][j] < 0.75) {
    					if (c == -1) {
    						c = j;
    					}
    					spec[j] = true;
    				}
    			}
    			c = -1;
    			for (int j = 0; j < n; j++) {
    				if (d[j2][j] < 0.75) {
    					if (c == -1) {
    						c = j;
    					}
    					spec[j] = true;
    				}
    			}
//    			nei[ci].add(cj1);
//    			nei[cj1].add(ci);
//    			nei[ci].add(cj2);
//    			nei[cj2].add(ci);
    			continue;
    		}
//    		for (int j = 0; j <= i; j++) {
//    			if (d[i][j] < 1.5) {
//    				color[i] = j;
//    				break;
//    			}
//    		}
    	}
    	for (int i = 0; i < n; i++) {
			if (!spec[i]) {
				for (int j = 0; j <= i; j++) {
					if (d[i][j] < 1.5) {
						color[i] = j;
						break;
					}
				}
				continue;
			}
    	}
    	for (int i = 0; i < n; i++) {
			if (!spec[i]) {
				continue;
			}
    		int j1 = -1;
    		int j2 = -1;
    		for (int j = 0; j < n; j++) {
    			if (i == j) {
    				continue;
    			}
    			if (d[i][j] > 0.75 && d[i][j] < 1.25) {
    				if (j1 == -1) {
    					j1 = j;
    					continue;
    				}
    				if (j2 != -1) {
    					continue;
    				}
    				if (d[j1][j] > 1.25) {
    					j2 = j;
    				}
    			}
    		}
    		if (j2 != -1) {
    			int ci, cj1, cj2;
    			int c = -1;
    			for (int j = 0; j < n; j++) {
    				if (d[i][j] < 0.75) {
    					if (c == -1) {
    						c = j;
    					}
    					spec[j] = true;
    					color[j] = c;
    				}
    			}
    			ci = c;
    			c = -1;
    			for (int j = 0; j < n; j++) {
    				if (d[j1][j] < 0.75) {
    					if (c == -1) {
    						c = j;
    					}
    					spec[j] = true;
    					color[j] = c;
    				}
    			}
    			cj1 = c;
    			c = -1;
    			for (int j = 0; j < n; j++) {
    				if (d[j2][j] < 0.75) {
    					if (c == -1) {
    						c = j;
    					}
    					spec[j] = true;
    					color[j] = c;
    				}
    			}
    			cj2 = c;
    			nei[ci].add(cj1);
    			nei[cj1].add(ci);
    			nei[ci].add(cj2);
    			nei[cj2].add(ci);
    			continue;
    		}
    	}
    	long ans = 1;
    	for (int i = 0; i < n; i++) {
    		size[color[i]]++;
    		nei[i] = new ArrayList<>(new TreeSet<>(nei[i]));
    	}
    	for (int i = 0; i < n; i++) {
    		if (color[i] != i) {
    			continue;
    		}
    		if (!spec[i]) {
    			ans *= size[i] + 1;
    			ans %= M;
    			continue;
    		}
    		if (mark[i]) {
    			continue;
    		}
    		ArrayList<Integer> p = new ArrayList<>();
    		ArrayList<Integer> q = new ArrayList<>();
    		mark[i] = true;
    		if (0 < nei[i].size()) {
    			trav(nei[i].get(0), i, p);
    		}
    		if (1 < nei[i].size()) {
    			trav(nei[i].get(1), i, q);
    		}
    		Collections.reverse(q);
    		q.add(i);
    		q.addAll(p);
    		long with = 0;
    		long without = 1;
    		for (Object jj : q) {
    			int j = (Integer) jj;
    			long without1 = (with + without) % M;
    			with = without * (size[j]) % M;
    			without = without1;
    		}
    		long v = (with + without) % M;
    		ans *= v;
    		ans %= M;
    	}
    	return (int) ans;
    }
    
    void trav(int v, int p, ArrayList<Integer> list) {
    	list.add(v);
    	mark[v] = true;
    	for (Object uu : nei[v]) {
    		int u = (Integer) uu;
    		if (u == p) {
    			continue;
    		}
    		trav(u, v, list);
    	}
	}

	int[] color;
    int[] size;
    boolean[] mark;
    boolean[] spec;
    ArrayList<Integer>[] nei;

}
