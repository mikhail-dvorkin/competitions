import java.util.*;

import com.topcoder.marathon.*;

public class GraphLabelingTester extends MarathonTester {
	//Ranges
	private static final int minN = 5, maxN = 500;
	private static final double minC = 0.05, maxC = 1;

	//Inputs
	private int N;          //number of nodes
	private double C;       //connectivity

	//State Control
	private boolean[][] Graph;      //graph's adjacency matrix
	private int Edges;              //number of edges


	protected void generate() {
		N = randomInt(minN, maxN);
		C = randomDouble(minC, maxC);

		//Special cases for seeds 1 and 2
		if (seed == 1) {
			N = minN;
		} else if (seed == 2) {
			N = maxN;
		}


		//User defined parameters
		if (parameters.isDefined("N")) {
			N = randomInt(parameters.getIntRange("N"), minN, maxN);
		}
		if (parameters.isDefined("C")) {
			C = randomDouble(parameters.getDoubleRange("C"), minC, maxC);
		}

		//generate the graph
		while (true) {
			Graph = new boolean[N][N];
			Edges = 0;

			for (int i = 0; i < N; i++)
				for (int k = i + 1; k < N; k++)
					if (randomDouble(0, 1) < C) {
						Graph[i][k] = true;
						Graph[k][i] = true;      //make symmetric
						Edges++;
					}

			if (isConnected()) break;
		}


		if (debug) {
			System.out.println("Nodes = " + N);
			System.out.println("Edges = " + Edges);
			System.out.println("Connectivity = " + C);
			System.out.println("Graph:");
			for (int i = 0; i < N; i++) {
				for (int k = 0; k < N; k++) System.out.print(Graph[i][k] ? "1" : "0");
				System.out.println();
			}
			System.out.println();
			System.out.println();
		}
	}

	//check that the graph forms a single component
	protected boolean isConnected() {
		boolean[] seen = new boolean[N];
		int count = 0;
		List<Integer> Q = new ArrayList<Integer>();
		Q.add(0);

		while (Q.size() > 0) {
			int node = Q.remove(Q.size() - 1);
			if (seen[node]) continue;

			seen[node] = true;
			count++;
			for (int i = 0; i < N; i++)
				if (Graph[node][i])
					Q.add(i);
		}

		return (count == N);
	}

	protected boolean isMaximize() {
		return false;
	}

	protected double run() throws Exception {
		writeLine(N);
		writeLine(Edges);
		for (int i = 0; i < N; i++)
			for (int k = i + 1; k < N; k++)
				if (Graph[i][k])
					writeLine(i + " " + k);
		flush();

		//run the solution and read its output
		startTime();
		String line = readLine();
		stopTime();

		String[] temp = line.trim().split(" ");

		if (debug) {
			System.out.println("Your solution:");
			for (int i = 0; i < N; i++) System.out.println("node " + i + " value " + temp[i]);
			System.out.println();
		}

		if (temp.length != N)
			return fatalError("Your output does not contain " + N + " elements");


		Set<Long> seen = new HashSet<Long>();
		long[] values = new long[N];
		long maxValue = -1;

		for (int i = 0; i < N; i++) {
			try {
				values[i] = Long.parseLong(temp[i]);
				if (values[i] < 0) return fatalError("Node values cannot be negative");
				if (seen.contains(values[i])) return fatalError("Node value " + values[i] + " appears multiple times");

				seen.add(values[i]);
				maxValue = Math.max(maxValue, values[i]);
			} catch (Exception e) {
				if (debug) System.out.println(e.toString());
				return fatalError("Cannot parse your output");
			}
		}

		//check that the solution is valid
		Set<Long> diffs = new HashSet<Long>();

		for (int i = 0; i < N; i++)
			for (int k = i + 1; k < N; k++)
				if (Graph[i][k]) {
					long diff = Math.abs(values[i] - values[k]);
					if (diffs.contains(diff)) return fatalError("Difference " + diff + " appears multiple times");

					diffs.add(diff);
				}

		return maxValue;
	}

	public static void main(String[] args) {
		new MarathonController().run(args);
	}
}
