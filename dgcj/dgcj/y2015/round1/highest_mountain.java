package dgcj.y2015.round1;

@SuppressWarnings("static-access")
public class highest_mountain extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long NumberOfPeaks() {
		return TESTED.NumberOfPeaks();
	}

	public static long GetHeight(long i) {
		return TESTED.GetHeight(i);
	}

	static class Test1 {
		public static int NumberOfPeaks() {
			return 4;
		}

		public static int GetHeight(long i) {
			switch ((int)i) {
			case 0: return 3;
			case 1: return 1;
			case 2: return 2;
			case 3: return 4;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static int NumberOfPeaks() {
			return 3;
		}

		public static int GetHeight(long i) {
			switch ((int)i) {
			case 0: return 2;
			case 1: return 2;
			case 2: return 2;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static int NumberOfPeaks() {
			return 5;
		}

		public static int GetHeight(long i) {
			switch ((int)i) {
			case 0: return 1;
			case 1: return 3;
			case 2: return 4;
			case 3: return 2;
			case 4: return 1;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
