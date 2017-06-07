package facebook.y2013.round1;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	class Event implements Comparable<Event> {
		int x, y, z, d;

		public Event(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}

		@Override
		public int compareTo(Event that) {
			if (x != that.x) {
				return x - that.x;
			}
			return y - that.y;
		}
		
		@Override
		public String toString() {
			return x + " " + y + " " + d;
		}
	}

	private void solve() {
		int wid = in.nextInt();
		int hei = in.nextInt();
		int w = in.nextInt();
		int h = in.nextInt();
		int m = in.nextInt();
		int x = in.nextInt();
		int y = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();
		int c = in.nextInt();
		int d = in.nextInt();
		long tt = System.currentTimeMillis();
		Event[] events = new Event[4 * m + 1];
		for (int i = 0; i < m; i++) {
			int x1 = Math.max(x - w + 1, 0);
			int y1 = Math.max(y - h + 1, 0);
			int x2 = x + 1;
			int y2 = y + 1;
			events[4 * i] = new Event(x1, y1, 1);
			events[4 * i + 1] = new Event(x1, y2, -1);
			events[4 * i + 2] = new Event(x2, y1, -1);
			events[4 * i + 3] = new Event(x2, y2, 1);
			
			int xx = (x * a + y * b + 1) % wid;
			int yy = (x * c + y * d + 1) % hei;
			x = xx;
			y = yy;
		}
		events[4 * m] = new Event(wid + 1, hei + 1, 0);
		Arrays.sort(events);
		int[] p = new int[hei + 1];
		int[] q = new int[hei + 1];
		int i = 0;
		int ans = 0;
		int hh = hei - h + 1;
		for (x = 0; x + w <= wid; x++) {
			if (x % 1000 == 0) System.out.println(x + " " + (System.currentTimeMillis() - tt));
			System.arraycopy(p, 0, q, 0, hh);
			while (events[i].x == x) {
				q[events[i].y] += events[i].d;
				i++;
			}
			if (q[0] == 0) {
				ans++;
			}
			for (y = 1; y < hh; y++) {
				q[y] += q[y - 1] - p[y - 1];
				if (q[y] == 0) {
					ans++;
				}
			}
			int[] t = p;
			p = q;
			q = t;
			while (events[i].x == x) {
				i++;
			}
		}
		out.println(ans);
		System.out.print(ans + " ");
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		for (int t = 1; t <= tests; t++) {
			out.print("Case #" + t + ": ");
			new C().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
