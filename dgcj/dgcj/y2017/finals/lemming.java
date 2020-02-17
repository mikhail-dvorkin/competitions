package dgcj.y2017.finals;

@SuppressWarnings("static-access")
public class lemming extends dgcj.DgcjProblem {
	static Test1 TESTED;
	
	@Override
	public void testLocally() {
		testLocally(1);
		testLocally(4);
		testLocally(9);
	}

	public static long GetRows() {
		return TESTED.GetRows();
	}

	public static long GetColumns() {
		return TESTED.GetColumns();
	}

	public static char GetDirection(long i, long j) {
		return TESTED.GetDirection(i, j);
	}

	static class Test1 {
		public static long GetRows() {
			return 3L;
		}

		public static long GetColumns() {
			return 4L;
		}

		public static char GetDirection(long i, long j) {
			if (i == 0L && j == 0L) return '<';
			if (i == 0L && j == 1L) return 'v';
			if (i == 0L && j == 2L) return '>';
			if (i == 0L && j == 3L) return '<';
			if (i == 1L && j == 0L) return '<';
			if (i == 1L && j == 1L) return '<';
			if (i == 1L && j == 2L) return 'v';
			if (i == 1L && j == 3L) return '>';
			if (i == 2L && j == 0L) return '>';
			if (i == 2L && j == 1L) return '>';
			if (i == 2L && j == 2L) return '<';
			if (i == 2L && j == 3L) return '>';
			throw new IllegalArgumentException("Invalid argument");
		}
	}

	static class Test2 {
		public static long GetRows() {
			return 1L;
		}

		public static long GetColumns() {
			return 15L;
		}

		public static char GetDirection(long i, long j) {
			if (i == 0L && j == 0L) return '>';
			if (i == 0L && j == 1L) return '<';
			if (i == 0L && j == 2L) return '>';
			if (i == 0L && j == 3L) return '<';
			if (i == 0L && j == 4L) return '>';
			if (i == 0L && j == 5L) return '<';
			if (i == 0L && j == 6L) return '>';
			if (i == 0L && j == 7L) return '<';
			if (i == 0L && j == 8L) return '>';
			if (i == 0L && j == 9L) return '<';
			if (i == 0L && j == 10L) return '>';
			if (i == 0L && j == 11L) return '<';
			if (i == 0L && j == 12L) return '>';
			if (i == 0L && j == 13L) return '<';
			if (i == 0L && j == 14L) return '>';
			throw new IllegalArgumentException("Invalid argument");
		}
	}

	static class Test3 {
		public static long GetRows() {
			return 4L;
		}

		public static long GetColumns() {
			return 3L;
		}

		public static char GetDirection(long i, long j) {
			if (i == 0L && j == 0L) return '<';
			if (i == 0L && j == 1L) return 'v';
			if (i == 0L && j == 2L) return '<';
			if (i == 1L && j == 0L) return '>';
			if (i == 1L && j == 1L) return '>';
			if (i == 1L && j == 2L) return '^';
			if (i == 2L && j == 0L) return 'v';
			if (i == 2L && j == 1L) return '>';
			if (i == 2L && j == 2L) return '>';
			if (i == 3L && j == 0L) return '^';
			if (i == 3L && j == 1L) return '^';
			if (i == 3L && j == 2L) return '^';
			throw new IllegalArgumentException("Invalid argument");
		}
	}
}
