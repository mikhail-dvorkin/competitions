package dgcj.y2018.round1;

@SuppressWarnings("static-access")
public class stones extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetNumberOfStones() {
		return TESTED.GetNumberOfStones();
	}

	public static long GetJumpLength(long stone) {
		return TESTED.GetJumpLength(stone);
	}

	static class Test1 {
		public static long GetNumberOfStones() {
			return 1L;
		}

		public static long GetJumpLength(long stone) {
			switch ((int)stone) {
			case 0: return 1L;
			case 1: return 2L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetNumberOfStones() {
			return 5L;
		}

		public static long GetJumpLength(long stone) {
			switch ((int)stone) {
			case 0: return 3L;
			case 1: return 1L;
			case 2: return 10L;
			case 3: return 1L;
			case 4: return 1L;
			case 5: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetNumberOfStones() {
			return 10L;
		}

		public static long GetJumpLength(long stone) {
			switch ((int)stone) {
			case 0: return 3L;
			case 1: return 4L;
			case 2: return 2L;
			case 3: return 3L;
			case 4: return 4L;
			case 5: return 2L;
			case 6: return 3L;
			case 7: return 4L;
			case 8: return 2L;
			case 9: return 3L;
			case 10: return 4L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
