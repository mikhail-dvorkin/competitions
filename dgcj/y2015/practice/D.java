package dgcj.y2015.practice;

import dgcj.message;
import java.util.*;

public class D {
	static final Object PROBLEM = new load_balance(); // PROBLEM NAME goes here
	static final String IMPOSSIBLE = "IMPOSSIBLE";
	static final String POSSIBLE = "POSSIBLE";

	public String run() {
		int n = load_balance.GetN();
		long[] a = new long[n];
		long sum = 0;
		for (int i = 0; i < n; i++) {
			a[i] = load_balance.GetWeight(i);
			sum += a[i];
		}
		if (sum % 2 != 0) {
			return (ID == 0) ? IMPOSSIBLE : null;
		}
		long s = sum / 2;
		int p = 1;
		while (2 << p <= NODES) {
			p++;
		}
		p = Math.min(p, n);
		long need = s;
		int m1 = (n - p) / 2;
		int m2 = n - p - m1;
		long[] a1 = new long[m1];
		long[] a2 = new long[m2];
		for (int i = 0; i < n; i++) {
			if (i < p) {
				if ((ID & (1 << i)) != 0) {
					need -= a[i];
				}
			} else if (i < p + m1) {
				a1[i - p] = a[i];
			} else {
				a2[i - p - m1] = a[i];
			}
 		}
		long[] b1 = all(a1);
		long[] b2 = all(a2);
		int j = b2.length - 1;
		boolean found = false;
		loop:
		for (long b : b1) {
			long want = need - b;
			while (b2[j] > want) {
				j--;
				if (j < 0) {
					break loop;
				}
			}
			if (b2[j] == want) {
				found = true;
				break;
			}
		}
		if (ID > 0) {
			message.PutInt(0, found ? 1 : 0);
			message.Send(0);
			return null;
		}
		for (int i = 1; i < NODES; i++) {
			message.Receive(i);
			boolean f = message.GetInt(i) == 1;
			found |= f;
		}
		return found ? POSSIBLE : IMPOSSIBLE;
	}
	
	static long[] all(long[] a) {
		int n = a.length;
		int masks = 1 << n;
		long[] b = new long[masks];
		long sum = 0;
		long mask = 0;
		for (int i = 0;;) {
			b[i] = sum;
			i++;
			if (i == masks) {
				break;
			}
			int j = 1;
			int k = 0;
			while ((i & j) == 0) {
				k++;
				j <<= 1;
			}
			mask ^= j;
			if ((mask & j) != 0) {
				sum += a[k];
			} else {
				sum -= a[k];
			}
		}
		Arrays.sort(b);
		return b;
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new D().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}
