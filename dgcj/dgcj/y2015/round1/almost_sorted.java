package dgcj.y2015.round1;

@SuppressWarnings("static-access")
public class almost_sorted extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long NumberOfFiles() {
		return TESTED.NumberOfFiles();
	}

	public static long MaxDistance() {
		return TESTED.MaxDistance();
	}

	public static long Identifier(long i) {
		return TESTED.Identifier(i);
	}

	static class Test1 {
		public static long NumberOfFiles() {
			return 3L;
		}

		public static long MaxDistance() {
			return 0L;
		}

		public static long Identifier(long i) {
			switch ((int)i) {
			case 0: return 0L;
			case 1: return 0L;
			case 2: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}	
	}

	static class Test2 {
		public static long NumberOfFiles() {
			return 4L;
		}

		public static long MaxDistance() {
			return 2L;
		}

		public static long Identifier(long i) {
			switch ((int)i) {
			case 0: return 1000L;
			case 1: return 1500L;
			case 2: return 0L;
			case 3: return 500L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long NumberOfFiles() {
			return 5L;
		}

		public static long MaxDistance() {
			return 4L;
		}

		public static long Identifier(long i) {
			switch ((int)i) {
			case 0: return 1000000L;
			case 1: return 2000000L;
			case 2: return 4000000L;
			case 3: return 3000000L;
			case 4: return 5000000L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
