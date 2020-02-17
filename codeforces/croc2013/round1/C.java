package codeforces.croc2013.round1;
import java.util.*;

public class C {
	private static Scanner in;

	int n;
	boolean[] need;
	int[] list;
	int[] take;

	public void run() {
		n = in.nextInt();
		need = new boolean[10];
		list = new int[n];
		take = new int[10];
		for (int i = 0; i < n; i++) {
			int d = in.nextInt();
			need[d] = true;
			list[i] = d;
		}
		search(0, 0);
		System.out.println(ans.size());
		for (String s : ans) {
			System.out.println(s);
		}
	}

	final int[] p = new int[12];
	int m;
	final List<String> ans = new ArrayList<>();

	void search(int v, int len) {
		boolean okSet = true;
		for (int d = 0; d < 10; d++) {
			if ((take[d] > 0) != need[d]) {
				okSet = false;
				break;
			}
		}
		if (okSet) {
			for (int odd = 0; odd < 2; odd++) {
				m = 2 * len - odd;
				if (m < 4) {
					continue;
				}
				int u = v;
				for (int i = len - 1; i >= 0; i--) {
					p[i] = p[m - 1 - i] = u % 10;
					u /= 10;
				}
				ipSearch(0, 4);
			}
		}
		if (len < 6) {
			v *= 10;
			for (int d = 0; d < 10; d++) {
				take[d]++;
				search(v + d, len + 1);
				take[d]--;
			}
		}
	}

	final int[] ip = new int[4];

	void ipSearch(int i, int t) {
		if (t < 0) {
			return;
		}
		int j = m - i;
		if (j < t || j > 3 * t) {
			return;
		}
		if (j == 0) {
			StringBuilder sb = new StringBuilder();
			for (int k = 0; k < 4; k++) {
				if (k > 0) {
					sb.append(".");
				}
				sb.append(ip[k]);
			}
			ans.add(sb.toString());
			return;
		}
		int v = 0;
		for (int k = i; k < i + 3 && k < m; k++) {
			if (v == 0 && k > i) {
				break;
			}
			v = 10 * v + p[k];
			if (v >= 256) {
				break;
			}
			ip[4 - t] = v;
			ipSearch(k + 1, t - 1);
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}
