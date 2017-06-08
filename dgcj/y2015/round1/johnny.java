package dgcj.y2015.round1;
@SuppressWarnings("static-access")
public class johnny extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long NumberOfCards() {
		return TESTED.NumberOfCards();
	}
	
	public static boolean IsBetter(long i, long j) {
		return TESTED.IsBetter(i, j);
	}

	static class Test1 {
		public static long NumberOfCards() {
			return 4L;
		}

		public static boolean IsBetter(long i, long j) {
			if (i == 0L && j == 0L) return false;
			if (i == 0L && j == 1L) return true;
			if (i == 0L && j == 2L) return true;
			if (i == 0L && j == 3L) return true;
			if (i == 1L && j == 0L) return false;
			if (i == 1L && j == 1L) return false;
			if (i == 1L && j == 2L) return false;
			if (i == 1L && j == 3L) return true;
			if (i == 2L && j == 0L) return false;
			if (i == 2L && j == 1L) return true;
			if (i == 2L && j == 2L) return false;
			if (i == 2L && j == 3L) return false;
			if (i == 3L && j == 0L) return false;
			if (i == 3L && j == 1L) return false;
			if (i == 3L && j == 2L) return true;
			if (i == 3L && j == 3L) return false;
			throw new IllegalArgumentException("Invalid argument");
		}	}

	static class Test2 {
		public static long NumberOfCards() {
			return 3L;
		}

		public static boolean IsBetter(long i, long j) {
			if (i == 0L && j == 0L) return false;
			if (i == 0L && j == 1L) return false;
			if (i == 0L && j == 2L) return true;
			if (i == 1L && j == 0L) return true;
			if (i == 1L && j == 1L) return false;
			if (i == 1L && j == 2L) return false;
			if (i == 2L && j == 0L) return false;
			if (i == 2L && j == 1L) return true;
			if (i == 2L && j == 2L) return false;
			throw new IllegalArgumentException("Invalid argument");
		}
	}

	static class Test3 {
		public static long NumberOfCards() {
			return 7L;
		}

		public static boolean IsBetter(long i, long j) {
			if (i == 0L && j == 0L) return false;
			if (i == 0L && j == 1L) return false;
			if (i == 0L && j == 2L) return true;
			if (i == 0L && j == 3L) return false;
			if (i == 0L && j == 4L) return false;
			if (i == 0L && j == 5L) return false;
			if (i == 0L && j == 6L) return false;
			if (i == 1L && j == 0L) return true;
			if (i == 1L && j == 1L) return false;
			if (i == 1L && j == 2L) return false;
			if (i == 1L && j == 3L) return false;
			if (i == 1L && j == 4L) return false;
			if (i == 1L && j == 5L) return false;
			if (i == 1L && j == 6L) return false;
			if (i == 2L && j == 0L) return false;
			if (i == 2L && j == 1L) return true;
			if (i == 2L && j == 2L) return false;
			if (i == 2L && j == 3L) return false;
			if (i == 2L && j == 4L) return false;
			if (i == 2L && j == 5L) return false;
			if (i == 2L && j == 6L) return false;
			if (i == 3L && j == 0L) return true;
			if (i == 3L && j == 1L) return true;
			if (i == 3L && j == 2L) return true;
			if (i == 3L && j == 3L) return false;
			if (i == 3L && j == 4L) return false;
			if (i == 3L && j == 5L) return true;
			if (i == 3L && j == 6L) return false;
			if (i == 4L && j == 0L) return true;
			if (i == 4L && j == 1L) return true;
			if (i == 4L && j == 2L) return true;
			if (i == 4L && j == 3L) return true;
			if (i == 4L && j == 4L) return false;
			if (i == 4L && j == 5L) return false;
			if (i == 4L && j == 6L) return false;
			if (i == 5L && j == 0L) return true;
			if (i == 5L && j == 1L) return true;
			if (i == 5L && j == 2L) return true;
			if (i == 5L && j == 3L) return false;
			if (i == 5L && j == 4L) return true;
			if (i == 5L && j == 5L) return false;
			if (i == 5L && j == 6L) return false;
			if (i == 6L && j == 0L) return true;
			if (i == 6L && j == 1L) return true;
			if (i == 6L && j == 2L) return true;
			if (i == 6L && j == 3L) return true;
			if (i == 6L && j == 4L) return true;
			if (i == 6L && j == 5L) return true;
			if (i == 6L && j == 6L) return false;
			throw new IllegalArgumentException("Invalid argument");
		}
	}
}
