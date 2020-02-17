package dgcj.y2015.practice;

@SuppressWarnings("static-access")
public class majority extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetN() {
		return TESTED.GetN();
	}

	public static long GetVote(long i) {
		return TESTED.GetVote(i);
	}

	static class Test1 {
		public static long GetN() {
			return 5L;
		}

		public static long GetVote(long i) {
			switch ((int)i) {
			case 0: return 7L;
			case 1: return 2L;
			case 2: return 7L;
			case 3: return 7L;
			case 4: return 5L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetN() {
			return 2L;
		}

		public static long GetVote(long i) {
			switch ((int)i) {
			case 0: return 10L;
			case 1: return 20L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetN() {
			return 5L;
		}

		public static long GetVote(long i) {
			switch ((int)i) {
			case 0: return 2L;
			case 1: return 2L;
			case 2: return 3L;
			case 3: return 4L;
			case 4: return 5L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
