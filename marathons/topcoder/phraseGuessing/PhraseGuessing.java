package marathons.topcoder.phraseGuessing;

import java.io.*;
import java.util.*;

public class PhraseGuessing {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new PhraseGuessingTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1,3 -myExec #-debug";
	public static String DICTIONARY_FILE_NAME = "words_alpha_filtered.txt";
	public static String TERMINATE = "-1";
	public static String resourcePrefix = "";
	public static final int TIME_LIMIT = 10000 - 150;

	int n, length, move, t = 1;
	double corruption;
	List<String> wordList;
	String lastGuess;

	public String makeMove() {
		if (move == 20) {
			return TERMINATE;
		}
		int[] primes = {113, 131, 197, 199, 311, 337, 373, 719, 733, 919};
		for (;; t++) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n; i++) {
				int id = (t * primes[i]) % wordList.size();
				sb.append(wordList.get(id));
				if (i < n - 1) sb.append(" ");
			}
			String guess = sb.toString();
			if (guess.length() == length) {
				t++;
				return guess;
			}
		}
	}

	private void consumeResult(String result, int elapsedTime) {
		move++;
	}

	public PhraseGuessing(int n, int p, double c, List<String> wordList) {
		this.n = n;
		this.length = p;
		this.corruption = c;
		this.wordList = wordList;
		log("n = " + n + "\tL = " + p + "\tc = " + Math.round(c * 100) + "\t");
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
		if (EVALUATOR != null) {
			EVALUATOR.call();
			return;
		}
		run(new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new PrintWriter(System.out)));
	}

	public static void run(BufferedReader in, BufferedWriter out) throws Exception {
		BufferedReader file = new BufferedReader(new FileReader(resourcePrefix + DICTIONARY_FILE_NAME));

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
		PhraseGuessing phraseGuessing = new PhraseGuessing(N, P, C, WordList);

		for (;;) {
			phraseGuessing.lastGuess = phraseGuessing.makeMove();
			out.write(phraseGuessing.lastGuess);
			out.newLine();
			out.flush();
			if (TERMINATE.equals(phraseGuessing.lastGuess)) {
				break;
			}

			//read elapsed time and result
			int elapsedTime = Integer.parseInt(in.readLine());
			String result = in.readLine();
			phraseGuessing.consumeResult(result, elapsedTime);
		}
	}
}
