package marathons.topcoder.phraseGuessing;

import java.util.*;
import java.io.*;
import java.util.concurrent.Callable;

import marathons.utils.ResourcesKt;
import marathons.utils.topcoderMy.*;

public class PhraseGuessingTester extends MarathonTester implements Callable<Void>, KnowsJavaSolution {
	//Ranges
	private static final int minN = 2, maxN = 10;
	private static final double minC = 0.05, maxC = 0.8;

	//Inputs
	private int N;          //number of words in the phrase
	private int P;          //phrase length
	private double C;       //Corruption rate

	//State Control
	private String Phrase;                //secret phrase
	private List<String> WordList;        //all the words
	private Set<String> WordSet;

	//Constants
	private static final char Correct = '*';
	private static final char Partial = '+';
	private static final char Wrong = '.';
	private static final char[] Res = {Correct, Partial, Wrong};
	private static final int P1 = 100;
	private static final int P2 = 20;


	protected void generate() {
		try {
			loadWords();
		} catch (Exception e) {
			System.out.println("Exception " + e);
			System.exit(0);
		}

		N = randomInt(minN, maxN);
		C = randomDouble(minC, maxC);


		//Special cases for seeds 1 and 2
		if (seed == 1) {
			N = minN;
			C = minC;
		} else if (seed == 2) {
			N = maxN;
			C = maxC;
		}


		//User defined parameters
		if (parameters.isDefined("N")) {
			N = randomInt(parameters.getIntRange("N"), 1, maxN);
		}
		if (parameters.isDefined("C")) {
			C = randomDouble(parameters.getDoubleRange("C"), 0, maxC);
		}


		int[] id = new int[WordList.size()];
		for (int i = 0; i < id.length; i++) id[i] = i;
		shuffle(id, WordList.size());

		//generate phrase
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			sb.append(WordList.get(id[i]));
			if (i < N - 1) sb.append(" ");
		}
		Phrase = sb.toString();
		P = Phrase.length();


		if (debug) {
			System.out.println("Words, N = " + N);
			System.out.println("Characters, P = " + P);
			System.out.println("Corruption rate, C = " + C);
			System.out.println();
			System.out.println("Secret phrase: " + Phrase);
			System.out.println();
		}
	}


	protected void loadWords() throws Exception {
		prepareResources();
		BufferedReader in = new BufferedReader(new FileReader(PhraseGuessing.resourcePrefix + PhraseGuessing.DICTIONARY_FILE_NAME));

		WordList = new ArrayList<>();
		WordSet = new HashSet<>();

		in.readLine();    //skip the header

		while (true) {
			String line = in.readLine();
			if (line == null) break;

			WordList.add(line);
			WordSet.add(line);
		}
		in.close();
	}


	//shuffle the array randomly
	protected void shuffle(int[] a, int size) {
		for (int i = 0; i < size; i++) {
			int k = randomInt(i, size - 1);
			int temp = a[i];
			a[i] = a[k];
			a[k] = temp;
		}
	}

	protected boolean isMaximize() {
		return false;
	}


	protected double run() throws Exception {
		writeLine("" + N);
		writeLine("" + P);
		writeLine("" + C);
		flush();

		int score = -1;
		int guesses = 1;
		int maxGuesses = 10000;

		for (; guesses <= maxGuesses; guesses++) {
			//run the solution and read its output
			startTime();
			String line = readLine();
			stopTime();

			String guess = line.trim();

			if (guess.equals("-1")) break;

			if (debug) System.out.println("Guess " + guesses + ":" + makeSpace(10) + guess);

			//You guessed the phrase!
			if (guess.equals(Phrase)) {
				if (debug) System.out.println("Correct!");
				return guesses;
			}

			if (guess.length() != P)
				return fatalError("Your guess does not contain the correct number of characters");

			String[] temp = guess.split(" ");

			if (temp.length != N)
				return fatalError("Your guess does not contain the correct number of words");

			for (String word : temp)
				if (!WordSet.contains(word))
					return fatalError("Your guess uses an unknown word: " + word);


			char[] out = new char[P];
			boolean[] usedPhrase = new boolean[P];
			boolean[] usedGuess = new boolean[P];

			for (int i = 0; i < P; i++) {
				char c = guess.charAt(i);
				if (!(c == ' ' || (c >= 'A' && c <= 'Z')))
					return fatalError("Your guess uses an invalid character: " + c);

				//correct letter in the right location
				if (c == Phrase.charAt(i)) {
					out[i] = Correct;
					usedGuess[i] = true;
					usedPhrase[i] = true;
				}
			}

			int wrong = 0;
			int partial = 0;

			for (int i = 0; i < P; i++) {
				if (usedGuess[i]) continue;

				char c = guess.charAt(i);

				boolean found = false;
				for (int k = 0; k < P; k++) {
					//correct letter in the wrong location
					if (!usedPhrase[k] && c == Phrase.charAt(k)) {
						found = true;
						usedPhrase[k] = true;
						out[i] = Partial;
						partial++;
						break;
					}
				}

				//letter not in the phrase
				if (!found) {
					out[i] = Wrong;
					wrong++;
				}

				usedGuess[i] = true;
			}

			score = guesses + P1 * wrong + P2 * partial;


			String trueResult = new String(out);


			//Now corrupt the output :)
			for (int i = 0; i < P; i++)
				if (randomDouble(0, 1) < C)
					out[i] = Res[randomInt(0, 2)];


			String corruptResult = new String(out);

			if (debug) {
				System.out.println("True result " + guesses + ":" + makeSpace(4) + trueResult);
				System.out.println("Corrupt result " + guesses + ": " + corruptResult);
				System.out.println("Potential score " + score);
				System.out.println();
			}

			//output elapsed time and result
			writeLine("" + getRunTime());
			writeLine(corruptResult);
			flush();
		}

		return score;
	}


	public static String makeSpace(int n) {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < n; i++) out.append(" ");
		return out.toString();
	}

	public static void mainOriginal(String[] args) {
		new MarathonController().run(args);
	}

	@Override
	public Void call() throws Exception {
		mainOriginal(PhraseGuessing.EVALUATOR_PARAMETERS.split(" "));
		return null;
	}

	void prepareResources() {
		PhraseGuessing.resourcePrefix = ResourcesKt.resourcePrefix();
	}

	@Override
	public void runSolution(BufferedReader in, BufferedWriter out) throws Exception {
		prepareResources();
		PhraseGuessing.run(in, out);
	}
}
