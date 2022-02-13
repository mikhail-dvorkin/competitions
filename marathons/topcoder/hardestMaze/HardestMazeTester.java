package marathons.topcoder.hardestMaze;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.concurrent.Callable;

import marathons.utils.Evaluator;
import marathons.utils.topcoderMy.*;

/**
 * Code published by TopCoder.
 *
 * Manual mode added by wleite.
 */
public class HardestMazeTester extends MarathonVis implements Callable<Void> {
	//parameter ranges
	private static final int minN = 10, maxN = 40;
	private static final int minR = 1, maxR = 6;
	private static final int minT = 2, maxT = 6;

	//Inputs
	private int N;      //grid size
	private int R;      //number of robots
	private int T;      //number of targets
	private int[][] Starts;       //start locations
	private int[][][] Targets;    //target locations

	//Constants
	private static final int INF=1000000;
	private static final int[] dr = {0,1,0,-1};     //L, U, R, D
	private static final int[] dc = {-1,0,1,0};
	private static final char Empty = '.';
	private static final char Wall  = '#';

	//Painting
	private Color[] colors;
	private Color[] colorsLight;

	//State Control
	private boolean[][] Grid;
	private int[] Dist;
	private int Score;
	private int[][] bestInd;
	private int[] PathLengths;

	//Output
	private char[] Solution;

	protected void generate() {
		N = randomInt(minN, maxN);
		R = randomInt(minR, maxR);
		T = randomInt(minT, maxT);

		//Special cases for seeds 1 and 2
		if (seed == 1) {
			N = minN;
			R = 2;
			T = minT;
		} else if (seed == 2) {
			N = maxN;
			R = maxR;
			T = maxT;
		}

		//User defined parameters
		if (parameters.isDefined("N")) N = randomInt(parameters.getIntRange("N"), minN, maxN);
		if (parameters.isDefined("R")) R = randomInt(parameters.getIntRange("R"), minR, maxR);
		if (parameters.isDefined("T")) T = randomInt(parameters.getIntRange("T"), minT, maxT);

		Starts = new int[R][2];
		Targets = new int[R][T][2];

		int[] ind=new int[N*N];
		for (int i=0; i<ind.length; i++) ind[i]=i;
		shuffle(ind);

		//set robot positions
		int cur=0;
		for (int i=0; i<R; i++,cur++)
		{
			Starts[i][0]=ind[cur]/N;
			Starts[i][1]=ind[cur]%N;
		}

		//set target positions
		for (int i=0; i<R; i++)
			for (int k=0; k<T; k++,cur++)
			{
				Targets[i][k][0]=ind[cur]/N;
				Targets[i][k][1]=ind[cur]%N;
			}


		bestInd=new int[R][T];


		if (debug) {
			System.out.println("Grid Size N = " + N);
			System.out.println("Robots R = " + R);
			System.out.println("Targets T = " + T);
			for (int i=0; i<R; i++)
			{
				System.out.println("Robot "+i+" starts at ("+Starts[i][0]+","+Starts[i][1]+")");
				System.out.print("  Targets:");
				for (int k=0; k<T; k++) System.out.print(" ("+Targets[i][k][0]+","+Targets[i][k][1]+")");
				System.out.println();
			}
		}
	}

	protected boolean isMaximize() {
		return true;
	}

	protected void contentClicked(double x, double y, int mouseButton, int clickCount) {
		if (Solution != null) {
			int id = coords2id((int)y, (int)x);
			Solution[id] = Solution[id] == '.'? '#' : '.';
			eval();
			addInfo("Score", Score);
			for (int c = 0; c < R; c++) {
				addInfo(colors[c], PathLengths[c]);
			}
			update();
		}
	}

	protected double run() throws Exception {
		init();
		Solution = callSolution();
		if (Solution == null) {
			if (isReadActive()) return fatalError();
			Solution = new char[N*N];
			Arrays.fill(Solution, '.');
		}
		Dist=new int[N*N*N*N];
		PathLengths=new int[R];
		eval();
		if (hasVis()) {
			addInfo("Score", Score);
			addInfo("Time", getRunTime() + " ms");
			for (int c = 0; c < R; c++) {
				addInfo(colors[c], PathLengths[c]);
			}
			for (String s : myLabels) addInfo(s);
			update();
		}
		return Score;
	}

