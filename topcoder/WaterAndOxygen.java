package topcoder;
public class WaterAndOxygen {
    public double maxDays(int remainH20, int remainO2, int costH2O, int costO2) {
    	double x = costO2 * 1.0 * remainH20 - costH2O * 1.0 * remainO2;
    	x /= costH2O / 2.0 + costO2;
    	x = Math.max(x, 0);
    	return (remainH20 - x) / costH2O;
    }

}
