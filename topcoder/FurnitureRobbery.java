package topcoder;
import java.util.*;

public class FurnitureRobbery {
	int hei, wid;
	boolean[] is = new boolean[26];
	HashMap<String, Integer> len = new HashMap<String, Integer>();
	ArrayList<String> que = new ArrayList<String>();
	int lo = 0;
	final int[] dx = new int[]{1, 0, -1, 0};
	final int[] dy = new int[]{0, 1, 0, -1};
	
	public int leastPushes(String[] plan) {
		hei = plan.length;
		wid = plan[0].length();
		int[] x = new int[26];
		int[] y = new int[26];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (plan[i].charAt(j) == '.')
					continue;
				int f = plan[i].charAt(j) - 'A';
				if (!is[f]) {
					x[f] = i;
					y[f] = j;
				}
				is[f] = true;
			}
		}
		String p = "";
		for (String s : plan)
			p += s;
		que.add(p);
		len.put(p, 0);
		int hw = hei * wid;
		StringBuilder sb = new StringBuilder(hw);
		for (int i = 0; i < hw; i++) {
			sb.append('.');
		}
		if (p.substring(0, wid).contains("A"))
			return 0;
		while (lo < que.size()) {
			p = que.get(lo++);
			System.out.println(p);
			int lengthtoq = len.get(p) + 1;
			for (int c = 0; c < 26; c++) if (is[c]) {
				char cc = (char) ('A' + c);
				dirloop:
				for (int dir = 0; dir < 4; dir++) {
					int k = 0;
					for (int i = 0; i < hw; i++) {
						sb.setCharAt(i, '.');
					}
					for (int i = 0; i < hei; i++) {
						for (int j = 0; j < wid; j++) {
							if (p.charAt(k) == cc) {
								int di = i + dx[dir];
								int dj = j + dy[dir];
								if (di < 0 || dj < 0 || di >= hei || dj >= wid) {
									continue dirloop;
								}
								int k1 = di * wid + dj;
								if (sb.charAt(k1) != '.')
									continue dirloop;
								sb.setCharAt(k1, cc);
							} else if (p.charAt(k) != '.') {
								if (sb.charAt(k) != '.')
									continue dirloop;
								sb.setCharAt(k, p.charAt(k));
							}
							k++;
						}
					}
					// move is ok
					String q = sb.toString();
					if (len.containsKey(q))
						continue dirloop;
					if (q.substring(0, wid).contains("A"))
						return lengthtoq;
					len.put(q, lengthtoq);
					que.add(q);
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int a = new FurnitureRobbery().leastPushes(new String[] {
				"BBCCDE",
					"FFGGDE",
					"....HH",
					"XX.YY.",
					"AAANN."
//				"......",
//				 ".BBB.X",
//				 ".B.B.X",
//				 "DDCC.Y",
//				 "..AAAY"	
		});
		System.out.println(a);
	}
}
