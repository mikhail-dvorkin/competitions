package marathons.topcoder.chickenRun;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.util.List;
import java.util.ArrayList;

import marathons.utils.topcoder2021_09_15.*;

public class ChickenRunTester extends MarathonAnimatedVis {
  //parameter ranges
  private static final int minN = 6, maxN = 30;
  private static final double minC = 0.03, maxC = 0.1;
  private static final double minP = 0.01, maxP = 0.03;
  private static final double minW = 0.01, maxW = 0.15;
  private static final int minPeople = 4;

  //Inputs
  private int N;      //grid size
  private double ratioC;    //chicken ratio
  private double ratioP;    //people ratio
  private double ratioW;    //wall ratio

  //Constants
  private static final char Chicken = 'C';
  private static final char Person = 'P';
  private static final char Wall = '#';
  private static final char Empty = '.';
  private static final int[] dr={0,-1,0,1};
  private static final int[] dc={-1,0,1,0};

  //Graphics
  private Image PersonPic;
  private Image ChickenPic;
  private Image WallPic;

  //State Control
  private char[][] Grid;
  private int Chickens;
  private int People;
  private int numTurns;
  private int[] catchTurns;
  private int global_score;
  private List<Integer> Catches;


  protected void generate()
  {
    N = randomInt(minN, maxN);
    ratioC = randomDouble(minC, maxC);
    ratioP = randomDouble(minP, maxP);
    ratioW = randomDouble(minW, maxW);

    //Special cases
    if (seed == 1)
    {
      N = minN;
    }
    else if (seed == 2)
    {
      N = maxN;
    }

    //User defined parameters
    if (parameters.isDefined("N"))
    {
      N = randomInt(parameters.getIntRange("N"), minN, maxN);
    }
    if (parameters.isDefined("ratioC"))
    {
      ratioC = randomDouble(parameters.getDoubleRange("ratioC"), minC, maxC);
    }
    if (parameters.isDefined("ratioP"))
    {
      ratioP = randomDouble(parameters.getDoubleRange("ratioP"), minP, maxP);
    }
    if (parameters.isDefined("ratioW"))
    {
      ratioW = randomDouble(parameters.getDoubleRange("ratioW"), minW, maxW);
    }

    Grid = new char[N][N];
    Catches = new ArrayList<Integer>();

    //initialize grid
    //make sure there is at least minPeople people, one chicken and all spaces are reachable
    while(true)
    {
      Chickens=0;
      People=0;
      boolean seenEmpty=false;

      for (int i=0; i<N; i++)
        for (int k=0; k<N; k++)
        {
          double a=randomDouble(0,1);
          if (a<ratioC)
          {
            Grid[i][k]=Chicken;
            Chickens++;
          }
          else if (a<ratioC+ratioP)
          {
            Grid[i][k]=Person;
            People++;
          }
          else if (a<ratioC+ratioP+ratioW)
          {
            Grid[i][k]=Wall;
          }
          else
          {
            Grid[i][k]=Empty;
            seenEmpty=true;
          }
        }

      if (Chickens>0 && People>=minPeople && seenEmpty && isReachable()) break;
    }


    if (debug)
    {
      System.out.println("Grid Size, N = " + N);
      System.out.println("Chicken ratio, ratioC = " + ratioC);
      System.out.println("Person ratio, ratioP = " + ratioP);
      System.out.println("Wall ratio, ratioW = " + ratioW);
      System.out.println();
      System.out.println("Grid:");
      for (int r=0; r<N; r++)
      {
        for (int c=0; c<N; c++)
          System.out.print(Grid[r][c]+" ");
        System.out.println();
      }
    }
  }

  //check that all empty cells are reachable
  private boolean isReachable()
  {
    boolean[][] seen=new boolean[N][N];

    loop:
    for (int r=0; r<N; r++)
      for (int c=0; c<N; c++)
        if (Grid[r][c]==Empty)
        {
          dfs(r,c,seen);
          break loop;
        }

    for (int r=0; r<N; r++)
      for (int c=0; c<N; c++)
        if (Grid[r][c]!=Wall && !seen[r][c])
          return false;

    return true;
  }

  private void dfs(int r, int c, boolean[][] seen)
  {
    if (!inGrid(r,c) || seen[r][c] || Grid[r][c]==Wall) return;

    seen[r][c]=true;
    dfs(r+1,c,seen);
    dfs(r-1,c,seen);
    dfs(r,c+1,seen);
    dfs(r,c-1,seen);
  }

  protected boolean isMaximize() {
    return true;
  }

  protected double run() throws Exception
  {
    init();

    return runAuto();
  }


