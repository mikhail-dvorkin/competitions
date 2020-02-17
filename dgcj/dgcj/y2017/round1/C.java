package dgcj.y2017.round1;

import dgcj.message;

public class C {
	static final Object PROBLEM = new todd_and_steven(); // PROBLEM NAME goes here
	static final int M = 1000000007;

	long nEven, nOdd, max;
	
	public String run() {
		nEven = todd_and_steven.GetStevenLength();
		nOdd = todd_and_steven.GetToddLength();
		max = Math.max(todd_and_steven.GetStevenValue(nEven - 1),
				todd_and_steven.GetToddValue(nOdd - 1));
		long len = nEven + nOdd;
		long fromPos = len * ID / NODES;
		long toPos = len * (ID + 1) / NODES;
		long from = kth(fromPos);
		long to = kth(toPos);
		
		long posEven, posOdd, toEven, toOdd;
		posEven = index(from, false);
		posOdd = index(from, true);
		toEven = index(to, false);
		toOdd = index(to, true);
		long valEven = posEven < toEven ? todd_and_steven.GetStevenValue(posEven) : -1;
		long valOdd = posOdd < toOdd ? todd_and_steven.GetToddValue(posOdd) : -1;
		int sum = 0;
		for (long j = fromPos; j < toPos; j++) {
			boolean takeOdd;
			if (valEven == -1) {
				takeOdd = true;
			} else if (valOdd == -1) {
				takeOdd = false;
			} else {
				takeOdd = valEven > valOdd;
			}
			long val;
			if (takeOdd) {
				val = valOdd;
				posOdd++;
				valOdd = posOdd < toOdd ? todd_and_steven.GetToddValue(posOdd) : -1;
			} else {
				val = valEven;
				posEven++;
				valEven = posEven < toEven ? todd_and_steven.GetStevenValue(posEven) : -1;
			}
			sum = (int) ((sum + (val ^ j)) % M);
		}
		
		if (ID > 0) {
			message.PutInt(0, sum);
			message.Send(0);
			return null;
		}
		for (int i = 1; i < NODES; i++) {
			message.Receive(i);
			sum += message.GetInt(i);
			sum %= M;
		}
		return sum + "";
	}
	
	long kth(long k) {
		long low = 0;
		long high = max + 2;
		while (low + 1 < high) {
			long mid = (low + high) / 2;
			if (index(mid, false) + index(mid, true) <= k) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return low;
	}
	
	long index(long v, boolean odd) {
		long low = -1;
		long high = odd ? nOdd : nEven;
		while (low + 1 < high) {
			long mid = (low + high) / 2;
			long x = odd ? todd_and_steven.GetToddValue(mid) : todd_and_steven.GetStevenValue(mid);
			if (x < v) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return high;
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new C().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}
