package dgcj.y2016.finals;

@SuppressWarnings("static-access")
public class encoded_sum extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetLength() {
		return TESTED.GetLength();
	}

	public static char GetScrollOne(long i) {
		return TESTED.GetScrollOne(i);
	}

	public static char GetScrollTwo(long i) {
		return TESTED.GetScrollTwo(i);
	}

	static class Test1 {
		public static long GetLength() {
			return 1L;
		}

		public static char GetScrollOne(long i) {
			switch ((int)i) {
			case 0: return 'B';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static char GetScrollTwo(long i) {
			switch ((int)i) {
			case 0: return 'A';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetLength() {
			return 3L;
		}

		public static char GetScrollOne(long i) {
			switch ((int)i) {
			case 0: return 'B';
			case 1: return 'A';
			case 2: return 'A';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static char GetScrollTwo(long i) {
			switch ((int)i) {
			case 0: return 'A';
			case 1: return 'B';
			case 2: return 'A';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetLength() {
			return 10L;
		}

		public static char GetScrollOne(long i) {
			switch ((int)i) {
			case 0: return 'A';
			case 1: return 'C';
			case 2: return 'E';
			case 3: return 'G';
			case 4: return 'I';
			case 5: return 'A';
			case 6: return 'C';
			case 7: return 'E';
			case 8: return 'I';
			case 9: return 'G';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

		public static char GetScrollTwo(long i) {
			switch ((int)i) {
			case 0: return 'B';
			case 1: return 'D';
			case 2: return 'F';
			case 3: return 'H';
			case 4: return 'J';
			case 5: return 'B';
			case 6: return 'D';
			case 7: return 'H';
			case 8: return 'F';
			case 9: return 'J';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
