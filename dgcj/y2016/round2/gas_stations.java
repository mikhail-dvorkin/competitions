package dgcj.y2016.round2;
@SuppressWarnings("static-access")
public class gas_stations extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetNumKms() {
		return TESTED.GetNumKms();
	}

	public static long GetTankSize() {
		return TESTED.GetTankSize();
	}

	public static long GetGasPrice(long i) {
		return TESTED.GetGasPrice(i);
	}

	static class Test1 {
		public static long GetNumKms() {
			return 3L;
		}

		public static long GetTankSize() {
			return 3L;
		}

		public static long GetGasPrice(long i) {
			switch ((int)i) {
			case 0: return 3L;
			case 1: return 2L;
			case 2: return 3L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetNumKms() {
			return 4L;
		}

		public static long GetTankSize() {
			return 2L;
		}

		public static long GetGasPrice(long i) {
			switch ((int)i) {
			case 0: return 1L;
			case 1: return 2L;
			case 2: return 4L;
			case 3: return 3L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetNumKms() {
			return 3L;
		}

		public static long GetTankSize() {
			return 1L;
		}

		public static long GetGasPrice(long i) {
			switch ((int)i) {
			case 0: return 5L;
			case 1: return 1L;
			case 2: return 5L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
