package marathons.topcoder.stopTheElves;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.imageio.*;
import java.util.concurrent.Callable;

import marathons.utils.topcoderMy.*;

public class StopTheElvesTester extends MarathonAnimatedVis implements Callable<Void> {
	//parameter ranges
	private static final int minN = 10, maxN = 30; // grid size range
	private static final int minC = 1, maxC = 10; // cost of box range
	private static final double minE = 0.1, maxE = 0.2; // elf spawning probability range
	private static final double minP = 0.1, maxP = 0.3; // present probability range
	private static final double minT = 0.1, maxT = 0.2; // tree probability range

	//Inputs
	private int N;            //grid size
	private int C;            //cost of a box
	private double elfP;      //elf spawn rate
	private double presentP;  //present probability
	private double treeP;     //present probability
	private int[] spawnPoint; //starting locations for elves

	//Constants
	private static final char Tree = 'T';
	private static final char Box = 'b';
	private static final char Elf = 'e';
	private static final char ElfPresent = 'E';
	private static final char ElfBox = 'B';
	private static final char Present = 'P';
	private static final char Empty = '.';
	private static final int[] dr = {0, -1, 0, 1};
	private static final int[] dc = {-1, 0, 1, 0};
	private static final int PresentValue = 100;

	//Derived values

	//Graphics
	private Image presentPic;
	private Image treePic;
	private Image boxPic;
	private Image elfPic;
	private Image elfPresentPic;
	private Image elfBoxPic;

	//State Control
	private char[][] grid;
	private int numTurns;
	private int presents;
	private int boxesPlaced;
	private int score;
	private int money;
	private final Queue<Integer> qr = new LinkedList<>();
	private final Queue<Integer> qc = new LinkedList<>();
	private final Queue<Integer> qd = new LinkedList<>();
	private int[] lastBoxes;
	private int numLastBoxes;

