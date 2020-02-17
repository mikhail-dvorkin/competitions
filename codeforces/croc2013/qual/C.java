package codeforces.croc2013.qual;
import java.io.*;
import java.util.*;

public class C {
	private static BufferedReader in;

	public void run() throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] ip = new int[n];
		for (int i = 0; i < n; i++) {
			String s = in.readLine() + ".";
			int p = 0;
			for (int j = 0; j < 4; j++) {
				int q = s.indexOf('.', p + 1);
				ip[i] = 256 * ip[i] + Integer.parseInt(s.substring(p, q));
				p = q + 1;
			}
		}
		Arrays.sort(ip);
		for (int m = 31; m >= 1; m--) {
			int prev = -1;
			int diff = 0;
			for (int i : ip) {
				int j = i >>> m;
				if (j != prev) {
					diff++;
				}
				prev = j;
			}
			if (diff == k) {
				m = -(1 << m);
				for (int j = 3; j >= 0; j--) {
					System.out.print((m >> (8 * j)) & 255);
					if (j > 0) {
						System.out.print(".");
					}
				}
				System.out.println();
				return;
			}
		}
		System.out.println(-1);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new C().run();
		in.close();
	}
}
