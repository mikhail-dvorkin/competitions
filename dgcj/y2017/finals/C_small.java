package dgcj.y2017.finals;

import dgcj.message;

public class C_small {
	static final Object PROBLEM = new median(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) median.GetN();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		
		int low = 1;
		int high = (int) (1e9 + 1);
		int bins = 1000;
		int desired = n / 2;
		while (low + 1 < high) {
			int binSize = (high - low) / bins;
			int[] size = new int[bins];
			for (int i = from; i < to; i++) {
				int v = median.GetData(i);
				if (v < low || v >= high) {
					continue;
				}
				int bin = (v - low) / binSize;
				size[bin]++;
			}
			
			if (ID > 0) {
				for (int i = 0; i < bins; i++) {
					message.PutInt(0, size[i]);
				}
				message.Send(0);
				
				message.Receive(ID - 1);
				low = message.GetInt(ID - 1);
			} else {
				for (int id = 1; id < NODES; id++) {
					message.Receive(id);
					for (int i = 0; i < bins; i++) {
						size[i] += message.GetInt(id);
					}
				}
				int i = 0;
				while (desired >= size[i]) {
					desired -= size[i];
					i++;
				}
				low += i * binSize;
			}
			
			high = low + binSize;
			if (ID + 1 < NODES) {
				message.PutInt(ID + 1, low);
				message.Send(ID + 1);
			}
		}
		
		if (ID == 0) {
			return "" + low;
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
		String ans = new C_small().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}
