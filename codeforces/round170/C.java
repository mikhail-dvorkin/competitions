package codeforces.round170;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

public class C {
	private static BufferedReader in;

	public void run() throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		int xm = Integer.parseInt(st.nextToken());
		int ym = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		Map<Integer, ArrayList<Integer>> hor = new TreeMap<>();
		Map<Integer, ArrayList<Integer>> ver = new TreeMap<>();
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(in.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			if (x1 == x2) {
				addSegment(ver, x1, y1, y2);
			} else {
				addSegment(hor, y1, x1, x2);
			}
		}
		int xor = 0;
		if ((xm - 1 - ver.size()) % 2 != 0) {
			xor ^= ym;
		}
		if ((ym - 1 - hor.size()) % 2 != 0) {
			xor ^= xm;
		}
		for (ArrayList<Integer> segments : ver.values()) {
			xor ^= xor(segments, ym);
		}
		for (ArrayList<Integer> segments : hor.values()) {
			xor ^= xor(segments, xm);
		}
		if (xor == 0) {
			System.out.println("SECOND");
			return;
		}
		System.out.println("FIRST");
		if (((xm - 1 - ver.size()) > 0) && (ym > (ym ^ xor))) {
			int x = 1;
			while (ver.containsKey(x)) {
				x++;
			}
			System.out.println(x + " " + 0 + " " + x + " " + (ym - (ym ^ xor)));
			return;
		}
		if (((ym - 1 - hor.size()) > 0) && (xm > (xm ^ xor))) {
			int y = 1;
			while (hor.containsKey(y)) {
				y++;
			}
			System.out.println(0 + " " + y + " " + (xm - (xm ^ xor)) + " " + y);
			return;
		}
		for (Entry<Integer, ArrayList<Integer>> entry : ver.entrySet()) {
			ArrayList<Integer> segments = entry.getValue();
			int v = xor(segments, ym);
			if (v > (v ^ xor)) {
				int y = bite(segments, v ^ xor, ym);
				int x = entry.getKey();
				System.out.println(x + " " + y + " " + x + " " + ym);
				return;
			}
		}
		for (Entry<Integer, ArrayList<Integer>> entry : hor.entrySet()) {
			ArrayList<Integer> segments = entry.getValue();
			int v = xor(segments, xm);
			if (v > (v ^ xor)) {
				int x = bite(segments, v ^ xor, xm);
				int y = entry.getKey();
				System.out.println(x + " " + y + " " + xm + " " + y);
				return;
			}
		}
	}

	private int bite(ArrayList<Integer> segments, int amount, int ri) {
		int le = 0;
		while (le + 1 < ri) {
			int m = (le + ri) / 2;
			if (xor(segments, m) > amount) {
				ri = m;
			} else {
				le = m;
			}
		}
		return le;
	}

	private int xor(ArrayList<Integer> segments, int len) {
		len *= 2;
		Collections.sort(segments);
		int open = 0;
		int p = 0;
		int res = 0;
		for (int s : segments) {
			if (s >= len) {
				break;
			}
			if (s % 2 == 0) {
				open++;
				if (open == 1) {
					res += s / 2 - p;
				}
			} else {
				open--;
				p = s / 2;
			}
		}
		if (open == 0) {
			res += len / 2 - p;
		}
		return res;
	}

	private void addSegment(Map<Integer, ArrayList<Integer>> ver, int z, int t1, int t2) {
		if (!ver.containsKey(z)) {
			ver.put(z, new ArrayList<>());
		}
		if (t1 > t2) {
			int t = t1; t1 = t2; t2 = t;
		}
		ver.get(z).add(2 * t1);
		ver.get(z).add(2 * t2 + 1);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new C().run();
		in.close();
	}
}
