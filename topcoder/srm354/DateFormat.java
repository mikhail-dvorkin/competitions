package topcoder;
public class DateFormat {
	public String fromEuropeanToUs(String[] dateList) {
		int[] max = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		String d = "";
		for (String ss : dateList)
			d += ss;
		String[] s = d.split(" ");
		int n = s.length;
		int[] a = new int[n];
		int[] b = new int[n];
		boolean[] r = new boolean[n];
		for (int i = 0; i < n; i++) {
			String t = s[i].substring(0, 2);
			if (t.startsWith("0"))
				t = t.substring(1);
			a[i] = Integer.parseInt(t);
			t = s[i].substring(3, 5);
			if (t.startsWith("0"))
				t = t.substring(1);
			b[i] = Integer.parseInt(t);
			if (a[i] > b[i]) {
				int q = a[i]; a[i] = b[i]; b[i] = q;
			}
			r[i] = (b[i] <= 12 && a[i] <= max[b[i]]);
		}
		boolean[] p = new boolean[n];
		boolean[] pr = new boolean[n];
		p[n - 1] = true;
		pr[n - 1] = r[n - 1];
		for (int i = n - 2; i >= 0; i--) {
			if (p[i + 1] && ok(a[i], b[i], a[i + 1], b[i + 1])) {
				p[i] = true;
			}
			if (pr[i + 1] && ok(a[i], b[i], b[i + 1], a[i + 1])) {
				p[i] = true;
			}
			if (r[i]) {
				if (p[i + 1] && ok(b[i], a[i], a[i + 1], b[i + 1])) {
					pr[i] = true;
				}
				if (pr[i + 1] && ok(b[i], a[i], b[i + 1], a[i + 1])) {
					pr[i] = true;
				}
			}
		}
		if (!p[0])
			return "";
		d = us(a[0], b[0]);
		int aa = a[0];
		int bb = b[0];
		for (int i = 1; i < n; i++) {
			if (p[i] && ok(aa, bb, a[i], b[i])) {
				d += " " + us(a[i], b[i]);
				aa = a[i];
				bb = b[i];
			} else {
				d += " " + us(b[i], a[i]);
				aa = b[i];
				bb = a[i];
			}
		}
		return d;
	}

	private String us(int i, int j) {
		return xx(i) + "/" + xx(j);
	}

	private String xx(int j) {
		return (j / 10) + "" + (j % 10);
	}

	private boolean ok(int i, int j, int k, int l) {
		return (i < k) || (i == k && j < l);
	}
}
