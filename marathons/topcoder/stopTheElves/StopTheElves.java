package marathons.topcoder.stopTheElves;

import java.io.*;
import java.util.*;

public class StopTheElves {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new StopTheElvesTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1,3 -pause -myExec -noVis";
	public static final int TIME_LIMIT = 10000 - 150;
	int n, c, money, turn = -1;
	char[][] grid;

	public String move() {
		int x = 1 + ((turn * (7919)) % (n - 2));
		int y = 1 + ((turn * (50091)) % (n - 2));
		if (money >= c && grid[y][x] == '.') {
			return y + " " + x;
		} else {
			return "-1";
		}
	}

	public void init(int n, int c, double elfP, int money, char[][] grid) {
		this.n = n;
		this.c = c;
		this.grid = new char[n][n];
		update(0, money, grid);
	}

	public void update(long elapsedTimeMillis, int money, char[][] grid) {
		turn++;
		this.money = money;
		for (int i = 0; i < n; i++) {
			System.arraycopy(grid[i], 0, this.grid[i], 0, n);
		}
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
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int C = Integer.parseInt(br.readLine());
		float elfP = Float.parseFloat(br.readLine());
		int money = Integer.parseInt(br.readLine());

		StopTheElves solution = new StopTheElves();
		solution.init(N, C, elfP, money, readGrid(br, N));

		for (int turn = 0; turn < N * N; turn++) {
			System.out.println(solution.move());
			//read elapsed time
			int elapsedTime = Integer.parseInt(br.readLine());
			//read the money
			money = Integer.parseInt(br.readLine());
			//read the updated grid
			solution.update(elapsedTime, money, readGrid(br, N));
		}
	}

	private static char[][] readGrid(BufferedReader br, int N) throws IOException {
		char[][] grid = new char[N][N];
		for (int y = 0; y < N; y++)
			for (int x = 0; x < N; x++)
				grid[y][x] = br.readLine().charAt(0);
		return grid;
	}
}
