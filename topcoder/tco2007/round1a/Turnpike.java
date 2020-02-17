package topcoder.tco2007.round1a;
import java.util.*;

public class Turnpike {
	public int unserviced(int pikeLength, int n, int[] plazas) {
		ArrayList<Integer> p = new ArrayList<Integer>();
		for (int x : plazas) {
			p.add(x);
		}
		p.add(0);
		p.add(pikeLength);
		Collections.sort(p);
		int ans = 0;
		for (int d = 1; d < pikeLength; d++) {
			int need = 0;
			for (int i = 0; i < p.size() - 1; i++) {
				int x = p.get(i + 1) - p.get(i);
				need += (x - 1) / d;
			}
			if (need <= n)
				ans = d;
		}
		return ans;
	}
}
