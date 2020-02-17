package dgcj.y2018.round1;

@SuppressWarnings("static-access")
public class towels extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetNumberOfStacks() {
		return TESTED.GetNumberOfStacks();
	}

	public static long GetNumberOfTowelsInStack() {
		return TESTED.GetNumberOfTowelsInStack();
	}

	public static long GetNumberOfGymUsersBeforeYou() {
		return TESTED.GetNumberOfGymUsersBeforeYou();
	}

	public static long GetTowelCleanlinessRank(long stack, long position) {
		return TESTED.GetTowelCleanlinessRank(stack, position);
	}

	static class Test1 {
		public static long GetNumberOfStacks() {
			return 2L;
		}

		public static long GetNumberOfTowelsInStack() {
			return 3L;
		}

		public static long GetNumberOfGymUsersBeforeYou() {
			return 5L;
		}

		public static long GetTowelCleanlinessRank(long stack, long position) {
			if (stack == 0L && position == 0L) return 1L;
			if (stack == 0L && position == 1L) return 4L;
			if (stack == 0L && position == 2L) return 6L;
			if (stack == 1L && position == 0L) return 3L;
			if (stack == 1L && position == 1L) return 2L;
			if (stack == 1L && position == 2L) return 5L;
			throw new IllegalArgumentException("Invalid argument");
		}
	}

	static class Test2 {
		public static long GetNumberOfStacks() {
			return 3L;
		}

		public static long GetNumberOfTowelsInStack() {
			return 3L;
		}

		public static long GetNumberOfGymUsersBeforeYou() {
			return 4L;
		}

		public static long GetTowelCleanlinessRank(long stack, long position) {
			if (stack == 0L && position == 0L) return 6L;
			if (stack == 0L && position == 1L) return 1L;
			if (stack == 0L && position == 2L) return 4L;
			if (stack == 1L && position == 0L) return 3L;
			if (stack == 1L && position == 1L) return 9L;
			if (stack == 1L && position == 2L) return 7L;
			if (stack == 2L && position == 0L) return 8L;
			if (stack == 2L && position == 1L) return 5L;
			if (stack == 2L && position == 2L) return 2L;
			throw new IllegalArgumentException("Invalid argument");
		}
	}

	static class Test3 {
		public static long GetNumberOfStacks() {
			return 4L;
		}

		public static long GetNumberOfTowelsInStack() {
			return 1L;
		}

		public static long GetNumberOfGymUsersBeforeYou() {
			return 2L;
		}

		public static long GetTowelCleanlinessRank(long stack, long position) {
			if (stack == 0L && position == 0L) return 1L;
			if (stack == 1L && position == 0L) return 2L;
			if (stack == 2L && position == 0L) return 3L;
			if (stack == 3L && position == 0L) return 4L;
			throw new IllegalArgumentException("Invalid argument");
		}
	}
}
