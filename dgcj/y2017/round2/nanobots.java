package dgcj.y2017.round2;

@SuppressWarnings("static-access")
public class nanobots extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetRange() {
		return TESTED.GetRange();
	}

	public static long GetNumNanobots() {
		return TESTED.GetNumNanobots();
	}

	public static char Experiment(long size, long speed) {
		return TESTED.Experiment(size, speed);
	}

	static class Test1 {
		public static long GetRange() {
			return 10;
		}

		public static long GetNumNanobots() {
			return 4;
		}

		static long sizes[] = {8,6,2,5};
		static long speeds[] = {2,4,10,5};

		public static char Experiment(long size, long speed) {
			if (size < 1 || size > GetRange() || speed < 1 || speed > GetRange())
				throw new IllegalArgumentException("Invalid argument");
			for (int i = 0; i < 4; ++i)
				if (sizes[i] > size && speeds[i] > speed) return 'T';
			return 'E';
		}
	}

	static class Test2 {
		public static long GetRange() {
			return 1000000;
		}

		public static long GetNumNanobots() {
			return 2;
		}

		static long sizes[] = {999999,500000};
		static long speeds[] = {2,999999};

		public static char Experiment(long size, long speed) {
			if (size < 1 || size > GetRange() || speed < 1 || speed > GetRange())
				throw new IllegalArgumentException("Invalid argument");
			for (int i = 0; i < 2; ++i)
				if (sizes[i] > size && speeds[i] > speed) return 'T';
			return 'E';
		}
	}

	static class Test3 {
		public static long GetRange() {
			return 2;
		}

		public static long GetNumNanobots() {
			return 4;
		}

		static long sizes[] = {2,1,1,1};
		static long speeds[] = {1,2,1,1};

		public static char Experiment(long size, long speed) {
			if (size < 1 || size > GetRange() || speed < 1 || speed > GetRange())
				throw new IllegalArgumentException("Invalid argument");
			for (int i = 0; i < 4; ++i)
				if (sizes[i] > size && speeds[i] > speed) return 'T';
			return 'E';
		}
	}
}
