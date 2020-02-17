package dgcj.y2015.practice;

@SuppressWarnings("static-access")
public class shhhh extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetN() {
		return TESTED.GetN();
	}

	public static long GetLeftNeighbour(long i) {
		return TESTED.GetLeftNeighbour(i);
	}

	public static long GetRightNeighbour(long i) {
		return TESTED.GetRightNeighbour(i);
	}

	static class Test1 {
		public static long GetN() {
			return 2L;
		}

		public static long GetLeftNeighbour(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetRightNeighbour(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetN() {
			return 5L;
		}

		public static long GetLeftNeighbour(long i) {
			switch ((int)i) {
			case 0: return 4L;
			case 4: return 3L;
			case 3: return 2L;
			case 2: return 1L;
			case 1: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetRightNeighbour(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 2L;
			case 2: return 3L;
			case 3: return 4L;
			case 4: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetN() {
			return 6L;
		}

		public static long GetLeftNeighbour(long i) {
			switch ((int)i) {
			case 0: return 3L;
			case 3: return 1L;
			case 1: return 4L;
			case 4: return 5L;
			case 5: return 2L;
			case 2: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetRightNeighbour(long i) {
			switch ((int)i) {
			case 0: return 2L;
			case 2: return 5L;
			case 5: return 4L;
			case 4: return 1L;
			case 1: return 3L;
			case 3: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
