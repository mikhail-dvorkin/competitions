package marathons.topcoder.stopTheElves;

import java.io.*;
import java.util.*;

public class StopTheElves {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new StopTheElvesTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1,3 -myExec -myVis";
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
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int C = Integer.parseInt(br.readLine());
		float elfP = Float.parseFloat(br.readLine());
		int money = Integer.parseInt(br.readLine());

		char[][] grid = new char[N][N];
		for (int y = 0; y < N; y++)
			for (int x = 0; x < N; x++)
				grid[x][y] = br.readLine().charAt(0);

		for (int turn = 0; turn < N * N; turn++) {
			int x = 1 + ((turn * (7919)) % (N - 2));
			int y = 1 + ((turn * (50091)) % (N - 2));
			if (money >= C && grid[x][y] == '.') {
				System.out.println(y + " " + x);
			} else {
				System.out.println("-1");
			}
			System.out.flush();

			//read elapsed time
			int elapsedTime = Integer.parseInt(br.readLine());
			//read the money
			money = Integer.parseInt(br.readLine());
			//read the updated grid
			for (y = 0; y < N; y++)
				for (x = 0; x < N; x++)
					grid[x][y] = br.readLine().charAt(0);
		}
	}
}
