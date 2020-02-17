package dgcj.y2016.round2;

import dgcj.message;

public class D_small {
	static final Object PROBLEM = new gas_stations(); // PROBLEM NAME goes here
	
	public String run() {
		if (ID > 0) {
			return null;
		}
		
		int n = (int) gas_stations.GetNumKms();
		int tank = (int) gas_stations.GetTankSize();
		
		int[] c = new int[n];
		for (int i = 0; i < n; i++) {
			c[i] = (int) gas_stations.GetGasPrice(i);
		}
		
		int stack = 0;
		int[] index = new int[n];
		int[] amount = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			int buy = 1;
			while (buy < tank) {
				if (stack == 0 || c[i] > c[index[stack - 1]]) {
					break;
				}
				int swap = Math.min(amount[stack - 1], tank - buy);
				buy += swap;
				amount[stack - 1] -= swap;
				if (amount[stack - 1] == 0) {
					stack--;
				}
			}
			index[stack] = i;
			amount[stack] = buy;
			stack++;
		}
		
		long ans = 0;
		for (int i = 0; i < stack; i++) {
			ans += 1L * c[index[i]] * amount[i];
		}

		return "" + ans;
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
