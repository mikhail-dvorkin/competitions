package marathons.topcoder.pathDefense;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

class CreepType {
	public static final int MIN_CREEP_HEALTH = 1;
	public static final int MAX_CREEP_HEALTH = 20;
	public static final int MIN_CREEP_MONEY = 1;
	public static final int MAX_CREEP_MONEY = 20;
	int health;
	int money;
	public CreepType(Random rnd) {
		health = rnd.nextInt(MAX_CREEP_HEALTH - MIN_CREEP_HEALTH + 1) + MIN_CREEP_HEALTH;
		money = rnd.nextInt(MAX_CREEP_MONEY - MIN_CREEP_MONEY + 1) + MIN_CREEP_MONEY;
	}
}

class Creep {
	int id;
	int health;
	int x, y;
	int spawnTime;
	ArrayList<Integer> moves = new ArrayList<Integer>();
}

class TowerType {
	public static final int MIN_TOWER_RANGE = 1;
	public static final int MAX_TOWER_RANGE = 5;
	public static final int MIN_TOWER_DAMAGE = 1;
	public static final int MAX_TOWER_DAMAGE = 5;
	public static final int MIN_TOWER_COST = 5;
	public static final int MAX_TOWER_COST = 40;
	int range;
	int damage;
	int cost;
	public TowerType(Random rnd) {
		range = rnd.nextInt(MAX_TOWER_RANGE - MIN_TOWER_RANGE + 1) + MIN_TOWER_RANGE;
		damage = rnd.nextInt(MAX_TOWER_DAMAGE - MIN_TOWER_DAMAGE + 1) + MIN_TOWER_DAMAGE;
		cost = rnd.nextInt(MAX_TOWER_COST - MIN_TOWER_COST + 1) + MIN_TOWER_COST;
	}

}

class Tower {
	int x, y;
	int type;
}
class AttackVis {
	int x1, y1, x2, y2;
	public AttackVis(int _x1, int _y1, int _x2, int _y2) {
		x1 = _x1;
		y1 = _y1;
		x2 = _x2;
		y2 = _y2;
	}
}

class TestCase {
	public static final int MIN_CREEP_COUNT = 500;
	public static final int MAX_CREEP_COUNT = 2000;
	public static final int MIN_TOWER_TYPES = 1;
	public static final int MAX_TOWER_TYPES = 20;
	public static final int MIN_BASE_COUNT = 1;
	public static final int MAX_BASE_COUNT = 8;
	public static final int MIN_WAVE_COUNT = 1;
	public static final int MAX_WAVE_COUNT = 15;
	public static final int MIN_BOARD_SIZE = 20;
	public static final int MAX_BOARD_SIZE = 60;

	public int boardSize;
	public int money;

	public CreepType creepType;

	public int towerTypeCnt;
	public TowerType[] towerTypes;

	public char[][] board;
	public int pathCnt;
	public int[] spawnX;
	public int[] spawnY;
	public int[][] boardPath;
	public int tx, ty;

	public int baseCnt;
	public int[] baseX;
	public int[] baseY;

	public int creepCnt;
	public Creep[] creeps;
	public int waveCnt;

	public SecureRandom rnd = null;

	public void connect(Random random, int x1, int y1, int x2, int y2) {
		while (x1!=x2 || y1!=y2) {
			if (board[y1][x1]>='0' && board[y1][x1]<='9') return;
			board[y1][x1] = '.';
			int x1_ = x1;
			int y1_ = y1;
			if (x1==x2) {
				if (y2>y1) {
					y1++;
				} else {
					y1--;
				}
			} else if (y1==y2) {
				if (x2>x1) {
					x1++;
				} else {
					x1--;
				}
			} else {
				int nx = x1;
				int ny = y1;
				if (x2>x1) nx++; else nx--;
				if (y2>y1) ny++; else ny--;
				if (board[ny][x1]=='.') { y1 = ny; }
				else if (board[y1][nx]=='.') { x1 = nx; }
				else {
					if (random.nextInt(2)==0)
						y1 = ny;
					else
						x1 = nx;
				}
			}
			if (x1>x1_) boardPath[y1_][x1_] |= 8;
			else if (x1<x1_) boardPath[y1_][x1_] |= 2;
			else if (y1>y1_) boardPath[y1_][x1_] |= 1;
			else if (y1<y1_) boardPath[y1_][x1_] |= 4;
		}
	}