	private void eval() {
		Arrays.fill(Dist, INF);
		for (int i=0; i<N*N; i++) Grid[i/N][i%N]=(Solution[i]==Wall);
		boolean hasFatalError = false;
		for (int i=0; i<R; i++)
		{
			if (Grid[Starts[i][0]][Starts[i][1]]) {
				fatalError("Cannot have a wall at ("+Starts[i][0]+","+Starts[i][1]+")");
				hasFatalError = true;
			}
			for (int k=0; k<T; k++)
				if (Grid[Targets[i][k][0]][Targets[i][k][1]]) {
					fatalError("Cannot have a wall at ("+Targets[i][k][0]+","+Targets[i][k][1]+")");
					hasFatalError = true;
				}
		}

		if (debug) {
			System.out.println("Your output maze");
			for (int i = 0; i < N; i++) {
				for (int k = 0; k < N; k++) System.out.print(Solution[i*N+k]);
				System.out.println();
			}
		}

		for (int r=0, id=0; r<N; r++)
			for (int c=0; c<N; c++,id++)
			{
				if (Grid[r][c]) continue;       //skip walls

				Dist[id*N*N+id]=0;
				for (int m=0; m<dr.length; m++)
				{
					int r2=r+dr[m];
					int c2=c+dc[m];

					if (inGrid(r2,c2) && !Grid[r2][c2])
					{
						int id2=coords2id(r2,c2);
						Dist[id*N*N+id2]=1;
						Dist[id2*N*N+id]=1;
					}
				}
			}

		allPairsShortestPath(Dist);

		Score=0;
		NEXT: for (int q=0; q<R; q++)
		{
			for (int i=0; i<T; i++) bestInd[q][i]=-1;
			int startId=coords2id(Starts[q][0],Starts[q][1]);

			//check that all targets are reachable
			for (int i=0; i<T; i++)
			{
				int targetId=coords2id(Targets[q][i][0],Targets[q][i][1]);
				if (Dist[startId*N*N+targetId]>=INF) {
					fatalError("Target "+i+" for robot "+q+" is not reachable");
					hasFatalError = true;
					PathLengths[q]=-1;
					continue NEXT;
				}
			}

			PathLengths[q]=Integer.MAX_VALUE;

			int[] ind=new int[T];
			for (int i=0; i<T; i++) ind[i]=i;

			do
			{
				int total=0;
				int prevId=startId;

				for (int i=0; i<T; i++)
				{
					int nextId=coords2id(Targets[q][ind[i]][0],Targets[q][ind[i]][1]);
					total+=Dist[prevId*N*N+nextId];
					prevId=nextId;
				}

				//store target permutation with the shortest path
				if (total<PathLengths[q])
				{
					PathLengths[q]=total;
					System.arraycopy(ind, 0, bestInd[q], 0, T);
				}
			}
			while(nextPermutation(ind));

			Score+=PathLengths[q];
		}
		if (hasFatalError) Score = getErrorScore();
		this.myScore = 100.0 * Score / R / N / N;
	}

	private int coords2id(int r, int c)
	{
		return r*N+c;
	}

    /*
    //Floyd-Warshall's all pairs shortest path algorithm
    //computes the shortest distance from every node to every other node
    //Coolest algorithm ever!
    private void allPairsShortestPath(int[] a)
    {
      for (int k=0; k<N*N; k++)
        for (int i=0; i<N*N; i++)
          for (int j=0; j<N*N; j++)
            a[i*N*N+j]=Math.min(a[i*N*N+j],a[i*N*N+k]+a[k*N*N+j]);
    }
    */

