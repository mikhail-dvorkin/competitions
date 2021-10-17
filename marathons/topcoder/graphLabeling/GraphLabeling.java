package marathons.topcoder.graphLabeling;

import java.io.*;

public class GraphLabeling {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(in.readLine());
		int edges = Integer.parseInt(in.readLine());

		boolean[][] graph = new boolean[N][N];

		for (int i = 0; i < edges; i++) {
			String[] temp = in.readLine().split(" ");
			int node1 = Integer.parseInt(temp[0]);
			int node2 = Integer.parseInt(temp[1]);
			graph[node1][node2] = true;
			graph[node2][node1] = true;
		}

		String out = "";
		for (int i = 0; i < N; i++)
			out += ((1L << i) - 1) + " ";

		System.out.println(out);
		System.out.flush();
	}
}
