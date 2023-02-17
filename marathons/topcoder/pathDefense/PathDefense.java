package marathons.topcoder.pathDefense;

import java.util.*;
import java.util.concurrent.Callable;

public class PathDefense {
	public static final int DOUBLINGS = 4;
	static final Callable<Void> VISUALIZER = new PathDefenseVis(); // <=> TESTING
//	static final Callable<Void> VISUALIZER = null; // <=> SUBMISSION
	static int EVALUATE = 10;
	static int VISUALIZE = 1;
	static long EVALUATE_FROM = 1;
	static long VISUALIZE_FROM = 1;
	static boolean VERBOSE_WHILE_VISUALIZING = true;
	static boolean VERBOSE_WHILE_EVALUATING = false;
	static double _delay = 300;
	static double _speed = 1;
	static int walkingSimulations = 512;
	static double greedyPlanSize = 4;

	@Override
	public String toString() {
		return _statusMove + "\n" + _status + "\n" +
				"types: " + Arrays.toString(towerTypes);
	}

	void setDelay() {
		if (depth >= 4 || proximity <= 4) {
			_delaySpecialCase = true;
		}
		_delay = _delaySpecialCase ? 1000 : 1;
	}

	void placeTowers() {
		if (moveInLevel == 0) {
			planGreedy();
		}
		for (int i = 0; i < planGreedy.size(); i++) {
			Tower tower = planGreedy.get(i);
			if (tower.type.cost > money) {
				continue;
			}
			boolean deep = depth(tower.x, tower.y) > tower.type.range;
			int closeCreeps = 0;
			{
				for (Creep creep : creeps) {
					if (dist2(creep.x, creep.y, tower.x, tower.y) <=
							tower.type.range2_1()) {
						closeCreeps++;
					}
				}
			}
			boolean need = true;
			if (level > 0) {
				if (closeCreeps == 0) {
					need = false;
				}
				if (closeCreeps <= 1) {
					double tth = totalHealthFraction();
					double planned = 1 - Math.pow(2, level - DOUBLINGS);
					if (tth > planned) {
						need = false;
					}
				}
			}
			if (deep && closeCreeps == 0) {
				need = false;
			}
			if (!need) {
				continue;
			}
			place(tower);
			planGreedy.remove(i);
			i--;
		}
	}

	double wave;
	ArrayList<Integer> spawnsStats = new ArrayList<Integer>();

	void evaluateWave() {
		spawnsStats.add(spawnsPerTick[move] * SIMULATION_TIME + move);
		Collections.sort(spawnsStats);
		double normalSpawn = 0;
		int side = move / 3, count = 0;
		for (int i = 0; i <= move; i++) {
			if (i < side || i > move - side) {
				continue;
			}
			normalSpawn += spawnsStats.get(i) / SIMULATION_TIME;
			count++;
		}
		normalSpawn /= count;
		_statusMove += "NS=" + normalSpawn + " " + count;
	}

	int[] placeTowers(int[] creep, int moneyParameter, int[] baseHealthParameter) {
		_statusMove = "";
		_delaySpecialCase = false;
		thisMove.clear();
		if (move == 0) assert creep.length == 0;
		assert (moneyParameter - money) % creepKillReward == 0;
		creepsKilled += (moneyParameter - money) / creepKillReward;
		money = moneyParameter;
		baseHealth = baseHealthParameter;
		int oldSeenSize = seenCreepIds.size();
		depth = -1;
		proximity = n * n + 1;
		creeps.clear();
		for (int i = 0; i < creep.length / 4; i++) {
			int health = creep[4 * i + 1];
			assert health > 0;
			int x = creep[4 * i + 3];
			int y = creep[4 * i + 2];
			assert path[x][y];
			int id = creep[4 * i];
			creeps.add(new Creep(x, y, health));
			seenCreepIds.add(id);
			actualWalkingCount[x][y]++;
			maxActualWalkingCount = Math.max(maxActualWalkingCount, actualWalkingCount[x][y]);
			depth = Math.max(depth, depth(x, y));
			proximity = Math.min(proximity, closestBase[x][y]);
		}
		spawnsPerTick[move] = seenCreepIds.size() - oldSeenSize;
		evaluateWave();
		placeTowers();
		setDelay();
		int[] outcome = new int[thisMove.size() * 3];
		for (int i = 0; i < thisMove.size(); i++) {
			outcome[3 * i] = thisMove.get(i).y;
			outcome[3 * i + 1] = thisMove.get(i).x;
			outcome[3 * i + 2] = thisMove.get(i).type.id;
			towers.add(thisMove.get(i));
		}
		move++;
		level = move / DOUBLING_TIME;
		moveInLevel = move % DOUBLING_TIME;
		if (move == SIMULATION_TIME) {
			System.out.print("$" + money + "\th" + Math.round(totalHealthFraction() * INITIAL_BASE_HEALTH) + "\t");
//			System.out.println(Arrays.toString(spawnsPerTick));
		}
		return outcome;
	}

