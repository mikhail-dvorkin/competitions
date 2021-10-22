package marathons.topcoder.graphLabeling; //TESTING

import java.io.*;
import java.util.*;

public class GraphLabeling {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new GraphLabelingTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1,50 -myExec -myVis";
	public static final long TIME_LIMIT = 10000 - 300;
	public static final int MIN_N = 5, MAX_N = 500;
	public static final double MIN_C = 0.05, MAX_C = 1;

	int n;
	boolean[][] e;
	long[] ans;

	private void solve() {
		_labels.add("n = " + n);
		for (int i = 0; i < n; i++) {
			ans[i] = (1L << i) - 1;
		}
	}

	public long[] solve(boolean[][] e) {
		this.e = e;
		n = e.length;
		ans = new long[n];
		try {
			solve();
		} catch (TimeOutException ignored) {
		}
		_myScore = 1;
		return ans;
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
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(in.readLine());
		int edges = Integer.parseInt(in.readLine());

		boolean[][] graph = new boolean[N][N];

		for (int i = 0; i < edges; i++) {
			String[] temp = in.readLine().split(" ");
			int node1 = Integer.parseInt(temp[0]);
			int node2 = Integer.parseInt(temp[1]);
			graph[node1][node2] = true;
			graph[node2][node1] = true;
		}

		long[] ans = new GraphLabeling().solve(graph);

		StringBuilder out = new StringBuilder();
		for (int i = 0; i < N; i++)
			out.append(ans[i]).append(" ");

		System.out.println(out);
		System.out.flush();
	}
}
