package topcoder.tccc2007.round1b;
import java.util.*;

public class Popularity {
	public String[] sort(String[] reply) {
		int n = reply.length;
		HashMap<String, Integer> cnt = new HashMap<String, Integer>();
		for (String s : reply) {
			String[] a = s.split(" ");
			int num = (cnt.containsKey(a[0])) ? cnt.get(a[0]) : 0;
			cnt.put(a[0], num + 1);
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - 1; j++) {
				String a = reply[j].split(" ")[0];
				String b = reply[j + 1].split(" ")[0];
				int aa = cnt.get(a);
				int bb = cnt.get(b);
				if (aa == bb)
					continue;
				if (aa < bb) {
					a = reply[j];
					reply[j] = reply[j + 1];
					reply[j + 1] = a;
				}
			}
		}
		return reply;
	}
}
