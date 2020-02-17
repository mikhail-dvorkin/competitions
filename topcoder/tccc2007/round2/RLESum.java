package topcoder;
import java.util.*;

public class RLESum {
	long infty = 1000000000000000000L;
	
	class Number {
		int n;
		long[] len;
		int[] dig;
		
		public Number(String a) {
			ArrayList<Long> leng = new ArrayList<Long>();
			ArrayList<Integer> digi = new ArrayList<Integer>();
			leng.add(infty);
			digi.add(0);
			int l = a.length();
			int i = 0;
			while (i < l) {
				if (a.charAt(i) == '[') {
					StringBuilder b = new StringBuilder();
					while (true) {
						i++;
						if (a.charAt(i) == ']')
							break;
						b.append(a.charAt(i));
					}
					leng.add(Long.parseLong(b.toString()));
					i++;
					digi.add(a.charAt(i) - '0');
				} else {
					leng.add(1L);
					digi.add(a.charAt(i) - '0');
				}
				i++;
			}
			n = leng.size();
			len = new long[n];
			dig = new int[n];
			for (i = 0; i < n; i++) {
				len[i] = leng.get(n - 1 - i);
				dig[i] = digi.get(n - 1 - i);
			}
		}
	}
	
	public int[] getDigits(String a, String b, String[] k) {
		Number aa = new Number(a);
		Number bb = new Number(b);
		ArrayList<Long> leng = new ArrayList<Long>();
		ArrayList<Integer> digi = new ArrayList<Integer>();
		int pa = 0;
		long ta = aa.len[0];
		int da = aa.dig[0];
		int pb = 0;
		long tb = bb.len[0];
		int db = bb.dig[0];
		int per = 0;
		int digc = 0;
		while (true) {
			long work = Math.min(ta, tb);
			for (int i = 0; i < 5 && i < work; i++) {
				int sum = per + da + db;
				leng.add(1L);
				digi.add(sum % 10);
				digc = sum % 10;
				per = sum / 10;
			}
			if (pa == aa.n - 1 && pb == bb.n - 1)
				break;
			if (work > 5) {
				leng.add(work - 5);
				digi.add(digc);
			}
			ta -= work;
			tb -= work;
			if (ta == 0) {
				pa++;
				ta = aa.len[pa];
				da = aa.dig[pa];
			}
			if (tb == 0) {
				pb++;
				tb = bb.len[pb];
				db = bb.dig[pb];
			}
		}
		leng.add(infty);
		digi.add(0);
		int[] ans = new int[k.length];
		iloop:
		for (int i = 0; i < k.length; i++) {
			long ind = Long.parseLong(k[i]);
			int p = 0;
			while (true) {
				if (ind < leng.get(p)) {
					ans[i] = digi.get(p);
					continue iloop;
				}
				ind -= leng.get(p);
				p++;
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		new RLESum().getDigits(
				"[12]3", "[3]1[3]2[3]3",
				new String[]{"12", "11", "10", "9", "8","7","6","5","4","3","2","1","0"});
	}
}

//"2[999999999999997]222[1000000000000000]8"
//"[1000000000000000]2[1000000000000000]8"
//{"1000000000000002","1000000000000001","1000000000000000","999999999999999","999999999999998","3","2","1","0"}