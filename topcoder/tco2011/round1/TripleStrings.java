package topcoder.tco2011.round1;
public class TripleStrings {
	public int getMinimumOperations(String init, String goal) {
		int n = init.length();
		for (int i = n; i >= 0; i--) {
			if (init.substring(n - i).equals(goal.substring(0, i))) {
				return 2 * (n - i);
			}
		}
		throw new RuntimeException();
	}

}
