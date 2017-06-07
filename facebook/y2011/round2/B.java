package facebook.y2011.round2;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		long s = Long.parseLong("1" + in.next().replaceAll("a", "0").replace("b", "1"), 2);
		out.println(straight(s));
		for (int i = 0; i < 1024; i++) {
			if (map.containsKey(Long.valueOf(i))) {
				System.out.println(Long.toBinaryString(i) + " " + map.get(Long.valueOf(i)));
			}
		}
	}
	
	static final int MODULO = 1000000007;
	static final Map<Long, Integer> map = new HashMap<Long, Integer>();

	private int straight(long s) {
		long b = Long.highestOneBit(s);
		if ((s & (b >> 1)) != 0) {
			s ^= b - 1;
		}
		if (map.containsKey(s)) {
			return map.get(s);
		}
		int n = 63 - Long.numberOfLeadingZeros(s);
		int res = 1;
		for (int i = 0; i < n; i++) {
			for (int j = i + 2; j <= n; j++) {
				int k = j - i;
				long rest = (s >> j) << (i + 1) | s & ((1 << i) - 1);
				long part = ((s & ((1 << j) - 1)) >> i);
				if (part != 0) {
					res += straight(rest | (1 << i));
					if (res >= MODULO) {
						res -= MODULO;
					}
				}	
				if (part != ((1 << k) - 1)) {
					res += straight(rest);
					if (res >= MODULO) {
						res -= MODULO;
					}
				}
			}
		}
		if (map.size() < 100000 || s <= 65536) {
			map.put(s, res);
		} else {
			System.out.println(s);
		}
		return res;
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
			new B().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
