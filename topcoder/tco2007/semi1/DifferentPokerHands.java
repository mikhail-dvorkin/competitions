package topcoder;
import java.util.*;

public class DifferentPokerHands {
	class Five implements Comparable<Five> {
		int[] a;
		int type = -1;
		boolean flush, straight, s2;
		
		public Five(int[] array) {
			a = array;
			Arrays.sort(a);
			
			flush = true;
			for (int i = 1; i < 5; i++) {
				if (a[i] % 4 != a[0] % 4)
					flush = false;
			}
			
			straight = true;
			for (int i = 0; i < 4; i++) {
				if (a[i] / 4 + 1 != a[i + 1] / 4)
					straight = false;
			}
			
			s2 = true;
			for (int i = 0; i < 3; i++) {
				if (a[i] / 4 + 1 != a[i + 1] / 4)
					s2 = false;
			}
			s2 &= (a[0] < 4) && (a[4] >= 48);
			
			if (s2) {
				type = 4;
				if (flush)
					type = 8;
				a[4] = -566;
				return;
			}
			
			if (straight && flush) {
				if (a[4] >= 48)
					type = 9;
				type = 8;
				return;
			}
			
			if (straight) {
				type = 4;
				return;
			}
			
			if (flush) {
				type = 5;
				return;
			}
			
			int[] am = new int[13];
			for (int i = 0; i < 5; i++) {
				am[a[i] / 4]++;
			}
			int[] sort = am.clone();
			Arrays.sort(sort);
			
			if (sort[12] == 4) {
				type = 7;
			} else if (sort[12] == 3 && sort[11] == 2) {
				type = 6;
			} else if (sort[12] == 3) {
				type = 3;
			} else if (sort[12] == 2 && sort[11] == 2) {
				type = 2;
			} else if (sort[12] == 2) {
				type = 1;
			} else {
				type = 0;
			}
			for (int j = 0; j < 5; j++) {
				for (int i = 0; i < 4; i++) {
					if ((am[a[i] / 4] > am[a[i + 1] / 4]) || ((am[a[i] / 4] == am[a[i + 1] / 4]) && (a[i] > a[i + 1]))) {
						int t = a[i];
						a[i] = a[i + 1];
						a[i + 1] = t;
					}
				}
			}
		}

		@Override
		public int compareTo(Five that) {
			if (that == null)
				return 1;
			if (type != that.type)
				return type - that.type;
			for (int i = 4; i >= 0; i--) {
				if (a[i] / 4 != that.a[i] / 4)
					return a[i] / 4 - that.a[i] / 4;
			}
			return 0;
		}
	}
	
	public int noDifferentHands(String[] communityCards) {
		int[] s = new int[7];
		for (int i = 0; i < 5; i++) {
			s[i] = toInt(communityCards[i]);
		}
		TreeSet<Five> ts = new TreeSet<Five>();
		for (int i = 0; i < 52; i++) {
			for (int j = 0; j < 52; j++) {
				s[5] = i;
				s[6] = j;
				Five f = getFive(s.clone());
				if (f == null)
					continue;
				ts.add(f);
			}
		}
		return ts.size();
	}

	private Five getFive(int[] s) {
		int[] t = new int[5];
		Five best = null;
		for (int a = 0; a < 7; a++) {
			for (int b = a + 1; b < 7; b++) {
				if (s[a] == s[b])
					return null;
				int j = 0;
				for (int i = 0; i < 7; i++) {
					if (i != a && i != b) {
						t[j++] = s[i];
					}
				}
				Five f = new Five(t.clone());
				if (f.compareTo(best) > 0)
					best = f;
			}
		}
		return best;
	}

	private int toInt(String s) {
		int rank = "23456789TJQKA".indexOf(s.charAt(0));
		int suit = "HDCS".indexOf(s.charAt(1));
		return rank * 4 + suit;
	}
	
	public static void main(String[] args) {
		int a = new DifferentPokerHands().noDifferentHands(new String[]
//{"5S", "6S", "7S", "8S", "9S"}
//{"AS", "AD", "KH", "KC", "QS"}
{"AH", "8S", "6S", "4S", "AS"}
		);
		System.out.println(a);
	}
}
