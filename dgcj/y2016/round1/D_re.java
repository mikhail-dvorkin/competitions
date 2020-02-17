package dgcj.y2016.round1;

import dgcj.message;
import java.util.*;

public class D_re {
	static final Object PROBLEM = new winning_move(); // PROBLEM NAME goes here

	// Why RE??
	public String run() {
		if (ID >= 2) {
			return null;
		}
		int n = (int) winning_move.GetNumPlayers();
		int from = n * ID / 2;
		int to = n * (ID + 1) / 2;
		int m = to - from;

		long[] a = new long[m];
		for (int i = 0; i < m; i++) {
			a[i] = winning_move.GetSubmission(from + i);
		}
		Arrays.sort(a);
		long[] b = new long[m];
		int bLen = 0;
		for (int i = 0; i < m; i++) {
			if ((i == 0 || a[i] != a[i - 1]) && (i == m - 1 || a[i] != a[i + 1])) {
				b[bLen++] = a[i];
			}
		}
		
		message.PutInt(P, bLen);
		for (int i = 0; i < bLen; i++) {
			message.PutLL(P, b[i]);
		}
		message.Send(P);
		
		message.Receive(P);
		int cLen = message.GetInt(P);
		long res = Long.MAX_VALUE;
		for (int i = 0; i < cLen; i++) {
			long v = message.GetLL(P);
			if (res == Long.MAX_VALUE && Arrays.binarySearch(a, v) < 0) {
				res = v;
			}
		}
		
		if (ID == 1) {
			message.PutLL(0, res);
			message.Send(0);
			return null;
		}
		message.Receive(1);
		res = Math.min(res, message.GetLL(1));
		if (res == Long.MAX_VALUE) {
			res = 0;
		}
		return "" + res;
	}
	
	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();
	final int P = ID ^ 1;

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new D_re().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}