	public void addPath(Random random, int baseIdx) {
		int sx=0,sy=0;
		boolean nextTo = false;
		int tryEdge = 0;
		do
		{
			tryEdge++;
			if (tryEdge>boardSize) break;
			nextTo = false;
			sx = random.nextInt(boardSize-1)+1;
			if (random.nextInt(2)==0) {
				sy = random.nextInt(2)*(boardSize-1);
				if (sx>0 && board[sy][sx-1]=='.') nextTo = true;
				if (sx+1<boardSize && board[sy][sx+1]=='.') nextTo = true;
			} else
			{
				sy = sx;
				sx = random.nextInt(2)*(boardSize-1);
				if (sy>0 && board[sy-1][sx]=='.') nextTo = true;
				if (sy+1<boardSize && board[sy+1][sx]=='.') nextTo = true;
			}
		} while (nextTo || board[sy][sx]!='#');
		if (tryEdge>boardSize) return;
		board[sy][sx] = '.';
		spawnX[baseIdx] = sx;
		spawnY[baseIdx] = sy;
		if (sx==0) { boardPath[sy][sx] |= 8; sx++; }
		else if (sy==0) { boardPath[sy][sx] |= 1; sy++; }
		else if (sx==boardSize-1) { boardPath[sy][sx] |= 2; sx--; }
		else { boardPath[sy][sx] |= 4; sy--; }
		int b = baseIdx%baseCnt;
		if (baseIdx>=baseCnt) b = random.nextInt(baseCnt);
		connect(random, sx, sy, baseX[b], baseY[b]);
	}

