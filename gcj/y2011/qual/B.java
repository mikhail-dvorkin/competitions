package gcj.y2011.qual;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int[][] comb = new int[26][26];
		boolean[][] opp = new boolean[26][26];
		for (int i = 0; i < comb.length; i++) {
			Arrays.fill(comb[i], -1);
		}
		int c = in.nextInt();
		for (int i = 0; i < c; i++) {
			String s = in.next();
			int x = s.charAt(0) - 'A';
			int y = s.charAt(1) - 'A';
			int z = s.charAt(2) - 'A';
			comb[x][y] = z;
			comb[y][x] = z;
		}
		int d = in.nextInt();
		for (int i = 0; i < d; i++) {
			String s = in.next();
			int x = s.charAt(0) - 'A';
			int y = s.charAt(1) - 'A';
			opp[x][y] = true;
			opp[y][x] = true;
		}
		in.nextInt();
		String s = in.nextLine().trim();
		ArrayList<Integer> list = new ArrayList<Integer>();
		invoke:
		for (int i = 0; i < s.length(); i++) {
			int x = s.charAt(i) - 'A';
			if (!list.isEmpty()) {
				int y = list.get(list.size() - 1);
				if (comb[x][y] >= 0) {
					list.set(list.size() - 1, comb[x][y]);
					continue invoke;
				}
			}
			for (int y : list) {
				if (opp[x][y]) {
					list.clear();
					continue invoke;
				}
			}
			list.add(x);
		}
		ArrayList<Character> ans = new ArrayList<Character>();
		for (int x : list) {
			ans.add((char) (x + 'A'));
		}
		out.println(ans);
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
			new B().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
