package marathons.topcoder.graphLabeling; //TESTING

import java.io.*;
import java.util.*;

public class GraphLabeling {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new GraphLabelingTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1,3 -myExec -myVis #myGen";
	public static final long TIME_LIMIT = 10000 - 300;
	public static final int MIN_N = 5, MAX_N = 500;
	public static final double MIN_C = 0.05, MAX_C = 1;

	int n, edges;
	double density;
	boolean[][] e;
	int[][] nei;
	int[] degree;
	int[] ans;
	int ansWidth;
	int[][] rulers = rulers();

	private void solve() {
		ans = rulers[n];
		ansWidth = ans[n - 1];
		int m = n;
		boolean won0 = false;
		indices = new Integer[n];
		periphery = new double[n];
		tick = new int[n];
		isBase = new boolean[n];
		isAssigned = new boolean[n];
		baseAssignments = new int[n];

		int step = (int) Math.round(2 / density);
		//noinspection InfiniteLoopStatement
		while (true) {
			checkTimeLimit();
			while (step > 1 && (m - step < 1 || rulers[m - step][m - step - 1] + 1 < n)) step--;
//			_labels.add(m - step + "");
			// d=45 -> 90%, d=25,65 -> 10%
			double probFormingBase = 0.05 + 0.45 * Math.min(Math.max((density - 0.25) / 0.5, 0), 1);
			if (won0) probFormingBase /= 2;
			int solver = rnd.nextDouble() < probFormingBase ? 1 : 0;
			int noise = rnd.nextInt(1 + rnd.nextInt(4));
			int solve = (solver == 0 ? solveRandomPrepositionedBase(m - step, noise) : solveFormingBase(m - 1, noise));
			if (solve != Integer.MAX_VALUE) {
//				_labels.add(t + ")_S" + solver + "_N" + noise + "_" + m + ">" + (m - step));
				m -= (solver == 0 ? step : 1);
				if (solver == 0) won0 = true;
				step = Math.min(step + 1 + step / 3, 64);
			} else {
				step--;
				step -= step / 8;
				if (step < 1) step = 1;
			}
		}
	}

