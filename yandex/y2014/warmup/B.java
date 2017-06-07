package yandex.y2014.warmup;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class B {
	void run() throws IOException {
		String aString = br.readLine();
		String bString = br.readLine();
		String s = br.readLine().trim();
		BigInteger k = new BigInteger(br.readLine());
		BigInteger a = BigInteger.valueOf(count(aString, s));
		BigInteger b = BigInteger.valueOf(count(bString, s));
		if (a.equals(k)) {
			out.println(0);
			return;
		}
		if (b.equals(k)) {
			out.println(1);
			return;
		}
		for (int i = 2; i < 15000; i++) {
			BigInteger c = a.add(b);
			if (c.equals(k)) {
				out.println(i);
				return;
			}
			a = b;
			b = c;
		}
		out.println("Impossible");
	}

	int count(String t, String s) {
		int result = 0;
		s = s.toLowerCase();
		Scanner sc = new Scanner(t);
		sc.useDelimiter("[^a-zA-Z]+");
		while (sc.hasNext()) {
			String token = sc.next();
			if (token.toLowerCase().equals(s)) {
				result++;
			}
		}
		sc.close();
		return result;
	}

	static boolean stdStreams = true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static BufferedReader br;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		if (stdStreams) {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			br = new BufferedReader(new FileReader(inputFileName));
			out = new PrintWriter(outputFileName);
		}
		new B().run();
		br.close();
		out.close();
	}
}
