package dgcj.y2016.round2;
@SuppressWarnings("static-access")
public class lisp_plus_plus extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetLength() {
		return TESTED.GetLength();
	}

	public static char GetCharacter(long index) {
		return TESTED.GetCharacter(index);
	}

	static class Test1 {
		public static long GetLength() {
			return 8L;
		}

		public static char GetCharacter(long index) {
			switch ((int)index) {
			case 0: return '(';
			case 1: return ')';
			case 2: return '(';
			case 3: return '(';
			case 4: return ')';
			case 5: return '(';
			case 6: return ')';
			case 7: return ')';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetLength() {
			return 5L;
		}

		public static char GetCharacter(long index) {
			switch ((int)index) {
			case 0: return '(';
			case 1: return '(';
			case 2: return ')';
			case 3: return ')';
			case 4: return ')';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}

	}

	static class Test3 {
		public static long GetLength() {
			return 1L;
		}

		public static char GetCharacter(long index) {
			switch ((int)index) {
			case 0: return ')';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
