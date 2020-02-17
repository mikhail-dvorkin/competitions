package topcoder.srm353;
import java.util.*;

public class Glossary {
	class SC implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return o1.toLowerCase().compareTo(o2.toLowerCase());
		}
	}

	SC comp = new SC();

	public String[] buildGlossary(String[] items) {
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		for (int i = 0; i < items.length; i++) {
			while (items[i].length() < 17)
				items[i] += " ";
		}
		for (char c = 'A'; c <= 'Z'; c++) {
			ArrayList<String> ss = new ArrayList<String>();
			for (String s : items) {
				if (s.toUpperCase().charAt(0) == c) {
					ss.add("  " + s);
				}
			}
			if (!ss.isEmpty()) {
				Collections.sort(ss, comp);
				ArrayList<String> d;
				if (c <= 'M')
					d = a;
				else
					d = b;
				d.add(c + "                  "); // 18
				d.add("-------------------"); // 19
				d.addAll(ss);
			}
		}
		while (a.size() < b.size())
			a.add("                   "); // 19;
		while (b.size() < a.size())
			b.add("                   "); // 19;
		String[] ans = new String[a.size()];
		for (int i = 0; i < a.size(); i++) {
			ans[i] = a.get(i) + "  " + b.get(i);
		}
		return ans;
	}
}
