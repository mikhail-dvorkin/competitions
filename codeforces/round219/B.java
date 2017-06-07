package codeforces.round219;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class B {
	private static BufferedReader in;
	private static PrintWriter out = new PrintWriter(System.out);

	public void run() throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		int h = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		int queries = Integer.parseInt(st.nextToken());
		boolean[][] f = new boolean[h][w];
		for (int i = 0; i < h; i++) {
			String s = in.readLine();
			for (int j = 0; j < w; j++) {
				f[i][j] = s.charAt(j) == '1';
			}
		}
		int[][][][] count = new int[h][w][h + 1][w + 1];
		int[][][][] local = new int[h + 1][w + 1][h + 1][w + 1];
		int[][][][] sum = new int[h][w][h + 1][w + 1];
		for (int iSize = 1; iSize <= h; iSize++) {
			for (int jSize = 1; jSize <= w; jSize++) {
				for (int i1 = 0; i1 + iSize <= h; i1++) {
					for (int j1 = 0; j1 + jSize <= w; j1++) {
						int i2 = i1 + iSize;
						int j2 = j1 + jSize;
						sum[i1][j1][i2][j2] =
								sum[i1][j1][i2 - 1][j2] +
								sum[i1][j1][i2][j2 - 1] -
								sum[i1][j1][i2 - 1][j2 - 1] +
								(f[i2 - 1][j2 - 1] ? 1 : 0);
						local[i1][j1][i2][j2] =
								local[i1 + 1][j1][i2][j2] +
								local[i1][j1 + 1][i2][j2] -
								local[i1 + 1][j1 + 1][i2][j2] +
								(sum[i1][j1][i2][j2] == 0 ? 1 : 0);
						count[i1][j1][i2][j2] =
								count[i1][j1][i2 - 1][j2] +
								count[i1][j1][i2][j2 - 1] -
								count[i1][j1][i2 - 1][j2 - 1] +
								local[i1][j1][i2][j2];
					}
				}
			}
		}
		for (int q = 0; q < queries; q++) {
			st = new StringTokenizer(in.readLine());
			int i1 = Integer.parseInt(st.nextToken()) - 1;
			int j1 = Integer.parseInt(st.nextToken()) - 1;
			int i2 = Integer.parseInt(st.nextToken());
			int j2 = Integer.parseInt(st.nextToken());
			out.println(count[i1][j1][i2][j2]);
		}
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new B().run();
		in.close();
		out.close();
	}
}
