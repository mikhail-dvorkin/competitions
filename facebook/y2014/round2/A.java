package facebook.y2014.round2;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	Random r = new Random(566);
	Map<Integer, Long> rnd = new HashMap<Integer, Long>();

	private void solve() {
		rnd.clear();
		int n = in.nextInt();
		int m = in.nextInt();
		int x1 = in.nextInt();
		int a1 = in.nextInt();
		int b1 = in.nextInt();
		int c1 = in.nextInt();
		int r1 = in.nextInt();
		int x2 = in.nextInt();
		int a2 = in.nextInt();
		int b2 = in.nextInt();
		int c2 = in.nextInt();
		int r2 = in.nextInt();
		int[] v1 = new int[n];
		int[] v2 = new int[m];
		v1[0] = x1;
		v2[0] = x2;
		for (int i = 1; i < Math.max(n, m); i++) {
			if (i < n) {
				v1[i] = (int) ((a1 * (long) v1[(i - 1) % n] + b1 * (long) v2[(i - 1) % m] + c1) % r1);
			}
			if (i < m) {
				v2[i] = (int) ((a2 * (long) v1[(i - 1) % n] + b2 * (long) v2[(i - 1) % m] + c2) % r2);
			}
		}
		Map<Long, Integer> count1 = process(v1);
		Map<Long, Integer> count2 = process(v2);
		long ans = 0;
		for (Entry<Long, Integer> e1 : count1.entrySet()) {
			if (count2.containsKey(e1.getKey())) {
				ans += e1.getValue() * (long) count2.get(e1.getKey());
			}
		}
		out.println(ans);
	}
	
	private Map<Long, Integer> process(int[] a) {
		Map<Long, Integer> count = new HashMap<Long, Integer>();
		Set<Long> seen = new HashSet<Long>();
		int n = a.length;
		long xor = 0;
		for (int i = 0; i < n; i++) {
			if (!rnd.containsKey(a[i])) {
				rnd.put(a[i], r.nextLong());
			}
			long v = rnd.get(a[i]);
			if (!seen.contains(v)) {
				seen.add(v);
				xor ^= v;
				if (count.containsKey(xor)) {
					throw new RuntimeException("Wow!");
				}
				count.put(xor, 0);
			}
			count.put(xor, count.get(xor) + 1);
		}
		return count;
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
			new A().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