	//Faster all pairs shortest path added by wleite
	private void allPairsShortestPath(int[] a) {
		int[] q = new int[N * N];
		int[] dx = new int[] {-1,1,0,0};
		int[] dy = new int[] {0,0,-1,1};
		for (int k = 0; k < N * N; k++) {
			int knn = k * N * N;
			if (a[knn + k] != 0) continue;
			int tot = 0;
			int curr = 0;
			for (int i = 0; i < N * N; i++) {
				if (a[knn + i] == 1) q[tot++] = i;
			}
			while (curr < tot) {
				int i = q[curr++];
				for (int j = 0; j < 4; j++) {
					int x = i % N + dx[j];
					int y = i / N + dy[j];
					int p = y * N + x;
					if (x >= 0 && x < N && y >= 0 && y < N && a[knn + p] > a[knn + i] + 1 && a[p * N * N + p] == 0) {
						a[knn + p] = a[knn + i] + 1;
						q[tot++] = p;
					}
				}
			}
		}
	}

	//elements in a have to be sorted in ascending order
	//changes the elements to achieve the next permutation
	//returns false if there are no more permutations
	private boolean nextPermutation(int[] a)
	{
		int n=a.length;
		int i=n-2;
		for (; i>=0; i--)
			if (a[i]<a[i+1])
				break;
		if (i<0) return false;

		for (int j=n-1; j>=i; j--)
		{
			if (a[j]>a[i])
			{
				int temp=a[i];
				a[i]=a[j];
				a[j]=temp;
				break;
			}
		}
		for (int j=i+1; j<(n+i+1)/2; j++)     //reverse from a[i+1] to a[n-1]
		{
			int temp=a[j];
			a[j]=a[n+i-j];
			a[n+i-j]=temp;
		}
		return true;
	}

