package topcoder.srm783;

public class ABBAReplace_stress {
	private static int dumb(String s) {
		boolean[] a = new boolean[s.length()];
		for (int i = 0; i < a.length; i++) {
			a[i] = s.charAt(i) == 'A';
		}
		for (int iter = 0;; iter++) {
			boolean done = true;
			for (int i = 0; i + 1 < a.length; i++) {
				if (a[i] && !a[i + 1]) {
					a[i] = false;
					a[++i] = true;
					done = false;
				}
			}
			if (done) return iter;
		}
	}

	public static void main(String[] args) {
		for (int i = 1; i < 1e6; i++) {
			String s = Integer.toBinaryString(i).substring(1).replace('1', 'A').replace('0', 'B');
			int ans = new ABBAReplace().countSteps(s, s.length(), 0, 0);
			int dumb = dumb(s);
			System.out.println(s + " " + ans + " -> " + dumb);
			if (ans != dumb) throw new RuntimeException();
		}
	}
}
