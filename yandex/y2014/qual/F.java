package yandex.y2014.qual;
import java.io.*;
import java.util.*;

public class F {
	void run() throws IOException {
		String s = in.readLine();
		s = s.replaceAll("\\([a-zA-Z]+\\)|[^a-zA-Z0-9 ()]\\)", "<S>$0</S>");
		out.println(s);
	}

	static boolean stdStreams = true;
	static String fileName = F.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static BufferedReader in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		BufferedReader br;
		if (stdStreams) {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			br = new BufferedReader(new FileReader(inputFileName));
			out = new PrintWriter(outputFileName);
		}
		in = br;
		new F().run();
		br.close();
		out.close();
	}
}
