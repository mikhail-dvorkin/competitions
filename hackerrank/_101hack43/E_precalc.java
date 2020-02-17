package hackerrank._101hack43;
import java.io.*;
import java.util.*;

public class E_precalc {
	static final String type = "Map<Long, Integer>";

	public Map<Long, Integer> run() {
		return E_heuristic_tl.precalc();
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
		String generatedClassName = E_precalc.class.getSimpleName().replaceFirst("_.*", "") + "Generated";
		String generatedFileName = generatedClassName + ".java";
		PrintWriter out;

		Locale.setDefault(Locale.US);
		Map<Long, Integer> map = new E_precalc().run();

		out = new PrintWriter(generatedFileName);
		out.println("import java.io.*;");
		out.println("import java.util.*;");
		out.println("");
		out.println("public class " + generatedClassName + " {");
		out.println("	@SuppressWarnings(\"unchecked\")");
		out.println("	" + type + " map = (" + type + ") " + serializeToJavaCodeWithZip((Serializable) map) + ";");
		out.println("}");
		out.close();
	}
}
