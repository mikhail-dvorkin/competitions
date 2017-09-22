package topcoder;
import java.util.*;

public class GuitarConcert {
	public String[] buyGuitars(String[] guitarNames, String[] guitarSongs) {
		int s = guitarSongs[0].length();
		int n = guitarNames.length;
		int best = -1;
		ArrayList<String> bst = new ArrayList<String>();
		for (int m = 0; m < (1 << n); m++) {
			boolean[] y = new boolean[s];
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < n; i++) if ((m & (1 << i)) > 0) {
				list.add(guitarNames[i]);
				for (int j = 0; j < s; j++) {
					y[j] = y[j] || guitarSongs[i].charAt(j) == 'Y';
				}
			}
			Collections.sort(list);
			int cur = 0;
			for (int j = 0; j < s; j++)
				if (y[j])
					cur++;
			if (cur > best || (cur == best && better(list, bst))) {
				best = cur;
				bst = list;
			}
		}
		return bst.toArray(new String[bst.size()]);
	}

	private boolean better(ArrayList<String> a, ArrayList<String> b) {
		if (a.size() != b.size())
			return a.size() < b.size();
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).equals(b.get(i)))
				return a.get(i).compareTo(b.get(i)) < 0;
		}
		return false;
	}
}
