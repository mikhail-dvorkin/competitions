package marathons.topcoder.phraseGuessing;

import java.io.*;
import java.util.*;

public class PhraseGuessing {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new PhraseGuessingTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1,3 -myExec";
	public static final int TIME_LIMIT = 10000 - 150;

	private void solve() {
	}

	public int solve(int input) {
		try {
			solve();
		} catch (TimeOutException ignored) {
		}
		_myScore = input;
		return 0;
	}

	private static void log(String s) {
		if (!SUBMIT) System.out.print(s + " ");
	}

	private static class TimeOutException extends RuntimeException {
	}

	private void checkTimeLimit() {
		checkTimeLimit(1);
	}

	@SuppressWarnings("SameParameterValue")
	private void checkTimeLimit(double threshold) {
		if (timePassed() >= threshold) {
			throw new TimeOutException();
		}
	}

	private double timePassed() {
		double timeLimit = TIME_LIMIT;
		if (!SUBMIT) {
			if (_localTimeCoefficient == 0) {
				_troubles.add("Local time coefficient not set.");
			} else {
				timeLimit *= _localTimeCoefficient;
			}
		}
		return (System.currentTimeMillis() - timeStart) / timeLimit;
	}

	@SuppressWarnings("ConstantConditions")
	private static final boolean SUBMIT = (EVALUATOR == null);
	public static double _localTimeCoefficient = 0;
	private final long timeStart = System.currentTimeMillis();
	ArrayList<String> _troubles = new ArrayList<>();
	ArrayList<String> _labels = new ArrayList<>();
	double _myScore;
	private final Random rnd = new Random(566);

	public static void main(String[] args) throws Exception {
		//noinspection ConstantConditions
		if (EVALUATOR != null) {
			EVALUATOR.call();
			return;
		}
		run(new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new PrintWriter(System.out)));
	}

	public static void run(BufferedReader in, BufferedWriter out) throws Exception {
		String fileName = "words_alpha_filtered.txt";
		BufferedReader file = new BufferedReader(new FileReader(fileName));

		List<String> WordList = new ArrayList<>();

		file.readLine();    //skip the header
		while (true) {
			String line = file.readLine();
			if (line == null) break;

			WordList.add(line);
		}
		file.close();

		int N = Integer.parseInt(in.readLine());
		int P = Integer.parseInt(in.readLine());
		double C = Double.parseDouble(in.readLine());


		int[] primes = {113, 131, 197, 199, 311, 337, 373, 719, 733, 919};

		for (int i = 1, guesses = 1; guesses <= 20; i++) {
			StringBuilder sb = new StringBuilder();
			for (int n = 0; n < N; n++) {
				int id = (i * primes[n]) % WordList.size();
				sb.append(WordList.get(id));
				if (n < N - 1) sb.append(" ");
			}
			String guess = sb.toString();

			//we found a valid guess
			if (guess.length() == P) {
				guesses++;
				out.write(guess);
				out.newLine();
				out.flush();

				//read elapsed time and result
				int elapsedTime = Integer.parseInt(in.readLine());
				String result = in.readLine();
			}
		}

		//terminate
		out.write("-1");
		out.newLine();
		out.flush();
	}
}
