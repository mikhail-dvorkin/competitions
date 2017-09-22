package topcoder;
public class NextNumber {
	char[] c, d;
	
	boolean go(boolean ok, int x, int ones) {
		if (c.length - x < ones || ones < 0)
			return false;
		if (x == c.length)
			return true;
		if (ok) {
			c[x] = '0';
			if (go(true, x + 1, ones)) return true;
			c[x] = '1';
			return go(true, x + 1, ones - 1);
		}
		if (d[x] == '1') {
			c[x] = '1';
			return go(false, x + 1, ones - 1);
		}
		c[x] = '0';
		if (go(false, x + 1, ones))
			return true;
		c[x] = '1';
		return go(true, x + 1, ones - 1);
	}
	
	public int getNextNumber(int n) {
		String b = "0" + Integer.toBinaryString(n + 1);
		c = b.toCharArray();
		d = c.clone();
		int o = ones(n);
		go(false, 0, o);
		b = new String(c);
		return Integer.parseInt(b, 2);
	}
	
	private int ones(int n) {
		if (n == 0)
			return 0;
		return 1 + ones(n & (n - 1));
	}

	public static void main(String[] args) {
		System.out.println(new NextNumber().getNextNumber(4));
	}
}
