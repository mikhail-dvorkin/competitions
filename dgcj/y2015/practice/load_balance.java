package dgcj.y2015.practice;

@SuppressWarnings("static-access")
public class load_balance extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static int GetN() {
		return TESTED.GetN();
	}

	public static long GetWeight(long i) {
		return TESTED.GetWeight(i);
	}

	static class Test1 {
		public static int GetN() {
			return 4;
		}

		public static long GetWeight(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 1L;
			case 2: return 1L;
			case 3: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static int GetN() {
			return 4;
		}

		public static long GetWeight(long i) {
			switch ((int)i) {
			case 0: return 4L;
			case 1: return 1L;
			case 2: return 6L;
			case 3: return 5L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static int GetN() {
			return 7;
		}

		public static long GetWeight(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 2L;
			case 2: return 3L;
			case 3: return 4L;
			case 4: return 5L;
			case 5: return 6L;
			case 6: return 21L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
