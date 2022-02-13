package marathons.topcoder.hardestMaze;

import java.io.*;
import java.util.*;

public class HardestMaze {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new HardestMazeTester(); //TESTING
//			null; //SUBMISSION
	public static final String EVALUATOR_PARAMETERS = "-seed 1,3 -myExec -fileVis";
	private static final long TIME_LIMIT = 9850;

	private void solve() {
		if (SUBMIT) relax();
		int emptyScore = ansScore;
		for (tries = 0; tries < 1e9; tries++) {
			checkTimeLimit();
			if (tries == 16 && ansScore == emptyScore) planHorizontalLine();
			planA();
		}
	}

	int[][] tree;
	int[] temp;

	void planAUnsafe() {
		for (int x = 0; x < n; x++) {
			Arrays.fill(wall[x], false);
			Arrays.fill(tree[x], -1);
		}
		int hole0 = findTree(0);
		if (hole0 < 0) return;
		int hole2 = findTree(2);
		if (hole2 < 0) return;
		if (bridgeIt() == -2) return;
		blowIter = 8 * n; if (blowIt() == -1) {
			wall[hole0 >> 16][hole0 & 65535] = false;
			wall[hole2 >> 16][hole2 & 65535] = false;
		}
//		polluteItDiagonal();
		polluteItStraight();
		lickTheRest(0); lickTheRest(1);
		relax();
	}

	void planA() {
		try {
			planAUnsafe();
		} catch (Exception e) {
			if (!SUBMIT) e.printStackTrace();
			_troubles.add(e.getMessage());
		}
	}