	void place(Tower tower) {
		thisMove.add(tower);
		money -= tower.type.cost;
	}

	ArrayList<Tower> planGreedy = new ArrayList<>();
	int[] planHistory = new int[DOUBLINGS];

	void planGreedy() {
		int simulations = walkingSimulations;
		int crHealth = creepInitHealth << level;
		int planSize = Integer.MAX_VALUE;
		TowerType[][] tt = new TowerType[n][n];
		for (Tower tower : towers) {
			tt[tower.x][tower.y] = tower.type;
			assert !path[tower.x][tower.y];
		}
		int[][] wc = new int[n][n];
		int[][] wc0 = new int[n][n];
		int[][] wcCum = new int[n][n];
		planGreedy.clear();
		if (level > 0) {
			planSize = planHistory[0];
		}
		planSize = Math.min(planSize, (int) (n * greedyPlanSize));
		for (int k = 0; k < planSize; k++) {
			for (int i = 0; i < n; i++) {
				Arrays.fill(wc[i], 0);
			}
			int maxWc = 0;
			for (int sim = 0; sim < simulations; sim++) {
				int health = crHealth;
				int x = possibleSpawnX[simSpawns[sim]] - DX[0];
				int y = possibleSpawnY[simSpawns[sim]] - DY[0];
				for (int d : simPaths[sim]) {
					x += DX[d];
					y += DY[d];
					for (int xx = x - MAX_R; xx <= x + MAX_R; xx++) {
						for (int yy = y - MAX_R; yy <= y + MAX_R; yy++) {
							if (xx < 0 || yy < 0 || xx >= n || yy >= n) {
								continue;
							}
							if (tt[xx][yy] == null) {
								continue;
							}
							if (dist2(x, y, xx, yy) <= tt[xx][yy].range2()) {
								health -= tt[xx][yy].damage;
							}
						}
					}
					if (health <= 0) {
						break;
					}
					wc[x][y]++;
					wcCum[x][y]++;
					maxWc = Math.max(maxWc, wc[x][y]);
				}
			}
			if (k == 0) {
				for (int i = 0; i < n; i++) {
					System.arraycopy(wc[i], 0, wc0[i], 0, n);
				}
			}
			int[] count = new int[MAX_R + 1];
			double bestScore = -1e100;
			int bestX = -1;
			int bestY = -1;
			TowerType bestTT = null;
			for (int x = 0; x < n; x++) {
				for (int y = 0; y < n; y++) {
					if (path[x][y] || tt[x][y] != null) {
						continue;
					}
					Arrays.fill(count, 0);
					for (int xx = x - MAX_R; xx <= x + MAX_R; xx++) {
						for (int yy = y - MAX_R; yy <= y + MAX_R; yy++) {
							if (xx < 0 || yy < 0 || xx >= n || yy >= n || !path[xx][yy]) {
								continue;
							}
							int d2 = dist2(x, y, xx, yy);
							for (int r = MAX_R; r * r >= d2; r--) {
								int score;
//								score = wc[xx][yy] * n + wcCum[xx][yy];
								score = wc[xx][yy];
								count[r] += score;
							}
						}
					}
					for (TowerType towerType : towerTypes) {
						double cost = towerType.cost;
						double score = count[towerType.range] * towerType.damage * 1.0 / cost;
						if (score > bestScore) {
							bestScore = score;
							bestX = x;
							bestY = y;
							bestTT = towerType;
						}
					}
				}
			}
//			if (VERBOSE) {
//				System.out.println(k + " " + maxWc + " " + bestScore);
//			}
			if (maxWc == 0) {
				break;
			}
			assert tt[bestX][bestY] == null;
			tt[bestX][bestY] = bestTT;
			planGreedy.add(new Tower(bestX, bestY, bestTT));
			_status += bestTT + " ";
		}
		planHistory[level] = planGreedy.size();
		_status = planGreedy.size() + "/" + planSize + " " + _status;
	}

