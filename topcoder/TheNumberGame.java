package topcoder;
public class TheNumberGame {
	String FIRST = "Manao wins";
	String SECOND = "Manao loses";
	
	public String determineOutcome(int a, int b) {
		String s = "" + a;
		String t = "" + b;
		String tr = new StringBuilder(t).reverse().toString();
		if (s.contains(t) || s.contains(tr)) {
			return FIRST;
		}
		return SECOND;
	}

}