	public TestCase(long seed) {

		try {
			rnd = SecureRandom.getInstance("SHA1PRNG");
		} catch (Exception e) {
			System.err.println("ERROR: unable to generate test case.");
			System.exit(1);
		}

		rnd.setSeed(seed);
		boolean genDone = true;
		do {
			boardSize = rnd.nextInt(MAX_BOARD_SIZE - MIN_BOARD_SIZE + 1) + MIN_BOARD_SIZE;
			if (seed==1) boardSize = 20;
			board = new char[boardSize][boardSize];
			boardPath = new int[boardSize][boardSize];
			creepType = new CreepType(rnd);

			towerTypeCnt = rnd.nextInt(MAX_TOWER_TYPES - MIN_TOWER_TYPES + 1) + MIN_TOWER_TYPES;
			towerTypes = new TowerType[towerTypeCnt];
			money = 0;
			for (int i = 0; i < towerTypeCnt; i++) {
				towerTypes[i] = new TowerType(rnd);
				money += towerTypes[i].cost;
			}
			baseCnt = rnd.nextInt(MAX_BASE_COUNT - MIN_BASE_COUNT + 1) + MIN_BASE_COUNT;
			for (int y=0;y<boardSize;y++) {
				for (int x=0;x<boardSize;x++) {
					board[y][x] = '#';
					boardPath[y][x] = 0;
				}
			}
			baseX = new int[baseCnt];
			baseY = new int[baseCnt];
			for (int i=0;i<baseCnt;i++) {
				int bx,by;
				do
				{
					bx = rnd.nextInt(boardSize-8)+4;
					by = rnd.nextInt(boardSize-8)+4;
				} while (board[by][bx]!='#');
				board[by][bx] = (char)('0'+i);
				baseX[i] = bx;
				baseY[i] = by;
			}

			pathCnt = rnd.nextInt(baseCnt*10 - baseCnt + 1) + baseCnt;
			spawnX = new int[pathCnt];
			spawnY = new int[pathCnt];
			for (int i=0;i<pathCnt;i++) {
				addPath(rnd, i);
			}

			creepCnt = rnd.nextInt(MAX_CREEP_COUNT - MIN_CREEP_COUNT + 1) + MIN_CREEP_COUNT;
			if (seed==1) creepCnt = MIN_CREEP_COUNT;
			creeps = new Creep[creepCnt];
			for (int i=0;i<creepCnt;i++) {
				Creep c = new Creep();
				int j = rnd.nextInt(pathCnt);
				c.x = spawnX[j];
				c.y = spawnY[j];
				c.id = i;
				c.spawnTime = rnd.nextInt(PathDefense.SIMULATION_TIME);
				c.health = creepType.health * (1<<(c.spawnTime / PathDefense.DOUBLING_TIME));
				creeps[i] = c;
			}
			// waves
			waveCnt = rnd.nextInt(MAX_WAVE_COUNT - MIN_WAVE_COUNT + 1) + MIN_WAVE_COUNT;
			int wi = 0;
			for (int w=0;w<waveCnt;w++) {
				int wavePath = rnd.nextInt(pathCnt);
				int waveSize = 5+rnd.nextInt(creepCnt/20);
				int waveStartT = rnd.nextInt(PathDefense.SIMULATION_TIME-waveSize);
				for (int i=0;i<waveSize;i++) {
					if (wi>=creepCnt) break;
					creeps[wi].x = spawnX[wavePath];
					creeps[wi].y = spawnY[wavePath];
					creeps[wi].spawnTime = waveStartT + rnd.nextInt(waveSize);
					creeps[wi].health = creepType.health * (1<<(creeps[wi].spawnTime / PathDefense.DOUBLING_TIME));
					wi++;
				}
				if (wi>=creepCnt) break;
			}

			// determine paths for each creep
			genDone = true;
					for (Creep c :creeps) {
						c.moves.clear();
						int x = c.x;
						int y = c.y;
						int prevx = -1;
						int prevy = -1;
						int tryPath = 0;
						while (!(board[y][x]>='0' && board[y][x]<='9')) {
							int dir = 0;
							tryPath++;
							if (tryPath>boardSize*boardSize) break;
							// select a random direction that will lead to a base, don't go back to where the creep was in the previous time step
							int tryDir = 0;
							do
							{
								if (tryDir==15) { tryDir = -1; break; }
								dir = rnd.nextInt(4);
								tryDir |= (1<<dir);
							} while ((boardPath[y][x]&(1<<dir))==0 ||
									(x+PathDefense.DX[dir]==prevx && y+PathDefense.DY[dir]==prevy));
							if (tryDir<0) break;
							c.moves.add(dir);
							prevx = x;
							prevy = y;
							x += PathDefense.DX[dir];
							y += PathDefense.DY[dir];
						}
						if (!(board[y][x]>='0' && board[y][x]<='9')) {
							genDone = false;
							break;
						}
					}
		} while (!genDone);
	}
}

@SuppressWarnings("serial")
class Drawer extends JFrame {
	public World world;
	public DrawerPanel panel;

	public int cellSize, boardSize;
	public int width, height;

	public boolean pauseMode = false;
	public boolean stepMode = false;
	public boolean debugMode = false;

