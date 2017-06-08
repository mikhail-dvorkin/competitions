package dgcj.y2016.round2;

import dgcj.message;
import java.util.*;

public class C {
	static final Object PROBLEM = new asteroids(); // PROBLEM NAME goes here
	
	public String run() {
		int hei = (int) asteroids.GetHeight();
		int wid = (int) asteroids.GetWidth();
		
		int s = (Math.max(hei, wid) + NODES - 1) / NODES;
		int size = NODES * s;
		int jFrom = ID * s;
		int jTo = (ID + 1) * s;
		int jLow = Math.max(ID - 1, 0) * s;
		int jHigh = Math.min(ID + 2, NODES) * s;
		int[] a = new int[size];
		int[] b = new int[size];
		char[] c = new char[size];
		Arrays.fill(a, -1);
		Arrays.fill(a, jFrom, jTo, 0);
		
		for (int i = 0; i < hei; i++) {
			for (int j = jLow; j < jHigh; j++) {
				c[j] = (j >= wid) ? '#' : ((i >= hei) ? '0' : asteroids.GetPosition(i, j));
			}
			Arrays.fill(b, jLow, jHigh, -1);
			for (int j = jLow; j < jHigh; j++) {
				if (a[j] == -1 || c[j] == '#') {
					continue;
				}
				for (int jj = j - 1; jj <= j + 1; jj++) {
					if (jj < jLow || jj >= jHigh || c[jj] == '#') {
						continue;
					}
					int plus = c[j] - '0';
					if (jj != j) {
						plus += c[jj] - '0';
					}
					b[jj] = Math.max(b[jj], a[j] + plus);
				}
			}
			int[] t = a; a = b; b = t;
			
			if (i % s > 0 && i != hei - 1) {
				continue;
			}
			
			for (int d = -1; d <= 1; d += 2) {
				int J = ID + d;
				if (J < 0 || J >= NODES) {
					continue;
				}
				int kFrom = J * s;
				int kTo = (J + 1) * s;
				for (int j = kFrom; j < kTo; j++) {
					message.PutInt(J, a[j]);
				}
				message.Send(J);
			}
			
			for (int d = 1; d >= -1; d -= 2) {
				int J = ID + d;
				if (J < 0 || J >= NODES) {
					continue;
				}
				message.Receive(J);
				for (int j = jFrom; j < jTo; j++) {
					a[j] = Math.max(a[j], message.GetInt(J));
				}
			}
		}

		int ans = -1;
		for (int j = jFrom; j < jTo; j++) {
			ans = Math.max(ans, a[j]);
		}
		
		if (ID > 0) {
			message.PutInt(0, ans);
			message.Send(0);
			return null;
		}
		
		for (int J = 1; J < NODES; J++) {
			message.Receive(J);
			ans = Math.max(ans, message.GetInt(J));
		}
		
		return "" + ans;
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
