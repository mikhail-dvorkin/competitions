package codeforces.round219;
import java.util.*;

public class C {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int d = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		long ans = 0;
		int tPrev = 0;
		for (int j = 0; j < m; j++) {
			int x = in.nextInt() - 1;
			ans += in.nextInt();
			int tCur = in.nextInt();
			int t = tCur - tPrev;
			int delta = (int) Math.min(n, (long) t * d);
			QueueMin q = new QueueMin(n);
			for (int i = 0, i1 = 0, i2 = 0; i < n; i++) {
				while (i2 < n && i2 <= i + delta) {
					q.push(a[i2]);
					i2++;
				}
				if (i1 == i - delta - 1) {
					q.pop();
					i1++;
				}
				b[i] = q.min() + Math.abs(i - x);
			}
			int[] c = a; a = b; b = c;
			tPrev = tCur;
		}
		Arrays.sort(a);
		System.out.println(ans - a[0]);
	}

	static class QueueMin {
		final StackMin a;
		final StackMin b;

		public QueueMin(int n) {
			a = new StackMin(n);
			b = new StackMin(n);
		}

		public void push(int v) {
			a.push(v);
		}

		public int min() {
			return Math.min(a.min(), b.min());
		}

		public void pop() {
			if (b.size() == 0) {
				while (a.size() > 0) {
					b.push(a.pop());
				}
			}
			b.pop();
		}
	}

	static class StackMin {
		final int[] a;
		final int[] min;
		int size;

		public StackMin(int n) {
			a = new int[n];
			min = new int[n];
			size = 0;
		}

		public void push(int v) {
			a[size] = v;
			if (size == 0) {
				min[size] = v;
			} else {
				min[size] = Math.min(min[size - 1], v);
			}
			size++;
		}

		public int size() {
			return size;
		}

		public int pop() {
			size--;
			return a[size];
		}

		public int min() {
			if (size == 0) {
				return Integer.MAX_VALUE;
			}
			return min[size - 1];
		}

	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}
