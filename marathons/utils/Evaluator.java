package marathons.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * Visualizer must:
 * - use Evaluator._seed and Evaluator._vis
 * - initially set Main._localTimeCoefficient = Evaluator.localTimeCoefficient()
 * - initially set Evaluator._useMyScore if needed
 * - before each run set Main._verbose = Evaluator._verbose
 * - after each run return all _outcomeFields
 * - on own errors add them to _outcomeTroubles
 */
public class Evaluator implements Callable<Void> {
	private final Callable<Void> visualizer;
	private final int evaluate;
	private final long evaluateFrom;
	private final boolean evaluateVerbose;
	private final int visualize;
	private final long visualizeFrom;
	private final boolean visualizeVerbose;

	public static long _outcomeTime;
	public static double _outcomeScore;
	public static double _outcomeMyScore;
	public static ArrayList<String> _outcomeTroubles;
	public static ArrayList<String> _outcomeLabels;

	public static long _seed;
	public static boolean _vis;
	public static boolean _verbose;
	public static boolean _useMyScore;
	private final ArrayList<String> allTroubles = new ArrayList<>();

	public Evaluator(Callable<Void> visualizer) {
		this(10, 1, false, 0, 1, true, visualizer);
	}

	public Evaluator(int evaluate, long evaluateFrom, boolean evaluateVerbose,
			Callable<Void> visualizer) {
		this(evaluate, evaluateFrom, evaluateVerbose, 0, 1, true, visualizer);
	}

	public Evaluator(int evaluate, long evaluateFrom, boolean evaluateVerbose,
			int visualize, long visualizeFrom, boolean visualizeVerbose,
			Callable<Void> visualizer) {
		this.visualizer = visualizer;
		this.evaluate = evaluate;
		this.evaluateFrom = evaluateFrom;
		this.evaluateVerbose = evaluateVerbose;
		this.visualize = visualize;
		this.visualizeFrom = visualizeFrom;
		this.visualizeVerbose = visualizeVerbose;
	}

	public static Properties settings() {
		try (FileReader reader = new FileReader("settings~.cfg")) {
			Properties settings = new Properties();
			settings.load(reader);
			return settings;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static double localTimeCoefficient() {
		return Double.parseDouble(settings().getProperty("localTimeCoefficient"));
	}

	private static boolean text() {
		return settings().getProperty("text", "" + false).equals("" + true);
	}

	public static String timeToString(double time) {
		return Math.round(time) / 1000.0 + "s (server " +
			Math.round(time / localTimeCoefficient()) / 1000.0 + "s)";
	}

	public static String round(double v, int precision) {
		if (Math.abs(v) >= 1e100) {
			return v > 0 ? "INF" : "-INF";
		}
		double ten = Math.round(Math.pow(10, precision));
		return "" + Math.round(v * ten) / ten;
	}

	private void callVisualizer() {
		_outcomeMyScore = Double.NaN;
		_outcomeTroubles = new ArrayList<>();
		try {
			visualizer.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (Double.isNaN(_outcomeMyScore)) {
			allTroubles.add(_seed + "\tDid not finish correctly: MyScore = NaN");
		} else {
			for (String s : _outcomeTroubles) {
				allTroubles.add(_seed + "\t" + s);
			}
		}
	}

	public static void requireEnablesAssertions() {
		int assertOn = 0;
		//noinspection AssertWithSideEffects,ConstantConditions
		assert (assertOn = 1) > 0;
		//noinspection ConstantConditions
		if (assertOn == 0) {
			throw new AssertionError("Asserts must be on.");
		}
	}

	@Override
	public Void call() {
		requireEnablesAssertions();
		if (visualize > 0 && !text()) {
			_vis = true;
			_verbose = visualizeVerbose;
			for (int t = 0; t < visualize && _vis; t++) {
				_seed = visualizeFrom + t;
				callVisualizer();
			}
			System.out.println();
		}
		if (evaluate > 0) {
			_vis = false;
			_verbose = evaluateVerbose;
			double sumScores = 0;
			double sumScores2 = 0;
			long totalT = 0;
			long maxT = 0;
			long maxTest = 0;
			int tests = evaluate;
			for (int t = 0; t < tests; t++) {
				_seed = evaluateFrom + t;
				_outcomeMyScore = _outcomeScore = Double.NaN;
				System.out.print("#" + _seed+ ":\t");
				callVisualizer();
				double score = _useMyScore ? _outcomeMyScore : _outcomeScore;
				System.out.println(score);
				sumScores += score;
				sumScores2 += score * score;
				if (_outcomeTime > maxT) {
					maxT = _outcomeTime;
					maxTest = _seed;
				}
				totalT += _outcomeTime;
			}
			double mean = sumScores / tests;
			double std = Math.sqrt(sumScores2 / tests - mean * mean);
			String scoreName = _useMyScore ? "MyScore" : "Score";
			StringBuilder sb = new StringBuilder();
			sb.append("=========================== ").append(scoreName).append(" = ").append(round(mean, 2));
			sb.append("\n+-").append(round(100 * std / mean, 2)).append("%");
			sb.append("\n======== AverageTime: ").append(timeToString(1.0 * totalT / tests));
			sb.append("\n======== MaxTime: ").append(timeToString(maxT)).append(" on test #").append(maxTest);
			if (!allTroubles.isEmpty()) {
				sb.append("\n\n== == == == == == == ==  TROUBLES!");
				for (String s : allTroubles) {
					sb.append("\n").append(s);
				}
				System.err.println("TROUBLES!");
			}
			System.out.println(sb);
			if (Pictures.mode) {
				Pictures.write(sb);
			}
		}
		if (Pictures.mode) {
			System.out.println("file://" + Pictures.picsFile.getAbsolutePath());
		}
		System.exit(0);
		return null;
	}
}
