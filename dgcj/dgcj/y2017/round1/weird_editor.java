package dgcj.y2017.round1;
@SuppressWarnings("static-access")
public class weird_editor extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetNumberLength() {
		return TESTED.GetNumberLength();
	}

	public static long GetDigit(long i) {
		return TESTED.GetDigit(i);
	}

	static class Test1 {
		public static long GetNumberLength() {
			return 4L;
		}

		public static long GetDigit(long i) {
			switch ((int)i) {
			case 0: return 3L;
			case 1: return 0L;
			case 2: return 0L;
			case 3: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetNumberLength() {
			return 8L;
		}

		public static long GetDigit(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 0L;
			case 2: return 2L;
			case 3: return 3L;
			case 4: return 2L;
			case 5: return 1L;
			case 6: return 3L;
			case 7: return 0L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetNumberLength() {
			return 10L;
		}

		public static long GetDigit(long i) {
			switch ((int)i) {
			case 0: return 4L;
			case 1: return 4L;
			case 2: return 3L;
			case 3: return 3L;
			case 4: return 2L;
			case 5: return 1L;
			case 6: return 0L;
			case 7: return 0L;
			case 8: return 0L;
			case 9: return 9L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
