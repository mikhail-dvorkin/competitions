package marathons.topcoder.hardestMaze;

import java.io.*;
import java.util.*;

public class HardestMaze {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new HardestMazeTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1,3";
	private static final long TIME_LIMIT = 9850;

	private void solve() {
		ans = new char[n * n];
		for (int i = 0; i < n * n; i++) ans[i] = '.';
	}

	int n, robots, targets;
	int[][] start;
	int[][][] target;
	char[] ans;

	public char[] findSolution(int N, int R, int T, int[][] Starts, int[][][] Targets) {
		n = N; robots = R; targets = T; start = Starts; target = Targets;
		try {
			solve();
		} catch (TimeOutException ignored) {
		}
		return ans;
	}

	@SuppressWarnings("serial")
	private static class TimeOutException extends RuntimeException {
	}

	private void checkTimeLimit() {
		if (timePassed() >= 1) {
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
	private final Random rnd = new Random(566);

	public static void main(String[] args) throws Exception {
		if (EVALUATOR != null) {
			EVALUATOR.call();
			return;
		}
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			int N = Integer.parseInt(br.readLine());
			int R = Integer.parseInt(br.readLine());
			int T = Integer.parseInt(br.readLine());

			int[][] Starts=new int[R][2];
			int[][][] Targets=new int[R][T][2];
			for (int i=0; i<R; i++) {
				String[] temp = br.readLine().split(" ");
				Starts[i][0]=Integer.parseInt(temp[0]);
				Starts[i][1]=Integer.parseInt(temp[1]);
				for (int k=0; k<T; k++) {
					String[] temp2 = br.readLine().split(" ");
					Targets[i][k][0]=Integer.parseInt(temp2[0]);
					Targets[i][k][1]=Integer.parseInt(temp2[1]);
				}
			}

			HardestMaze program = new HardestMaze();
			char[] ret = program.findSolution(N, R, T, Starts, Targets);

			System.out.println(ret.length);
			for (char c : ret) System.out.println(c);
		}
	}
}
