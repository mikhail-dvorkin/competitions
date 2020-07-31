package marathons.azspcs.y2019_hexagonalNeighbors;

import java.util.*;

public class HexagonalNeighbors {
	public static final int[] DX = new int[]{1, 1, 0, -1, -1, 0};
	public static final int[] DY = new int[]{0, 1, 1, 0, -1, -1};
	static int n;

	public static void main(String[] args) {
		n = 4;
		Random random = new Random(566);
		Field field = new Field(random);
		Settings settings = new Settings(1 << 11, 1 << 16, 0, Integer.MAX_VALUE, -Double.MAX_VALUE, 32);
		field = (Field) simulatedAnnealing(field, settings, random);
		int[][] f = field.f;
		System.out.println("SA:\t" + field.energy);
		printField(f);
		profileDP(f, 1, 4);
	}

	static void profileDP(int[][] f, int fromRow, int toRow) {
		int h = toRow - fromRow;
		Map<Long, Integer> map = new HashMap<>();
		Map<Long, BitSet> mapHow = new HashMap<>();
		map.put(0L, 0);
		mapHow.put(0L, new BitSet());
//		for (int x = 0; x <= 2 * n + 2; x++) {
//			for (int y = fromRow; y < toRow; y++) {
//				if (f[x][y] == -1) {
//
//				}
//			}
//		}
	}

