package dgcj.y2017.round1;
@SuppressWarnings("static-access")
public class pancakes extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetStackSize() {
		return TESTED.GetStackSize();
	}

	public static long GetNumDiners() {
		return TESTED.GetNumDiners();
	}

	public static long GetStackItem(long i) {
		return TESTED.GetStackItem(i);
	}

	static class Test1 {
		public static long GetStackSize() {
			return 4L;
		}

		public static long GetNumDiners() {
			return 4L;
		}

		public static long GetStackItem(long i) {
			switch ((int)i) {
			case 0: return 3L;
			case 1: return 1L;
			case 2: return 2L;
			case 3: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetStackSize() {
			return 6L;
		}

		public static long GetNumDiners() {
			return 4L;
		}

		public static long GetStackItem(long i) {
			switch ((int)i) {
			case 0: return 0L;
			case 1: return 0L;
			case 2: return 0L;
			case 3: return 2L;
			case 4: return 2L;
			case 5: return 3L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetStackSize() {
			return 7L;
		}

		public static long GetNumDiners() {
			return 5L;
		}

		public static long GetStackItem(long i) {
			switch ((int)i) {
			case 0: return 0L;
			case 1: return 1L;
			case 2: return 3L;
			case 3: return 2L;
			case 4: return 1L;
			case 5: return 3L;
			case 6: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
