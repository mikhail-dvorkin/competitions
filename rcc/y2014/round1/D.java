package rcc.y2014.round1;
import java.io.*;
import java.util.*;

public class D {
	final static int M = 1000000007;
	final static int N = 5000;

	public Map<String, String> run() {
		Map<String, String> map = new TreeMap<String, String>();
		int[][] a = new int[N][N];
		a[0][0] = 1;
		map.put("1", "1");
		for (int i = 1; i < N; i++) {
			int sum = 0;
			for (int j = 0; j <= i; j++) {
				for (int k = 0; k < i; k++) {
					if ((i % 2 == 1) == (k >= j)) {
						continue;
					}
					a[i][j] += a[i - 1][k];
					if (a[i][j] >= M) {
						a[i][j] -= M;
					}
				}
				sum += a[i][j];
				if (sum >= M) {
					sum -= M;
				}
				map.put("" + (i + 1), "" + sum);
			}
		}
		return map;
	}

	static boolean stdStreams = true;
	static String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static String generatedClassName = D.class.getSimpleName().replaceFirst("_.*", "") + "Generated";
	static String generatedFileName = generatedClassName + ".java";
	static PrintWriter out;

	public static byte[] compressWithZip(byte[] data) {
		byte[] result = new byte[data.length];
		java.util.zip.Deflater compresser = new java.util.zip.Deflater();
		compresser.setInput(data);
		compresser.finish();
		int compressedDataLength = compresser.deflate(result);
		compresser.end();
		return Arrays.copyOf(result, compressedDataLength);
	}

	public static byte[] decompressWithZip(byte[] data) {
		try {
			for (int bufferLen = 2 * data.length;; bufferLen *= 2) {
				java.util.zip.Inflater decompresser = new java.util.zip.Inflater();
				decompresser.setInput(data);
				byte[] result = new byte[bufferLen];
				int resultLength = decompresser.inflate(result);
				decompresser.end();
				if (resultLength > 0 && resultLength < bufferLen) {
					return Arrays.copyOf(result, resultLength);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String encodeBase64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
		//return javax.xml.bind.DatatypeConverter.printBase64Binary(data);
	}

	public static byte[] decodeBase64(String string) {
		return Base64.getDecoder().decode(string);
		//return javax.xml.bind.DatatypeConverter.parseBase64Binary(string);
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

	public static Object deserialize(byte[] bytes) {
		try {
			return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
		} catch (Exception e) {
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
		Locale.setDefault(Locale.US);
		Map<String, String> map = new D().run();

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
		out.println("		String s = in.nextLine();");
		out.println("		out.println(map.get(s));");
		out.println("		in.close();");
		out.println("		out.close();");
		out.println("	}");
		out.println("}");
		out.close();
	}
}
