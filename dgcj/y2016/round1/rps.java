package dgcj.y2016.round1;

@SuppressWarnings("static-access")
public class rps extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetN() {
		return TESTED.GetN();
	}

	public static char GetFavoriteMove(long id) {
		return TESTED.GetFavoriteMove(id);
	}

	static class Test1 {
		public static long GetN() {
			return 3L;
		}

		public static char GetFavoriteMove(long id) {
			switch ((int)id) {
			case 0: return 'R';
			case 1: return 'P';
			case 2: return 'S';
			case 3: return 'R';
			case 4: return 'P';
			case 5: return 'S';
			case 6: return 'R';
			case 7: return 'P';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}

	static class Test2 {
		public static long GetN() {
			return 2L;
		}

		public static char GetFavoriteMove(long id) {
			switch ((int)id) {
			case 0: return 'R';
			case 1: return 'R';
			case 2: return 'R';
			case 3: return 'R';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}	
	}

	static class Test3 {
		public static long GetN() {
			return 2L;
		}

		public static char GetFavoriteMove(long id) {
			switch ((int)id) {
			case 0: return 'S';
			case 1: return 'R';
			case 2: return 'P';
			case 3: return 'P';
			default: throw new IllegalArgumentException("Invalid argument");
			}
		}
	}
}
