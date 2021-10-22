package marathons.utils; //TESTING

import java.io.*;
import java.util.*;

public class Template {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
//			new marathons.utils.Evaluator(new TopCoderMarathonVis()); //TESTING
			null; //SUBMISSION
	public static final long TIME_LIMIT = 10000 - 150;

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
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.flush();
	}
}
