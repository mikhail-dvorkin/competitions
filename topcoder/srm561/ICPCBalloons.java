package topcoder.srm561;
import java.util.*;

public class ICPCBalloons {
	public int minRepaintings(int[] balloonCount, String balloonSize, int[] maxAccepted) {
		int n = balloonCount.length;
		int m = maxAccepted.length;
		Arrays.sort(maxAccepted);
		for (int i = 0; i < m / 2; i++) {
			int t = maxAccepted[i]; maxAccepted[i] = maxAccepted[m - 1 - i]; maxAccepted[m - 1 - i] = t;
		}
		List<Integer> ms = new ArrayList<Integer>();
		List<Integer> ls = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (balloonSize.charAt(i) == 'M') {
				ms.add(balloonCount[i]);
			} else {
				ls.add(balloonCount[i]);
			}
		}
		Collections.sort(ms);
		Collections.reverse(ms);
		Collections.sort(ls);
		Collections.reverse(ls);
		int maxMask = (1 << m) - 1;
		int ans = Integer.MAX_VALUE;
		for (int mask = 0; mask <= maxMask; mask++) {
			int r1 = repaint(maxAccepted, mask, ms);
			int r2 = repaint(maxAccepted, maxMask - mask, ls);
			if (r1 < 0 || r2 < 0) {
				continue;
			}
			ans = Math.min(ans, r1 + r2);
		}
		if (ans == Integer.MAX_VALUE) {
			return -1;
		}
		return ans;
	}

	int repaint(int[] maxAccepted, int mask, List<Integer> list) {
		int needed = 0;
		int available = 0;
		int j = 0;
		for (int i = 0; i < maxAccepted.length; i++) {
			if (((mask >> i) & 1) != 0) {
				int b = (j < list.size()) ? list.get(j) : 0;
				if (b >= maxAccepted[i]) {
					available += b - maxAccepted[i];
				} else {
					needed += maxAccepted[i] - b;
				}
				j++;
			}
		}
		for (; j < list.size(); j++) {
			available += list.get(j);
		}
		if (available >= needed) {
			return needed;
		}
		return -1;
	}

}
