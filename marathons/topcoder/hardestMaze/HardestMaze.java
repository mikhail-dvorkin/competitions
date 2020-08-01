package marathons.topcoder.hardestMaze;

import java.io.*;

public class HardestMaze {
	public char[] findSolution(int N, int R, int T, int[][] Starts, int[][][] Targets) {
		char[] grid=new char[N*N];
		for (int i=0; i<N*N; i++) grid[i]='.';
		return grid;
	}

	public static void main(String[] args) {
		try {
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

			HardestMaze prog = new HardestMaze();
			char[] ret = prog.findSolution(N, R, T, Starts, Targets);

			System.out.println(ret.length);
			for (int i = 0; i < ret.length; i++)
				System.out.println(ret[i]);
		}
		catch (Exception e) {}
	}
}
