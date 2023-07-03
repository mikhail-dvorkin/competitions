package marathons.topcoder.happyGrid;

import marathons.utils.topcoderMy.MarathonAnimatedVis;
import marathons.utils.topcoderMy.MarathonController;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Stack;

public class HappyGridTester extends MarathonAnimatedVis {
	//parameter ranges
	private static final int minN = 8, maxN = 30;     // grid size range
	private static final int minC = 2, maxC = 6;      // number of colours range
	private static final int minK = 2, maxK = 8;      // required component size range
	private static final int minP = 4, maxP = 12;     // P multiplier range
	private static final double minW = 0, maxW = 0.3;      // wall fill ratio range
	//Constants
	private static final int Wall = 0;
	//Graphics
	Color[] colors = {Color.blue, Color.red, Color.green, Color.magenta, Color.orange, Color.cyan};
	Stack<SaveUpdate> updates = new Stack<SaveUpdate>();
	//Inputs
	private int N;            // grid size
	private int C;            // number of colours
	private int K;            // required component size
	private int P;            // K-component value
	private double W;         // wall fill ratio
	//State Control
	private int[][] grid;
	private int[][] components;       // components[r][c] is the component id of cell (r,c)
	private int[] counts;             // counts[i] is the number of cells with component id i
	private int numMoves;             // number of moves used
	private int numComponents;        // number of K-components
	private int selectedR = -1;         // these are the selected locations for the current swap
	private int selectedC = -1;
	private int selectedR2 = -1;
	private int selectedC2 = -1;
	private double score;

	public static void main(String[] args) {
		new MarathonController().run(args);
	}

