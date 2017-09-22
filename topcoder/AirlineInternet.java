package topcoder;
import java.util.*;

public class AirlineInternet {
	int[] xa, ya;
	int as, fs;
	int[] from, to, begin, end;
	double r;
	double[] xx, yy;
	ArrayList<Double> times = new ArrayList<Double>();
	
	private boolean cover() {
		times.clear();
		for (int i = 0; i < fs; i++) {
			times.add((double) begin[i]);
			times.add((double) end[i]);
			for (int j = 0; j < as; j++) {
				times(from[i], to[i], begin[i], end[i], j, j, begin[i], end[i]);
			}
			for (int j = 0; j < i; j++) {
				times(from[i], to[i], begin[i], end[i], from[j], to[j], begin[j], end[j]);
			}
		}
		Collections.sort(times);
		for (int i = 0; i < times.size() - 1; i++) {
			double t1 = times.get(i);
			double t2 = times.get(i + 1);
			if (t2 - t1 < 1e-10)
				continue;
			double t = (t1 + t2) * 0.5;
			for (int j = 0; j < fs; j++) {
				if (begin[j] > t || end[j] < t) {
					were[j] = true;
					continue;
				}
				were[j] = false;
				double tt = (t - begin[j]) / (end[j] - begin[j]);
				xx[j] = xa[from[j]] + tt * (xa[to[j]] - xa[from[j]]);
				yy[j] = ya[from[j]] + tt * (ya[to[j]] - ya[from[j]]);
			}
			for (int j = 0; j < as; j++) {
				for (int k = 0; k < fs; k++) {
					double d = Math.hypot(xx[k] - xa[j], yy[k] - ya[j]);
					if (d <= r && !were[k])
						dfs(k);
				}
			}
			for (int j = 0; j < fs; j++) {
				if (!were[j])
					return false;
			}
		}
		return true;
	}
	
	private void times(int from1, int to1, int b1, int e1, int from2, int to2, int b2, int e2) {
		if (b2 >= e1 || b1 >= e2)
			return;
		int tt1 = Math.max(b1, b2);
		double t1 = (tt1 * 1.0 - b1) / (e1 - b1);
		double x1 = xa[from1] + t1 * (xa[to1] - xa[from1]);
		double y1 = ya[from1] + t1 * (ya[to1] - ya[from1]);
		double t2 = (tt1 * 1.0 - b2) / (e2 - b2);
		double x2 = xa[from2] + t2 * (xa[to2] - xa[from2]);
		double y2 = ya[from2] + t2 * (ya[to2] - ya[from2]);
		double x3 = x2 - x1;
		double y3 = y2 - y1;
		int tt2 = Math.min(e1, e2);
		t1 = (tt2 * 1.0 - b1) / (e1 - b1);
		x1 = xa[from1] + t1 * (xa[to1] - xa[from1]);
		y1 = ya[from1] + t1 * (ya[to1] - ya[from1]);
		t2 = (tt2 * 1.0 - b2) / (e2 - b2);
		x2 = xa[from2] + t2 * (xa[to2] - xa[from2]);
		y2 = ya[from2] + t2 * (ya[to2] - ya[from2]);
		double x4 = x2 - x1;
		double y4 = y2 - y1;
		double dx = x4 - x3;
		double dy = y4 - y3;
		double c = x3 * x3 + y3 * y3 - r * r;
		double b = 2 * x3 * dx + 2 * y3 * dy;
		double a = dx * dx + dy * dy;
		double d = b * b - 4 * a * c;
		if (d < 0)
			return;
		double tx = (- b + Math.sqrt(d)) / 2 / a;
		double ty = (- b - Math.sqrt(d)) / 2 / a;
		if (tx > 0 && tx < 1)
			times.add(tt1 + (tt2 - tt1) * tx);
		if (ty > 0 && ty < 1)
			times.add(tt1 + (tt2 - tt1) * ty);
	}

	private void dfs(int k) {
		were[k] = true;
		for (int i = 0; i < fs; i++) {
			double d = Math.hypot(xx[k] - xx[i], yy[k] - yy[i]);
			if (d <= r && !were[i])
				dfs(i);
		}
	}

	boolean[] were;
	
	public double minimumRange(String[] airportLocations, String[] flights) {
		as = airportLocations.length;
		xa = new int[as];
		ya = new int[as];
		for (int i = 0; i < as; i++) {
			Scanner in = new Scanner(airportLocations[i]);
			xa[i] = in.nextInt();
			ya[i] = in.nextInt();
			in.close();
		}
		fs = flights.length;
		from = new int[fs];
		to = new int[fs];
		begin = new int[fs];
		end = new int[fs];
		for (int i = 0; i < fs; i++) {
			Scanner in = new Scanner(flights[i]);
			from[i] = in.nextInt();
			to[i] = in.nextInt();
			begin[i] = in.nextInt();
			end[i] = in.nextInt();
			in.close();
		}
		were = new boolean[fs];
		xx = new double[fs];
		yy = new double[fs];
		double le = 0;
		double ri = 2000;
		while ((ri - le > 1e-9) && (1 - le / ri > 1e-9)) {
			r = (le + ri) * 0.5;
			if (cover())
				ri = r;
			else
				le = r;
		}
		return r;
	}
	
	public static void main(String[] args) {
		double a = new AirlineInternet().minimumRange(
				new String[]{"25 100","0 50","90 150","22 22","60 1","95 8","12 40"}
				,
				new String[]{"0 1 0 500","2 5 10 300","2 0 100 200"
						,"3 6 150 400","4 5 50 450","5 1 0 300"
						,"2 6 10 100"}
				);
		System.out.println(a);
	}
}
