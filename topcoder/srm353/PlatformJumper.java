package topcoder;
import java.util.*;

public class PlatformJumper {
	class Platform implements Comparable<Platform> {
		int x, y, c;
		
		public Platform(String s) {
			Scanner in = new Scanner(s);
			x = in.nextInt();
			y = in.nextInt();
			c = in.nextInt();
			in.close();
		}
		
		@Override
		public int compareTo(Platform o) {
			return o.y - y;
		}

		public boolean poss(Platform o) {
			int h = y - o.y;
			long mx = V * 1L * V * (h * 2L);
			long dx = G * 1L * (x - o.x) * 1L * (x - o.x);
			return (dx <= mx);
		}
	}
	
	int V, G;
	
	public int maxCoins(String[] platforms, int v, int g) {
		V = v;
		G = g;
		int n = platforms.length;
		Platform[] p = new Platform[n];
		for (int i = 0; i < n; i++) {
			p[i] = new Platform(platforms[i]);
		}
		Arrays.sort(p);
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = Math.max(a[i], p[i].c);
			for (int j = i + 1; j < n; j++) {
				if (p[i].poss(p[j])) {
					a[j] = Math.max(a[j], a[i] + p[j].c);
				}
			}
		}
		Arrays.sort(a);
		return a[n - 1];
	}
	
	public static void main(String[] args) {
		int a = new PlatformJumper().maxCoins(new String[]{"3 10 7", "5 15 7", "8 9 12" }, 2, 10);
		System.out.println(a);
	}
}