	protected void generate() {
		N = randomInt(minN, maxN);
		C = randomInt(minC, maxC);
		K = randomInt(minK, maxK);
		P = randomInt(minP * K, maxP * K);
		W = randomDouble(minW, maxW);

		//Special cases
		if (seed == 1) {
			N = minN;
			C = 3;
		} else if (seed == 2) {
			N = maxN;
			C = maxC;
		}

		//User defined parameters
		if (parameters.isDefined("N")) N = randomInt(parameters.getIntRange("N"), minN, maxN);
		if (parameters.isDefined("C")) C = randomInt(parameters.getIntRange("C"), minC, maxC);
		if (parameters.isDefined("K")) {
			K = randomInt(parameters.getIntRange("K"), minK, maxK);
			P = randomInt(minP * K, maxP * K);
		}
		if (parameters.isDefined("P")) P = randomInt(parameters.getIntRange("P"), minP * K, maxP * K);
		if (parameters.isDefined("W")) W = randomDouble(parameters.getDoubleRange("W"), minW, maxW);


		components = new int[N][N];
		counts = new int[N * N + 1];
		grid = new int[N][N];

		//generate grid
		int count = 0;
		while (true) {
			count++;
			for (int r = 0; r < N; r++)
				for (int c = 0; c < N; c++)
					if (randomDouble(0, 1) < W)
						grid[r][c] = Wall;
					else
						grid[r][c] = randomInt(1, C);

			if (isGridGood()) break;
		}

		numMoves = 0;
		numComponents = 0;

		if (debug) {
			System.out.println("Grids generated " + count);
			System.out.println("Grid size, N = " + N);
			System.out.println("Number of colours, C = " + C);
			System.out.println("Required component size, K = " + K);
			System.out.println("K-component value, P = " + P);
			System.out.println("Wall fill ratio, W = " + W);
			System.out.println("Grid:");
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) System.out.print(grid[r][c]);
				System.out.println();
			}
		}
	}

	//check that all the balls are connected and that there is enough to make a component in each colour
	protected boolean isGridGood() {
		findComponents(false);

		int[] q = new int[C + 1];

		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++) {
				q[grid[r][c]]++;
				if (components[r][c] > 1)
					return false;
			}

		for (int i = 1; i <= C; i++)
			if (q[i] < K)
				return false;

		return true;
	}

	protected boolean isMaximize() {
		return true;
	}

	protected double run() throws Exception {
		init();

		if (parameters.isDefined("manual")) {
			setDefaultDelay(0);
			return 0;
		} else return runAuto();
	}

	protected double runAuto() throws Exception {
		double score = callSolution();
		if (score < 0) {
			if (!isReadActive()) return getErrorScore();
			return fatalError();
		}
		return score;
	}

	protected void timeout() {
		addInfo("Time", getRunTime());
		update();
	}

	private double callSolution() throws Exception {
		writeLine(String.valueOf(N));
		writeLine(String.valueOf(C));
		writeLine(String.valueOf(K));
		writeLine(String.valueOf(P));
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++)
				writeLine(String.valueOf(grid[r][c]));

		flush();
		if (!isReadActive()) return -1;

		updateState();

		try {
			//read solution output
			startTime();
			int n = Integer.parseInt(readLine());
			if (n < 0 || n > 2 * N * N * N)
				return fatalError("Number of moves must between 0 and " + (2 * N * N * N) + " inclusive");

			String[] moves = new String[n];
			for (int i = 0; i < n; i++) moves[i] = readLine();
			stopTime();

			for (numMoves = 1; numMoves <= n; numMoves++) {
				String[] temp = moves[numMoves - 1].split(" ");
				if (temp.length != 4)
					return fatalError("Each move must contain exactly 4 numbers separated by a space");

				int r1 = Integer.parseInt(temp[0]);
				int c1 = Integer.parseInt(temp[1]);
				int r2 = Integer.parseInt(temp[2]);
				int c2 = Integer.parseInt(temp[3]);

				if (!inGrid(r1, c1))
					return fatalError("Location (" + r1 + "," + c1 + ") is not in the grid");
				if (!inGrid(r2, c2))
					return fatalError("Location (" + r2 + "," + c2 + ") is not in the grid");
				if (grid[r1][c1] == Wall)
					return fatalError("Location (" + r1 + "," + c1 + ") cannot be a wall");
				if (grid[r2][c2] == Wall)
					return fatalError("Location (" + r2 + "," + c2 + ") cannot be a wall");
				if (Math.abs(r1 - r2) + Math.abs(c1 - c2) != 1)
					return fatalError("Locations (" + r1 + "," + c1 + ") and (" + r2 + "," + c2 + ") are not adjacent");

				//make move
				int temp2 = grid[r1][c1];
				grid[r1][c1] = grid[r2][c2];
				grid[r2][c2] = temp2;
				selectedR = r1;
				selectedC = c1;
				selectedR2 = r2;
				selectedC2 = c2;
				findComponents(true);
				score = numComponents * P - numMoves;
				if (!parameters.isDefined("noanimate")) updateState();
			}
			if (parameters.isDefined("noanimate")) updateState();
		} catch (Exception e) {
			if (debug) System.out.println(e);
			return fatalError("Cannot parse your output");
		}

		return score;
	}

	protected void findComponents(boolean oneColour) {
		for (int i = 0; i < N; i++) for (int k = 0; k < N; k++) components[i][k] = 0;

		int id = 0;
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++)
				if (components[r][c] == 0 && grid[r][c] != Wall) {
					id++;
					findComponents(oneColour, r, c, id, grid[r][c]);
				}

		for (int i = 0; i < counts.length; i++) counts[i] = 0;
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++)
				counts[components[r][c]]++;

		numComponents = 0;
		for (int i = 1; i <= id; i++)
			if (counts[i] == K)
				numComponents++;
	}

	protected void findComponents(boolean oneColour, int r, int c, int id, int colour) {
		if (!inGrid(r, c)) return;   //out of bounds
		if (grid[r][c] == Wall) return;   //wall
		if (components[r][c] != 0) return;    //already seen
		if (oneColour && grid[r][c] != colour) return;     //wrong colour

		components[r][c] = id;
		findComponents(oneColour, r + 1, c, id, colour);
		findComponents(oneColour, r - 1, c, id, colour);
		findComponents(oneColour, r, c + 1, id, colour);
		findComponents(oneColour, r, c - 1, id, colour);
	}

	protected boolean inGrid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}

	protected void updateState() {
		if (hasVis()) {
			synchronized (updateLock) {
				addInfo("Moves", numMoves);
				addInfo("Components", numComponents);
				addInfo("Time", getRunTime());
				addInfo("Score", score);
			}
			updateDelay();
		}
	}

	protected void undo() {
		if (updates.isEmpty()) return;

		SaveUpdate u = updates.pop();

		int temp = grid[u.r1][u.c1];
		grid[u.r1][u.c1] = grid[u.r2][u.c2];
		grid[u.r2][u.c2] = temp;

		findComponents(true);
		numMoves--;
		score = numComponents * P - numMoves;
		selectedR = -1;
		selectedC = -1;
		selectedR2 = -1;
		selectedC2 = -1;
		updateState();
	}


	protected void contentClicked(double x, double y, int mouseButton, int clickCount) {
		if (!parameters.isDefined("manual")) return;

		//undo with right click
		if (mouseButton == java.awt.event.MouseEvent.BUTTON3) {
			undo();
			return;
		}

		int r = (int) Math.floor(y);
		int c = (int) Math.floor(x);

		if (!inGrid(r, c)) return;       //outside of grid
		if (grid[r][c] == Wall) return;   //cannot select a wall

		//unselect
		if (r == selectedR && c == selectedC) {
			selectedR = -1;
			selectedC = -1;
			updateState();
			return;
		} else if (selectedR == -1) {
			selectedR = r;
			selectedC = c;
			updateState();
			return;
		}

		if (Math.abs(r - selectedR) + Math.abs(c - selectedC) != 1) return;   //not neighbours

		selectedR2 = r;
		selectedC2 = c;

		//make move
		int temp = grid[selectedR][selectedC];
		grid[selectedR][selectedC] = grid[selectedR2][selectedC2];
		grid[selectedR2][selectedC2] = temp;

		updates.push(new SaveUpdate(selectedR, selectedC, selectedR2, selectedC2));

		findComponents(true);
		numMoves++;
		score = numComponents * P - numMoves;
		selectedR = -1;
		selectedC = -1;
		selectedR2 = -1;
		selectedC2 = -1;
		updateState();    //show swap and new score
	}


	protected void paintContent(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, N, N);

		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++) {
				if (grid[r][c] == Wall) {
					g.setColor(Color.lightGray);
					g.fillRect(c, r, 1, 1);
				} else {
					g.setColor(colors[grid[r][c] - 1]);
					Ellipse2D t = new Ellipse2D.Double(c + 0.2, r + 0.2, 0.6, 0.6);
					g.fill(t);

					if (counts[components[r][c]] == K) {
						//connection down
						if (r < N - 1 && components[r + 1][c] == components[r][c]) {
							Rectangle2D t2 = new Rectangle2D.Double(c + 0.4, r + 0.5, 0.2, 1);
							g.fill(t2);
						}
						//connection right
						if (c < N - 1 && components[r][c + 1] == components[r][c]) {
							Rectangle2D t2 = new Rectangle2D.Double(c + 0.5, r + 0.4, 1, 0.2);
							g.fill(t2);
						}
					}
				}
			}

		g.setColor(Color.black);
		g.setStroke(new BasicStroke(0.005f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		for (int i = 0; i < N; i++) {
			g.drawLine(i, 0, i, N);
			g.drawLine(0, i, N, i);
		}

		//draw selected squares
		g.setColor(Color.blue);
		g.setStroke(new BasicStroke(0.05f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if (selectedR != -1) g.drawRect(selectedC, selectedR, 1, 1);
		if (selectedR2 != -1) g.drawRect(selectedC2, selectedR2, 1, 1);
	}


	private double shorten(double a) {
		return (double) Math.round(a * 1000.0) / 1000.0;
	}


	private void init() {
		if (hasVis()) {
			setDefaultDelay(1000);    //this needs to be first

			setContentRect(0, 0, N, N);
			setInfoMaxDimension(20, 11);

			findComponents(true);
			score = numComponents * P;

			addInfo("Seed", seed);
			addInfo("N", N);
			addInfo("C", C);
			addInfo("K", K);
			addInfo("P", P);
			addInfo("W", shorten(W));

			addInfoBreak();
			addInfo("Moves", 0);
			addInfo("Components", numComponents);

			addInfoBreak();
			addInfo("Time", "-");
			addInfo("Score", score);
			update();
		}
	}

	class SaveUpdate {
		public int r1, c1, r2, c2;

		SaveUpdate(int R1, int C1, int R2, int C2) {
			r1 = R1;
			c1 = C1;
			r2 = R2;
			c2 = C2;
		}
	}
}
