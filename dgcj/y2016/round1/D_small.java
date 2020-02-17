package dgcj.y2016.round1;

import dgcj.message;
import java.util.*;

public class D_small {
	static final Object PROBLEM = new winning_move(); // PROBLEM NAME goes here

	public String run() {
		if (ID > 0) {
			return null;
		}
		int n = (int) winning_move.GetNumPlayers();
		long[] a = new long[n];
		for (int i = 0; i < n; i++) {
			a[i] = winning_move.GetSubmission(i);
		}
		Arrays.sort(a);
		for (int i = 0; i < n; i++) {
			if ((i == 0 || a[i] != a[i - 1]) && (i == n - 1 || a[i] != a[i + 1])) {
				return "" + a[i];
			}
		}
		return "0";
	}
	
	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new D_small().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}
