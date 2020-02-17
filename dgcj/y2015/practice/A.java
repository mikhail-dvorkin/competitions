package dgcj.y2015.practice;

import dgcj.message;

public class A {
	static final Object PROBLEM = new sandwich(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) sandwich.GetN();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		int m = to - from;
		long[] a = new long[m];
		long sum = 0;
		long minPref = 0;
		long maxPref = 0;
		long minSegment = 0;
		for (int i = 0; i < m; i++) {
			a[i] = sandwich.GetTaste(from + i);
			sum += a[i];
			minPref = Math.min(minPref, sum);
			maxPref = Math.max(maxPref, sum);
			minSegment = Math.min(minSegment, sum - maxPref);
		}
		long sumSuf = 0;
		long minSuf = 0;
		for (int i = m - 1; i >= 0; i--) {
			sumSuf += a[i];
			minSuf = Math.min(minSuf, sumSuf);
		}
		long sumReceived = 0;
		long bestSegmentReceived = 0;
		long bestContinuingReceived = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			sumReceived = message.GetLL(ID - 1);
			bestSegmentReceived = message.GetLL(ID - 1);
			bestContinuingReceived = message.GetLL(ID - 1);
		}
		long sumSent = sumReceived + sum;
		long bestHere = Math.min(minSegment, bestContinuingReceived + minPref);
		long bestSegmentSent = Math.min(bestSegmentReceived, bestHere);
		long bestContinuingSent = Math.min(minSuf, bestContinuingReceived + sum);
		if (ID < NODES - 1) {
			message.PutLL(ID + 1, sumSent);
			message.PutLL(ID + 1, bestSegmentSent);
			message.PutLL(ID + 1, bestContinuingSent);
			message.Send(ID + 1);
			return null;
		}
		return sumSent - bestSegmentSent + "";
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new A().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}
