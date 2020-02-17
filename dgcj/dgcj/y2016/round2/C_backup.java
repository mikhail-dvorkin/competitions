package dgcj.y2016.round2;

import dgcj.message;
import java.util.*;

public class C_backup {
	static final Object PROBLEM = new asteroids(); // PROBLEM NAME goes here
	
	public String run() {
		int hei0 = (int) asteroids.GetHeight();
		int wid0 = (int) asteroids.GetWidth();
		int size = Math.max(hei0, wid0);
		size = Math.max(size, 1000);
		int hei = size;
		int wid = size;
		int M = Math.min(NODES_ALL, wid);
		if (ID >= M) {
			return null;
		}
		int N = Math.min(NODES_ALL, hei) * 3 / 2;
		int INF = -Integer.MAX_VALUE / 4;
		
		int jFrom = wid * ID / M;
		int jTo = wid * (ID + 1) / M;
		int[] a = new int[wid];
		int[] b = new int[wid];
		char[] c = new char[wid];
		Arrays.fill(a, INF);
		Arrays.fill(a, jFrom, jTo, 0);
		for (int y = 0; y < N; y++) {
			int iFrom = hei * y / N;
			int iTo = hei * (y + 1) / N;
			int iNum = iTo - iFrom;
			
			int jMin = Math.max(jFrom - iNum - 2, 0);
			int jMax = Math.min(jTo + iNum + 2, wid);
			for (int i = iFrom; i < iTo; i++) {
				for (int j = jMin; j < jMax; j++) {
					if (j >= wid0) {
						c[j] = '#';
					} else if (i >= hei0) {
						c[j] = '0';
					} else {
						c[j] = asteroids.GetPosition(i, j);
					}
				}
				Arrays.fill(b, jMin, jMax, INF);
				for (int j = jMin; j < jMax; j++) {
					if (a[j] == INF || c[j] == '#') {
						continue;
					}
					for (int jj = j - 1; jj <= j + 1; jj++) {
						if (jj < jMin || jj >= jMax || c[jj] == '#') {
							continue;
						}
						int win = c[j] - '0';
						if (jj != j) {
							win += c[jj] - '0';
						}
						b[jj] = Math.max(b[jj], a[j] + win);
					}
				}
				int[] t = a; a = b; b = t;
			}

			for (int d = -1; d <= 1; d += 2) {
				int J = ID + d;
				if (J < 0 || J >= M) {
					continue;
				}
				int kFrom = wid * J / M;
				int kTo = wid * (J + 1) / M;
				for (int j = kFrom; j < kTo; j++) {
					int v;
					if (j < jMin || j >= jMax) {
						v = INF;
					} else {
						v = a[j];
					}
					message.PutInt(J, v);
				}
				message.Send(J);
			}
			
			for (int d = 1; d >= -1; d -= 2) {
				int J = ID + d;
				if (J < 0 || J >= M) {
					continue;
				}
				message.Receive(J);
				for (int j = jFrom; j < jTo; j++) {
					a[j] = Math.max(a[j], message.GetInt(J));
				}
			}
		}

		int ans = INF;
		for (int j = jFrom; j < jTo; j++) {
			ans = Math.max(ans, a[j]);
		}
		
		if (ID > 0) {
			message.PutInt(0, ans);
			message.Send(0);
			return null;
		}
		
		for (int J = 1; J < M; J++) {
			message.Receive(J);
			ans = Math.max(ans, message.GetInt(J));
		}
		
		if (ans == INF) {
			return "-1";
		}
		return "" + ans;
	}

	final int NODES_ALL = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new C_backup().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}
