package marathons.topcoder.chickenRun;

import java.io.*;
import java.util.*;

public class ChickenRun {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new ChickenRunTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1 -myExec";
	private static final long TIME_LIMIT = 9850;

	private void makeMove(char[][] grid) {

	}

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

	@SuppressWarnings("serial")
	private static class TimeOutException extends RuntimeException {
	}

	private void checkTimeLimit() {
		checkTimeLimit(1);
	}

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
		int[] dr = {0, -1, 0, 1};
		int[] dc = {-1, 0, 1, 0};

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		char[][] grid = new char[N][N];
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++)
				grid[r][c] = br.readLine().charAt(0);

		for (int turn = 1; turn <= N * N; turn++) {
			List<String> moves = new ArrayList<String>();
			boolean[][] used = new boolean[N][N];

			for (int r = 0; r < N; r++)
				for (int c = 0; c < N; c++)
					if (grid[r][c] == 'P' && !used[r][c])
						for (int m = 0; m < dr.length; m++) {
							int dir = (r * c + turn + m) % dr.length;
							int r2 = r + dr[dir];
							int c2 = c + dc[dir];

							if (r2 >= 0 && r2 < N && c2 >= 0 && c2 < N && (grid[r2][c2] == '.' || grid[r2][c2] == 'C')) {
								moves.add(r + " " + c + " " + r2 + " " + c2);
								grid[r][c] = '.';
								grid[r2][c2] = 'P';
								used[r2][c2] = true;
								break;
							}
						}

			//print the moves
			System.out.println(moves.size());
			for (String m : moves) System.out.println(m);
			System.out.flush();

			//read elapsed time
			int elapsedTime = Integer.parseInt(br.readLine());

			//read the updated grid
			for (int r = 0; r < N; r++)
				for (int c = 0; c < N; c++)
					grid[r][c] = br.readLine().charAt(0);
		}

		//signal end of solution
		System.out.println("-1");
		System.out.flush();
	}
}
