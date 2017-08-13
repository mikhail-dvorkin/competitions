package dgcj.y2017.round2;

@SuppressWarnings("static-access")
public class flagpoles extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetNumFlagpoles() {
		return TESTED.GetNumFlagpoles();
	}

	public static long GetHeight(long i) {
		return TESTED.GetHeight(i);
	}

	static class Test0 {
		public static long GetNumFlagpoles() {
			return 4L;
		}

		public static long GetHeight(long i) {
			switch ((int)i) {
			case 0: return 5L;
			case 1: return 9L;
			case 2: return 7L;
			case 3: return 5L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
	
	static class Test1 {
		public static long GetNumFlagpoles() {
			return 7L;
		}

		public static long GetHeight(long i) {
			switch ((int)i) {
			case 0: return 5L;
			case 1: return 7L;
			case 2: return 5L;
			case 3: return 3L;
			case 4: return 1L;
			case 5: return 2L;
			case 6: return 3L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetNumFlagpoles() {
			return 4L;
		}

		public static long GetHeight(long i) {
			switch ((int)i) {
			case 0: return 2L;
			case 1: return 2L;
			case 2: return 2L;
			case 3: return 2L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetNumFlagpoles() {
			return 5L;
		}

		public static long GetHeight(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 3L;
			case 2: return 2L;
			case 3: return 4L;
			case 4: return 3L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
