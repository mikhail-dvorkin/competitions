package dgcj.y2016.round2;
@SuppressWarnings("static-access")
public class asteroids extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetHeight() {
		return TESTED.GetHeight();
	}

	public static long GetWidth() {
		return TESTED.GetWidth();
	}

	public static char GetPosition(long i, long j) {
		return TESTED.GetPosition(i, j);
	}

	static class Test1 {
		public static long GetHeight() {
			return 4L;
		}

		public static long GetWidth() {
			return 6L;
		}

		public static char GetPosition(long i, long j) {
			if (i == 3L && j == 0L) return '8';
			if (i == 3L && j == 1L) return '#';
			if (i == 3L && j == 2L) return '#';
			if (i == 3L && j == 3L) return '1';
			if (i == 3L && j == 4L) return '2';
			if (i == 3L && j == 5L) return '3';
			if (i == 2L && j == 0L) return '#';
			if (i == 2L && j == 1L) return '9';
			if (i == 2L && j == 2L) return '9';
			if (i == 2L && j == 3L) return '9';
			if (i == 2L && j == 4L) return '#';
			if (i == 2L && j == 5L) return '1';
			if (i == 1L && j == 0L) return '2';
			if (i == 1L && j == 1L) return '1';
			if (i == 1L && j == 2L) return '#';
			if (i == 1L && j == 3L) return '#';
			if (i == 1L && j == 4L) return '1';
			if (i == 1L && j == 5L) return '1';
			if (i == 0L && j == 0L) return '5';
			if (i == 0L && j == 1L) return '2';
			if (i == 0L && j == 2L) return '#';
			if (i == 0L && j == 3L) return '1';
			if (i == 0L && j == 4L) return '1';
			if (i == 0L && j == 5L) return '#';
			throw new IllegalArgumentException("Invalid argument");
		}
	}

	static class Test2 {
		public static long GetHeight() {
			return 6L;
		}

		public static long GetWidth() {
			return 4L;
		}

		public static char GetPosition(long i, long j) {
			if (i == 5L && j == 0L) return '1';
			if (i == 5L && j == 1L) return '#';
			if (i == 5L && j == 2L) return '7';
			if (i == 5L && j == 3L) return '8';
			if (i == 4L && j == 0L) return '0';
			if (i == 4L && j == 1L) return '0';
			if (i == 4L && j == 2L) return '1';
			if (i == 4L && j == 3L) return '1';
			if (i == 3L && j == 0L) return '#';
			if (i == 3L && j == 1L) return '2';
			if (i == 3L && j == 2L) return '#';
			if (i == 3L && j == 3L) return '9';
			if (i == 2L && j == 0L) return '0';
			if (i == 2L && j == 1L) return '1';
			if (i == 2L && j == 2L) return '3';
			if (i == 2L && j == 3L) return '6';
			if (i == 1L && j == 0L) return '0';
			if (i == 1L && j == 1L) return '#';
			if (i == 1L && j == 2L) return '8';
			if (i == 1L && j == 3L) return '#';
			if (i == 0L && j == 0L) return '2';
			if (i == 0L && j == 1L) return '1';
			if (i == 0L && j == 2L) return '#';
			if (i == 0L && j == 3L) return '9';
			throw new IllegalArgumentException("Invalid argument");
		}

	}

	static class Test3 {
		public static long GetHeight() {
			return 3L;
		}

		public static long GetWidth() {
			return 3L;
		}

		public static char GetPosition(long i, long j) {
			if (i == 2L && j == 0L) return '0';
			if (i == 2L && j == 1L) return '#';
			if (i == 2L && j == 2L) return '#';
			if (i == 1L && j == 0L) return '0';
			if (i == 1L && j == 1L) return '0';
			if (i == 1L && j == 2L) return '0';
			if (i == 0L && j == 0L) return '#';
			if (i == 0L && j == 1L) return '#';
			if (i == 0L && j == 2L) return '0';
			throw new IllegalArgumentException("Invalid argument");
		}
	}
}
