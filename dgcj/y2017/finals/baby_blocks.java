package dgcj.y2017.finals;

@SuppressWarnings("static-access")
public class baby_blocks extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetNumberOfBlocks() {
		return TESTED.GetNumberOfBlocks();
	}

	public static long GetBlockWeight(long i) {
		return TESTED.GetBlockWeight(i);
	}

	static class Test1 {
		public static long GetNumberOfBlocks() {
			return 3L;
		}

		public static long GetBlockWeight(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 1L;
			case 2: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

	}

	static class Test2 {
		public static long GetNumberOfBlocks() {
			return 6L;
		}

		public static long GetBlockWeight(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 2L;
			case 2: return 1L;
			case 3: return 4L;
			case 4: return 3L;
			case 5: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetNumberOfBlocks() {
			return 10L;
		}

		public static long GetBlockWeight(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 2L;
			case 2: return 4L;
			case 3: return 8L;
			case 4: return 16L;
			case 5: return 32L;
			case 6: return 16L;
			case 7: return 8L;
			case 8: return 4L;
			case 9: return 2L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
