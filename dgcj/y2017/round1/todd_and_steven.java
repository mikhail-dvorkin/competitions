package dgcj.y2017.round1;
@SuppressWarnings("static-access")
public class todd_and_steven extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetToddLength() {
		return TESTED.GetToddLength();
	}
	public static long GetStevenLength() {
		return TESTED.GetStevenLength();
	}
	public static long GetToddValue(long i) {
		return TESTED.GetToddValue(i);
	}
	public static long GetStevenValue(long i) {
		return TESTED.GetStevenValue(i);
	}

	@SuppressWarnings("SwitchStatementWithTooFewBranches")
	static class Test1 {
		public static long GetToddLength() {
			return 1L;
		}

		public static long GetStevenLength() {
			return 2L;
		}

		public static long GetToddValue(long i) {
			switch ((int)i) {
			case 0: return 3L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetStevenValue(long i) {
			switch ((int)i) {
			case 0: return 2L;
			case 1: return 6L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	@SuppressWarnings("SwitchStatementWithTooFewBranches")
	static class Test2 {
		public static long GetToddLength() {
			return 1L;
		}

		public static long GetStevenLength() {
			return 1L;
		}

		public static long GetToddValue(long i) {
			switch ((int)i) {
			case 0: return 101L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetStevenValue(long i) {
			switch ((int)i) {
			case 0: return 100L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetToddLength() {
			return 2L;
		}

		public static long GetStevenLength() {
			return 4L;
		}

		public static long GetToddValue(long i) {
			switch ((int)i) {
			case 0: return 15L;
			case 1: return 23L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetStevenValue(long i) {
			switch ((int)i) {
			case 0: return 4L;
			case 1: return 8L;
			case 2: return 16L;
			case 3: return 42L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
