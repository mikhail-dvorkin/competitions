package marathons.topcoder.hardestMaze;

import java.io.*;

public class HardestMaze {
	private static final java.util.concurrent.Callable<Void> EVALUATOR =
			new HardestMazeTester(); //TESTING
//			null; //SUBMISSION
	private static final long TIME_LIMIT = 9850;

	public char[] findSolution(int N, int R, int T, int[][] Starts, int[][][] Targets) {
		char[] grid=new char[N*N];
		for (int i=0; i<N*N; i++) grid[i]='.';
		return grid;
	}

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
