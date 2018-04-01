package gcj.y2018.practice;
import java.util.*;

public class A {
	public void run() {
		int low = in.nextInt();
		int high = in.nextInt() + 1;
		in.nextInt();
		for (;;) {
			int mid = (low + high) / 2;
			System.out.println(mid);
			System.out.flush();
			String response = in.next();
			switch (response) {
			case "CORRECT":
				return;
			case "TOO_SMALL":
				low = mid;
				break;
			case "TOO_BIG":
				high = mid;
				break;
			}
		}
	}
	
	static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			new A().run();
		}
		in.close();
	}
}
