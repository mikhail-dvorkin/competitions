package dgcj.y2017.round2;
@SuppressWarnings("static-access")
public class number_bases extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetLength() {
		return TESTED.GetLength();
	}

	public static long GetDigitX(long i) {
		return TESTED.GetDigitX(i);
	}

	public static long GetDigitY(long i) {
		return TESTED.GetDigitY(i);
	}

	public static long GetDigitZ(long i) {
		return TESTED.GetDigitZ(i);
	}

	static class Test1 {
		public static long GetLength() {
			return 3L;
		}

		public static long GetDigitX(long i) {
			switch ((int)i) {
			case 0: return 3L;
			case 1: return 2L;
			case 2: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetDigitY(long i) {
			switch ((int)i) {
			case 0: return 6L;
			case 1: return 5L;
			case 2: return 4L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetDigitZ(long i) {
			switch ((int)i) {
			case 0: return 0L;
			case 1: return 8L;
			case 2: return 5L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetLength() {
			return 3L;
		}

		public static long GetDigitX(long i) {
			switch ((int)i) {
			case 0: return 1000L;
			case 1: return 20000L;
			case 2: return 300000L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetDigitY(long i) {
			switch ((int)i) {
			case 0: return 40000L;
			case 1: return 500000L;
			case 2: return 6L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetDigitZ(long i) {
			switch ((int)i) {
			case 0: return 41000L;
			case 1: return 520000L;
			case 2: return 300006L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

	}

	static class Test3 {
		public static long GetLength() {
			return 4L;
		}

		public static long GetDigitX(long i) {
			switch ((int)i) {
			case 0: return 5L;
			case 1: return 3L;
			case 2: return 0L;
			case 3: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetDigitY(long i) {
			switch ((int)i) {
			case 0: return 5L;
			case 1: return 3L;
			case 2: return 0L;
			case 3: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetDigitZ(long i) {
			switch ((int)i) {
			case 0: return 0L;
			case 1: return 6L;
			case 2: return 0L;
			case 3: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
