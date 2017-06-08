package dgcj;

import java.io.*;

public class LocalTesting {
	public static final File logFile = new File("log~.txt");

	public static void run(String problemName, int nodes) {
		if ("TemplateDgcjProblem".equals(problemName)) {
			throw new AssertionError("Change PROBLEM field in Main!");
		}
		logFile.delete();
		String exec = "./dgcj_tool~/dcj.sh"
				+ " test"
				+ " --source Main.java"
				+ " --library " + problemName + ".java"
				+ " --nodes " + nodes;
		System.out.println("Running: " + exec);
		try {
			System.out.println(execute(exec));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			if (logFile.exists()) {
				System.out.println(readToEnd(new FileReader(logFile)));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String readToEnd(InputStream is) throws IOException {
		return readToEnd(new BufferedReader(new InputStreamReader(is)));
	}

	public static String readToEnd(Reader reader) throws IOException {
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String s;
		while ((s = br.readLine()) != null) {
			sb.append(s).append('\n');
		}
		br.close();
		s = sb.toString();
		if (!s.isEmpty()) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	public static String execute(String exec) throws IOException {
		Process p = Runtime.getRuntime().exec(exec);
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
		return readToEnd(p.getInputStream());
	}
	
	public static void log(String message) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(logFile, true));
			pw.println(message);
			pw.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
