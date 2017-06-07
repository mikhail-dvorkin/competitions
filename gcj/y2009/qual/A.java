package gcj.y2009.qual;

import java.io.*;
import java.util.*;

public class A implements Runnable {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	@Override
	public void run() {
		int len = in.nextInt();
		int d = in.nextInt();
		int tests = in.nextInt();
		String[] words = new String[d];
		for (int i = 0; i < d; i++) {
			words[i] = in.next();
		}
		String[] pattern = new String[len];
		for (int t = 0; t < tests; t++) {
			out.print("Case #" + (t + 1) + ": ");
			String p = in.next();
			for (int i = 0; i < len; i++) {
				int x = (p.charAt(0) == '(') ? p.indexOf(')') : 0;
				pattern[i] = p.substring(0, x + 1);
				p = p.substring(x + 1);
			}
			int good = 0;
			for (String s : words) {
				boolean ok = true;
				for (int i = 0; i < len; i++) {
					if (!pattern[i].contains("" + s.charAt(i)))
						ok = false;
				}
				if (ok) good++;
			}
			out.println(good);
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		Thread thread = new Thread(new A());
		thread.start();
		thread.join();
		in.close();
		out.close();
	}
}