	class DrawerKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			synchronized (keyMutex) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Drawer.this.dispose();
				}
				if (e.getKeyChar() == 'q') {
					Drawer.this.dispose();
					PathDefense._vis = false;
				}
				if (e.getKeyChar() == '[') {
					PathDefense._speed /= 2;
				}
				if (e.getKeyChar() == ']') {
					PathDefense._speed *= 2;
				}
				if (e.getKeyChar() == ' ') {
					pauseMode = !pauseMode;
				}
				if (e.getKeyChar() == 'd') {
					debugMode = !debugMode;
				}
				if (e.getKeyChar() == 's') {
					stepMode = !stepMode;
				}
				keyPressed = true;
				keyMutex.notifyAll();
			}
		}
	}

	class DrawerPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			synchronized (world.worldLock) {
				int cCnt[][] = new int[boardSize][boardSize];
				double m = (PathDefenseVis.pathDefense.move % PathDefense.DOUBLING_TIME) * 1.0 / PathDefense.DOUBLING_TIME;
				g.setColor(new Color(0,(int) (128 * (1 - m)),(int) (128 * m)));
				g.fillRect(15, 15, cellSize * boardSize + 1, cellSize * boardSize + 1);
				g.setColor(Color.BLACK);
				for (int i = 0; i <= boardSize; i++) {
					g.drawLine(15 + i * cellSize, 15, 15 + i * cellSize, 15 + cellSize * boardSize);
					g.drawLine(15, 15 + i * cellSize, 15 + cellSize * boardSize, 15 + i * cellSize);
				}
				for (int i=0; i < boardSize; i++) {
					for (int j=0; j < boardSize; j++) {
						if (world.tc.board[i][j]=='.') {
							cCnt[i][j] = 0;
//							int wc = PathDefenseVis.pathDefense.dirLeadsToBase[i][j] * 15;
							int swc = PathDefenseVis.pathDefense.simWalkingCount[i][j] * 3 / 2;
							swc = Math.min(swc, 255);
							g.setColor(new Color(swc, swc, swc));
							g.fillRect(15 + j * cellSize + 1, 15 + i * cellSize + 1, cellSize - 1, cellSize - 1);
							int mawc = Math.max(PathDefenseVis.pathDefense.maxActualWalkingCount, 1);
							int awc = PathDefenseVis.pathDefense.actualWalkingCount[i][j] * 255 / mawc;
							awc = Math.min(awc, 255);
							g.setColor(new Color(awc, awc, awc));
							g.fillRect(15 + j * cellSize + 1, 15 + i * cellSize + 1, cellSize - 1, cellSize / 3 - 1);
						}
					}
				}

				g.setColor(Color.WHITE);
				for (int i=0; i < boardSize; i++) {
					for (int j=0; j < boardSize; j++) {
						if (world.tc.board[i][j]>='0' && world.tc.board[i][j]<='9') {
							g.fillRect(15 + j * cellSize + 1, 15 + i * cellSize + 1, cellSize - 1, cellSize - 1);
						}
					}
				}
				// draw the health of each base
				for (int b=0;b<world.tc.baseCnt;b++) {
					int col = world.baseHealth[b]*255/PathDefense.INITIAL_BASE_HEALTH;
					g.setColor(new Color(col, col, col));
					g.fillRect(15 + world.tc.baseX[b] * cellSize + 1+cellSize/4, 15 + world.tc.baseY[b] * cellSize + 1+cellSize/4, cellSize/2 - 1, cellSize/2 - 1);
				}


				for (int i=0; i < boardSize; i++) {
					for (int j=0; j < boardSize; j++) {
						if (world.tc.board[i][j]>='A') {
							int ttype = world.tc.board[i][j]-'A';
							float hue = (float)(ttype) / world.tc.towerTypeCnt;
							// tower color
							g.setColor(Color.getHSBColor(hue, 0.9f, 1.0f));
							g.fillOval(15 + j * cellSize + 2, 15 + i * cellSize + 2, cellSize - 4, cellSize - 4);
							if (debugMode) {
								// draw area of attack
								int r = world.tc.towerTypes[ttype].range;
								g.drawOval(15 + (j-r) * cellSize + 1, 15 + (i-r) * cellSize + 1, cellSize*(r*2+1) - 1, cellSize*(r*2+1) - 1);
								g.setColor(new Color(128, 128, 128, 30));
								g.fillOval(15 + (j-r) * cellSize + 1, 15 + (i-r) * cellSize + 1, cellSize*(r*2+1) - 1, cellSize*(r*2+1) - 1);
							}
						}
					}
				}
				int z1 = 2;
				for (Creep c : world.tc.creeps)
					if (c.health>0 && c.spawnTime<world.curStep) {
						float h = Math.min(1.f, (float)(c.health) / (world.tc.creepType.health));
						g.setColor(new Color(h,0,0));
						g.fillRect(15 + c.x * cellSize + 1 + cCnt[c.y][c.x], 15 + c.y * cellSize + 1 + cCnt[c.y][c.x], cellSize - 1, cellSize - 1);
						g.setColor(new Color(255,0,0));
						g.drawOval(15 + z1 + c.x * cellSize + 1 + cCnt[c.y][c.x], 15 + z1 + c.y * cellSize + 1 + cCnt[c.y][c.x], cellSize - 3 - z1, cellSize - 3 - z1);
						cCnt[c.y][c.x]+=2;
					}

				g.setColor(Color.GREEN);
				for (AttackVis a : world.attacks) {
					g.drawLine(15 + a.x1 * cellSize + cellSize/2, 15 + a.y1 * cellSize + cellSize/2,
							15 + a.x2 * cellSize + cellSize/2, 15 + a.y2 * cellSize + cellSize/2);
				}

				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				Graphics2D g2 = (Graphics2D)g;

				int horPos = 20 + boardSize * cellSize;
				int verPos = horPos + 10;

				for (String s : PathDefenseVis.pathDefense.toString().split("\\n")) {
					g2.drawString(s, 10, verPos);
					verPos += 18;
				}
				g2.drawString("Board size = " + boardSize, horPos, 30);
				g2.drawString("Simulation Step = " + world.curStep, horPos, 50);
				g2.drawString("Creeps Spawned = " + world.numSpawned, horPos, 70);
				g2.drawString("Creeps killed = " + world.numKilled, horPos, 90);
				g2.drawString("Towers placed = " + world.numTowers, horPos, 110);
				g2.drawString("Money gained = " + world.moneyIncome, horPos, 130);
				g2.drawString("Money spend = " + world.moneySpend, horPos, 150);
				g2.drawString("Money = " + world.totMoney, horPos, 170);
				int baseh = 0;
				for (int i=0;i<world.baseHealth.length;i++) {
					g.setColor(Color.GREEN);
					g.fillRect(horPos+30, 205+i*20, world.baseHealth[i]/10, 19);
					g.setColor(Color.BLACK);
					g.fillRect(horPos+30+world.baseHealth[i]/10, 205+i*20, 100 - world.baseHealth[i]/10, 19);
					g2.drawString(Integer.toString(world.baseHealth[i]), horPos, 220+i*20);
					baseh += world.baseHealth[i];
					g2.drawString(Integer.toString(i), 15 + world.tc.baseX[i] * cellSize + 2, 15 + (world.tc.baseY[i]+1) * cellSize-1);
				}
				g2.drawString("Base health = " + baseh, horPos, 195);
				int score = world.totMoney + baseh;
				g2.drawString("Score = " + score, horPos, 225+world.baseHealth.length*20);
			}
		}
	}

	@Override
	public void dispose() {
		PathDefenseVis.stopSolution();
		super.dispose();
		PathDefenseVis.drawer = null;
	}

	class DrawerWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(@SuppressWarnings("unused") WindowEvent event) {
			dispose();
		}
	}

	final Object keyMutex = new Object();
	boolean keyPressed;

	public void processPause() {
		synchronized (keyMutex) {
			if (!stepMode && !pauseMode) {
				return;
			}
			keyPressed = false;
			while (!keyPressed) {
				try {
					keyMutex.wait();
				} catch (InterruptedException e) {
					// do nothing
				}
			}
		}
	}

	public Drawer(World world, int cellSize) {
		super();

		panel = new DrawerPanel();
		getContentPane().add(panel);

		addWindowListener(new DrawerWindowListener());

		this.world = world;

		boardSize = world.tc.boardSize;
		this.cellSize = cellSize;
		width = cellSize * boardSize + 400;
		height = cellSize * boardSize + 150;

		addKeyListener(new DrawerKeyListener());

		setSize(width, height);
		setTitle("" + PathDefense._seed + "    HEALTH=" + world.tc.creepType.health + "   $" + world.tc.creepType.money);

		setResizable(true);
		setVisible(true);
	}
}

