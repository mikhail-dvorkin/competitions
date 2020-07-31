package marathons.codecup.y2018_blackhole;

import java.io.*;

public class CaiaEvaluator implements java.util.concurrent.Callable<Void> {
	static final File CAIA_BIN = new File("~/caia/blackhole/bin/");
//	static final File CP = new File(".../Marathons");
	static final String CAIA_EXEC = "caiaio";
	static final String CAIA_GENERATOR = "generator";
	static final String CAIA_MATCH_CONFIG = "manager.txt";

	static String caia() {
		return exec(CAIA_EXEC);
	}

	static String generate() {
		return exec(CAIA_GENERATOR);
	}

	static String exec(String command) {
		try {
			Process p = Runtime.getRuntime().exec("./" + command, null, CAIA_BIN);
			p.waitFor();
			return readToEnd(p.getInputStream()) + "\n" + readToEnd(p.getErrorStream());
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	static String readToEnd(InputStream is) throws IOException {
		return readToEnd(new BufferedReader(new InputStreamReader(is)));
	}

	static String readToEnd(Reader reader) throws IOException {
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

	@Override
	public Void call() {
		System.out.println(caia());
		return null;
	}
}
