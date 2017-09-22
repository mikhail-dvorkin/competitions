package topcoder;
import java.util.*;

public class Paintball {
	HashMap<String, Integer> teamscore = new HashMap<String, Integer>();
	HashMap<String, Integer> score = new HashMap<String, Integer>();
	ArrayList<String> guys = new ArrayList<String>();
	
	public String[] getLeaderboard(String[] players, String[] messages) {
		HashMap<String, String> p2t = new HashMap<String, String>();
		HashSet<String> teams = new HashSet<String>();
		HashSet<String> pls = new HashSet<String>();
		for (String s : players) {
			String[] ss = s.split(" ");
			p2t.put(ss[0], ss[1]);
			score.put(ss[0], 0);
			teams.add(ss[1]);
			teamscore.put(ss[1], 0);
			pls.add(ss[0]);
		}
		for (String s : messages) {
			String[] ss = s.split(" ");
			String ta = p2t.get(ss[0]);
			String tb = p2t.get(ss[2]);
			int pa = 1;
			int pb = -1;
			if (ta.equals(tb)) {
				pa = -1;
				pb = 0;
			}
			int sa = score.get(ss[0]);
			score.put(ss[0], sa + pa);
			int sb = score.get(ss[2]);
			score.put(ss[2], sb + pb);
		}
		for (String s : pls) {
			String t = p2t.get(s);
			int ts = teamscore.get(t);
			ts += score.get(s);
			teamscore.put(t, ts);
		}
		ArrayList<String> ts = new ArrayList<String>(teams);
		Collections.sort(ts, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				int sa = teamscore.get(a);
				int sb = teamscore.get(b);
				if (sa == sb)
					return a.compareTo(b);
				return sb - sa;
			}
		});
		ArrayList<String> ans = new ArrayList<String>();
		for (String team : ts) {
			ans.add(team + " " + teamscore.get(team));
			guys.clear();
			for (String s : pls)
				if (p2t.get(s).equals(team))
					guys.add(s);
			Collections.sort(guys, new Comparator<String>() {
				@Override
				public int compare(String a, String b) {
					int sa = score.get(a);
					int sb = score.get(b);
					if (sa == sb)
						return a.compareTo(b);
					return sb - sa;
				}
			});
			for (String s : guys) {
				ans.add("  " + s + " " + score.get(s));
			}
		}
		String[] answer = new String[ans.size()];
		for (int i = 0; i < answer.length; i++)
			answer[i] = ans.get(i);
		return answer;
	}
}
