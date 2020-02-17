package dgcj.y2017.finals;

import java.util.*;
import dgcj.message;

public class B {
	static final Object PROBLEM = new lemming(); // PROBLEM NAME goes here

	public String run() {
		int hei = (int) lemming.GetRows();
		int wid = (int) lemming.GetColumns();
		int fromX = hei * (ID / N) / N;
		int toX = hei * (ID / N + 1) / N;
		int lenX = toX - fromX;
		int fromY = wid * (ID % N) / N;
		int toY = wid * (ID % N + 1) / N;
		int lenY = toY - fromY;
		char[][] f = new char[lenX][lenY];
		for (int x = 0; x < lenX; x++) {
			for (int y = 0; y < lenY; y++) {
				f[x][y] = lemming.GetDirection(fromX + x, fromY + y);
			}
		}
		int[][] mark = new int[lenX][lenY];
		int loop = 0;
		int ans = 0;
		int[] loopEnd = new int[lenX * lenY + 1];
		for (int xx = 0; xx < lenX; xx++) {
			for (int yy = 0; yy < lenY; yy++) {
				if (mark[xx][yy] > 0) {
					continue;
				}
				int x = xx;
				int y = yy;
				loop++;
				for (;;) {
					mark[x][y] = loop;
					switch (f[x][y]) {
					case '^': x--; break;
					case 'v': x++; break;
					case '<': y--; break;
					case '>': y++; break;
					}
					if (x < 0 || x >= lenX || y < 0 || y >= lenY) {
						x += fromX;
						y += fromY;
						if (x < 0 || x >= hei || y < 0 || y >= wid) {
							ans++;
							loopEnd[loop] = -1;
						} else {
							loopEnd[loop] = x * wid + y;
						}
						break;
					}
					int m = mark[x][y];
					if (m > 0) {
						if (m == loop) {
							ans++;
							loopEnd[loop] = -2;
						} else {
							loopEnd[loop] = loopEnd[m];
						}
						break;
					}
				}
			}
		}
		
		ArrayList<Integer> msg = new ArrayList<>();
		for (int x = 0; x < lenX; x++) {
			for (int y = 0; y < lenY; y++) {
				if (x != 0 && x != lenX - 1 && y != 0 && y != lenY - 1) {
					continue;
				}
				int end = loopEnd[mark[x][y]];
				if (end >= 0) {
					msg.add((x + fromX) * wid + (y + fromY));
					msg.add(end);
				}
			}
		}
		
		message.PutInt(0, ans);
		message.PutInt(0, msg.size());
		for (int v : msg) {
			message.PutInt(0, v);
		}
		message.Send(0);
		if (ID > 0) {
			return null;
		}
		
		ans = 0;
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int id = 0; id < NODES; id++) {
			message.Receive(id);
			ans += message.GetInt(id);
			int msgSize = message.GetInt(id);
			for (int i = 0; i < msgSize; i += 2) {
				int a = message.GetInt(id);
				int b = message.GetInt(id);
				map.put(a, b);
			}
		}
		
		HashMap<Integer, Integer> were = new HashMap<>();
		loop = 0;
		for (int x : map.keySet()) {
			if (were.containsKey(x)) {
				continue;
			}
			loop++;
			for (;;) {
				Integer y = map.get(x);
				if (y == null) {
					break;
				}
				x = y;
				Integer m = were.get(x);
				if (m == null) {
					were.put(x, loop);
					continue;
				}
				if (m == loop) {
					ans++;
				}
				break;
			}
		}
		
		return "" + ans;
	}

	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int N = (int) Math.floor(Math.sqrt(NODES));
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new B().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}
