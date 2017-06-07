package gcj.y2012.qual;
import java.io.*;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	static char[] perm = new char['z' + 1];
	
	static void findPermutation() {
		StringBuilder a = new StringBuilder();
		StringBuilder b = new StringBuilder();
		a.append("y qee");
		b.append("a zoo");
		a.append("ejp mysljylc kd kxveddknmc re jsicpdrysi");
		b.append("our language is impossible to understand");
		a.append("rbcpc ypc rtcsra dkh wyfrepkym veddknkmkrkcd");
		b.append("there are twenty six factorial possibilities");
		a.append("de kr kd eoya kw aej tysr re ujdr lkgc jv");
		b.append("so it is okay if you want to just give up");
		String from = a.toString();
		String to = b.toString();
		for (int i = 0; i < from.length(); i++) {
			char c = from.charAt(i);
			char d = to.charAt(i);
			if (perm[c] > 0 && perm[c] != d) {
				throw new RuntimeException();
			}
			perm[c] = d;
		}
		char c = 0, d = 0;
		for (char i = 'a'; i <= 'z'; i++) {
			d += i;
			d -= perm[i];
			if (perm[i] == 0) {
				c = i;
			}
		}
		if (c > 0) {
			perm[c] = d;
		}
	}

	private void solve() {
		String s = in.nextLine();
		for (int i = 0; i < s.length(); i++) {
			out.print(perm[s.charAt(i)]);
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
		findPermutation();
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