  protected double runAuto() throws Exception
  {
    double score = callSolution();
    if (score < 0) {
      if (!isReadActive()) return getErrorScore();
      return fatalError();
    }
    return score;
  }

  protected void updateState()
  {
    if (hasVis())
    {
      synchronized (updateLock) {
        addInfo("Chickens", Chickens);
        addInfo("Turns", numTurns);
        addInfo("Time", getRunTime());
        addInfo("Score",getScore());
      }
      updateDelay();
    }
  }

  protected void timeout() {
    addInfo("Time", getRunTime());
    update();
  }

  private boolean inGrid(int r, int c)
  {
    return (r>=0 && r<N && c>=0 && c<N);
  }

  private boolean areNeighbours(int r1, int c1, int r2, int c2)
  {
    return (Math.abs(r1-r2)+Math.abs(c1-c2)==1);
  }

  private int getScore()
  {
    return global_score;
  }

  private int getScoreRecompute()
  {
    int ret = 0;
    for(int turn : catchTurns)
      if(turn>0)
        ret += N * N - (turn-1);
    return ret;
  }


  private double callSolution() throws Exception
  {
    writeLine(N);
    for (int r=0; r<N; r++)
      for (int c=0; c<N; c++)
        writeLine(""+Grid[r][c]);

    flush();
    if (!isReadActive()) return -1;

    if (hasVis() && hasDelay()) {
      synchronized (updateLock) {
      }
      updateDelay();
    }

    boolean[][] used=new boolean[N][N];       //objects that have already moved
    boolean[][] bad=new boolean[N][N];        //locations that chickens try to avoid

    int[] ind=new int[N*N];
    for (int i=0; i<ind.length; i++) ind[i]=i;

    int[] dir={0,1,2,3};

    int maxTurns = N*N;
    numTurns = 0;

    while(Chickens>0)
    {
      Catches.clear();

      if (debug) System.out.println("Turn "+numTurns+":");

      if (numTurns > maxTurns)
      {
        setErrorMessage("Used more than "+maxTurns+" turns");
        return -1;
      }

      String[] moves=null;
      int numMoves=0;

      startTime();
      try
      {
        numMoves = Integer.parseInt(readLine());
      }
      catch(Exception e)
      {
        setErrorMessage("Error reading number of moves");
        return -1;
      }
      if (numMoves>People)
      {
        setErrorMessage("Too many moves!");
        return -1;
      }

      if (numMoves==-1) break;

      numTurns++;


      moves=new String[numMoves];
      for (int i=0; i<numMoves; i++) moves[i] = readLine();
      stopTime();

      for (int i=0; i<N; i++) for (int k=0; k<N; k++) used[i][k]=false;

      //parse moves
      for (int i=0; i<numMoves; i++)
      {
        int r1, c1, r2, c2;
        String move=moves[i];

        try
        {
          String[] temp=move.split(" ");
          if (temp.length!=4)
          {
            setErrorMessage("Error reading move: "+move);
            return -1;
          }
          r1 = Integer.parseInt(temp[0]);
          c1 = Integer.parseInt(temp[1]);
          r2 = Integer.parseInt(temp[2]);
          c2 = Integer.parseInt(temp[3]);
        }
        catch(Exception e)
        {
          setErrorMessage("Error reading move: "+move);
          return -1;
        }
        if (!inGrid(r1,c1) || !inGrid(r2,c2) || !areNeighbours(r1,c1,r2,c2))
        {
          setErrorMessage("Invalid coordinates of move: "+move);
          return -1;
        }
        if (used[r1][c1])
        {
          setErrorMessage("This person has already moved this turn: "+move);
          return -1;
        }
        if (Grid[r1][c1]!=Person)
        {
          setErrorMessage("You can only move people: "+move);
          return -1;
        }
        if (!(Grid[r2][c2]==Empty || Grid[r2][c2]==Chicken))
        {
          setErrorMessage("You can only move into empty or chicken cells: "+move);
          return -1;
        }

        //if we are here then the move was valid, so apply it
        if (Grid[r2][c2]==Chicken)    //catch the chicken :)
        {
          Chickens--;
          catchTurns[Chickens] = numTurns;
          global_score += N * N - (numTurns-1);
          Catches.add(r2*N+c2);
        }
        Grid[r1][c1]=Empty;
        Grid[r2][c2]=Person;
        used[r2][c2]=true;
        if (parameters.isDefined("showAllMoves")) updateState();

        if (debug) System.out.println("   Move "+(i+1)+": "+move);
      }
      if (!parameters.isDefined("showAllMoves")) updateState();

      //now move the chickens away from people's reach
      for (int i=0; i<N; i++) for (int k=0; k<N; k++) used[i][k]=false;

      for (int i=0; i<N; i++) for (int k=0; k<N; k++) bad[i][k]=false;
      for (int r=0; r<N; r++)
        for (int c=0; c<N; c++)
          if (Grid[r][c]==Person)
            for (int m=0; m<dir.length; m++)
            {
              int r2=r+dr[m];
              int c2=c+dc[m];
              if (inGrid(r2,c2)) bad[r2][c2]=true;
            }

      shuffle(ind);

      for (int i=0; i<ind.length; i++)
      {
        int r=ind[i]/N;
        int c=ind[i]%N;
        if (Grid[r][c]==Chicken && !used[r][c])
        {
          shuffle(dir);
          for (int m=0; m<dir.length; m++)
          {
            int r2=r+dr[dir[m]];
            int c2=c+dc[dir[m]];
            if (inGrid(r2,c2) && Grid[r2][c2]==Empty && !bad[r2][c2])
            {
              Grid[r][c]=Empty;
              Grid[r2][c2]=Chicken;
              used[r2][c2]=true;
              if (parameters.isDefined("showAllMoves")) updateState();
              break;
            }
          }
        }
      }
      if (!parameters.isDefined("showAllMoves")) updateState();

      //print elapsed time
      writeLine(""+getRunTime());

      //print the new grid
      for (int r=0; r<N; r++)
        for (int c=0; c<N; c++)
          writeLine(""+Grid[r][c]);

      flush();
    }

    return getScore();
  }

