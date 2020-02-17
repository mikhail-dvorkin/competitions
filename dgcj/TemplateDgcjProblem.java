package dgcj;

@SuppressWarnings({"AccessStaticViaInstance"})
public class TemplateDgcjProblem extends dgcj.DgcjProblem {
	static Test1 TESTED;

	public static long GetN() {
		return TESTED.GetN();
	}

	static class Test1 {
		public static long GetN() {
			return 1L;
		}
	}

	static class Test2 {
		public static long GetN() {
			return 1L;
		}
	}

	static class Test3 {
		public static long GetN() {
			return 1L;
		}
	}
}
