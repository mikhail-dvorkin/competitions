package topcoder.srm594;
import java.util.*;

public class FoxAndAvatar {
    public int minimalSteps(int n, int width, int x) {
    	x--;
    	int inf = Integer.MAX_VALUE / 2;
    	int[][] tail = new int[5][n];
    	for (int s = 0; s < tail.length; s++) {
    		Arrays.fill(tail[s], inf);
    	}
    	tail[0][x] = n - 1 - x;
    	for (int step = 0; step <= 3; step++) {
    		for (int y = 0; y < n; y++) {
    			int t = tail[step][y];
				if (t == inf) {
    				continue;
    			}
				if (y == 0 && t == 0) {
					return step;
				}
				int m = y + 1 + t;
				int h1 = y / width;
				int w1 = y % width;
				int w2 = width - 1 - w1;
				int hf = m / width;
				int wf = m % width;
				{
					int top = (h1 + 1) * width;
					if (top < m) {
						tail[step + 1][y] = Math.min(tail[step + 1][y], t - (m - top));
					}
				}
				{
					int kill = (hf - h1) * w2 + (Math.max(wf - w1 - 1, 0));
					tail[step + 1][y] = Math.min(tail[step + 1][y], t - kill);
				}
				for (int w = 1; w <= width; w++) {
					for (int y0 = 0; y0 <= h1 + 1; y0++) {
						for (int f = 0; f < 2; f++) {
							int x0 = f * (w1 + 1);
							if (x0 == width) {
								continue;
							}
							if (x0 == 0 && w > w1) {
								int kill = w * (h1 - y0);
								if (kill <= 0) {
									continue;
								}
								tail[step + 1][y - kill] = Math.min(tail[step + 1][y - kill], t);
								continue;
							}
							int x1 = x0 + w;
							if (x1 > width) {
								continue;
							}
							int kill = w * ((f == 0) ? Math.max(h1 + 1 - y0, 0) : Math.max(h1 - y0, 0));
							if (y0 > hf) {
								continue;
							}
							int killAll = w * (hf - y0) + Math.max(Math.min(w, wf - x0), 0);
							int killTail = killAll - kill;
							tail[step + 1][y - kill] = Math.min(tail[step + 1][y - kill], t - killTail);
						}
					}
				}
    		}
    	}
    	return 4;
    }

}
