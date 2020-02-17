package topcoder;
import java.util.*;

public class PreciousStones {
	class Stone implements Comparable<Stone> {
		int g, s, m;
		double gm;
		
		public Stone(int ss, int gg) {
			s = ss;
			g = gg;
			m = s + g;
			gm = g * 1.0 / m;
		}

		@Override
		public int compareTo(Stone o) {
			return Double.compare(o.gm, gm);
		}
		
	}
	public double value(int[] silver, int[] gold) {
		int n = silver.length;
		ArrayList<Stone> stone = new ArrayList<Stone>();
		double sil = 0;
		for (int i = 0; i < n; i++) if (silver[i] + gold[i] > 0) {
			stone.add(new Stone(silver[i], gold[i]));
			sil += silver[i];
		}
		Collections.sort(stone);
		double mass = 0;
		double ans = 0;
		for (Stone s : stone) {
			double x = (sil - mass) / s.m;
			x = Math.min(x, 1);
			x = Math.max(x, 0);
			ans += x * s.g;
			mass += x * s.m;
		}
		return ans;
	}
}