  //shuffle the array randomly
  protected void shuffle(int[] a)
  {
    for (int i=0; i<a.length; i++)
    {
      int k=randomInt(i, a.length-1);
      int temp=a[i];
      a[i]=a[k];
      a[k]=temp;
    }
  }

  protected void paintContent(Graphics2D g)
  {
    g.setStroke(new BasicStroke(0.005f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    //draw grid
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
      {
        g.setColor(Color.white);
        g.fillRect(c, r, 1, 1);
        g.setColor(Color.gray);
        g.drawRect(c, r, 1, 1);
      }

    //draw catches
    for (int i : Catches)
    {
      int r=i/N;
      int c=i%N;

      g.setColor(Color.red);
      g.fillRect(c, r, 1, 1);
    }

    //draw objects
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
      {
        if (parameters.isDefined("noImages"))
        {
          if (Grid[r][c]==Wall)
          {
            g.setColor(Color.blue);
            g.fillRect(c, r, 1, 1);
          }
          if (Grid[r][c]==Person)
          {
            g.setColor(Color.green);
            Ellipse2D t = new Ellipse2D.Double(c + 0.15, r + 0.15, 0.7, 0.7);
            g.fill(t);
          }
          if (Grid[r][c]==Chicken)
          {
            g.setColor(Color.orange);
            Ellipse2D t = new Ellipse2D.Double(c + 0.15, r + 0.15, 0.7, 0.7);
            g.fill(t);
          }
        }
        else
        {
          if (Grid[r][c]==Wall) g.drawImage(WallPic,c,r,1,1,null);
          if (Grid[r][c]==Person) g.drawImage(PersonPic,c,r,1,1,null);
          if (Grid[r][c]==Chicken) g.drawImage(ChickenPic,c,r,1,1,null);
        }
      }
  }


  private void init()
  {
    numTurns = 0;
    catchTurns = new int[Chickens];
    global_score = 0;

    if (hasVis())
    {
      setDefaultDelay(100);    //this needs to be first

      if (!parameters.isDefined("noImages"))
      {
        PersonPic = loadImage("images/farmer2.png");
        ChickenPic = loadImage("images/chicken2.png");
        WallPic = loadImage("images/hay2.png");
      }

      setContentRect(0, 0, N, N);
      setInfoMaxDimension(15, 11);

      addInfo("Seed", seed);
      addInfo("Size N", N);
      addInfo("ratioC", String.format("%.5f",ratioC));
      addInfo("ratioP", String.format("%.5f",ratioP));
      addInfo("ratioW", String.format("%.5f",ratioW));
      addInfoBreak();
      addInfo("Chickens", Chickens);
      addInfo("Turns", numTurns);
      addInfo("Score", getScore());
      addInfoBreak();
      addInfo("Time", "-");
      update();
    }
  }

  Image loadImage(String name) {
    try{
      //Image im=ImageIO.read(new File(name));
      Image im=ImageIO.read(getClass().getResourceAsStream(name));
      return im;
    } catch (Exception e) {
      return null;
    }
  }

  public static void main(String[] args) {
    new MarathonController().run(args);
  }
}
