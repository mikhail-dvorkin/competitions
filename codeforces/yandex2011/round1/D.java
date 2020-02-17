package codeforces.yandex2011.round1;
import java.util.*;

public class D {
	private static Scanner in;

	int m = 1 << 17;
	int f = 5;
	long[][] a = new long[2 * m][f];
	int[] b = new int[2 * m];

	public void run() {
		int ops = in.nextInt();
		int[] op = new int[ops];
		List<Integer> allList = new ArrayList<>();
		for (int o = 0; o < ops; o++) {
			String s = in.next();
			switch (s.charAt(0)) {
			case 'a':
				op[o] = in.nextInt();
				allList.add(op[o]);
				break;
			case 'd':
				op[o] = -in.nextInt();
				break;
			}
		}
		Collections.sort(allList);
		Map<Integer, Integer> map = new HashMap<>();
		for (int v : allList) {
			if (!map.containsKey(v)) {
				int sz = map.size();
				map.put(v, sz);
			}
		}
		for (int o = 0; o < ops; o++) {
			if (op[o] == 0) {
				System.out.println(a[1][2]);
				continue;
			}
			int v = Math.abs(op[o]);
			int i = m + map.get(v);
			if (op[o] > 0) {
				b[i]++;
				a[i][0] = v;
			} else {
				b[i]--;
				a[i][0] = 0;
			}
			for (i /= 2; i >= 1; i /= 2) {
				int ff = f - (b[2 * i] % f);
				for (int j = 0; j < f; j++) {
					a[i][j] = a[2 * i][j] + a[2 * i + 1][(ff + j) % f];
				}
				b[i] = b[2 * i] + b[2 * i + 1];
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new D().run();
		in.close();
	}
}
