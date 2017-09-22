package topcoder;
public class FoxAndCake2 {
    public String isPossible(int a, int b) {
    	if (a > b) {
    		int t = a; a = b; b = t;
    	}
    	return solve(a, b) ? "Possible" : "Impossible";
    }
    
    static boolean solve(int a, int b) {
    	if (a % 2 == 0 && b % 2 == 0 || a % 3 == 0 && b % 3 == 0) {
    		return true;
    	}
    	if (a == 5) {
    		return b != 6;
    	}
    	return a >= 7;
    }

}
