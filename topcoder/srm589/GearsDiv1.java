package topcoder.srm589;
import java.util.*;

public class GearsDiv1 {
	public int n;
	public char[] c;
	public boolean[][] e;

	private boolean[] mark;
	private int[] left;

    public int getmin(String color, String[] graph) {
    	n = color.length();
    	c = color.toCharArray();
    	int ans = n;
    	e = new boolean[n][n];
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			e[i][j] = graph[i].charAt(j) == 'Y';
    		}
    	}
    	ans = Math.min(ans, solve('R', 'G'));
    	ans = Math.min(ans, solve('B', 'G'));
    	ans = Math.min(ans, solve('R', 'B'));
    	return ans;
    }

	public int solve(char p, char q) {
		mark = new boolean[n];
		left = new int[n];
		Arrays.fill(left, -1);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (c[i] != p) {
				continue;
			}
			Arrays.fill(mark, false);
			if (dfs(i, p, q)) {
				ans++;
			}
		}
		return ans;
	}

	private boolean dfs(int i, char p, char q) {
		if (mark[i]) {
			return false;
		}
		mark[i] = true;
		for (int j = 0; j < n; j++) {
			if (c[j] != q) {
				continue;
			}
			if (!e[i][j]) {
				continue;
			}
			if (left[j] == -1 || dfs(left[j], p, q)) {
				left[j] = i;
				return true;
			}
		}
		return false;
	}

}
