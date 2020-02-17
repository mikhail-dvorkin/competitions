package topcoder.srm426;
import java.util.ArrayList;
import java.util.HashMap;


public class LongStraightRoad {
	class Sign {
		ArrayList<Integer> place = new ArrayList<Integer>();
		ArrayList<Integer> dist = new ArrayList<Integer>();
	}

	int n;
	boolean[] mark;
	Sign[] all;

	public int distanceToDestination(String[] signs, String destination) {
		n = signs.length;
		mark = new boolean[n];
		HashMap<String, Integer> ids = new HashMap<String, Integer>();
		ids.put(destination, 0);
		all = new Sign[n];
		for (int i = 0; i < n; i++) {
			all[i] = new Sign();
			String[] ss = signs[i].split(";");
			for (String s2 : ss) {
				String[] s = s2.split(" ");
				if (!ids.containsKey(s[0])) {
					ids.put(s[0], ids.size());
				}
				int id = ids.get(s[0]);
				int dist = Integer.parseInt(s[1]);
				all[i].place.add(id);
				all[i].dist.add(dist);
			}
		}
		for (int i = 0; i < n; i++) {
			if (!mark[i]) {
				dfs(i);
			}
		}
		return -1;
	}

	private void dfs(int v) {
		if (mark[v])
			return;
		mark[v] = true;
		for (int u = 0; u < n; u++) {
			if (v == u)
				continue;
			boolean edge = false;
			for (int pl: all[u].place) {
				if (all[v].place.contains(pl)) {
					edge = true;
					break;
				}
			}
			if (edge) {
				dfs(u);
			}
		}
	}
}
