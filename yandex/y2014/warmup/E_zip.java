package yandex.y2014.warmup;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class E_zip {
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
	static String fileName = E_zip.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static String generatedClassName = E_zip.class.getSimpleName().replaceFirst("_.*", "") + "Generated";
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
		return javax.xml.bind.DatatypeConverter.printBase64Binary(data);
	}
	
	public static byte[] decodeBase64(String string) {
		return javax.xml.bind.DatatypeConverter.parseBase64Binary(string);
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
		result.append(base64.replaceAll(".{75,75}", "$0\"+\n\""));
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
		Map<String, String> map = new E_zip().run();

		out = new PrintWriter(generatedFileName);
		out.println("import java.io.*;");
		out.println("import java.util.*;");
		out.println("");
		out.println("public class " + generatedClassName + " {");
		out.println("	@SuppressWarnings(\"unchecked\")");
		out.println("	static Map<String, String> map = (Map<String, String>) " + serializeToJavaCodeWithZip((Serializable) map) + ";");
		out.println("");
		if (stdStreams) {
			out.println("	public static void main(String[] args) {");
			out.println("		Scanner in = new Scanner(System.in);");
			out.println("		PrintWriter out = new PrintWriter(System.out);");
		} else {
			out.println("	public static void main(String[] args) throws IOException {");
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
