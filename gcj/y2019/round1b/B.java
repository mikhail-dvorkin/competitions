package gcj.y2019.round1b;
import java.util.*;

public class B {
	public void run() {
		// 	200	100	66	50	40	33
		//  42	21	14	10	8	7
		long v200 = ask(200), v42 = ask(42);

		int[] r = new int[6];
		r[5] = (int) ((v200 >> 33) & 127);
		r[4] = (int) ((v200 >> 40) & 127);
		r[3] = (int) ((v200 >> 50) & 127);
		v42 -= (r[3] << 10) + (r[4] << 8) + (r[5] << 7);
		r[2] = (int) ((v42 >> 14) & 127);
		r[1] = (int) ((v42 >> 21) & 127);
		r[0] = (int) ((v42 >> 42) & 127);

		for (int x : r) {
			System.out.print(x + " ");
		}
		System.out.println();
		System.out.flush();
		in.next();
	}

	long ask(int n) {
		System.out.println(n);
		System.out.flush();
		return in.nextLong();
	}

	static void research() {
		for (int n = 1; n <= 500; n++) {
			for (int i = 1; i <= 6; i++) {
				System.out.print("\t" + (n / i));
			}
			System.out.println();
		}
	}

	static Scanner in;

	public static void main(String[] args) {
		//research();
		in = new Scanner(System.in);
		int tests = in.nextInt();
		in.nextInt();
		for (int test = 0; test < tests; test++) {
			new B().run();
		}
		in.close();
	}
}
