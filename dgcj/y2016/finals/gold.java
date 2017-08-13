package dgcj.y2016.finals;

@SuppressWarnings("static-access")
public class gold extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long RoadLength() {
		return TESTED.RoadLength();
	}

	public static long NumberOfNuggets() {
		return TESTED.NumberOfNuggets();
	}

	public static char Search(long i) {
		return TESTED.Search(i);
	}

	static class Test1 {
		public static long RoadLength() {
			return 1;
		}

		public static long NumberOfNuggets() {
			return 1;
		}

		public static char Search(long i) {
			return "X".charAt((int) i);
		}
	}

	static class Test2 {
		public static long RoadLength() {
			return 7;
		}

		public static long NumberOfNuggets() {
			return 3;
		}

		public static char Search(long i) {
			return ">X<=>XX".charAt((int) i);
		}
	}

	static class Test3 {
		public static long RoadLength() {
			return 19;
		}

		public static long NumberOfNuggets() {
			return 14;
		}

		public static char Search(long i) {
			return "X=XXX=XXX=XXX=XXX=X".charAt((int) i);
		}
	}
}