class World {
	final Object worldLock = new Object();
	TestCase tc;
	int totMoney;
	int curStep = -1;
	int numSpawned;
	int numKilled;
	int numTowers;
	int moneyIncome;
	int moneySpend;
	int[] baseHealth;
	List<Tower> towers = new ArrayList<Tower>();
	List<AttackVis> attacks = new ArrayList<AttackVis>();

	public World(TestCase tc) {
		this.tc = tc;
		totMoney = tc.money;
		numSpawned = 0;
		numKilled = 0;
		numTowers = 0;
		moneyIncome = 0;
		moneySpend = 0;
		baseHealth = new int[tc.baseCnt];
		for (int i=0;i<tc.baseCnt;i++)
			baseHealth[i] = PathDefense.INITIAL_BASE_HEALTH;
	}

	public void updateCreeps() {
		synchronized (worldLock) {
			for (Creep c : tc.creeps)
				if (c.health>0 && c.spawnTime<curStep) {
					int dir = c.moves.get(curStep-c.spawnTime-1);
					c.x += PathDefense.DX[dir];
					c.y += PathDefense.DY[dir];
					if (tc.board[c.y][c.x]>='0' && tc.board[c.y][c.x]<='9') {
						// reached a base
						int b = tc.board[c.y][c.x]-'0';
						// decrease the health of the base
						baseHealth[b] = Math.max(0, baseHealth[b]-c.health);
						c.health = 0;
					}
				} else if (c.spawnTime==curStep) {
					numSpawned++;
				}
		}
	}

