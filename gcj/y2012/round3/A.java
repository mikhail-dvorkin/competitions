package gcj.y2012.round3;

import java.io.*;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	class Level implements Comparable<Level> {
		int id;
		int time;
		int prob;
		
		public Level(int id, int time, int prob) {
			this.id = id;
			this.time = time;
			this.prob = prob;
		}

		@Override
		public int compareTo(Level t) {
			int c = time * t.prob - t.time * prob;
			if (c != 0) {
				return c;
			}
			return id - t.id;
		}
	}
	
	private void solve() {
		int n = in.nextInt();
		int[] time = new int[n];
		int[] prob = new int[n];
		for (int i = 0; i < n; i++) {
			time[i] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			prob[i] = in.nextInt();
		}
		Level[] levels = new Level[n];
		for (int i = 0; i < n; i++) {
			levels[i] = new Level(i, time[i], prob[i]);
		}
		Arrays.sort(levels);
		for (Level level : levels) {
			out.print(" " + level.id);
		}
		out.println();
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
			new A().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
