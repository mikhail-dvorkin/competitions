package dgcj.y2017.round2;

import dgcj.message;

@SuppressWarnings("static-access")
public class broken_memory extends dgcj.DgcjProblem {
	static Test0 TESTED;

	@Override
	public void testLocally() {
		testLocally(10);
	}

	public static long GetLength() {
		return TESTED.GetLength();
	}
	public static long GetValue(long i) {
		return TESTED.GetValue(i);
	}

	static class Test0 {
		public static long GetLength() {
			return 1000L;
		}

		public static long GetValue(long i) {
			if (i < 0L || i >= GetLength())
				throw new IllegalArgumentException("Invalid argument");
			if ((message.MyNodeId()) == i)
				return i + 2L;
			return (i) + 1L;
		}
	}

	static class Test1 {
		public static long GetLength() {
			return 10L;
		}

		public static long GetValue(long i) {
			if (i < 0L || i >= GetLength())
				throw new IllegalArgumentException("Invalid argument");
			if ((message.MyNodeId()) == i)
				return (i ^ (i + message.MyNodeId() + 1)) + 1L;
			return (i) + 1L;
		}
	}

	static class Test2 {
		public static long GetLength() {
			return 30L;
		}

		public static long GetValue(long i) {
			if (i < 0L || i >= GetLength())
				throw new IllegalArgumentException("Invalid argument");
			if ((29L - message.MyNodeId()) == i)
				return (((i % 9L) + 1L) * ((i % 7L) + 1L) ^ (i + message.MyNodeId() + 1)) + 1L;
			return (((i % 9L) + 1L) * ((i % 7L) + 1L)) + 1L;
		}
	}

	static class Test3 {
		public static long GetLength() {
			return 16L;
		}

		public static long GetValue(long i) {
			if (i < 0L || i >= GetLength())
				throw new IllegalArgumentException("Invalid argument");
			if ((12L ^ message.MyNodeId()) == i)
				return ((i * i) ^ 21L ^ (i + message.MyNodeId() + 1)) + 1L;
			return ((i * i) ^ 21L) + 1L;
		}
	}
}
