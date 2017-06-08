package dgcj;

public abstract class DgcjProblem {
	public void testLocally() {
		testLocally(1, 6, 1);
	}
	
	public void testLocally(int nodes) {
		LocalTesting.run(this.getClass().getSimpleName(), nodes);
	}

	public void testLocally(int nodesFrom, int nodesTo, int step) {
		for (int i = nodesFrom; i < nodesTo; i += step) {
			testLocally(i);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String[]) {
			if (((String[]) obj).length > 0) {
				testLocally();
				System.exit(0);
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
