package topcoder.tco2008.round1;
import java.util.ArrayList;
import java.util.Collections;

public class DiscountCombination {
	static int take = 0;
	static int[] t = null;

	@SuppressWarnings("unchecked")
	public double minimumPrice(String[] discounts, int price) {
		double best = price;
		ArrayList<Integer>[] al = new ArrayList[4];
		al[1] = new ArrayList<Integer>();
		al[2] = new ArrayList<Integer>();
		al[3] = new ArrayList<Integer>();
		for (String s : discounts) {
			String[] ss = s.split(" ");
			int cost = Integer.parseInt(ss[0]);
			int percent = Integer.parseInt(ss[1]);
			al[percent].add(cost);
		}
		Collections.sort(al[1]);
		Collections.sort(al[2]);
		Collections.sort(al[3]);
		System.out.println(al[1].size() + " " + al[2].size() + " " + al[3].size());
		for (int n1 = 0; n1 <= al[1].size(); n1++) {
			for (int n2 = 0; n2 <= al[2].size(); n2++) {
				for (int n3 = 0; n3 <= al[3].size(); n3++) {
					double pr = 0;
					double pay = price;
					for (int i = 0; i < n1; i++) {
						pr += al[1].get(i);
						pay *= 0.99;
					}
					for (int i = 0; i < n2; i++) {
						pr += al[2].get(i);
						pay *= 0.98;
					}
					for (int i = 0; i < n3; i++) {
						pr += al[3].get(i);
						pay *= 0.97;
					}
					if (pr + pay < best) {
						best = pr + pay;
						take = n1 + n2 + n3;
						t = new int[]{n1, n2, n3};
					}
				}
			}
		}
		return best;
	}

	public static void main(String[] args) {
		String[] discounts = new String[]{"313799 3", "2025809 1", "6716544 1", "1617954 2", "4256737 1", "4747940 1", "8408544 1", "2932965 3", "3679064 3", "3279255 3", "8441709 3", "3066413 1", "3294964 2", "2466536 3", "2790294 2", "1491849 3", "2872942 3", "9764598 2", "8879360 3", "202968 1", "1435042 2", "216758 2", "96489 3", "6506576 3", "7081069 2", "2060300 3", "5928512 3", "6440555 3", "2437804 3", "2956950 1", "7723217 2", "8816838 2", "5733226 3", "6848337 2", "76075 3", "9889372 3", "7495726 1", "1436470 3", "7841955 2", "8647091 3", "2878332 1", "6289201 1", "8771846 1", "2731883 2", "9607922 1", "1570695 1", "1774573 2", "4981558 1", "1559447 2", "7396899 3"};
		double ans = new DiscountCombination().minimumPrice(discounts, 1000000000);
		System.out.println(ans);
		System.out.println(take);
		System.out.println(t[0] + " " + t[1] + " " + t[2]);
	}
}
