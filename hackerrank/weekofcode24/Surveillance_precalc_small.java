package hackerrank.weekofcode24;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Surveillance_precalc_small {
	public Map<String, String> run() {
//		solve3();
		Map<String, String> memo = new TreeMap<String, String>();
		int n = 15;
		for (int h = 3; h <= n; h++) {
			HashMap<Pair, Integer> map = new HashMap<>();
			gen2(0, h, 0, 0, map);
			for (int w = 3; w <= n; w++) {
				HashMap<Pair, Integer> mapNext = new HashMap<>();
				for (Entry<Pair, Integer> entry : map.entrySet()) {
					long ma = entry.getKey().a;
					long mb = entry.getKey().b;
					kloop:
					for (int k = 0; k < 4; k++) {
						long mc = k;
						for (int i = 2; i < h; i++) {
							int sum = Integer.bitCount((int) ((ma >> (i - 2)) & 7)) + Integer.bitCount((int) ((mb >> (i - 2)) & 7)) + Integer.bitCount((int) ((mc >> (i - 2)) & 3));
							if (sum > 2 || sum < 1) {
								continue kloop;
							}
							if (sum == 1) {
								mc |= 1L << i;
							}
						}
						Pair key = new Pair(mb, mc);
						mapNext.put(key, mapNext.getOrDefault(key, 0) + entry.getValue());
					}
				}
				map = mapNext;
				int ans = 0;
//				Map<Integer, Integer> count = new TreeMap<>();
				for (Entry<Pair, Integer> entry : map.entrySet()) {
					int v = entry.getValue();
					ans += v;
//					count.put(v, count.getOrDefault(v, 0) + 1);
				}
				memo.put(h + " " + w, "" + ans);
				System.out.println(h + " " + w + " " + ans + " //" + map.size());
			}
		}
		return memo;
	}

	static void gen2(int x, int h, long ma, long mb, HashMap<Pair, Integer> storage) {
		if (x == h) {
			storage.put(new Pair(ma, mb), 1);
			return;
		}
		int sum2x2 = 0;
		sum2x2 += (ma >> (x - 2)) & 1;
		sum2x2 += (ma >> (x - 1)) & 1;
		sum2x2 += (mb >> (x - 2)) & 1;
		sum2x2 += (mb >> (x - 1)) & 1;
		long cur = 1L << x;
		if (sum2x2 == 0) {
			gen2(x + 1, h, ma | cur, mb | cur, storage);
		}
		if (sum2x2 <= 1) {
			gen2(x + 1, h, ma, mb | cur, storage);
			gen2(x + 1, h, ma | cur, mb, storage);
		}
		gen2(x + 1, h, ma, mb, storage);
	}

	static void solve3() {
		int n = 20;
		int[][][] a = new int[n + 1][3][3];
		int[] b = new int[n + 1];
		a[2][0][0] = 1;
		a[2][0][1] = 3;
		a[2][0][2] = 3;
		a[2][1][0] = 3;
		a[2][1][1] = 9;
		a[2][2][0] = 3;
		for (int i = 3; i <= n; i++) {
			for (int j = 0; j <= 2; j++) {
				for (int k = 0; k <= 2; k++) {
					int m = 2 - j - k;
					if (m < 0) {
						continue;
					}
					a[i][k][m] += a[i - 1][j][k] * (m == 0 ? 1 : 3);
				}
			}
			for (int j = 0; j <= 2; j++) {
				for (int k = 0; k <= 2; k++) {
					b[i] += a[i][j][k];
				}
			}
			double c = p((i + 2) / 3) + p((i + 1) / 3) + p(i / 3);
			c += p(i - (i + 2) / 3) + p(i - (i + 1) / 3) + p(i - i / 3);
			System.out.println(i + " " + b[i] + " " + c);
		}
	}

	static class Pair {
		long a, b;

		public Pair(long a, long b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public int hashCode() {
			return (int) (a ^ (a >>> 32)) * 31 + (int) (b ^ (b >>> 32));
		}

		@Override
		public boolean equals(Object obj) {
			Pair other = (Pair) obj;
			return (a == other.a) && (b == other.b);
		}

		@Override
		public String toString() {
			return a + "_" + b;
		}
	}

	static double p(int n) {
		return Math.pow(3, n);
	}

	public static byte[] compressWithZip(byte[] data) {
		byte[] result = new byte[data.length];
		java.util.zip.Deflater compresser = new java.util.zip.Deflater();
		compresser.setInput(data);
		compresser.finish();
		int compressedDataLength = compresser.deflate(result);
		compresser.end();
		return Arrays.copyOf(result, compressedDataLength);
	}

	public static String encodeBase64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
		//return javax.xml.bind.DatatypeConverter.printBase64Binary(data);
	}

	public static byte[] serialize(Serializable object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(object);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String serializeToJavaCodeWithZip(Serializable object) {
		byte[] bytes = serialize(object);
		byte[] zipped = compressWithZip(bytes);
		String base64 = encodeBase64(zipped);
		StringBuilder result = new StringBuilder();
		result.append("(new java.util.concurrent.Callable<Object>() {\n");
		result.append("@Override\n");
		result.append("public Object call() {\n");
		result.append("try {\n");
		result.append("String s =\n\"");
		result.append(base64.replaceAll(".{75}", "$0\"+\n\""));
		result.append("\";\n");
		result.append("byte[] z = javax.xml.bind.DatatypeConverter.parseBase64Binary(s);\n");
		result.append("java.util.zip.Inflater i = new java.util.zip.Inflater();\n");
		result.append("i.setInput(z);\n");
		result.append("byte[] b = new byte[" + bytes.length + "];\n");
		result.append("i.inflate(b);\n");
		result.append("return new ObjectInputStream(new ByteArrayInputStream(b)).readObject();\n");
		result.append("} catch (Exception e) {\n");
		result.append("throw new RuntimeException(e);\n");
		result.append("}\n");
		result.append("}\n");
		result.append("}).call()");
		return result.toString();
	}

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = Surveillance_precalc_small.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";
		String generatedClassName = Surveillance_precalc_small.class.getSimpleName().replaceFirst("_.*", "") + "Generated";
		String generatedFileName = generatedClassName + ".java";
		PrintWriter out;

		Locale.setDefault(Locale.US);
		Map<String, String> map = new Surveillance_precalc_small().run();

		out = new PrintWriter(generatedFileName);
		out.println("import java.io.*;");
		out.println("import java.util.*;");
		out.println("");
		out.println("public class " + generatedClassName + " {");
		out.println("	@SuppressWarnings(\"unchecked\")");
		out.println("	static Map<String, String> map = (Map<String, String>) " + serializeToJavaCodeWithZip((Serializable) map) + ";");
		out.println("");
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
		out.println("		int n = in.nextInt();");
		out.println("		in.nextLine();");
		out.println("		for (int i = 0; i < n; i++) {");
		out.println("			String s = in.nextLine();");
		out.println("			out.println(map.get(s));");
		out.println("		}");
		out.println("		in.close();");
		out.println("		out.close();");
		out.println("	}");
		out.println("}");
		out.close();
	}
}
