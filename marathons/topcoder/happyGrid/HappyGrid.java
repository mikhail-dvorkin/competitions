package marathons.topcoder.happyGrid; //TESTING

import java.util.*;

public class HappyGrid {
	private static final Object EVALUATOR =
			new HappyGridTester(); //TESTING
//			null; //SUBMISSION
//	public static final String EVALUATOR_PARAMETERS = "-seed 1 -myExec -delay 1 -noVis";
//	public static final String EVALUATOR_PARAMETERS = "-seed 1,10 -myExec -noVis -noPrettyPrint";
	public static final String EVALUATOR_PARAMETERS = "-seed 1,100 -noanimate -myExec -delay 1 -saveVis pics~";
	public static final int TIME_LIMIT = 10000 - 600;
	@SuppressWarnings("ConstantValue")
	static boolean noPrettyPrint = EVALUATOR_PARAMETERS.contains("-noPrettyPrint");
	boolean hardcore = true;//SUBMIT;
	int magicMaxStripe = 5;
	int magicMaxProfilesNG = 1 << 14;//Integer.MAX_VALUE;

	ArrayList<String> ans = new ArrayList<>();
	int n, colors, group, multiplier, idealGroups, gotGroups, rememberedGotGroups, nonWallCount, failedColoring, ansScore, attempt;
	int[][] field;
	int[] colorCount;
	boolean[][] wall;
	int[][][][] dist;
	int[][] groupId, rememberedGroups;
	int[][] desired;
	int[] colorAssignment;

	void solve() {
		logMap("Need ", String.valueOf(idealGroups));
		if (colors == 2) {
			solveTwoColors(colorCount);
			movePebbles("solveTwo");
		}
		if (group == 2) {
			for (attempt = 0; attempt < 4; attempt++) {
				selectGroupsOfSizeTwo();
			}
		}
		attempt = group;
		int bestGen = selectAndSolveGroups();
		if (colors > 2) {
			for (attempt = 1; attempt >= 0; attempt--) {
				selectGroupsProfileDynamics();
			}
		}
		for (attempt = 0; hardcore; attempt++) {
			if (colors > 2 || attempt < 2 || attempt > 10) {
				groupId = rememberedGroups; gotGroups = rememberedGotGroups;
				solveGroups("Re" + attempt);
			}
			if (colors == 2) {
				magicMaxStripe++;
				if (magicMaxStripe <= 12) {
					solveTwoColors(colorCount);
					movePebbles("solveTwo" + magicMaxStripe);
				}
			}
			if (group == 2) {
				selectGroupsOfSizeTwo();
			}
			if (attempt < 2 || bestGen >= ansScore - 100) {
				bestGen = Math.max(bestGen, selectAndSolveGroups());
			}
		}
	}

	int solveGroups(String solution) {
		prettyPrintGroupId();
		checkTimeLimit();
		colorGroups();
		checkTimeLimit();
		prettyPrintColorAssignment();
		checkTimeLimit();
		colorLeftover();
		if (!hardcore) {
			log("Got " + gotGroups);
			log("of size " + group + ",\t#colors: " + colors + ",\t");
			log("Uncolor "+ failedColoring);
		}
		return movePebbles(solution);
	}

