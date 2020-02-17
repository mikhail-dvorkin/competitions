package topcoder.srm621;
import java.util.*;

public class StringsNightmareAgain {
    public long UniqueSubstrings(int s, int b, int c, int d, int length) {
    	int n = length;
    	char[] chars = new char[n];
    	Arrays.fill(chars, 'a');
    	for (int i = 0; i < s; ++i) {
    		b = (int) ((b * (long) c + d) % n);
    		chars[b] = 'b';
    	}
    	String string = new String(chars);
    	string += 'c';
    	n++;
//		System.out.println(string);
        int ts;
    	int[][] t_;
    	int[] l_, r_, p_, s_;
    	{
    		t_ = new int[3 * n + 10][3];
    		l_ = new int[3 * n + 10];
    		r_ = new int[3 * n + 10];
    		p_ = new int[3 * n + 10];
    		s_ = new int[3 * n + 10];
    		for (int i = 0; i < t_.length; i++) {
    			Arrays.fill(t_[i], -1);
    		}
    		Arrays.fill(t_[1], 0);
    		Arrays.fill(r_, n - 1);
    		ts = 2;
    		s_[0] = 1;
    		l_[0] = -1;
    		r_[0] = -1;
    		l_[1] = -1;
    		r_[1] = -1;
    		int tv = 0;
    		int tp = 0;
    		for (int i = 0; i < n; i++) {
    			int x = string.charAt(i) - 'a';
    			for (;;) {
    				if (r_[tv] < tp) {
    					if (t_[tv][x] == -1) {
    						t_[tv][x] = ts;
    						l_[ts] = i;
    						p_[ts++] = tv;
    						tv = s_[tv];
    						tp = r_[tv] + 1;
    						continue;
    					}
    					tv = t_[tv][x];
    					tp = l_[tv];
    				}
    				if (tp == -1 || x == string.charAt(tp) - 'a') {
    					tp++;
    				} else {
    					l_[ts + 1] = i;
    					p_[ts + 1] = ts;
    					l_[ts] = l_[tv];
    					r_[ts] = tp - 1;
    					p_[ts] = p_[tv];
    					t_[ts][x] = ts + 1;
    					t_[ts][string.charAt(tp) - 'a'] = tv;
    					l_[tv] = tp;
    					p_[tv] = ts;
    					t_[p_[ts]][string.charAt(l_[ts]) - 'a'] = ts;
    					ts += 2;
    					tv = s_[p_[ts - 2]];
    					tp = l_[ts - 2];
    					while (tp <= r_[ts - 2]) {
    						tv = t_[tv][string.charAt(tp) - 'a'];
    						tp += r_[tv] - l_[tv] + 1;
    					}
    					if (tp == r_[ts - 2] + 1) {
    						s_[ts - 2] = tv;
    					} else {
    						s_[ts - 2] = ts;
    					}
    					tp = r_[tv] - tp + r_[ts - 2] + 2;
    					continue;
    				}
    				break;
    			}
    		}
    		t_ = Arrays.copyOf(t_, ts);
    		l_ = Arrays.copyOf(l_, ts);
    		r_ = Arrays.copyOf(r_, ts);
    		p_ = Arrays.copyOf(p_, ts);
    	}
    	int[] len = new int[ts];
    	int[] min = new int[ts];
    	int[] max = new int[ts];
    	long[] res = new long[ts];
    	Arrays.fill(max, -1);
    	Arrays.fill(min, n + 1);
    	List<Integer> queue = new ArrayList<Integer>();
    	queue.add(0);
    	for (int i = 0; i < queue.size(); i++) {
    		int v = queue.get(i);
    		for (int x = 0; x < 3; x++) {
    			int u = t_[v][x];
    			if (u == -1) {
    				continue;
    			}
    			len[u] = len[v] + r_[u] + 1 - l_[u];
    			queue.add(u);
    		}
    	}
    	for (int i = queue.size() - 1; i >= 0; i--) {
    		int v = queue.get(i);
    		if (r_[v] == n - 1) {
    			min[v] = max[v] = len[v];
    			continue;
    		}
    		for (int x = 0; x < 3; x++) {
    			int u = t_[v][x];
    			if (u == -1) {
    				continue;
    			}
    			res[v] += res[u];
    			min[v] = Math.min(min[v], min[u]);
    			max[v] = Math.max(max[v], max[u]);
    		}
    		int diff = max[v] - min[v];
    		if (len[v] > 0) {
    			int e = r_[v] + 1 - l_[v];
    			int r = e - (len[v] - diff);
    			r = Math.max(r, 0);
    			r = Math.min(e, r);
    			res[v] += r;
    		}
    	}
    	return res[0];
    }

}