	static void printField(int[][] f) {
		ArrayList<String> lines = new ArrayList<>();
		int maxLength = 0;
		for (int[] row : f) {
			String line = "";
			for (int x : row) {
				if (x < 0) {
					continue;
				}
				line += x + " ";
			}
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}
			lines.add(line);
			maxLength = Math.max(maxLength, line.length());
		}
		for (String line : lines) {
			for (int i = 0; i < (maxLength - line.length()) / 2; i++) {
				System.out.print(" ");
			}
			System.out.println(line);
		}
	}

	static class Field implements AnnealableWithStepBack {
		int[][] f;
		int energy;
		int lastX, lastY, prevValue, prevEnergy;

		public Field(Random random) {
			f = new int[2 * n + 1][2 * n + 1];
			for (int i = 0; i <= 2 * n; i++) {
				f[0][i] = f[i][0] = f[2 * n][i] = f[i][2 * n] = -1;
			}
			for (int i = 1; i < n; i++) {
				for (int j = 0; j < n - i; j++) {
					f[i][2 * n - 1 - j] = f[2 * n - i][1 + j] = -1;
				}
			}
			for (int i = 1; i < 2 * n; i++) {
				for (int j = 1; j < 2 * n; j++) {
					if (f[i][j] == -1) {
						continue;
					}
					f[i][j] = random.nextInt(6);
				}
			}
			calcEnergy();
		}

		public Field(int[][] f, int energy) {
			this.f = new int[f.length][];
			for (int i = 0; i < f.length; i++) {
				this.f[i] = f[i].clone();
			}
			this.energy = energy;
		}

		public Field(int[][] f) {
			this.f = new int[f.length][];
			for (int i = 0; i < f.length; i++) {
				this.f[i] = f[i].clone();
			}
			calcEnergy();
		}

		void calcEnergy() {
			for (int i = 1; i < 2 * n; i++) {
				for (int j = 1; j < 2 * n; j++) {
					if (f[i][j] == -1) {
						continue;
					}
					energy += localEnergy(i, j);
				}
			}
		}

		@Override
		public double energy() {
			return energy;
		}

		@Override
		public Field randomInstance(Random random) {
			return new Field(random);
		}

		@Override
		public void vary(Random random) {
			int x, y;
			do {
				x = random.nextInt(2 * n);
				y = random.nextInt(2 * n);
			} while (f[x][y] == -1);
			int prevLocalEnergy = localishEnergy(x, y);
			prevValue = f[x][y];
			lastX = x;
			lastY = y;
			f[x][y] = random.nextInt(6);
			int localEnergy = localishEnergy(x, y);
			prevEnergy = energy;
			energy += localEnergy - prevLocalEnergy;
		}

		int localishEnergy(int x, int y) {
			int res = localEnergy(x, y);
			for (int dir = 0; dir < 6; dir++) {
				res += localEnergy(x + DX[dir], y + DY[dir]);
			}
			return res;
		}

		int localEnergy(int x, int y) {
			if (f[x][y] == -1) {
				return 0;
			}
			int want = (1 << f[x][y]) - 1;
			int seen = 0;
			for (int dir = 0; dir < 6; dir++) {
				int v = f[x + DX[dir]][y + DY[dir]];
				if (v == -1) {
					continue;
				}
				seen |= 1 << v;
			}
			seen &= want;
			return (Integer.bitCount(want ^ seen) << 4) - f[x][y];
		}

		@Override
		public void undo() {
			f[lastX][lastY] = prevValue;
			energy = prevEnergy;
		}

		@Override
		public AnnealableWithStepBack cloneAnswer() {
			return new Field(f, energy);
		}

	}

	public static interface Annealable {
		public double energy();
		public Annealable randomInstance(Random random);
	}

	public static interface AnnealableWithoutStepBack extends Annealable {
		public AnnealableWithoutStepBack vary(Random random);
	}

	public static interface AnnealableWithStepBack extends Annealable {
		public void vary(Random random);
		public void undo();
		public AnnealableWithStepBack cloneAnswer();
	}

	public static class Settings {
		public int globalIterations;
		public int iterations;
		public double probStartWithPrevious;
		public int recessionLimit;
		public double desiredEnergy;
		public double temp0;

		public Settings(int globalIterations, int iterations, double probStartWithPrevious, int recessionLimit, double desiredEnergy, double temp0) {
			this.globalIterations = globalIterations;
			this.iterations = iterations;
			this.probStartWithPrevious = probStartWithPrevious;
			this.recessionLimit = recessionLimit;
			this.desiredEnergy = desiredEnergy;
			this.temp0 = temp0;
		}
	}

	public static Annealable simulatedAnnealing(Annealable item, Settings settings, Random r) {
		boolean stepBack = item instanceof AnnealableWithStepBack;
		double energy = item.energy();
		double answerEnergy = Double.MAX_VALUE;
		Annealable answer = null;
		for (int glob = 0; glob != settings.globalIterations; glob++) {
			if (glob > 0 && r.nextDouble() >= settings.probStartWithPrevious) {
				item = item.randomInstance(r);
				energy = item.energy();
			}
			int end = settings.iterations;
			for (int iter = 1, recession = 0;; iter++) {
				if (energy < answerEnergy) {
					answerEnergy = energy;
					if (stepBack) {
						answer = ((AnnealableWithStepBack) item).cloneAnswer();
					} else {
						answer = item;
					}
					if (answerEnergy <= settings.desiredEnergy) {
						return answer;
					}
					end = Math.max(end, iter + settings.iterations);
				}
				if (iter > end) {
					break;
				}
				double nextEnergy;
				AnnealableWithoutStepBack next = null;
				if (stepBack) {
					((AnnealableWithStepBack) item).vary(r);
					nextEnergy = item.energy();
				} else {
					next = ((AnnealableWithoutStepBack) item).vary(r);
					nextEnergy = next.energy();
				}
				double dEnergy = nextEnergy - energy;
				boolean accept;
				if (dEnergy < 0) {
					accept = true;
					recession = 0;
				} else {
					recession++;
					if (recession == settings.recessionLimit) {
						break;
					}
					double barrier = Math.exp(-1.0 * dEnergy * iter / settings.temp0);
					accept = r.nextDouble() < barrier;
				}
				if (accept) {
					if (!stepBack) {
						assert(next != null);
						item = next;
					}
					energy = nextEnergy;
				} else {
					if (stepBack) {
						((AnnealableWithStepBack) item).undo();
					}
				}
			}
		}
		return answer;
	}
}