	int money, moneyInitial;
	int n;
	boolean[][] path;
	int[][] base;
	int[][] closestBase;
	int[] baseX, baseY;
	int[] baseHealth;
	int creepInitHealth;
	int creepKillReward;
	TowerType[] towerTypes;
	int[] possibleSpawnX, possibleSpawnY;
	int[][] dirLeadsToBase;
	int[][] simWalkingCount;
	int[][] simPaths;
	int[] simSpawns;
	int[][] actualWalkingCount;
	int maxActualWalkingCount;
	HashSet<Integer> seenCreepIds = new HashSet<Integer>();
	int[] spawnsPerTick = new int[SIMULATION_TIME];
	int creepsKilled;
	int depth, proximity;
	int move, level, moveInLevel;
	ArrayList<Tower> thisMove = new ArrayList<>();
	ArrayList<Tower> towers = new ArrayList<>();
	ArrayList<Creep> creeps = new ArrayList<>();

	void initSimulateWalking() {
		dirLeadsToBase = new int[n][n];
		closestBase = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(closestBase[i], Integer.MAX_VALUE);
		}
		for (int k = 0; k < baseX.length; k++) {
			ArrayList<Integer> queue = new ArrayList<Integer>();
			queue.add(n * baseX[k] + baseY[k]);
			int[][] dist = new int[n][n];
			for (int i = 0; i < n; i++) {
				Arrays.fill(dist[i], Integer.MAX_VALUE);
			}
			dist[baseX[k]][baseY[k]] = 0;
			for (int i = 0; i < queue.size(); i++) {
				int v = queue.get(i);
				int x = v / n;
				int y = v % n;
				closestBase[x][y] = Math.min(closestBase[x][y], dist[x][y]);
				for (int d = 0; d < 4; d++) {
					int xx = x + DX[d];
					int yy = y + DY[d];
					if (xx < 0 || xx >= n || yy < 0 || yy >= n || !path[xx][yy]) {
						continue;
					}
					if (dist[xx][yy] != Integer.MAX_VALUE) {
						continue;
					}
					dist[xx][yy] = dist[x][y] + 1;
					queue.add(n * xx + yy);
				}
			}
			for (int x = 0; x < n; x++) {
				for (int y = 0; y < n; y++) {
					if (!path[x][y]) {
						continue;
					}
					for (int d = 0; d < 4; d++) {
						int xx = x + DX[d];
						int yy = y + DY[d];
						if (xx < 0 || xx >= n || yy < 0 || yy >= n || !path[xx][yy]) {
							continue;
						}
						if (dist[xx][yy] == dist[x][y] - 1) {
							dirLeadsToBase[x][y] |= 1 << d;
						}
					}
				}
			}
		}
		ArrayList<Integer> possibleSpawns = new ArrayList<Integer>();
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (x > 0 && x < n - 1 && y > 0 && y < n - 1) {
					continue;
				}
				if ((x == 0 || x == n - 1) && (y == 0 || y == n - 1)) {
					continue;
				}
				if (!path[x][y]) {
					continue;
				}
				possibleSpawns.add(n * x + y);
			}
		}
		possibleSpawnX = new int[possibleSpawns.size()];
		possibleSpawnY = new int[possibleSpawns.size()];
		for (int i = 0; i < possibleSpawnX.length; i++) {
			int v = possibleSpawns.get(i);
			possibleSpawnX[i] = v / n;
			possibleSpawnY[i] = v % n;
		}

		simWalkingCount = new int[n][n];
		simPaths = new int[walkingSimulations][];
		simSpawns = new int[walkingSimulations];
		ArrayList<Integer> dirs = new ArrayList<Integer>();
		for (int k = 0; k < walkingSimulations; k++) {
			int spawnId = rnd.nextInt(possibleSpawnX.length);
			int x = possibleSpawnX[spawnId];
			int y = possibleSpawnY[spawnId];
			int prevx = -1;
			int prevy = -1;
			int tryPath = 0;
			dirs.clear();
			dirs.add(0);
			while (base[x][y] == -1) {
				int dir = 0;
				tryPath++;
				if (tryPath > n * n) {
					break;
				}
				int tryDir = 0;
				do {
					if (tryDir == 15) {
						tryDir = -1;
						break;
					}
					dir = rnd.nextInt(4);
					tryDir |= (1<<dir);
				} while ((dirLeadsToBase[x][y] & (1<<dir)) == 0 || (x + DX[dir] == prevx && y + DY[dir] == prevy));
				if (tryDir < 0) {
					_troubles.add("Nowhere to go @" + _seed);
					break;
				}
				dirs.add(dir);
				prevx = x;
				prevy = y;
				x += DX[dir];
				y += DY[dir];
			}
			if (base[x][y] == -1) {
				k--;
				_troubles.add("Didn't reach base" + _seed);
				continue;
			}
			simSpawns[k] = spawnId;
			simPaths[k] = new int[dirs.size()];
			for (int i = 0; i < dirs.size(); i++) {
				simPaths[k][i] = dirs.get(i);
			}
			x = possibleSpawnX[spawnId] - DX[0];
			y = possibleSpawnY[spawnId] - DY[0];
			for (int d : simPaths[k]) {
				x += DX[d];
				y += DY[d];
				simWalkingCount[x][y]++;
			}
		}
		int maxWalkingCount = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				maxWalkingCount = Math.max(maxWalkingCount, simWalkingCount[i][j]);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				simWalkingCount[i][j] = simWalkingCount[i][j] * 128 / maxWalkingCount;
			}
		}
	}

	int init(String[] board, int moneyParameter, int creepHealth, int creepMoney, int[] towerTypesParameter) {
		_status = "";
		n = board.length;
		path = new boolean[n][n];
		base = new int[n][n];
		int[] bx = new int[10];
		int[] by = new int[10];
		int maxId = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				char c = board[i].charAt(j);
				path[i][j] = c != '#';
				if (c >= '0' && c <= '9') {
					int id = c - '0';
					bx[id] = i;
					by[id] = j;
					maxId = Math.max(id, maxId);
					base[i][j] = id;
				} else {
					base[i][j] = -1;
				}
			}
		}
		baseX = Arrays.copyOf(bx, maxId + 1);
		baseY = Arrays.copyOf(by, maxId + 1);
		baseHealth = new int[baseX.length];
		Arrays.fill(baseHealth, INITIAL_BASE_HEALTH);
		money = moneyInitial = moneyParameter;
		creepInitHealth = creepHealth;
		creepKillReward = creepMoney;
		towerTypes = new TowerType[towerTypesParameter.length / 3];
		for (int i = 0; i < towerTypes.length; i++) {
			towerTypes[i] = new TowerType(towerTypesParameter[i * 3], towerTypesParameter[i * 3 + 1], towerTypesParameter[i * 3 + 2], i);
		}
		actualWalkingCount = new int[n][n];
		maxActualWalkingCount = 0;
		init();
		return 0;
	}

	void init() {
		initSimulateWalking();
	}

	double totalHealthFraction() {
		int result = 0;
		for (int h : baseHealth) {
			result += h;
		}
		return result * 1.0 / baseX.length / INITIAL_BASE_HEALTH;
	}

	class TowerType {
		int range;
		int damage;
		int cost;
		int id;

		public TowerType(int range, int damage, int cost, int id) {
			this.range = range;
			this.damage = damage;
			this.cost = cost;
			this.id = id;
		}

		int range2() {
			return range * range;
		}

		int range2_1() {
			return (range + 1) * (range + 1);
		}

		@Override
		public String toString() {
			return "$" + cost + "r" + range + "x" + damage;
		}
	}

	class Tower {
		int x, y;
		TowerType type;

		public Tower(int x, int y, TowerType type) {
			this.x = x;
			this.y = y;
			this.type = type;
		}
	}

	class Creep {
		int x, y;
		int health;

		public Creep(int x, int y, int health) {
			this.x = x;
			this.y = y;
			this.health = health;
		}
	}

	static int dist2(int x, int y, int x2, int y2) {
		return (x - x2) * (x - x2) + (y - y2) * (y - y2);
	}

	int depth(int x, int y) {
		return Math.min(Math.min(x, y), n - 1 - Math.max(x, y));
	}

	public static final int DOUBLING_TIME = 500;
	public static final int SIMULATION_TIME = DOUBLING_TIME * DOUBLINGS;
	public static final int INITIAL_BASE_HEALTH = 1000;
	public static final int MAX_R = 5;
	public static final int[] DY = new int[]{1, 0, -1, 0};
	public static final int[] DX = new int[]{0, -1, 0, 1};

	static String round(double v, int precision) {
		if (Math.abs(v) >= 1e100) {
			return v > 0 ? "INF" : "-INF";
		}
		double ten = Math.round(Math.pow(10, precision));
		return "" + Math.round(v * ten) / ten;
	}

	@SuppressWarnings("serial")
	class TimeOutException extends RuntimeException {
	}

	void checkTimeLimit() {
		if (timePassed() >= 1) {
			throw new TimeOutException();
		}
	}

	double timePassed() {
		return (System.currentTimeMillis() - timeStart) * 1.0 / LOCAL_TIME_TIMIT;
	}

	static final boolean SUBMIT = (VISUALIZER == null);
	static boolean VERBOSE = !SUBMIT;
	static double myScore;
	static long _seed;
	static boolean _vis;
	static int _score;
	static long _time;
	static String _status;
	static String _statusMove;
	boolean _delaySpecialCase;
	static ArrayList<String> _troubles = new ArrayList<>();
	static final boolean USE_MY_SCORE = false;
	static final long TIME_LIMIT = 19800;
	static final double LOCAL_TIME_COEFFICIENT = 0.85 /* thinkpad */;
	static final long LOCAL_TIME_TIMIT = (long) (TIME_LIMIT * (SUBMIT ? 1 : LOCAL_TIME_COEFFICIENT));

	final long timeStart = System.currentTimeMillis();
	final Random rnd = new Random(566);

	public static void main(String[] args) throws Exception {
		if (VISUALIZE > 0) {
			VERBOSE = !SUBMIT && VERBOSE_WHILE_VISUALIZING;
			_vis = true;
			for (int t = 0; t < VISUALIZE && _vis; t++) {
				_seed = VISUALIZE_FROM + t;
				VISUALIZER.call();
			}
		}
		if (EVALUATE > 0) {
			System.out.println();
			System.out.println();
			VERBOSE = !SUBMIT && VERBOSE_WHILE_EVALUATING;
			_vis = false;
			double sumScores = 0;
			double sumScores2 = 0;
			long totalT = 0;
			long maxT = 0;
			long maxTest = 0;
			int tests = EVALUATE;
			for (int t = 0; t < tests; t++) {
				_seed = EVALUATE_FROM + t;
				System.out.print("#" + _seed+ ": ");
				VISUALIZER.call();
				double score = USE_MY_SCORE ? myScore : _score;
				System.out.println(score);
				sumScores += score;
				sumScores2 += score * score;
				if (_time > maxT) {
					maxT = _time;
					maxTest = _seed;
				}
				totalT += _time;
			}
			double mean = sumScores / tests;
			double std = Math.sqrt(sumScores2 / tests - mean * mean);
			String scoreName = USE_MY_SCORE ? "MyScore" : "Score";
			System.out.println("=========================== " + scoreName + " = " + round(mean, 2));
			System.out.println("+-" + round(100 * std / mean, 2) + "%");
			System.out.println("======== AverageTime: " + Math.round(totalT / tests) / 1000.0 + "s");
			System.out.println("======== MaxTime: " + Math.round(maxT) / 1000.0 + "s on test #" + maxTest);
			if (!_troubles.isEmpty()) {
				System.out.println("");
				System.out.println("== == == == == == == ==  TROUBLES!");
				for (String s : _troubles) {
					System.out.println(s);
				}
				System.err.println("TROUBLES!");
			}
		}
	}
}
