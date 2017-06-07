package roi.y2017;
import java.io.*;
import java.util.*;

public class A {
	private static BufferedReader in;

	public void run() throws IOException {
		int n = Integer.parseInt(in.readLine());
		boolean[] ans = new boolean[n];
		ArrayList<Integer> open = new ArrayList<Integer>();
		open.add(0);
		for (int i = 1; i < n; i++) {
			int from = open.get(open.size() - 1);
			System.out.println("? " + (from + 1) + " " + (i + 1));
			if ("No".equals(in.readLine())) {
				open.add(i);
				continue;
			}
			ans[i] = true;
			open.remove(open.size() - 1);
			if (open.isEmpty()) {
				i++;
				open.add(i);
			}
		}
		System.out.print("! ");
		for (int i = 0; i < n; i++) {
			System.out.print(ans[i] ? ")" : "(");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		new A().run();
		in.close();
	}
}
