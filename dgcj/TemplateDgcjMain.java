package dgcj;

public class TemplateDgcjMain {
	static final Object PROBLEM = new TemplateDgcjProblem(); // PROBLEM NAME goes here

	public String run() {
//		long n = ;
//		long from = 1L * n * ID / NODES;
//		long to = 1L * n * (ID + 1) / NODES;

		return null;
	}

	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new TemplateDgcjMain().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}
