package dgcj.y2015.round1;

@SuppressWarnings("static-access")
public class mutexes extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long NumberOfOperations(long i) {
		return TESTED.NumberOfOperations(i);
	}

	public static long GetOperation(long i, long index) {
		return TESTED.GetOperation(i, index);
	}

	static class Test1 {
		public static long NumberOfOperations(long i) {
			switch ((int)i) {
			case 0: return 4;
			case 1: return 4;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetOperation(long i, long index) {
			if (i == 0L && index == 0L) return 1;
			if (i == 0L && index == 1L) return 2;
			if (i == 0L && index == 2L) return -2;
			if (i == 0L && index == 3L) return -1;
			if (i == 1L && index == 0L) return 1;
			if (i == 1L && index == 1L) return 2;
			if (i == 1L && index == 2L) return -1;
			if (i == 1L && index == 3L) return -2;
			throw new IllegalArgumentException("Invalid argument");
		}
	}

	static class Test2 {
		public static long NumberOfOperations(long i) {
			switch ((int)i) {
			case 0: return 5;
			case 1: return 2;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetOperation(long i, long index) {
			if (i == 0L && index == 0L) return 10;
			if (i == 0L && index == 1L) return -10;
			if (i == 0L && index == 2L) return 10;
			if (i == 0L && index == 3L) return 20;
			if (i == 0L && index == 4L) return 30;
			if (i == 1L && index == 0L) return 30;
			if (i == 1L && index == 1L) return 10;
			throw new IllegalArgumentException("Invalid argument");
		}
	}

	static class Test3 {
		public static long NumberOfOperations(long i) {
			switch ((int)i) {
			case 0: return 4;
			case 1: return 5;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static long GetOperation(long i, long index) {
			if (i == 0L && index == 0L) return 1;
			if (i == 0L && index == 1L) return 2;
			if (i == 0L && index == 2L) return 3;
			if (i == 0L && index == 3L) return 4;
			if (i == 1L && index == 0L) return 4;
			if (i == 1L && index == 1L) return 3;
			if (i == 1L && index == 2L) return 200;
			if (i == 1L && index == 3L) return 2;
			if (i == 1L && index == 4L) return 1;
			throw new IllegalArgumentException("Invalid argument");
		}
	}
}
