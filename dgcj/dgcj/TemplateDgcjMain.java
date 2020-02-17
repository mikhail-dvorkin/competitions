package dgcj;

public class TemplateDgcjMain { // RENAME to Main
	static final Object PROBLEM = new TemplateDgcjProblem(); // PROBLEM NAME goes here

	public String run() {
//		long n = ;
//		long from = 1L * n * ID / NODES;
//		long to = 1L * n * (ID + 1) / NODES;

		return null;
	}

	static int NODES;
	static int ID;

	/**
	 * Arguments:
	 * ""	= as for submit
	 * "0"	= run multi node infrastructure
	 * "1"	= run single node
	 */
	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		boolean single = args != null && args.length == 1 && "1".equals(args[0]);
		NODES = single ? 1 : message.NumberOfNodes();
		ID = single ? 0 : message.MyNodeId();
		String ans = new TemplateDgcjMain().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public static void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}