	int selectAndSolveGroups() {
		checkTimeLimit();
		groupId = new int[n][n];
		int magicGroupThrows = attempt % 16 + 1;//group;
		int magicFurther = group;//mode % 2 == 0 ? group : 1;
		boolean[][] mark = new boolean[n][];
		int[][] nei = new int[n][n];
		for (int y = 0; y < n; y++) {
			mark[y] = wall[y].clone();
			Arrays.fill(groupId[y], -1);
		}
		gotGroups = nonWallCount / group;
		for (int iter = 0; iter < gotGroups; iter++) {
			int minNei = 5;
			for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
				if (mark[y][x]) continue;
				nei[y][x] = 0;
				for (int d = 0; d < 4; d++) {
					int yy = y + DY[d], xx = x + DX[d];
					if (yy < 0 || yy >= n || xx < 0 || xx >= n || mark[yy][xx]) continue;
					nei[y][x]++;
				}
				if (nei[y][x] < minNei) {
					minNei = nei[y][x];
				}
			}
			if (minNei == 5) {
				gotGroups = iter;
				break;
			}
			double bestScore = -1e99;
			TreeSet<Integer> bestGroup = null;
			boolean didKillCavity = false;
			for (int y0 = 0; y0 < n; y0++) for (int x0 = 0; x0 < n; x0++) for (int t = 0; t < magicGroupThrows; t++) {
				checkTimeLimit();
				if (mark[y0][x0] || nei[y0][x0] != minNei) continue;
				int y9 = (int) (y0 + Math.round(rnd.nextGaussian() * magicFurther));
				int x9 = (int) (x0 + Math.round(rnd.nextGaussian() * magicFurther));
				TreeSet<Integer> possible = new TreeSet<>();
				TreeSet<Integer> taken = new TreeSet<>();
				TreeMap<Double, Integer> possibleScored = new TreeMap<>();
				possible.add(encode(y0, x0));
				possibleScored.put(scoreClosenessTo9(y0, x0, y9, x9), encode(y0, x0));
				for (int k = 0; k < group; k++) {
					if (possible.isEmpty()) break;
					int yx = possibleScored.pollFirstEntry().getValue();
					taken.add(yx);
					possible.remove(yx);
					int y = decodeY(yx), x = decodeX(yx);
					for (int d = 0; d < 4; d++) {
						int yy = y + DY[d], xx = x + DX[d];
						if (yy < 0 || yy >= n || xx < 0 || xx >= n || mark[yy][xx]) continue;
						int neiYX = encode(yy, xx);
						if (taken.contains(neiYX) || possible.contains(neiYX)) continue;
						possible.add(neiYX);
						possibleScored.put(scoreClosenessTo9(yy, xx, y9, x9), neiYX);
					}
				}
				if (taken.size() != group) {
					didKillCavity = true;
					for (int yx : taken) {
						int y = decodeY(yx), x = decodeX(yx);
						mark[y][x] = true;
					}
					continue;
				}
				for (int yx : taken) {
					int y = decodeY(yx), x = decodeX(yx);
					mark[y][x] = true;
				}
				double score = scoreFreeSpace(mark);
				if (score > bestScore) {
					bestScore = score;
					bestGroup = taken;
				}
				for (int yx : taken) {
					int y = decodeY(yx), x = decodeX(yx);
					mark[y][x] = false;
				}
			}
			if (bestGroup == null) {
				if (didKillCavity) {
					iter--;
					continue;
				}
				assert false;
			}
			for (int yx : bestGroup) {
				int y = decodeY(yx), x = decodeX(yx);
				mark[y][x] = true;
				groupId[y][x] = iter;
			}
		}
		if (!hardcore && gotGroups < idealGroups) logBad("Gro");
		return solveGroups("solveGen" + attempt);
	}

	void selectGroupsOfSizeTwo() {
		checkTimeLimit();
		assert group == 2;
		groupId = new int[n][n];
		for (int y = 0; y < n; y++) {
			Arrays.fill(groupId[y], -1);
		}
		int m = (n * n / 2) + 1;
		int[][] e = new int[m][m];
		for (int i = 0; i < m; i++) {
			Arrays.fill(e[i], 1000);
		}
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if ((x + y) % 2 == 1 || wall[y][x]) continue;
			int id1 = (y * n + x) / 2;
			for (int d = 0; d < 4; d++) {
				int yy = y + DY[d], xx = x + DX[d];
				if (yy < 0 || yy >= n || xx < 0 || xx >= n || wall[yy][xx]) continue;
				int id2 = (yy * n + xx) / 2;
				if (attempt <= 2) {
					e[id1][id2] = (d % 2) * (attempt - 1);
				} else {
					e[id1][id2] = rnd.nextInt(10);
				}
			}
		}
		int[] assignment = hungarian(e);
		gotGroups = 0;
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if ((x + y) % 2 == 1 || wall[y][x]) continue;
			int id1 = (y * n + x) / 2;
			for (int d = 0; d < 4; d++) {
				int yy = y + DY[d], xx = x + DX[d];
				if (yy < 0 || yy >= n || xx < 0 || xx >= n || wall[yy][xx]) continue;
				int id2 = (yy * n + xx) / 2;
				if (assignment[id1] == id2) {
					groupId[y][x] = gotGroups;
					groupId[yy][xx] = gotGroups;
					gotGroups++;
				}
			}
		}
		solveGroups("solveMtc" + attempt);
	}

	void colorGroups() {
		int m = gotGroups;
		@SuppressWarnings("unchecked")
		TreeSet<Integer>[] nei = new TreeSet[m];
		for (int i = 0; i < m; i++) {
			nei[i] = new TreeSet<>();
		}
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) for (int d = 0; d < 2; d++) {
			int g = groupId[y][x];
			if (g == -1) continue;
			int yy = y + DY[d], xx = x + DX[d];
			if (yy >= n || xx >= n) continue;
			int gg = groupId[yy][xx];
			if (gg == -1 || gg == g) continue;
			nei[g].add(gg); nei[gg].add(g);
		}
		int[] toAssign = new int[colors];
		for (int i = 0; i < colors; i++) {
			toAssign[i] = colorCount[i] / group;
		}
		colorAssignment = new int[m];
		int[] seen = new int[colors + 1];
		int seenTime = 0;
		Arrays.fill(colorAssignment, -1);
		for (int iter = 0; iter < m; iter++) {
			int bestScore = Integer.MIN_VALUE;
			int bestV = -1;
			int shift = rnd.nextInt(m);
			for (int vv = 0; vv < m; vv++) {
				int v = (shift + vv) % m;
				if (colorAssignment[v] >= 0) continue;
				int degree = 0;
				seenTime++;
				for (int u : nei[v]) {
					if (colorAssignment[u] == -1) {
						degree++;
					} else {
						seen[colorAssignment[u]] = seenTime;
					}
				}
				int diffNei = 0;
				for (int i = 0; i < colors + 1; i++) {
					if (seen[i] == seenTime) diffNei++;
				}
				int score = diffNei * 10000 + degree;
				if (score > bestScore) {
					bestScore = score;
					bestV = v;
				}
			}

			seenTime++;
			for (int u : nei[bestV]) {
				if (colorAssignment[u] >= 0) {
					seen[colorAssignment[u]] = seenTime;
				}
			}
			bestScore = Integer.MIN_VALUE;
			int bestColor = colors;
			for (int c = 0; c < colors; c++) {
				int score = toAssign[c];
				if (seen[c] == seenTime) score -= 1000;
				if (toAssign[c] == 0) score = Integer.MIN_VALUE;
				if (score > bestScore) {
					bestScore = score;
					bestColor = c;
				}
			}
			if (bestColor < colors) toAssign[bestColor]--;
			if (bestScore < 0 && bestScore > Integer.MIN_VALUE) {
				failedColoring++;
				if (failedColoring == 1 && !SUBMIT) {
					colorAssignment[bestV] = -2;
					prettyPrintColorAssignment();
				}
			}
			colorAssignment[bestV] = bestColor;
		}
		if (!hardcore && colors >= 4 && failedColoring > 0) logBad("Col");
		improveColoring(nei);
	}

	void improveColoring(TreeSet<Integer>[] nei) {
		int m = nei.length;
		int[][] quality = new int[m][colors + 1];
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if (wall[y][x] || groupId[y][x] < 0) continue;
			quality[groupId[y][x]][field[y][x]] += 3;
			for (int d = 0; d < 4; d++) {
				int yy = y + DY[d], xx = x + DX[d];
				if (yy < 0 || yy >= n || xx < 0 || xx >= n || wall[yy][xx]) continue;
				quality[groupId[y][x]][field[yy][xx]] += 1;
			}
		}
		int coefBadColor = 4 * group;
		boolean annealing = attempt > 4 && attempt % 3 > 0;
		double temp0 = 4 * Math.pow(3, rnd.nextInt(5));
		int iters = 16 * m * m;
		int energy = 0;
		int[] best = colorAssignment.clone();
		int bestEnergy = 0;
		int unimproved = 0;
		for (int iter = 0; iter < iters; iter++) {
			if (unimproved == m * m) break;
			int v, u;
			if (annealing) {
				v = rnd.nextInt(m); u = rnd.nextInt(m);
			} else {
				v = iter / m % m; u = iter % m;
			}
			if (colorAssignment[u] == colorAssignment[v]) continue;
			int badV = 0, newBadV = 0;
			for (int w : nei[v]) {
				if (colorAssignment[w] == colorAssignment[v]) badV++;
				if (colorAssignment[w] == colorAssignment[u]) newBadV++;
			}
			int badU = 0, newBadU = 0;
			for (int w : nei[u]) {
				if (colorAssignment[w] == colorAssignment[u]) badU++;
				if (colorAssignment[w] == colorAssignment[v]) newBadU++;
			}
			int old = (badV + badU) * coefBadColor - quality[v][colorAssignment[v]] - quality[u][colorAssignment[u]];
			int nov = (newBadV + newBadU) * coefBadColor - quality[v][colorAssignment[u]] - quality[u][colorAssignment[v]];
			int dEnergy = nov - old;

			if (dEnergy < 0 || annealing && rnd.nextDouble() < Math.exp(-1.0 * dEnergy * temp0 * iter / iters)) {
				int t = colorAssignment[v];
				colorAssignment[v] = colorAssignment[u];
				colorAssignment[u] = t;
				energy += dEnergy;
				unimproved = 0;
				if (energy < bestEnergy) {
					bestEnergy = energy;
					best = colorAssignment.clone();
				}
				checkTimeLimit();
			} else {
				unimproved++;
			}
		}
		colorAssignment = best;
	}

	void colorLeftover() {
		desired = new int[n][n];
		int[] toAssign = colorCount.clone();
		for (int c : colorAssignment) {
			if (c == colors) continue;
			toAssign[c] -= group;
		}
		ArrayList<Integer> leftover = new ArrayList<>();
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if (wall[y][x]) {
				desired[y][x] = -1;
				continue;
			}
			if (groupId[y][x] >= 0 && colorAssignment[groupId[y][x]] < colors) {
				desired[y][x] = colorAssignment[groupId[y][x]];
				continue;
			}
			leftover.add(encode(y, x));
			desired[y][x] = colors;
		}

		int m = leftover.size();
		int[][] e = new int[m][m];
		int[] rightColor = new int[m];
		int j = 0;
		for (int i = 0; i < colors; i++) {
			for (int k = 0; k < toAssign[i]; k++) {
				rightColor[j++] = i;
			}
		}
		assert j == m;
		for (int v = 0; v < m; v++) for (int u = 0; u < m; u++) {
			int y = decodeY(leftover.get(v)), x = decodeX(leftover.get(v));
			int c = rightColor[u];
			TreeSet<Integer> dangerousNei = new TreeSet<>();
			for (int d = 0; d < 4; d++) {
				int yy = y + DY[d], xx = x + DX[d];
				if (yy < 0 || yy >= n || xx < 0 || xx >= n || desired[yy][xx] != c) continue;
				dangerousNei.add(groupId[yy][xx]);
			}
			e[v][u] = 1000 * dangerousNei.size() - (field[y][x] == c ? 1 : 0);
		}
		int[] assignment = hungarian(e);
		for (int v = 0; v < m; v++) {
			int y = decodeY(leftover.get(v)), x = decodeX(leftover.get(v));
			if (!hardcore && colors >= 4 && e[v][assignment[v]] > 0) logBad("Lef");
			int c = rightColor[assignment[v]];
			desired[y][x] = c;
		}
	}

	void assertCorrectAmount() {
		int[] f = new int[colors];
		int[] d = new int[colors];
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if (wall[y][x]) continue;
			f[field[y][x]]++;
			d[desired[y][x]]++;
		}
		for (int c = 0; c < colors; c++) {
			assert colorCount[c] == f[c];
			assert colorCount[c] == d[c];
		}
	}

	int movePebbles(String solution) {
		assertCorrectAmount();
		ArrayList<String> moves = new ArrayList<>();
		int yCenter = -1, xCenter = -1;
		int[][] f = new int[n][n];
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			f[y][x] = field[y][x];
			if (wall[y][x]) continue;
			if (Math.hypot(y - (n - 1) / 2.0, x - (n - 1) / 2.0) < Math.hypot(yCenter - (n - 1) / 2.0, xCenter - (n - 1) / 2.0)) {
				yCenter = y; xCenter = x;
			}
		}
		int[][] dCenter = dist[yCenter][xCenter];
		int maxDist = 0;
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if (wall[y][x]) continue;
			maxDist = Math.max(maxDist, dCenter[y][x]);
		}
		int[] queue = new int[n * n];
		boolean[][] mark = new boolean[n][n];
		int[][] prev = new int[n][n];
		for (int layer = maxDist; layer > 0; layer--) {
			for (int y0 = 0; y0 < n; y0++) for (int x0 = 0; x0 < n; x0++) {
				if (wall[y0][x0] || dCenter[y0][x0] != layer) continue;
				for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
					mark[y][x] = wall[y][x] || dCenter[y][x] > layer;
					if (dCenter[y][x] == layer) {
						if (y < y0 || y == y0 && x <= x0) mark[y][x] = true;
					}
				}
				queue[0] = encode(y0, x0);
				int low = 0, high = 1;
				while (low < high) {
					int y = decodeY(queue[low]), x = decodeX(queue[low]);
					if (f[y][x] == desired[y0][x0]) break;
					low++;
					for (int d = 0; d < 4; d++) {
						int yy = y + DY[d], xx = x + DX[d];
						if (yy < 0 || yy >= n || xx < 0 || xx >= n || mark[yy][xx]) continue;
						mark[yy][xx] = true;
						prev[yy][xx] = encode(y, x);
						queue[high++] = encode(yy, xx);
					}
				}
				assert low < high;
				int y = decodeY(queue[low]), x = decodeX(queue[low]);
				while (y != y0 || x != x0) {
					int yy = decodeY(prev[y][x]), xx = decodeX(prev[y][x]);
					moves.add(move(y, x, yy, xx));
					int t = f[y][x]; f[y][x] = f[yy][xx]; f[yy][xx] = t;
					y = yy; x = xx;
				}
				assert f[y0][x0] == desired[y0][x0];
			}
		}
		if (!SUBMIT) {
			int diff = 0;
			for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
				if (wall[y][x]) continue;
				if (desired[y][x] != f[y][x]) diff++;
			}
			assert diff == 0;
		}
		int calcComponents = calcComponents();
		int score = calcComponents * multiplier - moves.size();
		logMap(solution, String.valueOf(score));
		if (score > ansScore) {
			ansScore = score;
			ans = moves;
			rememberedGroups = new int[n][];
			for (int y = 0; y < n; y++) rememberedGroups[y] = groupId[y].clone();
			rememberedGotGroups = calcComponents;
		}
		return score;
	}

	void solveTwoColors(int[] toAssign) {
		checkTimeLimit();
		toAssign = toAssign.clone();
		int maxHei = magicMaxStripe;
		int stripes = 1;
		while (stripes * maxHei < n) stripes++;
		int[] yStart = new int[stripes + 1];

		desired = new int[n][n];
		for (int y = 0; y < n; y++) {
			Arrays.fill(desired[y], -1);
		}

		for (int k = 0; k <= stripes; k++) {
			yStart[k] = n * k / stripes;
		}
		for (int k = 0; k < stripes; k++) {
			int yFrom = yStart[k], yTo = yStart[k + 1];
			int freeSpace = 0;
			for (int x = 0; x < n; x++) for (int y = yFrom; y < yTo; y++) {
				if (wall[y][x]) continue;
				assert desired[y][x] == -1;
				freeSpace++;
			}
			int[] toAssignHere = new int[2];
			toAssignHere[0] = (int) Math.round(1.0 * toAssign[0] * freeSpace / (toAssign[0] + toAssign[1]));
			toAssignHere[1] = freeSpace - toAssignHere[0];
			toAssign[0] -= toAssignHere[0]; toAssign[1] -= toAssignHere[1];
			solveTwoColorsStripe(yFrom, yTo, toAssignHere, true);
		}
	}

	void solveTwoColorsStripe(int yFrom, int yTo, int[] toAssign, boolean allowSkip) {
		checkTimeLimit();
		int magicDiffZ = Math.max(15, 3 * group);
		int magicMaxProfiles = magicMaxProfilesNG / n / group;
		int hei = yTo - yFrom;
		int[] taboo = tabooAbove(yFrom - 1);
		col = new int[hei];
		which = new int[hei];
		size = new int[hei];
		Arrays.fill(col, -1);
		Arrays.fill(which, -1);
		Arrays.fill(size, -1);
		ArrayList<HashMap<Long, Integer>> dp = new ArrayList<>();
		dp.add(new HashMap<>());
		dp.get(0).put(encodeMask(), 0);
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<Long, Long>>[][] how = new ArrayList[hei][n + 1];
		int freeSpace = 0;
		for (int x = 0; x <= n; x++) {
			for (int y = 0; y < hei; y++) {
				checkTimeLimit();
				ArrayList<HashMap<Long, Integer>> dpNew = new ArrayList<>();
				ArrayList<HashMap<Long, Long>> dpNewHow = new ArrayList<>();
				how[y][x] = dpNewHow;
				for (int i = 0; i <= dp.size(); i++) {
					dpNew.add(new HashMap<>());
					dpNewHow.add(new HashMap<>());
				}
				int forbiddenAbove = -2;
				if (y == 0 && yFrom > 0 && x < n && !wall[yFrom - 1][x]) {
					forbiddenAbove = desired[yFrom - 1][x];
				}
				boolean emptyBelow = y == hei - 1 && yTo < n && x < n && !wall[yTo][x];
//				int borderBelow = -1;
//				if (y == hei - 1 && yTo < n && x < n && !wall[yTo][x]) {
//					borderBelow = desired[yTo][x];
//				}
				boolean wallHere = (x == n) || wall[yFrom + y][x];
				if (!wallHere) freeSpace++;
				for (int iter = (wallHere ? 1 : 0); iter < 2; iter++) {
					int here = wallHere ? -1 : iter;
					if (here == forbiddenAbove && taboo[x] > 0 && taboo[x] <= group) continue;
					for (int z = 0; z < dp.size(); z++) {
						int newZ = z + (here == 0 ? 1 : 0);
						if (Math.abs(2 * newZ - freeSpace) > magicDiffZ) continue;
						HashMap<Long, Integer> dpZ = dp.get(z);
						HashMap<Long, Integer> dpNewZ = dpNew.get(newZ);
						HashMap<Long, Long> dpHowNewZ = dpNewHow.get(newZ);
						int take = Math.min(dpZ.size(), magicMaxProfiles);
						Collection<Map.Entry<Long, Integer>> collection;
						if (take < dpZ.size()) {
							ArrayList<Map.Entry<Long, Integer>> temp = new ArrayList<>(dpZ.entrySet());
							temp.sort((o1, o2) -> o2.getValue() - o1.getValue());
							collection = temp.subList(0, take);
						} else {
							collection = dpZ.entrySet();
						}
						for (Map.Entry<Long, Integer> entry : collection) {
							long mask = entry.getKey();
							int newScore = entry.getValue();
							decodeMask(mask);
							if (col[y] >= 0 && which[y] == y && col[y] != here) {
								int newId = -1;
								for (int yy = y + 1; yy < which.length; yy++) {
									if (which[yy] == y) {
										newId = yy;
										break;
									}
								}
								if (newId == -1) {
									if (size[y] == group) {
										newScore += 1_000_000;
									} else if (size[y] < group && emptyBelow) {
										newScore += size[y] * size[y];
									} else {
										newScore -= 1000 * size[y];
									}
									col[y] = -1;
									which[y] = -1;
									size[y] = -1;
								} else {
									for (int yy = y + 1; yy < which.length; yy++) {
										if (which[yy] == y) {
											which[yy] = newId;
										}
									}
									size[newId] = size[y];
									size[y] = 0;
								}
							}
							if (!wallHere) {
								boolean sameAsAbove = y > 0 && col[y - 1] == here;
								boolean sameAsLeft = col[y] == here;
								int sizeHere = 1;
//								if (here == borderAbove) sizeHere++;
//								if (here == borderBelow) sizeHere++;
								if (here == forbiddenAbove) {
									assert taboo[x] != 0;
									if (taboo[x] < 0) {
										sizeHere -= taboo[x];
									} else {
										sizeHere += group;
									}
								}
								if (!sameAsAbove && !sameAsLeft) {
									which[y] = y;
									size[y] = Math.min(sizeHere, group + 1);
								} else if (sameAsAbove && sameAsLeft) {
									if (which[y - 1] == which[y]) {
										size[which[y]] = Math.min(size[which[y]] + sizeHere, group + 1);
									} else {
										int w = Math.min(which[y - 1], which[y]);
										int ww = w ^ which[y - 1] ^ which[y];
										size[w] = Math.min(size[w] + size[ww] + sizeHere, group + 1);
										size[ww] = 0;
										for (int yy = 0; yy < which.length; yy++) {
											if (which[yy] == ww) which[yy] = w;
										}
									}
									size[y] = 0;
								} else {
									if (sameAsAbove) which[y] = which[y - 1];
									size[which[y]] = Math.min(size[which[y]] + sizeHere, group + 1);
									if (which[y] != y) size[y] = 0;
								}
								if (allowSkip && group >= 4 && size[which[y]] > group) continue;
							} else {
								which[y] = -1;
								size[y] = -1;
							}
							col[y] = here;

							long newMask = encodeMask();
							Integer newMaskScore = dpNewZ.get(newMask);
							if (newMaskScore == null || newScore > newMaskScore) {
								dpNewZ.put(newMask, newScore);
								dpHowNewZ.put(newMask, mask);
							}
						}
					}
				}
				dp = dpNew;
			}
		}

		int z = toAssign[0];
		int zz = toAssign[1];
		HashMap<Long, Integer> selectFrom = dp.get(z);
		long bestMask = -1L;
		int bestScore = Integer.MIN_VALUE;
		for (Map.Entry<Long, Integer> entry : selectFrom.entrySet()) {
			if (entry.getValue() > bestScore) {
				bestScore = entry.getValue();
				bestMask = entry.getKey();
			}
		}
		if (bestMask < 0) {
			assert allowSkip;
			solveTwoColorsStripe(yFrom, yTo, toAssign, false);
			return;
		}
		long mask = bestMask;
		for (int x = n; x >= 0; x--) {
			for (int y = hei - 1; y >= 0; y--) {
//				log("" + Math.abs(z - zz));
				decodeMask(mask);
				mask = how[y][x].get(z).get(mask);
				if (col[y] == 0) z--;
				if (col[y] == 1) zz--;
				if (x < n) {
					assert (col[y] == -1) == wall[yFrom + y][x];
					assert desired[yFrom + y][x] == -1;
					desired[yFrom + y][x] = col[y];
				} else {
					assert col[y] == -1;
				}
			}
		}
		assert z == 0;
		assert zz == 0;
//		log(yFrom + String.valueOf((char) 8230) + yTo + ":" + bestScore);
	}

	int[] tabooAbove(int yAt) {
		int[] res = new int[n + 1];
		if (yAt == -1) {
			return res;
		}
		boolean[][] mark = new boolean[yAt + 1][n];
		for (int x = 0; x < n; x++) {
			if (mark[yAt][x] || wall[yAt][x]) continue;
			dfsCompSize = 0;
			dfsTabooWayDown = -1;
			dfs(mark, yAt, x, desired[yAt][x], yAt);
			for (int xx = 0; xx < n; xx++) {
				if (wall[yAt][xx] || !mark[yAt][xx] || res[xx] != 0) continue;
				res[xx] = dfsCompSize;
			}
			if (dfsTabooWayDown != -1 && dfsCompSize < group) {
				res[dfsTabooWayDown] *= -1;
			}
		}
		return res;
	}

	int dfsCompSize, dfsTabooWayDown;
	int[][] dfsComp;

	void dfs(boolean[][] mark, int y, int x, int areaColor, int yAt) {
		mark[y][x] = true;
		dfsComp[y][x] = yAt;
		dfsCompSize++;
		for (int d = 0; d < 4; d++) {
			int yy = y + DY[d], xx = x + DX[d];
			if (yy < 0 || yy >= n || xx < 0 || xx >= n|| desired[yy][xx] != areaColor || mark[yy][xx] || wall[yy][xx]) continue;
			dfs(mark, yy, xx, areaColor, yAt);
		}
		if (y == yAt && dfsTabooWayDown == -1 && !wall[yAt + 1][x]) {
			dfsTabooWayDown = x;
		}
	}

	int calcComponents() {
		int score = 0;
		boolean[][] mark = new boolean[n][n];
		int[] recolor = new int[n * n];
		groupId = new int[n][n];
		Arrays.fill(recolor,  -1);
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if (wall[y][x] || mark[y][x]) continue;
			assert desired[y][x] >= 0 && desired[y][x] < colors;
			dfsCompSize = 0;
			int id = y * n + x;
			dfs(mark, y, x, desired[y][x], -1 - id);
			if (dfsCompSize == group) {
				recolor[id] = score;
				score++;
			}
		}
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			groupId[y][x] = -1;
			if (wall[y][x]) continue;
			if (recolor[-1 - dfsComp[y][x]] >= 0) {
				groupId[y][x] = recolor[-1 - dfsComp[y][x]];
			}
		}
		return score;
	}

	int[] col;
	int[] which;
	int[] size;

	long encodeMask() {
		long mask = 0L;
		for (int y = 0; y < col.length; y++) {
			int code;
			if (col[y] == -1) {
				assert which[y] == -1;
				assert size[y] == -1;
				code = 31;
			} else if (which[y] == y) {
				assert 1 <= size[y];
				assert size[y] <= group + 1;
				code = col[y] * (group + 1) + size[y] - 1;
			} else {
				assert which[y] < y;
				assert which[which[y]] == which[y];
				assert col[which[y]] == col[y];
				assert size[y] == 0;
				code = 2 * (group + 1) + (y - which[y] - 1);
				assert code < 31;
			}
			mask |= ((long) code) << (5 * y);
		}
		assert 5 * col.length <= 64;
		return mask;
	}

	void decodeMask(long mask) {
		for (int y = 0; y < col.length; y++) {
			int code = (int) ((mask >> (5 * y)) & 31);
			if (code == 31) {
				col[y] = -1; which[y] = -1; size[y] = -1;
				continue;
			}
			if (code >= 2 * (group + 1)) {
				which[y] = 2 * (group + 1) + y - code - 1;
				col[y] = col[which[y]]; size[y] = 0;
				continue;
			}
			col[y] = code / (group + 1);
			size[y] = 1 + code % (group + 1);
			which[y] = y;
		}
		assert encodeMask() == mask;
	}

	HashSet<Integer> triedX = new HashSet<>();

	void selectGroupsProfileDynamics() {
		checkTimeLimit();
		int x;
		if (attempt == 0) {
			int extra = nonWallCount % group;
			x = nonWallCount / group / 2 * group + (extra + 1) / 2;
		} else {
			x = nonWallCount / 2;
		}
		if (!triedX.add(x)) return;
		solveTwoColors(new int[]{x, nonWallCount - x});
		prettyPrintDesired();
		gotGroups = calcComponents();
		solveGroups("solveDP" + attempt);
	}

	double scoreFreeSpace(boolean[][] nonfree) {
		double score = 0;
		boolean[][] markFreeSpace = new boolean[n][n];
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			if (nonfree[y][x] || markFreeSpace[y][x]) continue;
			freeSpaceCompSize = 0;
			dfsFreeSpace(markFreeSpace, nonfree, y, x);
			//noinspection IntegerDivisionInFloatingPointContext
			score += freeSpaceCompSize / group + freeSpaceCompSize * freeSpaceCompSize * 0.01 / n / n;
			if (freeSpaceCompSize < group) score -= 0.1;
		}
		return score;
	}

	int freeSpaceCompSize;

	void dfsFreeSpace(boolean[][] markFreeSpace, boolean[][] nonfree, int y, int x) {
		markFreeSpace[y][x] = true;
		freeSpaceCompSize++;
		for (int d = 0; d < 4; d++) {
			int yy = y + DY[d], xx = x + DX[d];
			if (yy < 0 || yy >= n || xx < 0 || xx >= n || nonfree[yy][xx] || markFreeSpace[yy][xx]) continue;
			dfsFreeSpace(markFreeSpace, nonfree, yy, xx);
		}
	}

	double scoreClosenessTo9(int y, int x, int y9, int x9) {
		return Math.hypot(y - y9, x - x9) + y * 1e-3 + x * 1e-6;
	}

	static final int[] DX = new int[]{1, 0, -1, 0};
	static final int[] DY = new int[]{0, 1, 0, -1};

	int encode(int y, int x) {
		return (y << 16) | x;
	}

	int decodeY(int yx) {
		return yx >> 16;
	}

	int decodeX(int code) {
		return code & ((1 << 16) - 1);
	}

	String move(int y, int x, int yy, int xx) {
		return y + " " + x + " " + yy + " " + xx;
	}

	void prettyPrintGroupId() {
		prettyPrint(0);
	}

	void prettyPrintColorAssignment() {
		prettyPrint(10);
	}

	void prettyPrintDesired() {
		prettyPrint(20);
	}

	void prettyPrint(int mode) {
		if (SUBMIT || noPrettyPrint) return;
		log("\n");
		for (int y = 0; y < n; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < n; x++) {
				if (wall[y][x]) {
					sb.append((char) 9632);
					continue;
				}
				if (mode < 20 && groupId[y][x] == -1) {
					sb.append(" ");
					continue;
				}
				int value;
				switch (mode) {
					case 0: value = groupId[y][x] % 64; break;
					case 10:
						value = colorAssignment[groupId[y][x]];
						if (value == colors) value = '?' - '0';
						break;
					case 20:
						value = desired[y][x];
						break;
					default: throw new AssertionError();
				}
				sb.append((char) ('0' + value));
			}
			log(sb + "\n");
		}
	}

	int[] hungarian(int[][] e) {
		int[] ans = new int[e.length];
		Arrays.fill(ans, -1);
		if (e.length == 0 || e[0].length == 0) {
			return ans;
		}
		int infty = Integer.MAX_VALUE / 2;
		boolean swap = false;
		if (e.length > e[0].length) {
			swap = true;
			int[][] f = new int[e[0].length][e.length];
			for (int i = 0; i < e.length; i++) {
				for (int j = 0; j < e[0].length; j++) {
					f[j][i] = e[i][j];
				}
			}
			e = f;
		}
		int n1 = e.length;
		int n2 = e[0].length;
		int[] u = new int[n1 + 1];
		int[] v = new int[n2 + 1];
		int[] p = new int[n2 + 1];
		int[] way = new int[n2 + 1];
		for (int i = 1; i <= n1; i++) {
			p[0] = i;
			int j0 = 0;
			int[] minv = new int[n2 + 1];
			Arrays.fill(minv, infty);
			boolean[] used = new boolean[n2 + 1];
			do {
				used[j0] = true;
				int i0 = p[j0], j1 = 0;
				int delta = infty;
				for (int j = 1; j <= n2; j++) {
					if (!used[j]) {
						int cur = e[i0 - 1][j - 1] - u[i0] - v[j];
						if (cur < minv[j]) {
							minv[j] = cur;
							way[j] = j0;
						}
						if (minv[j] < delta) {
							delta = minv[j];
							j1 = j;
						}
					}
				}
				for (int j = 0; j <= n2; j++) {
					if (used[j]) {
						u[p[j]] += delta;
						v[j] -= delta;
					} else {
						minv[j] -= delta;
					}
				}
				j0 = j1;
			} while (p[j0] != 0);
			do {
				int j1 = way[j0];
				p[j0] = p[j1];
				j0 = j1;
			} while (j0 > 0);
		}
		for (int j = 1; j <= n2; j++) {
			if (p[j] > 0) {
				// if (e[p[j] - 1][j - 1] >= infty) no matching of size n1;
				// sum += e[p[j] - 1][j - 1];
				if (swap) {
					ans[j - 1] = p[j] - 1;
				} else {
					ans[p[j] - 1] = j - 1;
				}
			}
		}
		return ans;
	}

	public String[] solve(int n, int c, int k, int p, int[][] field) {
		this.n = n;
		colors = c;
		group = k;
		multiplier = p;
		this.field = field;
		wall = new boolean[n][n];
		colorCount = new int[colors];
		for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) {
			field[y][x]--;
			if (field[y][x] == -1) {
				wall[y][x] = true;
			} else {
				colorCount[field[y][x]]++;
				nonWallCount++;
			}
		}
		for (int i = 0; i < colors; i++) {
			idealGroups += colorCount[i] / group;
		}
		dfsComp = new int[n][n];

		dist = new int[n][n][n][n];
		boolean[][] mark = new boolean[n][n];
		int[] queue = new int[n * n];
		for (int y0 = 0; y0 < n; y0++) for (int x0 = 0; x0 < n; x0++) {
			if (wall[y0][x0]) continue;
			for (int y = 0; y < n; y++) {
				Arrays.fill(mark[y], false);
				Arrays.fill(dist[y0][x0][y], Integer.MAX_VALUE / 2);
			}
			mark[y0][x0] = true;
			dist[y0][x0][y0][x0] = 0;
			queue[0] = encode(y0, x0);
			int low = 0, high = 1;
			while (low < high) {
				int y = decodeY(queue[low]), x = decodeX(queue[low]);
				low++;
				for (int d = 0; d < 4; d++) {
					int yy = y + DY[d], xx = x + DX[d];
					if (yy < 0 || yy >= n || xx < 0 || xx >= n || mark[yy][xx] || wall[yy][xx]) continue;
					mark[yy][xx] = true;
					dist[y0][x0][yy][xx] = dist[y0][x0][y][x] + 1;
					queue[high++] = encode(yy, xx);
				}
			}
		}

		try {
			solve();
		} catch (Exception ignored) {
		}
		return ans.toArray(new String[0]);
	}

	StringBuilder log = new StringBuilder();
	LinkedHashMap<String, String> logMap = new LinkedHashMap<>();

	void log(String s) {
		if (!SUBMIT) log.append(" ").append(s);
	}

	void logMap(String key, String value) {
		if (SUBMIT) return;
		log.append("\t").append(key).append(": ").append(value);
		logMap.put(key, value);
	}

	void logBad(String bad) {
		log("\t" + "Bad_" + bad);
		logMap.put("BAD", logMap.getOrDefault("BAD", "") + bad);
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
		return (System.currentTimeMillis() - timeStart) / (double) TIME_LIMIT;
	}

	@SuppressWarnings("ConstantConditions")
	private static final boolean SUBMIT = (EVALUATOR == null);
	public static double _localTimeCoefficient = 0;
	private final long timeStart = System.currentTimeMillis();
	private final Random rnd = new Random(566);

	public static void main(String[] args) throws Exception {
		//noinspection ConstantConditions
		if (EVALUATOR != null) {
			//noinspection ResultOfMethodCallIgnored
			EVALUATOR.hashCode();
			return;
		}
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int c = in.nextInt();
		int k = in.nextInt();
		int p = in.nextInt();
		int[][] field = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				field[i][j] = in.nextInt();
			}
		}
		String[] moves = new HappyGrid().solve(n, c, k, p, field);
		System.out.println(moves.length);
		for (String move : moves) {
			System.out.println(move);
		}
	}
}