	int findTree(int color) {
		int modeDiagonal = rnd.nextInt(2);
		int seed, seedR, seedQ, seedX, seedY;
		for (int i = 0;; i++) {
			if (i == n * n) throw new RuntimeException();
			seed = rnd.nextInt(robots * (targets + 1));
			seedR = seed % robots;
			seedQ = seed / robots;
			seedX = coordX[seedR][seedQ];
			seedY = coordY[seedR][seedQ];
			if (tree[seedX][seedY] == -1) break;
		}
		int mask = 1 << seedR;
		int needMask = (1 << robots) - 1;
		bfsQueue[0] = seedX << 16 | seedY;
		tree[seedX][seedY] = color;
		int size = 1;
		while (mask < needMask) {
			task = 1;
			taskInfo = mask;
			int cell = bfs(size);
			if (cell == -1) {
				return -1;
			}
			int x = cell >> 16;
			int y = cell & 65535;
			int r = field[x][y];
			if (r == -1 || (mask >> r & 1) == 1) continue;
			mask |= 1 << r;
			int undesiredDir = dirTo(x, y, bfsPrev[x][y] >> 16, bfsPrev[x][y] & 65535);
			while (bfsDist[x][y] > 0) {
				bfsQueue[size++] = x << 16 | y;
				tree[x][y] = color;
				if (modeDiagonal == 0) {
					int xx = bfsPrev[x][y] >> 16;
					int yy = bfsPrev[x][y] & 65535;
					x = xx; y = yy;
					continue;
				}
				int bestD = -1;
				int bestScore = -1;
				for (int d = 0; d < 4; d++) {
					int xx = x + DX[d], yy = y + DY[d];
					if (xx < 0 || yy < 0 || xx >= n || yy >= n) continue;
					if (bfsTimestamp[xx][yy] < bfsTime || bfsDist[xx][yy] != bfsDist[x][y] - 1) continue;
					int score = 0;
					if (field[xx][yy] != -1) score += 2;
					if (d != undesiredDir) score += 1;
					if (score > bestScore) {
						bestScore = score;
						bestD = d;
					}
				}
				x += DX[bestD]; y += DY[bestD];
				undesiredDir = bestD;
			}
		}
		task = 0;
		taskInfo = color;
		dfs(seedX, seedY);
		int count = 0;
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (tree[x][y] == - 2 - color) {
					wall[x][y] = true;
					temp[count++] = x << 16 | y;
				}
			}
		}
		return temp[rnd.nextInt(count)];
	}

	int bridgeIt() {
		boolean ao = false, bo = false, ab = false;
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (tree[x][y] > -2) continue;
				for (int d = 0; d < 8; d++) {
					int xx = x + DX[d];
					int yy = y + DY[d];
					if (xx < 0 || yy < 0 || xx >= n || yy >= n) {
						if (tree[x][y] == -2) {
							ao = true;
						} else {
							bo = true;
						}
						continue;
					}
					if (tree[xx][yy] <= -2 && tree[xx][yy] != tree[x][y]) {
						ab = true;
					}
				}
			}
		}
		if (ab) {
			if (!ao && !bo) {
				bridgeToOutside(rnd.nextInt(2) * 2);
			}
		} else {
			if (!ao) if (bridgeToOutside(0) < 0) return -1;
			if (!bo) if (bridgeToOutside(2) < 0) return -1;
		}
		return 0;
	}

	int bridgeToOutside(int color) {
		int size = 0;
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (tree[x][y] == -2 - color) {
					bfsQueue[size++] = x << 16 | y;
				}
			}
		}
		task = 2;
		int cell = bfs(size);
		if (cell < 0) return -1;
		int x = cell >> 16;
		int y = cell & 65535;
		while (tree[x][y] > -2) {
			tree[x][y] = -3 - color;
			wall[x][y] = true;
			int xx = bfsPrev[x][y] >> 16;
			int yy = bfsPrev[x][y] & 65535;
			x = xx; y = yy;
		}
		return 0;
	}

	int[] blower, blowerD;
	int blowAX, blowAY, blowBX, blowBY, blowIter;

	int blowIt() {
		if (blowIter == 0) return -1;
		int x, y, d;
		for (int i = 0;; i++) {
			if (i == blowIter) return -1;
			d = rnd.nextInt(4);
			int r = rnd.nextInt(n);
			switch (d) {
				case 0: x = n - 1; y = r; break;
				case 1: y = n - 1; x = r; break;
				case 2: x = 0; y = r; break;
				default: y = 0; x = r; break;
			}
			if (tree[x][y] == -1) break;
		}
		blower[0] = x << 16 | y;
		blowerD[0] = d;
		int blowerSize = 1;
		int modeStep = rnd.nextInt(2) * 2 + 1;
		while (true) {
			if (blowerSize == n * n) throw new RuntimeException();
			x = blower[blowerSize - 1] >> 16;
			y = blower[blowerSize - 1] & 65535;
			d = blowerD[blowerSize - 1];
			int dd = (d + modeStep) % 4;
			int xx = x + DX[dd], yy = y + DY[dd];
			boolean noForward = xx < 0 || yy < 0 || xx >= n || yy >= n || wall[xx][yy];
			if (noForward) {
				blower[blowerSize] = x << 16 | y;
				blowerD[blowerSize] = dd;
			} else {
				int xd = x + DX[dd] + DX[d], yd = y + DY[dd] + DY[d];
				boolean noDiagonal = xd < 0 || yd < 0 || xd >= n || yd >= n || wall[xd][yd];
				blower[blowerSize] = xx << 16 | yy;
				if (noDiagonal) {
					blowerD[blowerSize] = d;
				} else {
					blowerD[blowerSize] = -1;
					blowerSize++;
					blower[blowerSize] = xd << 16 | yd;
					blowerD[blowerSize] = dd ^ 2;
				}
			}
			if (blower[blowerSize] == blower[0] && blowerD[blowerSize] == blowerD[0]) break;
			blowerSize++;
		}
		for (int i = 0; i < blowerSize; i++) {
			temp[i] = -1;
			d = blowerD[i];
			if (d < 0) continue;
			x = blower[i] >> 16;
			y = blower[i] & 65535;
			int xx = x + DX[d], yy = y + DY[d];
			if (xx < 0 || yy < 0 || xx >= n || yy >= n || !wall[xx][yy]) continue;
			boolean nei0 = false, nei2 = false;
			for (int dd = 0; dd < 4; dd++) {
				int xxx = xx + DX[dd], yyy = yy + DY[dd];
				if (xxx < 0 || yyy < 0 || xxx >= n || yyy >= n) continue;
				if (tree[xxx][yyy] == 1) nei0 = true; else if (tree[xxx][yyy] == 3) nei2 = true;
			}
			if (nei0 == nei2) continue;
			if (nei0) temp[i] = 0; else temp[i] = 2;
		}
		int first = -1;
		int a = -1, b = -1;
		for (int i = 0; i < blowerSize; i++) {
			if (first == -1) first = temp[i];
			if (first >= 0 && temp[i] == first) {
				a = i;
			}
		}
		for (int i = a + 1; i < blowerSize; i++) {
			if (temp[i] >= 0 && temp[i] != first) {
				b = i;
				break;
			}
		}
		if (b < 0) {
			for (int i = 0; i < a; i++) {
				if (temp[i] >= 0 && temp[i] != first) {
					b = a;
					a = i;
					break;
				}
			}
			if (b < 0) {
				blowIter--;
				return blowIt();
			}
		}
		for (int i = 0; i < blowerSize; i++) {
			if (i > a && i < b) continue;
			x = blower[i] >> 16;
			y = blower[i] & 65535;
			tree[x][y] = 9;
		}
		d = blowerD[a];
		blowAX = (blower[a] >> 16) + DX[d];
		blowAY = (blower[a] & 65535) + DY[d];
		tree[blowAX][blowAY] = 9;
		d = blowerD[b];
		blowBX = (blower[b] >> 16) + DX[d];
		blowBY = (blower[b] & 65535) + DY[d];
		tree[blowBX][blowBY] = 9;

		wall[blowAX][blowAY] = false;
		wall[blowBX][blowBY] = false;
		task = 1;
		taskInfo = 9;
		dfs(blowAX, blowAY);
		task = 3;
		bfs(blowAX, blowAY);
		x = blowBX;
		y = blowBY;
		while (true) {
			tree[x][y] = 7;
			if (x == blowAX && y == blowAY) break;
			int cell = bfsPrev[x][y];
			x = cell >> 16;
			y = cell & 65535;
		}
		task = 2;
		taskInfo = 7;
		dfs(blowAX, blowAY);
		for (x = 0; x < n; x++) {
			for (y = 0; y < n; y++) {
				if (tree[x][y] == 10) tree[x][y] = -1;
			}
		}
		return 0;
	}

	void polluteItStraight() {
		task = 16; polluteGreedy();
		task = 2; polluteGreedy();
		task = 6; polluteGreedy();
	}

	/**
	 * Work in progress, not working currently.
	 */
	void polluteItDiagonal() {
		pPath = 8;
		if (polluteDebug == 0) return;
		int bestDiagSize = -1, bestX = -1, bestY = -1, bestD = -1;
		for (int i = 0; i < n * n; i++) {
			px = i / n;
			py = i % n;
			for (int d = 0; d < 4; d++) {
				pdx = DX[d];
				pdy = DY[d];
				if (pTree(0, 0) == pPath && pTree(1, 0) == pPath && pTree(-1, 0) == pPath && pTree(0, -1) == -2) {
					if (pMovableWall(0, 1) && pMovableWall(1, 1) && pMovableWall(-1, 1) && pTree(0, 2) == -1 && pTree(1, 2) == -1 && pTree(-1, 2) == -1) {
						if (pCanBecomeWall(0, 3) && pCanBecomeWall(1, 3) && pCanBecomeWall(-1, 3) && pCanBecomeWall(-2, 1) && pCanBecomeWall(-2, 2) && pCanBecomeWall(2, 1)  && pCanBecomeWall(2, 2) && pField(0, 0) == -1 && pField(0, 1) == -1) {
							int xx = px + 2 * pdy;
							int yy = py - 2 * pdx;
							diagSize = 0;
							for (int t = 0; t < n; t++) {
								Arrays.fill(diag[t], 0);
							}

							dfsDiagonal(xx, yy);
							if (diagSize > bestDiagSize) {
								bestDiagSize = diagSize;
								bestX = px;
								bestY = py;
								bestD = d;
							}
							for (int j = 0; j < diagSize; j++) {
								xx = temp[j] >> 16;
								yy = temp[j] & 65535;
								diag[xx][yy] = 0;
							}
						}
					}
				}
			}
		}
		if (bestDiagSize < 1) return;
		px = bestX;
		py = bestY;
		pdx = DX[bestD];
		pdy = DY[bestD];
		int xx = px + 2 * pdy;
		int yy = py - 2 * pdx;
		diagSize = 0;
		for (int t = 0; t < n; t++) {
			Arrays.fill(diag[t], 0);
		}

		dfsDiagonal(xx, yy);
		for (int j = 0; j < diagSize; j++) {
			int x = temp[j] >> 16;
			int y = temp[j] & 65535;
			diag[x][y] = 0;
			if (j > 0) {
				xx = diagPrev[x][y] >> 16;
				yy = diagPrev[x][y] & 65535;
				wall[x][y] = true;
				wall[(x + 2 * xx) / 3][(y + 2 * yy) / 3] = true;
				wall[(xx + 2 * x) / 3][(yy + 2 * y) / 3] = true;
			}
		}
		pMakeWall(0, 0);
		pMakeWall(0, 1);
		pMakeWall(0, 2);
		pMakePath(-1, 0);
		pMakePath(-1, 1);
		pMakePath(1, 0);
		pMakePath(1, 1);
	}

	int[][] diag, diagPrev;
	int dx, dy, sx, sy, diagSize;

	void dfsDiagonal(int x, int y) {
		diag[x][y] = 1;
		temp[diagSize++] = x << 16 | y;
		dx = x;
		dy = y;
		for (int ssx = -1; ssx <= 1; ssx += 2) {
			for (int ssy = -1; ssy <= 1; ssy += 2) {
				sx = ssx;
				sy = ssy;
				if (dDiag(3, 3) != 0) continue;
				if (dCannotBecomeWall(3, 3)) continue;
				if (dCannotBecomeWall(2, 2)) continue;
				if (dCannotBecomeWall(1, 1)) continue;
				if (dCannotBecomeWall(0, 3)) continue;
				if (dCannotBecomeWall(1, 4)) continue;
				if (dCannotBecomeWall(2, 5)) continue;
				if (dCannotBecomeWall(3, 5)) continue;
				if (dCannotBecomeWall(4, 5)) continue;
				if (dCannotBecomeWall(5, 4)) continue;
				if (dCannotBecomeWall(5, 3)) continue;
				if (dCannotBecomeWall(5, 2)) continue;
				if (dCannotBecomeWall(4, 1)) continue;
				if (dCannotBecomeWall(3, 0)) continue;
				if (dIsNotEmpty(0, 1)) continue;
				if (dIsNotEmpty(0, 2)) continue;
				if (dIsNotEmpty(1, 2)) continue;
				if (dIsNotEmpty(1, 3)) continue;
				if (dIsNotEmpty(2, 3)) continue;
				if (dIsNotEmpty(2, 4)) continue;
				if (dIsNotEmpty(3, 4)) continue;
				if (dIsNotEmpty(4, 4)) continue;
				if (dIsNotEmpty(4, 3)) continue;
				if (dIsNotEmpty(4, 2)) continue;
				if (dIsNotEmpty(3, 2)) continue;
				if (dIsNotEmpty(3, 1)) continue;
				if (dIsNotEmpty(2, 1)) continue;
				if (dIsNotEmpty(2, 0)) continue;
				if (dIsNotEmpty(1, 0)) continue;
				diagPrev[x + 3 * ssx][y + 3 * ssy] = x << 16 | y;
				dfsDiagonal(x + 3 * ssx, y + 3 * ssy);
			}
		}
	}

	@SuppressWarnings("SameParameterValue")
	private int dDiag(int x, int y) {
		int xx = dx + x * sx, yy = dy + y * sy;
		if (xx < 0 || yy < 0 || xx >= n || yy >= n) return -2;
		return diag[xx][yy];
	}

	boolean dCannotBecomeWall(int x, int y) {
		int xx = dx + x * sx, yy = dy + y * sy;
		if (xx < 0 || yy < 0 || xx >= n || yy >= n || wall[xx][yy]) return false;
		return tree[xx][yy] != -1 || field[xx][yy] != -1;
	}

	boolean dIsNotEmpty(int x, int y) {
		int xx = dx + x * sx, yy = dy + y * sy;
		if (xx < 0 || yy < 0 || xx >= n || yy >= n || wall[xx][yy]) return true;
		return tree[xx][yy] != -1;
	}

	int polluteDebug = SUBMIT ? -1: -2;

	void polluteGreedy() {
		if (polluteDebug == 0) return;
		pPath = task / 2;
		boolean improved = false;
		for (int i = 3; i > 0; i--) {
			int j = rnd.nextInt(i + 1);
			int t = DS[i]; DS[i] = DS[j]; DS[j] = t;
		}
		for (px = 0; px < n; px++) for (py = 0; py < n; py++) temp[px * n + py] = px << 16 | py;
		for (int i = n * n - 1; i > 0; i--) {
			int j = rnd.nextInt(i + 1);
			int t = temp[i]; temp[i] = temp[j]; temp[j] = t;
		}
		for (int i = 0; i < n * n; i++) {
			px = temp[i] >> 16;
			py = temp[i] & 65535;
			for (int d : DS) {
				pdx = DX[d];
				pdy = DY[d];
				if (pTree(0, 0) == pPath && pTree(1, 0) == pPath && pTree(-1, 0) == pPath && pTree(0, -1) == -2) {
					if (pMovableWall(0, 1) && pMovableWall(1, 1) && pMovableWall(-1, 1) && pTree(0, 2) == -1 && pTree(1, 2) == -1 && pTree(-1, 2) == -1) {
						if (pCanBecomeWall(0, 3) && pCanBecomeWall(1, 3) && pCanBecomeWall(-1, 3) && pCanBecomeWall(-2, 1) && pCanBecomeWall(-2, 2) && pCanBecomeWall(2, 1)  && pCanBecomeWall(2, 2) && pField(0, 0) == -1 && pField(0, 1) == -1) {
							pMakeWall(0, 3); pMakeWall(1, 3); pMakeWall(-1, 3); pMakeWall(-2, 1); pMakeWall(-2, 2); pMakeWall(2, 1) ; pMakeWall(2, 2); pMakeWall(0, 0);
							pMakePath(0, 2); pMakePath(1, 2); pMakePath(-1, 2);
							pMakePath(1, 1); pMakePath(-1, 1);
							improved = true;
							polluteDebug--;
							if (polluteDebug == 0) return;
						}
					}
				}
			}
		}
		if (improved) polluteGreedy();
		for (int i = 0; i < n * n; i++) {
			px = temp[i] >> 16;
			py = temp[i] & 65535;
			for (int d : DS) {
				pdx = DX[d];
				pdy = DY[d];
				if (pTree(0, 0) == pPath && pTree(1, 0) == pPath && pTree(-1, 0) == pPath && pTree(0, -1) == -2) {
					if (pMovableWall(0, 1) && pMovableWall(1, 1) && pMovableWall(-1, 1)) {
						if (pCanBecomeWall(0, 2) && pCanBecomeWall(1, 2) && pCanBecomeWall(-1, 2) && pCanBecomeWall(-2, 1) && pCanBecomeWall(2, 1) && pField(0, 0) == -1) {
							pMakeWall(0, 2); pMakeWall(1, 2); pMakeWall(-1, 2); pMakeWall(-2, 1); pMakeWall(2, 1);
							pMakeWall(0, 0);
							pMakePath(0, 1); pMakePath(1, 1); pMakePath(-1, 1);
							improved = true;
							polluteDebug--;
							if (polluteDebug == 0) return;
						}
					}
				}
			}
		}
		if (improved) polluteGreedy();
	}

	int px, py, pdx, pdy, pPath;

	int pTree(int x, int y) {
		int xx = px + x * pdx + y * pdy;
		int yy = py + x * pdy - y * pdx;
		if (xx < 0 || yy < 0 || xx >= n || yy >= n || wall[xx][yy]) return -2;
		return tree[xx][yy];
	}

	int pField(int x, int y) {
		int xx = px + x * pdx + y * pdy;
		int yy = py + x * pdy - y * pdx;
		if (xx < 0 || yy < 0 || xx >= n || yy >= n) return -2;
		return field[xx][yy];
	}

	boolean pMovableWall(int x, @SuppressWarnings("SameParameterValue") int y) {
		return pTree(x, y) == -2 && pField(x, y) != -2;
	}

	boolean pCanBecomeWall(int x, int y) {
		int pTree = pTree(x, y);
		return (pTree == -2 || pTree == -1) && (pField(x, y) <= -1);
	}

	void pMakeWall(int x, int y) {
		int xx = px + x * pdx + y * pdy;
		int yy = py + x * pdy - y * pdx;
		if (xx < 0 || yy < 0 || xx >= n || yy >= n) return;
		wall[xx][yy] = true;
		tree[xx][yy] = -1;
	}

	void pMakePath(int x, @SuppressWarnings("SameParameterValue") int y) {
		int xx = px + x * pdx + y * pdy;
		int yy = py + x * pdy - y * pdx;
		wall[xx][yy] = false;
		tree[xx][yy] = pPath;
	}

	void lickTheRest(int level) {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (field[x][y] == -1) continue;
				if (tree[x][y] != -1) continue;
				task = 4;
				int queueSize = bfs(x, y);
				int wallX = -1, wally = -1;
				wallLoop:
				for (int i = queueSize - 1; i >= 0; i--) {
					int cell = bfsQueue[i];
					int x2 = cell >> 16;
					int y2 = cell & 65535;
					for (int d = 0; d < 4; d++) {
						int x3 = x2 + DX[d];
						int y3 = y2 + DY[d];
						if (x3 < 0 || y3 < 0 || x3 >= n || y3 >= n || !wall[x3][y3]) continue;
						int neighbours = 0;
						for (int dd = 0; dd < 4; dd++) {
							int x4 = x3 + DX[dd];
							int y4 = y3 + DY[dd];
							if (x4 < 0 || y4 < 0 || x4 >= n || y4 >= n || wall[x4][y4]) continue;
							int t = tree[x4][y4];
							if (level == 0) {
								if (t == 8 || t == -2) neighbours++;
							} else if (level == 1) {
								if (t == 8 || t == -2 || t == 1 || t == 3) neighbours++;
							}
						}
						if (neighbours == 1) {
							wallX = x3; wally = y3;
							break wallLoop;
						}
					}
				}
				if (wallX == -1) {
					continue;
				}
				wall[wallX][wally] = false;
				tree[wallX][wally] = -2;
				for (int i = queueSize - 1; i >= 0; i--) {
					int cell = bfsQueue[i];
					int x2 = cell >> 16;
					int y2 = cell & 65535;
					tree[x2][y2] = -2;
				}
			}
		}
	}

	void planHorizontalLine() {
		for (int x = 0; x < n; x++) {
			Arrays.fill(wall[x], 0, n - 1, true);
			relax();
			Arrays.fill(wall[x], 0, n - 1, false);
			Arrays.fill(wall[x], 1, n, true);
			relax();
			Arrays.fill(wall[x], 1, n, false);
		}
	}

	void relax() {
		int score = eval();
		if (score > ansScore) {
			ansScore = score;
			for (int x = 0; x < n; x++) for (int y = 0; y < n; y++) ans[x * n + y] = wall[x][y] ? '#' : '.';
		}
	}

	int eval() {
		for (int x = 0; x < n; x++) for (int y = 0; y < n; y++) if (wall[x][y] && field[x][y] >= 0) return -inf;
		int score = 0;
		for (int r = 0; r < robots; r++) {
			score += evalRobot(r);
		}
		return score;
	}

	int evalRobot(int r) {
		int[][] e = new int[targets + 1][targets + 1];
		for (int q = 0; q <= targets; q++) {
			task = 0;
			taskInfo = r;
			bfs(coordX[r][q], coordY[r][q]);
			for (int i = 0; i <= targets; i++) {
				e[q][i] = bfsDist[coordX[r][i]][coordY[r][i]];
				if (e[q][i] == inf) return -inf;
			}
		}
		return evalSearch(e, targets, (1 << targets) - 1);
	}

	private int evalSearch(int[][] e, int q, int toVisit) {
		if (toVisit == 0) return 0;
		int best = inf;
		for (int i = 0; i < e.length; i++) {
			if (((toVisit >> i) & 1) == 0) continue;
			best = Math.min(best, e[q][i] + evalSearch(e, i, toVisit ^ (1 << i)));
		}
		return best;
	}

	int[] bfsQueue;
	int[][] bfsTimestamp;
	int[][] bfsDist;
	int[][] bfsPrev;
	int inf = 1 << 24;
	static final int[] DX = new int[]{1, 0, -1, 0, 1, -1, -1, 1};
	static final int[] DY = new int[]{0, 1, 0, -1, 1, 1, -1, -1};
	int[] DS = new int[]{0, 1, 2, 3};

	static int dirTo(int x1, int y1, int x2, int y2) {
		if (Math.abs(x1 - x2) < Math.abs(y1 - y2)) {
			return y1 < y2 ? 1 : 3;
		}
		return x1 < x2 ? 0 : 2;
	}

	int task, taskInfo, bfsTime;

	int bfs(int x0, int y0) {
		bfsQueue[0] = x0 << 16 | y0;
		return bfs(1);
	}

	int bfs(int high) {
		bfsTime++;
		for (int i = 0; i < high; i++) {
			int cell = bfsQueue[i];
			int x = cell >> 16;
			int y = cell & 65535;
			bfsTimestamp[x][y] = bfsTime;
			bfsDist[x][y] = 0;
			bfsPrev[x][y] = -1;
		}
		int low = 0;
		int modeCount = 0;
		while (low < high) {
			int cell = bfsQueue[low++];
			int x = cell >> 16;
			int y = cell & 65535;
			switch (task) {
				case 0:
					if (field[x][y] == taskInfo) {
						modeCount++;
						if (modeCount == targets + 1) return 0;
					}
					break;
				case 1:
					if (field[x][y] != -1 && (taskInfo >> field[x][y] & 1) != 1) return cell;
					break;
				case 2:
					if (x == 0 || y == 0 || x == n - 1 || y == n - 1) return cell;
					break;
				case 3:
					if (x == blowBX && y == blowBY) return cell;
					break;
			}
			int dist = bfsDist[x][y];
			for (int i = 3; i > 0; i--) {
				int j = rnd.nextInt(i + 1);
				int t = DS[i]; DS[i] = DS[j]; DS[j] = t;
			}
			for (int d : DS) {
				int xx = x + DX[d];
				int yy = y + DY[d];
				if (xx < 0 || yy < 0 || xx >= n || yy >= n || (bfsTimestamp[xx][yy] == bfsTime && bfsDist[xx][yy] < inf) || wall[xx][yy]) continue;
				if (task == 2 && field[xx][yy] >= 0) continue;
				if (task == 3 && tree[xx][yy] != 10) continue;
				if (task == 4 && tree[xx][yy] != -1) continue;
				bfsTimestamp[xx][yy] = bfsTime;
				bfsDist[xx][yy] = dist + 1;
				bfsPrev[xx][yy] = cell;
				bfsQueue[high++] = xx << 16 | yy;
			}
		}
		if (task == 4) return high;
		return -1;
	}

	void dfs(int x, int y) {
		tree[x][y] = taskInfo + 1;
		for (int d = 0; d < 4; d++) {
			int xx = x + DX[d];
			int yy = y + DY[d];
			if (xx < 0 || yy < 0 || xx >= n || yy >= n || tree[xx][yy] == taskInfo + 1) continue;
			if (tree[xx][yy] == taskInfo || field[xx][yy] != -1) {
				dfs(xx, yy);
				continue;
			}
			if (task == 0) {
				tree[xx][yy] = - 2 - taskInfo;
			}
			if (task == 2 && (tree[xx][yy] < 0 || tree[xx][yy] > 3)) {
				wall[xx][yy] = true;
			}
		}
	}

	int n, robots, targets, tries;
	int[][] coordX, coordY;
	int[][] field;
	boolean[][] fieldIsStart;
	boolean[][] wall;
	int ansScore = Integer.MIN_VALUE;
	char[] ans;

	public char[] findSolution(int N, int R, int T, int[][] Starts, int[][][] Targets) {
		n = N; robots = R; targets = T;
		field = new int[n][n];
		fieldIsStart = new boolean[n][n];
		coordX = new int[robots][targets + 1];
		coordY = new int[robots][targets + 1];
		wall = new boolean[n][n];
		ans = new char[n * n];
		bfsQueue = new int[n * n];
		bfsDist = new int[n][n];
		bfsPrev = new int[n][n];
		bfsTimestamp = new int[n][n];
		tree = new int[n][n];
		blower = new int[n * n];
		blowerD = new int[n * n];
		temp = new int[n * n];
		diag = new int[n][n];
		diagPrev = new int[n][n];
		for (int x = 0; x < n; x++) Arrays.fill(field[x], -1);
		for (int r = 0; r < R; r++) {
			field[Starts[r][0]][Starts[r][1]] = r;
			fieldIsStart[Starts[r][0]][Starts[r][1]] = true;
			coordX[r][targets] = Starts[r][0];
			coordY[r][targets] = Starts[r][1];
			for (int q = 0; q < targets; q++) {
				field[Targets[r][q][0]][Targets[r][q][1]] = r;
				coordX[r][q] = Targets[r][q][0];
				coordY[r][q] = Targets[r][q][1];
			}
		}
		try {
			solve();
		} catch (TimeOutException ignored) {
		}
		if (ansScore < 0) _troubles.add("Not found");
		return ans;
	}

	@SuppressWarnings("unused")
	private static void log(String s) {
		if (!SUBMIT) System.out.print(s + " ");
	}

	@SuppressWarnings("serial")
	private static class TimeOutException extends RuntimeException {
	}

	private void checkTimeLimit() {
		checkTimeLimit(1);
	}

	private void checkTimeLimit(@SuppressWarnings("SameParameterValue") double threshold) {
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
