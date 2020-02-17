package dgcj.y2016.round1;

@SuppressWarnings("static-access")
public class winning_move extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetNumPlayers() {
		return TESTED.GetNumPlayers();
	}

	public static long GetSubmission(long playernum) {
		return TESTED.GetSubmission(playernum);
	}

	static class Test1 {
		public static long GetNumPlayers() {
			return 6L;
		}

		public static long GetSubmission(long playernum) {
			switch ((int)playernum) {
			case 0: return 4L;
			case 1: return 8L;
			case 2: return 15L;
			case 3: return 16L;
			case 4: return 23L;
			case 5: return 42L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetNumPlayers() {
			return 8L;
		}

		public static long GetSubmission(long playernum) {
			switch ((int)playernum) {
			case 0: return 4L;
			case 1: return 2L;
			case 2: return 1L;
			case 3: return 3L;
			case 4: return 4L;
			case 5: return 1L;
			case 6: return 2L;
			case 7: return 2L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test3 {
		public static long GetNumPlayers() {
			return 3L;
		}

		public static long GetSubmission(long playernum) {
			switch ((int)playernum) {
			case 0: return 1L;
			case 1: return 1L;
			case 2: return 1L;
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
