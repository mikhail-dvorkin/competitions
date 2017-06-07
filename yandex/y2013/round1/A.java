package yandex.y2013.round1;
import java.io.*;
import java.util.*;

public class A {
	private static BufferedReader in;

	int n;
	Task[] tasks;
	
	public void run() throws IOException {
		n = Integer.parseInt(in.readLine());
		tasks = new Task[n];
		ans = new long[n];
		long sum = 0;
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int p = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			sum += p;
			tasks[i] = new Task(a, p, i);
		}
		Arrays.sort(tasks);
		long low = 0;
		long high = sum;
		while (low + 1 < high) {
			long med = (low + high) / 2;
			if (solve(med, false)) {
				high = med;
			} else {
				low = med;
			}
		}
		solve(high, true);
		System.out.println(high);
		for (int i = 0; i < n; i++) {
			System.out.println(((ans[i] < 0) ? "A" : "B") + " " + Math.abs(ans[i]));
		}
	}
	
	long[] ans;

	boolean solve(long m, boolean last) {
		long need = 0;
		FenwickTree ft = new FenwickTree(2 * n + 1);
		long sum = 0;
		for (int i = 0; i < n; i++) {
			Task t = tasks[i];
			int d = (int) Math.min(m - 1 - t.a, 2 * n);
			if (d >= 0) {
				long ftsum = ft.sum(d);
				if (ftsum <= d) {
					int low = 0;
					int high = d + 1;
					while (low + 1 < high) {
						int s = (low + high) / 2;
						long p = ftsum - ft.sum(s - 1);
						if (p == d - s + 1) {
							high = s;
						} else {
							low = s;
						}
					}
					d = low;
					ft.add(d, 1);
					need += 1;
					ans[t.id] = -1;
					continue;
				}
			}
			need += t.p;
			sum += t.p;
			ans[t.id] = m - sum + 1;
		}
		if (last) {
			List<Task> list = new ArrayList<Task>();
			for (Task t : tasks) {
				if (ans[t.id] < 0) {
					list.add(t);
				}
			}
			Collections.sort(list, new Comparator<Task>() {
				@Override
				public int compare(Task o1, Task o2) {
					return o2.a - o1.a;
				}
			});
			int k = -1;
			for (Task t : list) {
				ans[t.id] = k;
				k--;
			}
		}
		return need <= m;
	}
	
	static class FenwickTree {
		long[] t;
		int n;

		public FenwickTree(int n) {
			this.n = n;
			t = new long[n];
		}

		public void add(int i, long value) {
			for (; i < n; i += (i + 1) & -(i + 1)) {
				t[i] += value;
			}
		}

		public long sum(int i) {
			long res = 0;
			for (; i >= 0; i -= (i + 1) & -(i + 1)) {
				res += t[i];
			}
			return res;
		}
	}
	
	class Task implements Comparable<Task> {
		int a, p, id;

		public Task(int a, int p, int id) {
			this.a = a;
			this.p = p;
			this.id = id;
		}

		@Override
		public int compareTo(Task o) {
			return o.p - p;
		}
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new A().run();
		in.close();
	}
}