	protected void generate() {
		N = randomInt(minN, maxN);
		C = randomInt(minC, maxC);
		elfP = randomDouble(minE, maxE);
		presentP = randomDouble(minP, maxP);
		treeP = randomDouble(minT, maxT);
		// Starting money
		money = C * randomInt(minC, maxC);

		//Special cases
		if (seed == 1) {
			N = minN;
			C = minC;
			elfP = minE;
			presentP = minP;
			treeP = minT;
		} else if (seed == 2) {
			N = maxN;
			C = maxC;
			elfP = maxE;
			presentP = maxP;
			treeP = maxT;
		}

		//User defined parameters
		if (parameters.isDefined("N")) {
			N = randomInt(parameters.getIntRange("N"), minN, maxN);
		}
		if (parameters.isDefined("C")) {
			C = randomInt(parameters.getIntRange("C"), minC, maxC);
		}
		if (parameters.isDefined("money")) {
			money = randomInt(parameters.getIntRange("money"), 0, N * N);
		}
		if (parameters.isDefined("elfP")) {
			elfP = randomDouble(parameters.getDoubleRange("elfP"), 0, maxE);
		}
		if (parameters.isDefined("presentP")) {
			presentP = randomDouble(parameters.getDoubleRange("presentP"), 0, maxP);
		}
		if (parameters.isDefined("treeP")) {
			treeP = randomDouble(parameters.getDoubleRange("treeP"), 0, maxT);
		}

		//starting locations
		spawnPoint = new int[N * N + 1];
		for (int i = 1; i <= N * N; i++) {
			double a = randomDouble(0, 1);
			if (a < elfP) {
				int p = randomInt(0, N * 4 - 4);
				spawnPoint[i] = p;
			} else {
				spawnPoint[i] = -1; // nothing will spawn at turn i
			}
		}

		//storage for boxes placed, used for visualization
		numLastBoxes = 0;
		lastBoxes = new int[N * N];

		//initialize grid
		do {
			grid = new char[N][N];
			presents = 0;

			for (int i = 0; i < N; i++)
				for (int k = 0; k < N; k++) {
					int distFromBorder = Math.min(Math.min(Math.min(i, k), N - k - 1), N - i - 1);
					double a = randomDouble(0, 1);
					if (a < treeP) {
						grid[i][k] = Tree;
					} else if (a < treeP + presentP && distFromBorder > 2) {
						grid[i][k] = Present;
						presents++;
					} else {
						grid[i][k] = Empty;
					}
				}
		} while (presents <= 0);


		boxesPlaced = 0;

		if (debug) {
			System.out.println("Grid size, N = " + N);
			System.out.println("Cost of box, C = " + C);
			System.out.println("Elf spawn probability, elfP = " + elfP);
			System.out.println("Present probability, presentP = " + presentP);
			System.out.println("Tree probability, treeP = " + treeP);
			System.out.println();
			System.out.println("Grid:");
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++)
					System.out.print(grid[r][c] + " ");
				System.out.println();
			}
		}
	}

	protected boolean isMaximize() {
		return true;
	}

	protected double run() throws Exception {
		init();

		return runAuto();
	}


	protected double runAuto() throws Exception {
		double score = callSolution();
		if (score < 0) {
			if (!isReadActive()) return getErrorScore();
			return fatalError();
		}
		return score;
	}

	protected void updateState() {
		if (hasVis()) {
			synchronized (updateLock) {
				addInfo("Time", getRunTime());
				addInfo("Presents left", presents);
				addInfo("Boxes placed", boxesPlaced);
				addInfo("Money", money);
				addInfo("Turns", numTurns);
				addInfo("Score", score);
			}
			updateDelay();
		}
	}

	protected void timeout() {
		addInfo("Time", getRunTime());
		update();
	}

	private void moveElf(int r, int c, int d) {
		char e = grid[r][c];
		grid[r][c] = Empty;
		r += dr[d];
		c += dc[d];
		if (r < 0 || r >= N || c < 0 || c >= N) {
			if (e == ElfPresent) presents--; // Stole the present
			return;
		}
		if (grid[r][c] == Box) // Pick up box
		{
			grid[r][c] = ElfBox;
		} else if (grid[r][c] == Present) // Pick up present
		{
			grid[r][c] = ElfPresent;
		} else {
			grid[r][c] = e;
		}
	}

	//shuffle the array randomly
	private void shuffle(int[] a) {
		for (int i = 0; i < a.length; i++) {
			int k = randomInt(i, a.length - 1);
			int temp = a[i];
			a[i] = a[k];
			a[k] = temp;
		}
	}

	private void findMove(int y, int x) {
		qd.clear();
		qr.clear();
		qc.clear();
		qd.add(-1);
		qr.add(y);
		qc.add(x);
		char e = grid[y][x];
		int[] order = {0, 1, 2, 3};
		int[] visited = new int[N * N];
		int dirToBox = -1; // direction to the nearest box
		for (int i = 0; i < N * N; i++) visited[i] = 0;
		while (qd.size() != 0) {
			int d = qd.remove();
			int r = qr.remove();
			int c = qc.remove();
			if (e == Elf && grid[r][c] == Present) {
				moveElf(y, x, d);
				return;
			}
			if (visited[r + c * N] != 1) {
				visited[r + c * N] = 1;
				shuffle(order);
				for (int dd = 0; dd < 4; dd++) {
					int dir = order[dd];
					int nr = r + dr[dir];
					int nc = c + dc[dir];
					if ((nc >= 0 && nc < N && nr >= 0 && nr < N) || e == ElfPresent || e == ElfBox) {
						boolean canMove = false;
						if (e == Elf) {
							if (grid[nr][nc] == Empty || grid[nr][nc] == Present) canMove = true;
						} else {
							if (nc < 0 || nc >= N || nr < 0 || nr >= N) {
								// found a path to the border
								moveElf(y, x, d == -1 ? dir : d);
								return;
							}
							if (grid[nr][nc] == Empty) canMove = true;
						}
						if (grid[nr][nc] == Box && dirToBox == -1) // Collect info about the nearest box
						{
							dirToBox = (d == -1 ? dir : d);
						}
						if (canMove) {
							if (d == -1)
								qd.add(dir);
							else
								qd.add(d);
							qr.add(nr);
							qc.add(nc);
						}
					}
				}
			}
		}
		// can't find a path to a present or border when carrying a present, go and pick up a box
		if (dirToBox != -1 && e == Elf) {
			moveElf(y, x, dirToBox);
		}
		// otherwise, remain stationary
	}

	private double callSolution() throws Exception {
		StopTheElves solution = null;
		if (parameters.isDefined(Parameters.myExec)) {
			solution = new StopTheElves();
		}
		writeLine(N);
		writeLine(C);
		writeLine("" + elfP);
		writeLine(money);

		//print grid
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++)
				writeLine("" + grid[r][c]);

		flush();
		if (solution != null) {
			solution.init(N, C, elfP, money, grid);
		} else {
			if (!isReadActive()) return -1;
		}

		if (hasVis() && hasDelay()) {
			updateDelay();
		}

		int[] elves = new int[N * N];
		for (numTurns = 1; numTurns <= N * N; numTurns++) {

			startTime();
			String line;
			if (solution != null) {
				line = solution.move();
			} else  {
				line = readLine();
			}
			stopTime();

			numLastBoxes = 0;
			String[] temp = line.trim().split(" ");
			if (temp.length % 2 == 1) {
				if (temp.length != 1) {
					return fatalError("Cannot parse your output. Uneven number of elements. It should contain coordinates.");
				}
			} else {
				// Plant the boxes
				for (int i = 0; i < temp.length; i += 2) {
					try {
						int row = Integer.parseInt(temp[i]);
						int col = Integer.parseInt(temp[i + 1]);
						if (col <= 0 || col >= N - 1 || row <= 0 || row >= N - 1)
							return fatalError("You can not place on the border or outside the grid.");
						if (grid[row][col] != Empty) {
							return fatalError("Trying to place a box on a non empty cell.");
						}
						if (money < C) {
							return fatalError("Not enough money.");
						}
						grid[row][col] = Box;
						money -= C;
						boxesPlaced++;
						lastBoxes[numLastBoxes++] = row + col * N;
					} catch (Exception e) {
						if (debug) e.printStackTrace();
						return fatalError("Cannot parse your output");
					}
				}
			}

			// Move the elves
			int numElves = 0;
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++)
					if (grid[r][c] == Elf || grid[r][c] == ElfPresent || grid[r][c] == ElfBox) {
						elves[numElves++] = r + c * N;
					}
			}
			for (int i = 0; i < numElves; i++) {
				findMove(elves[i] % N, elves[i] / N);
			}

			// Spawn the elves
			int p = spawnPoint[numTurns];
			if (p >= 0) {
				// spawn one
				int pr, pc;
				int check = 0;
				while (true) {
					check++;
					if (check > N * 4) break; // no space to spawn, then don't spawn
					if (p < N) {
						pr = 0;
						pc = p;
					} else if (p < N * 2 - 1) {
						pr = p + 1 - N;
						pc = N - 1;
					} else if (p < N * 3 - 2) {
						pr = N - 1;
						pc = N - 1 - (p - N * 2 + 2);
					} else {
						pr = N - 1 - (p - N * 3 + 3);
						pc = 0;
					}
					if (grid[pr][pc] == Empty) {
						grid[pr][pc] = Elf;
						break;
					}
					p = (1 + p) % (N * 4 - 4);
				}
			}

			// Increase money
			money++;

			// Update score
			score = PresentValue * presents + money;

			updateState();    //state

			if (presents == 0) break; // end simulation when all the presents were stolen.

			//output elapsed time, money and the grid
			writeLine("" + getRunTime());
			writeLine("" + money);
			//print grid
			for (int r = 0; r < N; r++)
				for (int c = 0; c < N; c++)
					writeLine("" + grid[r][c]);
			flush();
			if (solution != null) {
				solution.update(getRunTime(), money, grid);
			}
		}

		return score;
	}


	protected void paintContent(Graphics2D g) {
		g.setStroke(new BasicStroke(0.005f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		//draw grid
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++) {
				g.setColor(Color.white);
				g.fillRect(c, r, 1, 1);
				g.setColor(Color.gray);
				g.drawRect(c, r, 1, 1);
			}
		//draw objects
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++) {
				if (parameters.isDefined("noImages")) {
					if (grid[r][c] != Empty) {
						if (grid[r][c] == Elf || grid[r][c] == ElfPresent || grid[r][c] == ElfBox) {
							if (grid[r][c] == Elf) g.setColor(Color.blue);
							else if (grid[r][c] == ElfBox) g.setColor(new Color(153, 102, 0));
							else g.setColor(Color.red);
							Ellipse2D t = new Ellipse2D.Double(c + 0.15, r + 0.15, 0.7, 0.7);
							g.fill(t);
							continue;
						}

						if (grid[r][c] == Tree) g.setColor(Color.green);
						else if (grid[r][c] == Present) g.setColor(Color.red);
						else if (grid[r][c] == Box) g.setColor(new Color(153, 102, 0));
						g.fillRect(c, r, 1, 1);
					}
				} else {
					if (grid[r][c] == Tree) g.drawImage(treePic, c, r, 1, 1, null);
					else if (grid[r][c] == Box) g.drawImage(boxPic, c, r, 1, 1, null);
					else if (grid[r][c] == Present) g.drawImage(presentPic, c, r, 1, 1, null);
					else if (grid[r][c] == Elf) g.drawImage(elfPic, c, r, 1, 1, null);
					else if (grid[r][c] == ElfPresent) g.drawImage(elfPresentPic, c, r, 1, 1, null);
					else if (grid[r][c] == ElfBox) g.drawImage(elfBoxPic, c, r, 1, 1, null);
				}
			}
		//highlight the last placed boxes
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(0.05f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		for (int i = 0; i < numLastBoxes; i++) {
			g.drawRect(lastBoxes[i] / N, lastBoxes[i] % N, 1, 1);
		}

	}


	private void init() {
		numTurns = 0;
		score = PresentValue * presents + money;
		if (hasVis()) {
			setDefaultDelay(100);    //this needs to be first

			if (!parameters.isDefined("noImages")) {
				presentPic = loadImage("images/present.png");
				treePic = loadImage("images/tree.png");
				boxPic = loadImage("images/box.png");
				elfPic = loadImage("images/elf.png");
				elfPresentPic = loadImage("images/elfPresent.png");
				elfBoxPic = loadImage("images/elfBox.png");
			}

			setContentRect(0, 0, N, N);
			setInfoMaxDimension(17, 13);

			addInfo("Seed", seed);
			addInfo("N", N);
			addInfo("C", C);
			addInfo("elfP", String.format("%.5f", elfP));
			addInfo("presentP", String.format("%.5f", presentP));
			addInfo("treeP", String.format("%.5f", treeP));

			addInfoBreak();
			addInfo("Presents left", presents);
			addInfo("Boxes placed", boxesPlaced);
			addInfo("Money", money);
			addInfo("Turns", numTurns);
			addInfo("Score", score);

			addInfoBreak();
			addInfo("Time", "-");
			update();
		}
	}

	Image loadImage(String name) {
		try {
			return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(name)));
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		new MarathonController().run(args);
	}

	@Override
	public Void call() throws Exception {
		main(StopTheElves.EVALUATOR_PARAMETERS.split(" "));
		return null;
	}
}
