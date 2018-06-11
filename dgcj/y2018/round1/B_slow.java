package dgcj.y2018.round1;

import dgcj.message;

public class B_slow {
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
		
		int low = 0;
		int high = hei * wid + 1;
		while (low + 1 < high) {
			int max = (low + high) / 2;
			
			int count = 0;
			for (int i = 0; i < len; i++) {
				for (int j = 0; j < hei; j++) {
					if (a[i][j] >= max) {
						break;
					}
					count++;
				}
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
	
	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new B_slow().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}
