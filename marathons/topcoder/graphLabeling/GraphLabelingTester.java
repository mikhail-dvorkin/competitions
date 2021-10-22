package marathons.topcoder.graphLabeling;

import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.Callable;

import marathons.utils.Evaluator;
import marathons.utils.Pictures;
import marathons.utils.dot.*;
import marathons.utils.topcoderMy.*;

public class GraphLabelingTester extends MarathonTester implements Callable<Void> {
	//Ranges
	private static final int minN = GraphLabeling.MIN_N, maxN = GraphLabeling.MAX_N;
	private static final double minC = GraphLabeling.MIN_C, maxC = GraphLabeling.MAX_C;

	//Inputs
	private int N;          //number of nodes

	//State Control
	private boolean[][] Graph;      //graph's adjacency matrix
	private int Edges;              //number of edges


	protected void generate() {
		N = randomInt(minN, maxN);
		//connectivity
		double c = randomDouble(minC, maxC);

		//Special cases for seeds 1 and 2
		if (seed == 1) {
			N = minN;
		} else if (seed == 2) {
			N = maxN;
		}
		if (parameters.isDefined(Parameters.myGen)) {
			c = minC + (seed - 3) % 95 / 95.0 * (maxC - minC);
		}

		//User defined parameters
		if (parameters.isDefined("N")) {
			N = randomInt(parameters.getIntRange("N"), minN, maxN);
		}
		if (parameters.isDefined("C")) {
			c = randomDouble(parameters.getDoubleRange("C"), minC, maxC);
		}

		//generate the graph
		do {
			Graph = new boolean[N][N];
			Edges = 0;

			for (int i = 0; i < N; i++)
				for (int k = i + 1; k < N; k++)
					if (randomDouble(0, 1) < c) {
						Graph[i][k] = true;
						Graph[k][i] = true;      //make symmetric
						Edges++;
					}

		} while (!isConnected());


		if (debug) {
			System.out.println("Nodes = " + N);
			System.out.println("Edges = " + Edges);
			System.out.println("Connectivity = " + c);
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
		List<Integer> Q = new ArrayList<>();
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
		String[] temp;
		if (parameters.isDefined(Parameters.myExec)) {
			GraphLabeling._localTimeCoefficient = Evaluator.localTimeCoefficient();
			startTime();
			GraphLabeling program = new GraphLabeling();
			int[] ans = program.solve(Graph);
			stopTime();
			myScore = program._myScore;
			temp = new String[ans.length];
			for (int i = 0; i < ans.length; i++) {
				temp[i] = "" + ans[i];
			}
			myTroubles = program._troubles;
			myLabels = program._labels;
		} else {
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
			temp = line.trim().split(" ");
		}

		if (debug) {
			System.out.println("Your solution:");
			for (int i = 0; i < N; i++) System.out.println("node " + i + " value " + temp[i]);
			System.out.println();
		}

		if (temp.length != N)
			return fatalError("Your output does not contain " + N + " elements");


		Set<Long> seen = new HashSet<>();
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
				if (debug) e.printStackTrace();
				return fatalError("Cannot parse your output");
			}
		}

		//check that the solution is valid
		Set<Long> diffs = new HashSet<>();

		for (int i = 0; i < N; i++)
			for (int k = i + 1; k < N; k++)
				if (Graph[i][k]) {
					long diff = Math.abs(values[i] - values[k]);
					if (diffs.contains(diff)) return fatalError("Difference " + diff + " appears multiple times");

					diffs.add(diff);
				}

		visualizeDot(values);
		return maxValue;
	}

	private void visualizeDot(long[] values) {
		if (!parameters.isDefined(Parameters.myVis)) return;
		DotGraphImpl graph = new DotGraphImpl();
		for (int i = 0; i < N; i++) {
			graph.vertices().add(new DotVertexImpl("v" + i).setLabel(values[i] + "@" + i));
			for (int j = 0; j < i; j++) {
				if (Graph[i][j]) {
					graph.edges().add(new DotEdgeImpl("v" + i, "v" + j).undirected().setLabel("" + Math.abs(values[i] - values[j])));
				}
			}
		}
		new DotGraphPrinter("pic" + seed + "~.dot", "test" + seed).printAndClose(graph);
		PrintWriter sh = DotGraphPrinter.printWriter("pics~.sh");
		String format = "png";
		sh.println("sfdp -x -Goverlap=scale -O -T" + format + " pic*~.dot");
		Pictures.write("pic" + seed + "~.dot." + format);
		Pictures.br();
		sh.close();
	}

	public static void mainRenamed(String[] args) {
		new MarathonController().run(args);
	}

	@Override
	public Void call() throws Exception {
		mainRenamed(GraphLabeling.EVALUATOR_PARAMETERS.split(" "));
		return null;
	}
}