	public void updateAttack() {
		synchronized (worldLock) {
			attacks.clear();
			for (Tower t : towers) {
				// search for nearest attackable creep
				int cidx = -1;
				int cdist = 1<<29;
				for (int i=0;i<tc.creeps.length;i++)
					if (tc.creeps[i].health>0 && tc.creeps[i].spawnTime<=curStep) {
						int dst = (t.x-tc.creeps[i].x)*(t.x-tc.creeps[i].x) + (t.y-tc.creeps[i].y)*(t.y-tc.creeps[i].y);
						// within range of tower?
								if (dst<=tc.towerTypes[t.type].range*tc.towerTypes[t.type].range) {
									// nearest creep?
									if (dst<cdist) {
										cdist = dst;
										cidx = i;
									} else if (dst==cdist) {
										// creep with smallest id gets attacked first if they are the same distance away
										if (tc.creeps[i].id<tc.creeps[cidx].id) {
											cdist = dst;
											cidx = i;
										}
									}
								}
					}
				if (cidx>=0) {
					// we hit something
					tc.creeps[cidx].health -= tc.towerTypes[t.type].damage;
					attacks.add(new AttackVis(t.x, t.y, tc.creeps[cidx].x, tc.creeps[cidx].y));
					if (tc.creeps[cidx].health<=0) {
						// killed it!
						totMoney += tc.creepType.money;
						numKilled++;
						moneyIncome += tc.creepType.money;
					}
				}
			}

		}
	}

	public void startNewStep() {
		curStep++;
	}

}

public class PathDefenseVis implements Callable<Void> {
	public static String execCommand = null;
//	public static long seed = 1;
//	public static boolean vis = true;
	public static boolean debug = false;
	public static int cellSize = 16;
	public static boolean startPaused = false;

	public static Process solution;
	public static PathDefense pathDefense;
	public static Drawer drawer = null;

	@Override
	public Void call() {
		runTest();
		return null;
	}

	public static int runTest() {
		solution = null;

		if (execCommand != null) {
			try {
				solution = Runtime.getRuntime().exec(execCommand);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to execute your solution using the provided command: "
						+ execCommand + ".");
				return -1;
			}
		} else {
			pathDefense = new PathDefense();
		}

