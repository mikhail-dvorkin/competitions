package yandex.y2014.warmup;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

public class E {
	int solve(int n, int k) {
		BigInteger r = BigInteger.ONE;
		BigInteger f = BigInteger.ONE;
		for (int i = 1; i <= n; i++) {
			f = f.multiply(BigInteger.valueOf(i));
			r = r.add(f.pow(k));
		}
		String s = r.toString();
		s = s.replaceAll("0+$", "");
		return s.charAt(s.length() - 1) - '0';
	}

	public Map<String, String> run() {
		Map<String, String> map = new TreeMap<String, String>();
		for (int n = 1; n <= 100; n++) {
			for (int k = 0; k <= 3; k++) {
				map.put(n + " " + k, "" + solve(n, k));
			}
		}
		return map;
	}

	static boolean stdStreams = true;
	static String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static String generatedClassName = E.class.getSimpleName().replaceFirst("_.*", "") + "Generated";
	static String generatedFileName = generatedClassName + ".java";
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		Map<String, String> map = new E().run();
		out = new PrintWriter(generatedFileName);
		out.println("import java.io.*;");
		out.println("import java.util.*;");
		out.println("");
		out.println("public class " + generatedClassName + " {");
		out.print("	public static void m" + "ain(String[] args) ");
		if (stdStreams) {
			out.println("{");
			out.println("		Scanner in = new Scanner(System.in);");
			out.println("		PrintWriter out = new PrintWriter(System.out);");
		} else {
			out.println("throws IOException {");
			out.println("		Scanner in = new Scanner(new File(\"" + inputFileName + "\"));");
			out.println("		PrintWriter out = new PrintWriter(\"" + outputFileName + "\");");
		}
		out.println("		String s = in.nextLine();");
		for (Entry<String, String> entry : map.entrySet()) {
			out.println("		if (s.equals(\"" + entry.getKey() + "\")) out.println(\"" + entry.getValue().replaceAll("\n", "\\\\n") + "\");");
		}
		out.println("		in.close();");
		out.println("		out.close();");
		out.println("	}");
		out.println("}");
		out.close();
	}
}
