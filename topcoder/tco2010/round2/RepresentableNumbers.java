package topcoder;
import java.util.*;

public class RepresentableNumbers {
	List<Integer> list = new ArrayList<Integer>();
	List<Integer> cur = new ArrayList<Integer>();
	
	public int getNext(int x) {
		cur.add(0);
		for (int i = 1; i <= 8; i++) {
			List<Integer> next = new ArrayList<Integer>();
			for (int c : cur) {
				for (int j = 1; j <= 9; j+= 2) {
					next.add(10 * c + j);
				}
			}
			list.addAll(next);
			cur = next;
		}
		list.add(111111111);
		int j = list.size() - 1;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < list.size(); i++) {
			int li = list.get(i);
			while (j > 0 && li + list.get(j - 1) >= x) {
				j--;
			}
			ans = Math.min(ans, li + list.get(j));
			if (i > j) {
				break;
			}
		}
		return ans;
	}
	
}