	int solveRandomPrepositionedBase(int m, int noise) {
		int added = n - m;
		int[] baseTicks = rulers[m];
		int width = baseTicks[m - 1];
		if (width >= ansWidth) return Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			periphery[i] = degree[i] + (0.5 + noise) * rnd.nextDouble();
			indices[i] = i;
		}
		Arrays.sort(indices, Comparator.comparingDouble(a -> periphery[a]));
		Arrays.fill(tick, -1);
		{
			ArrayList<Integer> temp = new ArrayList<>();
			for (int x : baseTicks) temp.add(x);
			Collections.shuffle(temp, rnd);
			for (int i = 0; i < m; i++) {
				tick[indices[added + i]] = temp.get(i);
			}
		}
		BitSet seenDiffs = new BitSet();
		for (int i = 0; i < m; i++) {
			int ii = indices[added + i];
			int tickII = tick[ii];
			for (int u : nei[ii]) {
				int tickU = tick[u];
				if (tickU == -1) continue;
				seenDiffs.set(Math.abs(tickII - tickU));
			}
		}
		BitSet usedTicks = new BitSet();
		for (int j = 0; j < m; j++) {
			usedTicks.set(tick[indices[added + j]]);
		}
		for (int i = added - 1;; i--) {
			checkTimeLimit();
			int v = indices[i];
			int shift = rnd.nextInt(width);
			for (int j = 0; j < width; j++) {
				int t = (shift + j) % width;
				if (usedTicks.get(t)) continue;
				boolean goodT = true;
				for (int u : nei[v]) {
					if (tick[u] == -1) continue;
					int diff = Math.abs(t - tick[u]);
					int badSymmetryTick = 2 * t - tick[u];
					boolean badSymmetry = badSymmetryTick >= 0 && usedTicks.get(badSymmetryTick);
					if (seenDiffs.get(diff) || badSymmetry) {
						goodT = false;
						break;
					}
				}
				if (goodT) {
					tick[v] = t;
					break;
				}
			}
			if (tick[v] == -1) {
				return Integer.MAX_VALUE;
			}
			if (i == 0) break;
			for (int u : nei[v]) {
				if (tick[u] == -1) continue;
				int diff = Math.abs(tick[v] - tick[u]);
				seenDiffs.set(diff);
			}
			usedTicks.set(tick[v]);
		}
		ans = tick.clone();
		ansWidth = width;
		return width;
	}

	Integer[] indices;
	double[] periphery;
	int[] tick;
	boolean[] isBase;
	boolean[] isAssigned;
	int[] baseAssignments;

	int solveFormingBase(int m, int noise) {
		int added = n - m;
		int[] baseTicks = rulers[m];
		int width = baseTicks[m - 1];
		if (width >= ansWidth) return Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			periphery[i] = degree[i] + (0.5 + noise) * rnd.nextDouble();
			indices[i] = i;
		}
		Arrays.sort(indices, Comparator.comparingDouble(a -> periphery[a]));
		Arrays.fill(tick, -1);
		Arrays.fill(isBase, false);
		Arrays.fill(isAssigned, false);
		for (int i = 0; i < m; i++) {
			isBase[indices[added + i]] = true;
		}
		BitSet allBaseDiffs = new BitSet();
		BitSet addedDiffs = new BitSet();
		BitSet usedTicks = new BitSet();
		for (int i = 0; i < m; i++) {
			usedTicks.set(baseTicks[i]);
			for (int j = 0; j < i; j++) {
				allBaseDiffs.set(baseTicks[i] - baseTicks[j]);
			}
		}
		int[] baseAssignments = new int[m];
		int baseAssignmentsCount = 0;

		for (int i = added - 1; i >= 0; i--) {
			checkTimeLimit();
			int v = indices[i];
			int shift = rnd.nextInt(width);
			int freeDegree = degree[v];
			ArrayList<Integer> assignedNeighboursTicks = new ArrayList<>();
			for (int u : nei[v]) {
				if (isBase[u]) {
					if (tick[u] >= 0) {
						assignedNeighboursTicks.add(tick[u]);
						freeDegree--;
					}
				} else {
					assignedNeighboursTicks.add(tick[u]);
				}
			}
			for (int j = 0; j < width && j < 1 << 10; j++) {
				int t = (shift + j) % width;
				if (usedTicks.get(t)) continue;
				boolean badT = false;
				for (int assignedNeighboursTick : assignedNeighboursTicks) {
					int diff = Math.abs(assignedNeighboursTick - t);
					int badSymmetryTick = 2 * t - assignedNeighboursTick;
					boolean badSymmetry = badSymmetryTick >= 0 && usedTicks.get(badSymmetryTick);
					if (allBaseDiffs.get(diff) || addedDiffs.get(diff) || badSymmetry) {
						badT = true;
						break;
					}
				}
				if (badT) continue;

				HashSet<Integer> addableDiffs = new HashSet<>();
				for (int k = 0; k < m; k++) {
					if (isAssigned[k]) continue;
					int diff = Math.abs(t - baseTicks[k]);
					if (allBaseDiffs.get(diff) || addedDiffs.get(diff)) continue;
					addableDiffs.add(diff);
				}
				boolean goodT = addableDiffs.size() >= freeDegree;
				if (goodT) {
					tick[v] = t;
					break;
				}
			}
			if (tick[v] == -1) {
//				_labels.add(i + "/" + added);
				return Integer.MAX_VALUE;
			}
			usedTicks.set(tick[v]);
			int neiVIndex = 0;
			int k = 0;
			for (int u : nei[v]) {
				if (tick[u] >= 0) {
					int diff = Math.abs(tick[v] - tick[u]);
					addedDiffs.set(diff);
					continue;
				}
				if (!isBase[u]) continue;
				int diff;
				while (true) {
					boolean goodK = true;
					diff = Math.abs(tick[v] - baseTicks[k]);
					if (allBaseDiffs.get(diff) || addedDiffs.get(diff)) goodK = false;
					if (isAssigned[k]) goodK = false;
					if (goodK) break;
					k++;
				}
				tick[u] = baseTicks[k];
				isAssigned[k] = true;
				addedDiffs.set(diff);
				baseAssignments[baseAssignmentsCount] = u;
				if (i > 0) for (int j = 0; j < baseAssignmentsCount; j++) {
					int w = baseAssignments[j];
					if (e[u][w]) continue;
					allBaseDiffs.clear(Math.abs(tick[u] - tick[w]));
				}
				baseAssignmentsCount++;
			}
		}
		for (int i = 0, k = 0; i < m; i++) {
			int v = indices[added + i];
			if (tick[v] >= 0) continue;
			while (isAssigned[k]) k++;
			tick[v] = baseTicks[k];
			isAssigned[k] = true;
		}
		ans = tick.clone();
		ansWidth = width;
		return width;
	}

	static int[][] rulers() {
		int[][] rulers = new int[rulersStr.length][];
		for (int i = 0; i < rulers.length; i++) {
			rulers[i] = new int[i];
			String[] s = rulersStr[i].split(",");
			for (int j = 1; j < i; j++) {
				rulers[i][j] = rulers[i][j - 1] + Integer.parseInt(s[j - 1], 36);
			}
		}
		return rulers;
	}

	public int[] solve(boolean[][] e) {
		this.e = e;
		n = e.length;
		degree = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (e[i][j]) {
					degree[i]++;
					degree[j]++;
					edges++;
				}
			}
		}
		nei = new int[n][];
		for (int i = 0; i < n; i++) {
			nei[i] = new int[degree[i]];
			for (int j = 0, k = 0; j < n; j++) {
				if (e[i][j]) {
					nei[i][k++] = j;
				}
			}
		}
		density = edges / (n * (n - 1) / 2.0);
		if (!SUBMIT) {
			_labels.add("w0 = " + rulers[n][n - 1]);
			_labels.add("n = " + n);
			_labels.add("d = " + Math.round(density * 100));
			_labels.add("E = " + edges);
		}
		try {
			solve();
		} catch (TimeOutException ignored) {
		}
		_myScore = 100.0 * edges / ansWidth;
		return ans;
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

		int[] ans = new GraphLabeling().solve(graph);

		StringBuilder out = new StringBuilder();
		for (int i = 0; i < N; i++)
			out.append(ans[i]).append(" ");

		System.out.println(out);
		System.out.flush();
	}

	public static final String[] rulersStr = new String[]{
			// GENERATED
			// https://pastebin.com/9fhwvJAe
	};
}
