package dgcj.y2017.round1;
@SuppressWarnings("static-access")
public class query_of_death extends dgcj.DgcjProblem {
	static Test1 TESTED;

	@Override
	public void testLocally() {
		testLocally(6, 11, 2);
	}

	public static long GetLength() {
		return TESTED.GetLength();
	}

	public static long GetValue(long i) {
		return TESTED.GetValue(i);
	}

	static class Test1 {
		static int isthenodebroken = 0;
		static int testvs[] = {1, 1, 0};

		public static long GetLength() {
			return 3L;
		}

		public static long GetValue(long i) {
			if (i < 0L || i >= GetLength())
				throw new IllegalArgumentException("Invalid argument");
			int val = testvs[(int)i]^((int)(Math.random() * 2 + 1) % (isthenodebroken + 1));
			if (i == 1)
				isthenodebroken = 1;
			return val;
		}
	}

	static class Test2 {
		static int isthenodebroken = 0;
		static int testvs[] = {1, 1, 1};

		public static long GetLength() {
			return 3L;
		}

		public static long GetValue(long i) {
			if (i < 0L || i >= GetLength())
				throw new IllegalArgumentException("Invalid argument");
			int val = testvs[(int)i]^((int)(Math.random() * 2 + 1) % (isthenodebroken + 1));
			if (i == 0)
				isthenodebroken = 1;
			return val;
		}
	}

	static class Test3 {
		static int isthenodebroken = 0;
		static int testvs[] = {1, 0, 1, 0, 1};

		public static long GetLength() {
			return 5L;
		}

		public static long GetValue(long i) {
			if (i < 0L || i >= GetLength())
				throw new IllegalArgumentException("Invalid argument");
			int val = testvs[(int)i]^((int)(Math.random() * 2 + 1) % (isthenodebroken + 1));
			if (i == 4)
				isthenodebroken = 1;
			return val;
		}
	}
}
