package marathons.codecup.y2018_blackhole;

import java.util.*;

public class DarnleyBlackhole {
	static final java.util.concurrent.Callable<Void> EVALUATOR =
			new CaiaEvaluator(); // <=> TESTING
//			null; // <=> SUBMISSION

	static final int N = 8;
	static final int SZ = (N + 2) * (N + 2);
	static final int DEL = 5;
	static final int EMPTY = N * (N + 1) / 2 - DEL;
	static final int MOVES = EMPTY / 2;
	static final int WALL = MOVES + 1;
	static final int[] DELTA = {1, -1, N + 2, -(N + 2), N + 1, -(N + 1)};
	static String[] name = new String[SZ];
	static Map<String, Integer> name2cell = new TreeMap<>();

	boolean first;

	static class Field {
		final int[] f;
		final boolean[] usedMine;
		final boolean[] usedHis;
		final int empties;

		public Field(int[] f) {
			this.f = f.clone();
			usedMine = new boolean[MOVES + 1];
			usedHis = new boolean[MOVES + 1];
			int emptiesCount = 0;
			for (int c = 0; c < f.length; c++) {
				if (f[c] == 0) {
					emptiesCount++;
				} else if (f[c] == WALL) {
				} else if (f[c] > 0) {
					usedMine[f[c]] = true;
				} else {
					usedHis[-f[c]] = true;
				}
			}
			empties = emptiesCount;
		}

		public Field makeMove(Move move) {
			int[] g = new int[f.length];
			for (int i = 0; i < f.length; i++) {
				if (f[i] == WALL) {
					g[i] = WALL;
				} else {
					g[i] = -f[i];
				}
			}
			g[move.cell] = -move.value;
			return new Field(g);
		}
	}

	static class Move {
		int cell;
		int value;

		public Move(int cell, int value) {
			this.cell = cell;
			this.value = value;
		}
	}

	static interface Strategy {
		Move makeMove(Field field);
	}

	static class StupidOrdered implements Strategy {
		@Override
		public Move makeMove(Field field) {
			for (int c = 0; c < SZ; c++) {
				if (field.f[c] != 0) {
					continue;
				}
				int i = 1;
				while (field.usedMine[i]) i++;
				return new Move(c, i);
			}
			throw new AssertionError();
		}
	}

	static class MaximizeBorderSum implements Strategy {
		@Override
		public Move makeMove(Field field) {
			int bestImprovement = Integer.MIN_VALUE;
			int bestC = -1;
			int bestV = -1;
			for (int c = 0; c < SZ; c++) {
				if (field.f[c] != 0) {
					continue;
				}
				// a * x + b
				int borderA = 0;
				int borderB = 0;
				for (int delta : DELTA) {
					int fd = field.f[c + delta];
					if (fd == WALL) {
						continue;
					}
					if (fd != 0) {
						borderB -= fd;
						continue;
					}
					borderA++;
				}
				for (int v = 1; v <= MOVES; v++) {
					if (field.usedMine[v]) {
						continue;
					}
					int improvement = borderA * v + borderB;
					improvement *= MOVES;
					if (borderA == 0) {
						improvement -= v;
					}
					if (improvement > bestImprovement) {
						bestImprovement = improvement;
						bestC = c;
						bestV = v;
					}
				}
			}
			return new Move(bestC, bestV);
		}

	}

	public void run(Strategy strategy) {
		int[] f = new int[SZ];
		Arrays.fill(f, WALL);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N - i; j++) {
				int c = (i + 1) * (N + 2) + j + 1;
				name[c] = (char) ('A' + i) + "" + (1 + j);
				name2cell.put(name[c], c);
				f[c] = 0;
			}
		}
		Scanner in = new Scanner(System.in);
		for (int i = 0; i < DEL; i++) {
			int c = name2cell.get(in.next());
			f[c] = WALL;
		}
		Field gameField = new Field(f);
		for (int t = 0; t < MOVES; t++) {
			String s = in.next();
			if ("Start".equals(s)) {
				first = true;
			} else if ("Quit".equals(s)) {
				break;
			} else {
				int c = name2cell.get(s.substring(0, 2));
				int v = Integer.parseInt(s.substring(3));
				gameField = gameField.makeMove(new Move(c, v));
			}
			Move move = strategy.makeMove(gameField);
			gameField = gameField.makeMove(move);
			System.out.println(name[move.cell] + "=" + move.value);
			System.out.flush();
		}
		in.close();
	}

	static Strategy officialStrategy = new MaximizeBorderSum();

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && EVALUATOR != null) {
			System.out.println("Running evaluator");
			EVALUATOR.call();
			return;
		}
		new DarnleyBlackhole().run(officialStrategy);
	}
}
