package dgcj;

public class DgcjProblem {
	private static boolean single;
	
	public void testLocally() {
		testLocally(1, 6, 1);
		testLocally(7);
		testLocally(100);
	}
	
	public void testLocally(int nodes) {
		LocalTesting.run(this.getClass().getSimpleName(), nodes);
	}

	public void testLocally(int nodesFrom, int nodesTo, int step) {
		for (int i = nodesFrom; i < nodesTo; i += step) {
			testLocally(i);
		}
	}
	
	public static boolean single() {
		return single;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String[]) {
			String[] args = (String[]) obj;
			if (args.length == 1 && args[0].equals("0")) {
				testLocally();
				System.exit(0);
			}
			if (args.length == 1 && args[0].equals("1")) {
				single = true;
			}
			return false;
		}
		LocalTesting.log(obj.toString());
		return false;
	}

	// To avoid a warning
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