		TestCase tc = new TestCase(PathDefense._seed);
		World world = new World(tc);

		// Board information
		String[] tcBoard = new String[tc.boardSize];
		for (int y=0;y<tc.boardSize;y++) {
			tcBoard[y] = "";
			for (int x=0;x<tc.boardSize;x++) {
				tcBoard[y] += tc.board[y][x];
			}
		}
		// Tower type information
		int[] towerTypeData = new int[tc.towerTypeCnt*3];
		int ii = 0;
		for (int i=0;i<tc.towerTypeCnt;i++) {
			towerTypeData[ii++] = tc.towerTypes[i].range;
			towerTypeData[ii++] = tc.towerTypes[i].damage;
			towerTypeData[ii++] = tc.towerTypes[i].cost;
		}
		BufferedReader reader = null;
		PrintWriter writer = null;
		if (solution != null) {
			reader = new BufferedReader(new InputStreamReader(solution.getInputStream()));
			writer = new PrintWriter(solution.getOutputStream());
			new ErrorStreamRedirector(solution.getErrorStream()).start();

			writer.println(tc.boardSize);
			writer.println(tc.money);
			// Board information
			for (int y=0;y<tc.boardSize;y++) {
				writer.println(tcBoard[y]);
			}
			// Creep type information
			writer.println(tc.creepType.health);
			writer.println(tc.creepType.money);
			writer.flush();
			// Tower type information
			writer.println(towerTypeData.length);
			for (int v : towerTypeData)
				writer.println(v);
			writer.flush();
		} else {
			PathDefense._time = -System.currentTimeMillis();
			pathDefense.init(tcBoard, tc.money, tc.creepType.health, tc.creepType.money, towerTypeData);
			PathDefense._time += System.currentTimeMillis();
		}

		if (PathDefense._vis) {
			int c = cellSize;
			if (world.tc.boardSize > 40) {
				c = c * 40 / world.tc.boardSize;
			}
			drawer = new Drawer(world, c);
			drawer.debugMode = debug;
			if (startPaused) {
				drawer.pauseMode = true;
			}
		}

		for (int t = 0; t < PathDefense.SIMULATION_TIME; t++) {
			world.startNewStep();

			int numLive = 0;
			for (Creep c : world.tc.creeps)
				if (c.health>0 && c.spawnTime<world.curStep) numLive++;

			int[] creeps = new int[numLive*4];
			int ci = 0;
			for (Creep c : world.tc.creeps)
				if (c.health>0 && c.spawnTime<world.curStep) {
					creeps[ci++] = c.id;
					creeps[ci++] = c.health;
					creeps[ci++] = c.x;
					creeps[ci++] = c.y;
				}

			int[] newTowers = null;
			if (solution != null) {
				assert writer != null;
				writer.println(world.totMoney);
				writer.println(creeps.length);
				for (int v : creeps)
					writer.println(v);
				writer.println(world.baseHealth.length);
				for (int v : world.baseHealth)
					writer.println(v);
				writer.flush();
			} else {
				PathDefense._time -= System.currentTimeMillis();
				newTowers = pathDefense.placeTowers(creeps, world.totMoney, world.baseHealth);
				PathDefense._time += System.currentTimeMillis();
			}

			int commandCnt;
			try {
				if (solution != null) {
					assert reader != null;
					commandCnt = Integer.parseInt(reader.readLine());
					if (commandCnt>tc.boardSize*tc.boardSize*3) {
						System.err.println("ERROR: Return array from placeTowers too large.");
						return -1;
					}
					if ((commandCnt%3)!=0) {
						System.err.println("ERROR: Return array from placeTowers must be a multiple of 3.");
						return -1;
					}
					newTowers = new int[commandCnt];
					for (int i=0;i<commandCnt;i++) {
						newTowers[i] = Integer.parseInt(reader.readLine());
					}
				}
				assert newTowers != null;
				for (int i=0;i<newTowers.length;i+=3) {
					Tower newT = new Tower();
					newT.x = newTowers[i];
					newT.y = newTowers[i+1];
					newT.type = newTowers[i+2];
					if (newT.x<0 || newT.x>=tc.boardSize || newT.y<0 || newT.y>=tc.boardSize) {
						System.err.println("ERROR: Placement (" + newT.x + "," + newT.y + ") outside of bounds.");
						return -1;
					}
					if (tc.board[newT.y][newT.x]!='#') {
						System.err.println("ERROR: Cannot place a tower at (" + newT.x + "," + newT.y + "): " + tc.board[newT.y][newT.x]);
						return -1;
					}
					if (newT.type<0 || newT.type>=tc.towerTypeCnt) {
						System.err.println("ERROR: Trying to place an illegal tower type.");
						return -1;
					}
					if (world.totMoney<tc.towerTypes[newT.type].cost) {
						System.err.println("ERROR: Not enough money to place tower.");
						return -1;
					}
					world.totMoney -= tc.towerTypes[newT.type].cost;
					tc.board[newT.y][newT.x] = (char)('A'+newT.type);
					world.towers.add(newT);
					world.numTowers++;
					world.moneySpend += tc.towerTypes[newT.type].cost;
				}
			} catch (Exception e) {
				System.err.println("ERROR: time step = " + t + " (0-based). Unable to get the build commands" +
						" from your solution.");
				return -1;
			}

			world.updateCreeps();
			world.updateAttack();

			if (drawer != null) {
				drawer.processPause();
				if (drawer != null) {
					drawer.repaint();
					try {
						Thread.sleep(1 + (int) (PathDefense._delay * PathDefense._speed));
					} catch (Exception e) {
						// do nothing
					}
				}
			}
		}

