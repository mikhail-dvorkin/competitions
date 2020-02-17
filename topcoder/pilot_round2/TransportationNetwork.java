package topcoder.pilot_round2;
import java.util.*;

public class TransportationNetwork {
	class Road implements Comparable<Road> {
		int i, j;
		double len;

		public Road(int i, int j, double len) {
			this.i = i;
			this.j = j;
			this.len = len;
		}

		@Override
		public int compareTo(Road o) {
			return Double.compare(len, o.len);
		}
	}

	public double minCost(int[] x, int[] y, double roadCost, double airportCost) {
		int n = x.length;
		ArrayList<Road> roads = new ArrayList<Road>();
		int[] col = new int[n];
		for (int i = 0; i < n; i++) {
			col[i] = i;
			for (int j = i + 1; j < n; j++) {
				roads.add(new Road(i, j, Math.hypot(x[i] - x[j], y[i] - y[j])));
			}
		}
		Collections.sort(roads);
		int airports = n;
		double ans = (airports == 1 ? 0 : airports) * airportCost;
		double sum = 0;
		for (Road r : roads) {
			int c1 = col[r.i];
			int c2 = col[r.j];
			if (c1 == c2) {
				continue;
			}
			double cost = r.len * roadCost;
			sum += cost;
			for (int i = 0; i < n; i++) {
				if (col[i] == c1) {
					col[i] = c2;
				}
			}
			airports--;
			ans = Math.min(ans, sum + (airports == 1 ? 0 : airports) * airportCost);
		}
		return ans;
		// {0,0,0} {0,1,100} 1 55
	}

}
