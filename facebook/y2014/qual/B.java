package facebook.y2014.qual;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		int m = in.nextInt();
		int p = in.nextInt();
		Player[] players = new Player[n];
		for (int i = 0; i < n; i++) {
			players[i] = new Player(in.next(), in.nextInt(), in.nextInt());
		}
		Arrays.sort(players);
		int[] timePlayed = new int[n];
		boolean[] onField = new boolean[n];
		for (int i = 0; i < 2 * p; i++) {
			onField[i] = true;
		}
		while (m-- > 0) {
			for (int i = 0; i < n; i++) {
				if (onField[i]) {
					timePlayed[i]++;
				}
			}
			for (int r = 0; r < 2; r++) {
				int getIn = -1;
				int getOut = -1;
				for (int i = 0; i < n; i++) {
					if (i % 2 != r) {
						continue;
					}
					if (onField[i]) {
						if (getOut == -1 || timePlayed[i] >= timePlayed[getOut]) {
							getOut = i;
						}
					} else {
						if (getIn == -1 || timePlayed[i] < timePlayed[getIn]) {
							getIn = i;
						}
					}
				}
				if (getIn == -1) {
					continue;
				}
				onField[getIn] = true;
				onField[getOut] = false;
			}
		}
		String[] ans = new String[2 * p];
		for (int i = 0, j = 0; i < n; i++) {
			if (onField[i]) {
				ans[j++] = players[i].name;
			}
		}
		Arrays.sort(ans);
		for (int i = 0; i < 2 * p; i++) {
			out.print(" " + ans[i]);
		}
		out.println();
	}
	
	class Player implements Comparable<Player> {
		String name;
		int shots;
		int height;
		
		public Player(String name, int shots, int height) {
			this.name = name;
			this.shots = shots;
			this.height = height;
		}

		@Override
		public int compareTo(Player that) {
			if (shots != that.shots) {
				return that.shots - shots;
			}
			return that.height - height;
		}
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
			out.print("Case #" + t + ":");
			new B().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}