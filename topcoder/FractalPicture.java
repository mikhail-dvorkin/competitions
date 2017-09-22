package topcoder;
 // Rest in peace, rem.          //
// We will always remember you. //

public class FractalPicture {
	int x1, y1, x2, y2;
	final int s = 500 - 4 + 1;
	
	public double getLength(int x1_, int y1_, int x2_, int y2_) {
		this.x1 = x1_;
		this.y1 = y1_;
		this.x2 = x2_;
		this.y2 = y2_;
		return a(0, 0, 0, 1, 81);
	}

private double a(double x, double y, double dx, double dy, double len) {
		if (len > 1) {
			double xc = x + dx * (2 * len / 3);
			double yc = y + dy * (2 * len / 3);
			double res = s(x, y, xc, yc);
			res += a(xc, yc, dx, dy, len / 3);
			res += a(xc, yc, -dy, dx, len / 3);
			res += a(xc, yc, dy, -dx, len / 3);
			return res;
		}
		double res = s(x, y, x + dx * len, y + dy * len);
		res += f(x + dx * len / 2 + dy * len / 2, y + dy * len / 2 - dx * len / 2);
		res += f(x + dx * len / 2 - dy * len / 2, y + dy * len / 2 + dx * len / 2);
		return res;
	}

private double f(double x, double y) {
	if (x1 <= x && y1 <= y && x <= x2 && y <= y2) {
		return (s * 2 / 3 - 1) / 2;
	}
	return 0;
}

private double s(double x, double y, double xx, double yy) {
	if (x == xx) {
		if (x1 <= x && x <= x2) {
			return inter(y1, y2, y, yy);
		}
		return 0;
	}
	if (y1 <= y && y <= y2) {
		return inter(x1, x2, x, xx);
	}
	return 0;
}

private double inter(int y1_, int y2_, double y3, double y4) {
	if (y3 > y4) {
		double t = y3;
		y3 = y4;
		y4 = t;
	}
	y3 = Math.max(y3, y1_);
	y4 = Math.min(y4, y2_);
	return Math.max(y4 - y3, 0);
}

}
