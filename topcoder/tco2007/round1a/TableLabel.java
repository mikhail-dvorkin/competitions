package topcoder.tco2007.round1a;
import java.util.*;

public class TableLabel {
	public String[] labels(String[] table) {
		int hei = table.length;
		int wid = 0;
		String[][] a = new String[hei][];
		for (int i = 0; i < hei; i++) {
			Scanner in = new Scanner(table[i]);
			ArrayList<String> al = new ArrayList<String>();
			while (in.hasNext()) {
				al.add(in.next().trim());
			}
			in.close();
			wid = al.size();
			a[i] = new String[wid];
			for (int j = 0; j < wid; j++) {
				a[i][j] = al.get(j);
			}
		}
		String[] r = new String[hei];
		String[] c = new String[wid];
		String[] q = new String[hei + wid];
		String[] qq = null;
		int ans = 0;
		floop:
		for (int f = 1; f <= a[0][0].length(); f++) {
			r[0] = a[0][0].substring(0, f);
			for (int j = 0; j < wid; j++) {
				if (a[0][j].length() <= f)
					continue floop;
				if (!a[0][j].substring(0, f).equals(r[0]))
					continue floop;
				c[j] = a[0][j].substring(f);
				q[j] = c[j];
			}
			for (int i = 0; i < hei; i++) {
				int l = a[i][0].length() - c[0].length();
				if (l < 1)
					continue floop;
				r[i] = a[i][0].substring(0, l);
				q[wid + i] = r[i];
			}
			for (int i = 0; i < hei; i++) {
				for (int j = 0; j < wid; j++) {
					if (!a[i][j].equals(r[i] + c[j]))
						continue floop;
				}
			}
			qq = q.clone();
			ans++;
		}
		if (ans == 0)
			return new String[]{"none"};
		if (ans > 1)
			return new String[]{"multiple"}	;
		return qq;
	}

	public static void main(String[] args) {
		new TableLabel().labels(new String[]{"ABCDEFG   ABCDEFG ABCDEFG  ABCDEFG ACDXX "});
	}
}