	protected void paintContent(Graphics2D g) {

		//draw grid
		g.setStroke(new BasicStroke(0.005f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++)
			{
				//g.setColor(Color.lightGray);
				g.setColor(Color.white);
				g.fillRect(c, r, 1, 1);
				g.setColor(Color.gray);
				g.drawRect(c, r, 1, 1);
			}

		if (Solution != null)
		{
			//draw walls
			g.setColor(Color.gray);
			for (int r=0; r<N; r++)
				for (int c=0; c<N; c++)
					if (Grid[r][c])
					{
						g.fillRect(c, r, 1, 1);
					}
		}
		//draw starts and targets
		for (int i=0; i<R; i++)
		{
			g.setColor(colors[i]);
			//circle
			// Ellipse2D s = new Ellipse2D.Double(Starts[i][1] + 0.2, Starts[i][0] + 0.2, 0.6, 0.6);
			// g.fill(s);
			//+
			// Rectangle2D s = new Rectangle2D.Double(Starts[i][1] + 0.25, Starts[i][0], 0.5, 1);
			// g.fill(s);
			// Rectangle2D s2 = new Rectangle2D.Double(Starts[i][1], Starts[i][0] + 0.25, 1, 0.5);
			// g.fill(s2);
			//x
			GeneralPath gp = new GeneralPath();
			gp.moveTo(Starts[i][1]+0.2,Starts[i][0]+0.2);
			gp.lineTo(Starts[i][1]+0.8,Starts[i][0]+0.8);
			gp.moveTo(Starts[i][1]+0.8,Starts[i][0]+0.2);
			gp.lineTo(Starts[i][1]+0.2,Starts[i][0]+0.8);
			g.setStroke(new BasicStroke(0.25f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g.draw(gp);

			for (int k=0; k<T; k++)
			{
				g.setColor(colors[i]);
				//circle
				Ellipse2D t = new Ellipse2D.Double(Targets[i][k][1] + 0.2, Targets[i][k][0] + 0.2, 0.6, 0.6);
				g.fill(t);
				//square
				// Rectangle2D t = new Rectangle2D.Double(Targets[i][k][1] + 0.2, Targets[i][k][0] + 0.2, 0.6, 0.6);
				// g.fill(t);
				//+
				// Rectangle2D t = new Rectangle2D.Double(Targets[i][k][1] + 0.25, Targets[i][k][0], 0.5, 1);
				// g.fill(t);
				// Rectangle2D t2 = new Rectangle2D.Double(Targets[i][k][1], Targets[i][k][0] + 0.25, 1, 0.5);
				// g.fill(t2);
			}
		}

		if (Solution != null)
		{
			//draw paths
			for (int i=0; i<R; i++)
			{
				if (!isInfoChecked(colors[i])) continue;
				GeneralPath gp = new GeneralPath();

				int r=Starts[i][0];
				int c=Starts[i][1];
				gp.moveTo(c+0.5,r+0.5);

				for (int k=0; k<T; k++)
				{
					int idx = bestInd[i][k];
					if (idx < 0) continue;
					int targetR=Targets[i][idx][0];
					int targetC=Targets[i][idx][1];
					int targetId=coords2id(targetR,targetC);

					while(true)
					{
						if (r==targetR && c==targetC) break;    //reached our target, so exit

						boolean found=false;
						for (int m=0; m<dr.length; m++)
						{
							int r2=r+dr[m];
							int c2=c+dc[m];

							if (inGrid(r2,c2) && Dist[targetId*N*N+coords2id(r2,c2)]<Dist[targetId*N*N+coords2id(r,c)])
							{
								r=r2;
								c=c2;
								found=true;
								break;
							}
						}
						if (!found) break;          //catch problems when Dist hasn't been computed yet
						gp.lineTo(c+0.5,r+0.5);
					}
				}

				//see-through path
				g.setColor(colorsLight[i]);
				g.setStroke(new BasicStroke(0.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g.draw(gp);
				//central line
				g.setColor(Color.black);
				g.setStroke(new BasicStroke(0.03f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g.draw(gp);
			}
		}
	}

	private boolean inGrid(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}

	private void init() {
		Grid = new boolean[N][N];

		if (hasVis()) {
			colors = new Color[] {Color.red,Color.cyan,Color.green,Color.blue,Color.orange,Color.magenta};

			colorsLight = new Color[colors.length];
			for (int i=0; i<colors.length; i++)
				colorsLight[i] =  new Color(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue(), 80);

			setInfoMaxDimension(15, 11 + R);
			setContentRect(0, 0, N, N);
			setDefaultSize(30);

			addInfo("Seed", seed);
			addInfoBreak();
			addInfo("Size N", N);
			addInfo("Robots R", R);
			addInfo("Targets T", T);
			addInfoBreak();
			addInfo("Score", "-");
			addInfoBreak();
			addInfo("Time", "-");
			addInfoBreak();
			addInfo("Path lengths");
			for (int c = 0; c < R; c++) {
				addInfo(colors[c], 0, true);
			}
			if (!Evaluator._visOnlyFile) update();
		}
	}

	private char[] callSolution() throws Exception {
		if (parameters.isDefined(Parameters.myExec)) {
			HardestMaze._localTimeCoefficient = Evaluator.localTimeCoefficient();
			startTime();
			HardestMaze program = new HardestMaze();
			char[] ans = program.findSolution(N, R, T, Starts, Targets);
			stopTime();
			myTroubles = program._troubles;
			myLabels = program._labels;
			return ans;
		}
		writeLine(N);
		writeLine(R);
		writeLine(T);
		for (int i=0; i<R; i++)
		{
			writeLine(Starts[i][0]+" "+Starts[i][1]);
			for (int k=0; k<T; k++) writeLine(Targets[i][k][0]+" "+Targets[i][k][1]);
		}
		flush();
		if (!isReadActive()) return null;


		startTime();
		int n = readLineToInt(-1);
		if (n != N*N) {
			setErrorMessage("Invalid number of rows: " + getLastLineRead());
			return null;
		}
		char[] ret = new char[n];
		for (int i = 0; i < n; i++) {
			String s = readLine();
			if (s.length() != 1 || !(s.charAt(0)==Empty || s.charAt(0)==Wall)) {
				setErrorMessage("Invalid return in line " + (i + 1) + " : " + getLastLineRead());
				return null;
			}
			ret[i] = s.charAt(0);
		}
		stopTime();
		return ret;
	}

	//shuffle the array randomly
	private void shuffle(int[] a)
	{
		for (int i=0; i<a.length; i++)
		{
			int k=randomInt(i,a.length-1);
			int temp=a[i];
			a[i]=a[k];
			a[k]=temp;
		}
	}

	private static void main(String[] args) {
		new MarathonController().run(args);
	}

	@Override
	public Void call() {
		main(HardestMaze.EVALUATOR_PARAMETERS.split(" "));
		return null;
	}
}
