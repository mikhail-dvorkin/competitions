package dgcj;

public class TemplateDgcjMain { // RENAME to Main
	static final Object PROBLEM = new TemplateDgcjProblem(); // PROBLEM NAME goes here

	public String run() {
//		long n = ;
//		long from = (long) n * ID / NODES;
//		long to = (long) n * (ID + 1) / NODES;

		return null;
	}

	static int NODES = 1;
	static int ID;

	/**
	 * Arguments:
	 * ""	= as for submit
	 * "0"	= run multi node infrastructure
	 * "1"	= run single node
	 */
	public static void main(String[] args) {
		//noinspection ResultOfMethodCallIgnored
		PROBLEM.equals(args); // Local testing framework invocation
		if (args == null || args.length != 1 || !"1".equals(args[0])) {
			NODES = message.NumberOfNodes();
			ID = message.MyNodeId();
		}
		String ans = new TemplateDgcjMain().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public static void log(String msg) {
		//noinspection ResultOfMethodCallIgnored
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}
