package dgcj.y2018.round1;

import dgcj.message;

@SuppressWarnings("static-access")
public class kenneth extends dgcj.DgcjProblem {
	static Test1 TESTED;
	
	private static int from(long node_index) {
		int length = TESTED.data.length();
		int nodes = single() ? 1 : message.NumberOfNodes();
		return (int) (1L * length * node_index / nodes);
	}

	public static long GetPieceLength(long node_index) {
		return from(node_index + 1) - from(node_index);
	}

	public static char GetSignalCharacter(long position) {
		int id = single() ? 0 : message.MyNodeId();
		int from = from(id);
		int to = from(id + 1);
		if (position < from || position >= to) {
			throw new IllegalArgumentException("Invalid argument");
		}
		return TESTED.data.charAt((int) position);
	}

	static class Test1 {
		static String data = "ABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCA";
	}

	static class Test2 {
		static String data = "ABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAAABAA";
	}

	static class Test3 {
		static String data = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	}
}
