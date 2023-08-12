package topcoder.tco2020.round4;
import java.util.Arrays;

public class CovidCinema {
	public String[] seat(int h, int w, int a, int b) {
		String[] r;
		r = seat(h, w, a, b, 'A', 'B');
		if (r != null) return r;
		r = seat(h, w, b, a, 'B', 'A');
		if (r != null) return r;
		return new String[0];
	}

	public String[] seat(int h, int w, int a, int b, char ca, char cb) {
		char[][] ans = new char[h][w];
		for (int d = 1; d <= h; d++) {
			for (int i = 0; i < h; i++) {
				Arrays.fill(ans[i], cb);
			}
			int count = a;
			for (int j = 0; j < w; j++) {
				for (int i = 0; i < d; i++) {
					ans[i][j] = ca;
					if (i + 1 < h) ans[i + 1][j] = '.';
					if (j + 1 < w) ans[i][j + 1] = '.';
					count--;
					if (count == 0) break;
				}
				if (count == 0) break;
			}
			if (count > 0) continue;
			count = b;
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if (ans[i][j] == cb) {
						if (count == 0) ans[i][j] = '.'; else count--;
					}
				}
			}
			if (count == 0) {
				String[] answer = new String[h];
				for (int i = 0; i < h; i++) {
					answer[i] = new String(ans[i]);
				}
				return answer;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String[] r;
//		r = new CovidCinema().seat(5, 5, 1, 1);
//		r = new CovidCinema().seat(5, 5, 1, 12);
		r = new CovidCinema().seat(5, 5, 12, 1);
		r = new CovidCinema().seat(5, 5, 13, 7);
		for (String s : r) {
			System.out.println(s);
		}
	}
}
