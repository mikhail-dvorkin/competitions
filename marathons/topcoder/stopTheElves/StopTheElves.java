package marathons.topcoder.stopTheElves;

import java.io.*;
import java.util.*;

public class StopTheElves {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int C = Integer.parseInt(br.readLine());
		float elfP = Float.parseFloat(br.readLine());
		int money = Integer.parseInt(br.readLine());

		char[][] grid = new char[N][N];
		for (int y = 0; y < N; y++)
			for (int x = 0; x < N; x++)
				grid[x][y] = br.readLine().charAt(0);

		for (int turn = 0; turn < N * N; turn++) {
			int x = 1 + ((turn * (7919)) % (N - 2));
			int y = 1 + ((turn * (50091)) % (N - 2));
			if (money >= C && grid[x][y] == '.') {
				System.out.println(y + " " + x);
			} else {
				System.out.println("-1");
			}
			System.out.flush();

			//read elapsed time
			int elapsedTime = Integer.parseInt(br.readLine());
			//read the money
			money = Integer.parseInt(br.readLine());
			//read the updated grid
			for (y = 0; y < N; y++)
				for (x = 0; x < N; x++)
					grid[x][y] = br.readLine().charAt(0);
		}
	}
}
