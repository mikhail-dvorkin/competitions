package dgcj.y2017.finals;

import dgcj.message;

@SuppressWarnings("static-access")
public class median extends dgcj.DgcjProblem {
	static Test1 TESTED;
	static boolean TEST_LARGE = false;

	public static long GetN() {
		return TEST_LARGE ? -1 : TESTED._the_data_.length;
	}

	public static int GetData(long i) {
		if (i < 0L || i > 1000000000000000000L) {
			throw new IllegalArgumentException("Invalid argument");
		}
		int delta = TEST_LARGE ? TESTED._the_delta_[message.MyNodeId()] : 0;
		return TESTED._the_data_[(int)((i + delta) % GetN())];
	}
	
	static class Test1 {
		static int _the_data_[] = {5,9,4,8,11,7,10,6,1,2,3};
		static int _the_delta_[] = {0,1,2,3,4,5,6,7,8,9,10,0,1,2,3,4,5,6,7,8,9,10,0,1,2,3,4,5,6,7,8,9,10,0,1,2,3,4,5,6,7,8,9,10,0,1,2,3,4,5,6,7,8,9,10,0,1,2,3,4,5,6,7,8,9,10,0,1,2,3,4,5,6,7,8,9,10,0,1,2,3,4,5,6,7,8,9,10,0,1,2,3,4,5,6,7,8,9,10,0};
	}

	static class Test2 {
		static int _the_data_[] = {1000000000};
		static int _the_delta_[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	}

	static class Test3 {
		static int _the_data_[] = {2,2,1,2,1,2,1,2,2,1,1,1,1,1,1,2,2};
		static int _the_delta_[] = {6,8,13,15,14,6,11,13,12,14,2,11,10,12,0,2,8,10,15,0,16,8,13,15,14,16,4,13,12,14,2,4,10,12,0,2,1,10,15,0,16,1,6,15,14,16,4,6,12,14,2,4,3,12,0,2,1,3,8,0,16,1,6,8,14,16,4,6,5,14,2,4,3,5,10,2,1,3,8,10,16,1,6,8,7,16,4,6,5,7,12,4,3,5,10,12,1,3,8,10};
	}
}
