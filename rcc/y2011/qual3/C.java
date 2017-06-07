package rcc.y2011.qual3;
import java.util.*;

public class C {
	static Scanner in;
	
	int readTime() {
		String[] s = in.next().split(":");
		int h = Integer.parseInt(s[0]);
		int m = Integer.parseInt(s[1]);
		return h * 60 + m;
	}
	
	void printTime(int time) {
		int m = time % 60;
		int h = time / 60;
		if (h < 10) {
			System.out.print("0");
		}
		System.out.print(h);
		System.out.print(":");
		if (m < 10) {
			System.out.print("0");
		}
		System.out.print(m);
	}

	class Window {
		int begin, end;
		int process;
		
		public Window() {
			begin = readTime();
			end = readTime();
			process = in.nextInt();
		}
	}
	
	public void run() {
		int t = readTime();
		int n = in.nextInt();
		Window[] windows = new Window[n];
		for (int i = 0; i < n; i++) {
			windows[i] = new Window();
		}
		for (Window w : windows) {
			if (t < w.begin) {
				t = w.begin;
			}
			t += w.process;
			if (t > w.end) {
				System.out.println("No");
				return;
			}
		}
		System.out.println("Yes");
		printTime(t);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}
