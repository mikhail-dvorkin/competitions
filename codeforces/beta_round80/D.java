package codeforces.beta_round80;
import java.io.*;
import java.util.*;

public class D {
	private static BufferedReader in;

	public void run() throws IOException {
		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] w = new int[n];
		for (int i = 0; i < n; i++) {
			w[i] = Integer.parseInt(st.nextToken());
		}
		int x = (int) Math.sqrt(n) + 2;
		int p = Integer.parseInt(in.readLine());
		int[] a = new int[p];
		int[] b = new int[p];
		@SuppressWarnings("unchecked")
		HashMap<Integer, Long>[] ans = new HashMap[x];
		for (int j = 1; j < x; j++) {
			ans[j] = new HashMap<Integer, Long>();
		}
		for (int i = 0; i < p; i++) {
			st = new StringTokenizer(in.readLine());
			a[i] = Integer.parseInt(st.nextToken()) - 1;
			b[i] = Integer.parseInt(st.nextToken());
			if (b[i] < x) {
				ans[b[i]].put(a[i], 0L);
			}
		}
		long[] sum = new long[n];
		for (int j = 1; j < x; j++) {
			HashMap<Integer, Long> an = ans[j];
			if (an.isEmpty()) {
				continue;
			}
			for (int i = n - 1; i >= 0; i--) {
				int ii = i + j;
				sum[i] = w[i] + ((ii < n) ? sum[ii] : 0);
				if (an.containsKey(i)) {
					an.put(i, sum[i]);
				}
			}
		}
		for (int i = 0; i < p; i++) {
			int aa = a[i];
			int bb = b[i];
			if (bb < x) {
				System.out.println(ans[bb].get(aa));
				continue;
			}
			long res = 0;
			for (int j = aa; j < n; j += bb) {
				res += w[j];
			}
			System.out.println(res);
		}
		
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new D().run();
		in.close();
	}
}
