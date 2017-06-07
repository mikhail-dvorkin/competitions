package rcc.y2011.qual3;
import java.util.*;

public class B {
	private static Scanner in;
	
	int[] dx = new int[]{-1, 0, 1, 0};
	int[] dy = new int[]{0, -1, 0, 1};

	public void run() {
		int w = in.nextInt();
		int h = in.nextInt();
		boolean[][] a = new boolean[h][w];
		for (int i = 0; i < h; i++) {
			String s = in.next();
			for (int j = 0; j < w; j++) {
				a[i][j] = (s.charAt(j) == 'A');
			}
		}
		int ans = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				for (int k = 0; k < 4; k++) {
					int ii = i + dx[k];
					int jj = j + dy[k];
					if (ii < 0 || ii >= h || jj < 0 || jj >= w) {
						continue;
					}
					if (a[i][j] != a[ii][jj]) {
						ans++;
					}
				}
			}
		}
		System.out.println(ans / 2);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}
