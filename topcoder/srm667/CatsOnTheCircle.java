package topcoder;
public class CatsOnTheCircle {
	double gamblerRuin(int up, int down, double p) {
		if (p == 0.5) {
			return down * 1.0 / (up + down);
		}
		if (p < 0.5) {
			return 1 - gamblerRuin(down, up, 1 - p);
		}
		double qp = (1 - p) / p;
		return (Math.pow(qp, down) - 1) / (Math.pow(qp, up + down) - 1);
	}
	
    public double getProb(int n, int k, int prob) {
    	double p = prob / 1000000000.0;
    	double up = gamblerRuin(k - 1, n - k - 1, p);
    	return up * (1 - gamblerRuin(1, n - 2, p)) + (1 - up) * gamblerRuin(n - 2, 1, p);
    }

}
