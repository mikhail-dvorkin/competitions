package topcoder;
import java.util.*;

public class CommonSubsequence {
	public String maxLex(String[] aa, String[] bb, int suffixLength) {
		StringBuilder sb = new StringBuilder();
		for (String aaa : aa)
			sb.append(aaa);
		String a = sb.toString();
		sb = new StringBuilder();
		for (String aaa : bb)
			sb.append(aaa);
		String b = sb.toString();
		int an = a.length();
		int bn = b.length();
		int[] ac = new int[128];
		int[] bc = new int[128];
		int[] al = new int[128];
		int[] bl = new int[128];
		int[] ans = new int[128];
		for (int i = 0; i < an; i++) {
			ac[a.charAt(i)]++;
		}
		for (int i = 0; i < bn; i++) {
			bc[b.charAt(i)]++;
		}
		int[] cnt = new int[128];
		int x, len;
		for (int c = 127; c >= 30; c--) {
			if (ac[c] == 0 && bc[c] == 0)
				continue;
//			System.out.println((char)c);
			Arrays.fill(cnt, 0);
			len = x = an;
			for (int i = 0; i < an; i++) {
				int cc = a.charAt(i);
				if (cc < c)
					continue;
				cnt[cc]++;
				if (cc > c && cnt[cc] > al[cc]) {
					len = x = i;
					cnt[cc]--;
					break;
				}
			}
			int ya = cnt[c];
			for (int i = an - 1; i >= 0; i--) {
				int ca = a.charAt(i);
				len++;
				cnt[ca]++;
				if (ca > c) {
					while (cnt[ca] > al[ca]) {
						x--;
						len--;
						if (x < 0)
							x = an - 1;
						cnt[a.charAt(x)]--;
					}
				}
				if (len > an) {
					x--;
					len--;
					if (x < 0)
						x = an - 1;
					cnt[a.charAt(x)]--;
				}
				ya = Math.max(ya, cnt[c]);
			}
			
			Arrays.fill(cnt, 0);
			len = x = bn;
			for (int i = 0; i < bn; i++) {
				int cc = b.charAt(i);
				if (cc < c)
					continue;
				cnt[cc]++;
				if (cc > c && cnt[cc] > bl[cc]) {
					len = x = i;
					cnt[cc]--;
					break;
				}
			}
			int yb = cnt[c];
			for (int i = bn - 1; i >= 0; i--) {
				int ca = b.charAt(i);
				len++;
				cnt[ca]++;
				if (ca > c) {
					while (cnt[ca] > bl[ca]) {
						x--;
						len--;
						if (x < 0)
							x = bn - 1;
						cnt[b.charAt(x)]--;
					}
				}
				if (len > bn) {
					x--;
					len--;
					if (x < 0)
						x = bn - 1;
					cnt[b.charAt(x)]--;
				}
				yb = Math.max(yb, cnt[c]);
			}
			
			ans[c] = Math.min(ya, yb);
			al[c] = ac[c] - ans[c];
			bl[c] = bc[c] - ans[c];
		}
		sb = new StringBuilder();
		for (int c = 127; c >= 30; c--) {
			for (int i = 0; i < ans[c]; i++)
				sb.append((char) c);
		}
		int l = sb.length();
		int start = Math.max(l - suffixLength, 0);
		return sb.substring(start);
	}
	
	public static void main(String[] args) {
		String a = new CommonSubsequence().maxLex(new String[]{"ichhbca", "aghafbbgbaehi"}, new String[]{"jdhccgeiaaijbddh"}, 10);
		System.out.println(a);
	}
}
