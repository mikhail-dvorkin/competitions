package topcoder.srm417;
public class CubeNets {
	final String[][] p = new String[][]{
			new String[]{"..#.", "####", "..#."},
			new String[]{"..#.", "####", "...#"},
			new String[]{"...#", "####", "...#"},
			new String[]{"..#.", "####", ".#.."},
			new String[]{"..#.", "####", "#..."},
			new String[]{"...#", "####", "#..."},
			new String[]{"###..", "..###"},
			new String[]{"##..", ".###", "..#."},
			new String[]{"##..", ".###", "...#"},
			new String[]{"..#.", "###.", "..##"},
			new String[]{"##..", ".##.", "..##"}
	};

	boolean good(String[] f) {
		for (String[] s : p) {
			for (int dx = -10; dx < 10; dx++) {
				for (int dy = -10; dy < 10; dy++) {
					int m = 0;
					for (int i = 0; i < f.length; i++) {
						for (int j = 0; j < f[0].length(); j++) {
							char c = f[i].charAt(j);
							if (i + dx < 0)
								continue;
							if (i + dx >= s.length)
								continue;
							if (j + dy < 0)
								continue;
							if (j + dy >= s[0].length())
								continue;
							char d = s[i + dx].charAt(j + dy);
							if (c == '#' && d == '#')
								m++;
						}
					}
					if (m == 6)
						return true;
				}
			}
		}
		return false;
	}

	public String isCubeNet(String[] f) {
		if (good(f))
			return "YES";
		f = rotate(f);
		if (good(f))
			return "YES";
		f = rotate(f);
		if (good(f))
			return "YES";
		f = rotate(f);
		if (good(f))
			return "YES";
		f = transpose(f);
		if (good(f))
			return "YES";
		f = rotate(f);
		if (good(f))
			return "YES";
		f = rotate(f);
		if (good(f))
			return "YES";
		f = rotate(f);
		if (good(f))
			return "YES";
		return "NO";
	}

	private String[] rotate(String[] f) {
		int hei = f.length;
		int wid = f[0].length();
		char[][] g = new char[wid][hei];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				g[j][hei - 1 - i] = f[i].charAt(j);
			}
		}
		String[] gg = new String[wid];
		for (int i = 0; i < wid; i++) {
			gg[i] = new String(g[i]);
		}
		return gg;
	}

	private String[] transpose(String[] f) {
		int hei = f.length;
		int wid = f[0].length();
		char[][] g = new char[wid][hei];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				g[j][i] = f[i].charAt(j);
			}
		}
		String[] gg = new String[wid];
		for (int i = 0; i < wid; i++) {
			gg[i] = new String(g[i]);
		}
		return gg;
	}

	public static void main(String[] args) {
		System.out.println(new CubeNets().isCubeNet(new String[]{"####",
			 "...#",
		 "...#"}));
	}
}
