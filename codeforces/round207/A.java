package codeforces.round207;
import java.io.*;
import java.util.*;

public class A {
	private static BufferedReader in;

	public void run() throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		Tour[] tours = new Tour[m];
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(in.readLine());
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int win = Integer.parseInt(st.nextToken()) - 1;
			tours[i] = new Tour(from, to, win, i);
		}
		Tour[] byFrom = tours.clone();
		Tour[] byTo = tours.clone();
		Arrays.sort(byFrom, Comparator.comparingInt(a -> a.from));
		Arrays.sort(byTo, Comparator.comparingInt(a -> a.to));
		TreeSet<Tour> set = new TreeSet<>();
		int i1 = 0;
		int i2 = 0;
		for (int i = 0; i < n; i++) {
			while (i1 < m && byFrom[i1].from == i) {
				set.add(byFrom[i1]);
				i1++;
			}
			boolean winner = true;
			for (Tour t : set) {
				if (t.win == i) {
					continue;
				}
				winner = false;
				System.out.print(t.win + 1 + " ");
				break;
			}
			if (winner) {
				System.out.print("0 ");
			}
			while (i2 < m && byTo[i2].to == i) {
				set.remove(byTo[i2]);
				i2++;
			}
		}
	}

	static class Tour implements Comparable<Tour> {
		final int from;
		final int to;
		final int win;
		final int id;

		public Tour(int from, int to, int win, int id) {
			this.from = from;
			this.to = to;
			this.win = win;
			this.id = id;
		}

		@Override
		public int compareTo(Tour that) {
			return id - that.id;
		}
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new A().run();
		in.close();
	}
}
