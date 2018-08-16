package dgcj.y2018.round1;

import java.util.*;
import dgcj.message;

public class B {
	static final Object PROBLEM = new towels(); // PROBLEM NAME goes here
	
	public String run() {
		int wid = (int) towels.GetNumberOfStacks();
		int hei = (int) towels.GetNumberOfTowelsInStack();
		int m = (int) towels.GetNumberOfGymUsersBeforeYou() + 1;
		int from = (int) (1L * wid * ID / NODES);
		int to = (int) (1L * wid * (ID + 1) / NODES);
		int len = to - from;
		int sumMax = -1;
		int[][] a = new int[len][hei];
		for (int i = from; i < to; i++) {
			for (int j = 0; j < hei; j++) {
				a[i - from][hei - 1 - j] = (int) towels.GetTowelCleanlinessRank(i, j);
			}
		}

		int[][] vals = new int[len][];
		int[][] inds = new int[len][];
		int[] val = new int[hei + 1];
		int[] index = new int[hei + 1];
		for (int i = 0; i < len; i++) {
			int prev = -1;
			int k = 0;
			for (int j = 0; j < hei; j++) {
				int x = a[i][j];
				if (x <= prev) {
					continue;
				}
				val[k] = x;
				index[k] = j;
				k++;
				prev = x;
			}
			val[k] = hei * wid + 1;
			index[k] = hei;
			k++;
			vals[i] = Arrays.copyOf(val, k);
			inds[i] = Arrays.copyOf(index, k);
		}
		
		int low = 0;
		int high = hei * wid + 1;
		while (low + 1 < high) {
			int max = (low + high) / 2;
			
			int count = 0;
			for (int i = 0; i < len; i++) {
				int x = Arrays.binarySearch(vals[i], max);
				if (x < 0) {
					x = -1 - x;
				}
				count += inds[i][x];
			}
			
			int sum = 1;
			if (ID > 0) {
				message.Receive(ID - 1);
				sum = message.GetInt(ID - 1);
			}
			sum += count;
			if (ID + 1 < NODES) {
				message.PutInt(ID + 1, sum);
				message.Send(ID + 1);
			}
			
			if (ID + 1 < NODES) {
				message.Receive(ID + 1);
				sum = message.GetInt(ID + 1);
			}
			if (ID > 0) {
				message.PutInt(ID - 1, sum);
				message.Send(ID - 1);
			}
			
			if (sum <= m) {
				low = max;
				sumMax = sum;
			} else {
				high = max;
			}
		}
		
		int max = low;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < hei; j++) {
				if (a[i][j] == max) {
					return "" + (a[i][j + m - sumMax]);
				}
			}
		}
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
		String ans = new B().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public static void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}