		stopSolution();
		if (drawer != null) {
			drawer.dispose();
		}

		int score = world.totMoney;
		for (int b=0;b<world.baseHealth.length;b++)
			score += world.baseHealth[b];

//		System.err.println("Money = " + world.totMoney);
//		System.err.println("Total base health = " + (score-world.totMoney));

		PathDefense._score = score;
		return score;
	}

	public static void stopSolution() {
		if (solution != null) {
			try {
				solution.destroy();
			} catch (Exception e) {
				// do nothing
			}
		}
	}

	public static void main1(String[] args) {
		for (int i = 0; i < args.length; i++)
			if (args[i].equals("-exec")) {
				execCommand = args[++i];
			} else if (args[i].equals("-seed")) {
				PathDefense._seed = Long.parseLong(args[++i]);
			} else if (args[i].equals("-novis")) {
				PathDefense._vis = false;
			} else if (args[i].equals("-debug")) {
				debug = true;
			} else if (args[i].equals("-sz")) {
				cellSize = Integer.parseInt(args[++i]);
			} else if (args[i].equals("-delay")) {
				PathDefense._delay = Integer.parseInt(args[++i]);
			} else if (args[i].equals("-pause")) {
				startPaused = true;
			} else {
				System.out.println("WARNING: unknown argument " + args[i] + ".");
			}

		if (execCommand == null) {
			System.err.println("ERROR: You did not provide the command to execute your solution." +
					" Please use -exec <command> for this.");
			System.exit(1);
		}

		try {
			int score = PathDefenseVis.runTest();
			System.out.println("Score = " + score);
		} catch (RuntimeException e) {
			System.err.println("ERROR: Unexpected error while running your test case.");
			e.printStackTrace();
			PathDefenseVis.stopSolution();
		}
	}
}

class ErrorStreamRedirector extends Thread {
	public BufferedReader reader;

	public ErrorStreamRedirector(InputStream is) {
		reader = new BufferedReader(new InputStreamReader(is));
	}

	@Override
	public void run() {
		while (true) {
			String s;
			try {
				s = reader.readLine();
			} catch (Exception e) {
				//e.printStackTrace();
				return;
			}
			if (s == null) {
				break;
			}
			System.err.println(s);
		}
	}
}
