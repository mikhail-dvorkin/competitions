package topcoder.tco2008.round3;
public class ZenoDivision {
	public String cycle(String aa, String bb) {
		long a = Long.parseLong(aa);
		long b = Long.parseLong(bb);
		for (int n = 1; n <= 62; n++) {
			long two = (1L << n) - 1;
			if (two % b != 0)
				continue;
			long star = (two / b) * a;
			String ans = "";
			for (int i = 0; i < n; i++) {
				if ((star & (1L << i)) != 0)
					ans = "*" + ans;
				else
					ans = "-" + ans;
			}
			return ans;
		}
		return "impossible";
	}

	public static void main(String[] args) {
		String a = new ZenoDivision().cycle("153", "2147483649");
		System.out.println(a);
	}
}
