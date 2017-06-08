package dgcj.y2015.practice;

@SuppressWarnings("static-access")
public class sandwich extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetN() {
		return TESTED.GetN();
	}

	public static long GetTaste(long index) {
		return TESTED.GetTaste(index);
	}

	static class Test1 {
		public static long GetN() {
			return 7L;
		}

		public static long GetTaste(long index) {
			switch ((int)index) {
			case 0: return 10L;
			case 1: return -2L;
			case 2: return 5L;
			case 3: return -4L;
			case 4: return 3L;
			case 5: return -5L;
			case 6: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}	
	}

	static class Test2 {
		public static long GetN() {
			return 3L;
		}

		public static long GetTaste(long index) {
			switch ((int)index) {
			case 0: return -2L;
			case 1: return 1L;
			case 2: return -2L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetN() {
			return 4L;
		}

		public static long GetTaste(long index) {
			switch ((int)index) {
			case 0: return 1L;
			case 1: return 1L;
			case 2: return 2L;
			case 3: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}