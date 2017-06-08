package dgcj.y2016.round1;

@SuppressWarnings("static-access")
public class crates extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetNumStacks() {
		return TESTED.GetNumStacks();
	}

	public static long GetStackHeight(long i) {
		return TESTED.GetStackHeight(i);
	}

	static class Test1 {
		public static long GetNumStacks() {
			return 3L;
		}

		public static long GetStackHeight(long i) {
			switch ((int)i) {
			case 1: return 2L;
			case 2: return 2L;
			case 3: return 4L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetNumStacks() {
			return 4L;
		}

		public static long GetStackHeight(long i) {
			switch ((int)i) {
			case 1: return 1L;
			case 2: return 2L;
			case 3: return 5L;
			case 4: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetNumStacks() {
			return 3L;
		}

		public static long GetStackHeight(long i) {
			switch ((int)i) {
			case 1: return 2L;
			case 2: return 2L;
			case 3: return 2L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
