package topcoder;
import java.util.*;

public class ErdosNumber {
    public String[] calculateNumbers(String[] publications) {
        Map<String, Collection<String>> nei = new HashMap<String, Collection<String>>();
        Set<String> all = new TreeSet<String>();
        for (String s : publications) {
        	String[] ss = s.split(" ");
        	for (int i = 0; i < ss.length; i++) {
        		all.add(ss[i]);
        		for (int j = 0; j < ss.length; j++) {
        			if (i == j)
        				continue;
        			if (!nei.containsKey(ss[i])) {
        				nei.put(ss[i], new HashSet<String>());
        			}
        			if (!nei.containsKey(ss[j])) {
        				nei.put(ss[j], new HashSet<String>());
        			}
        			nei.get(ss[i]).add(ss[j]);
        			nei.get(ss[j]).add(ss[i]);
        		}
        	}
        }
        ArrayList<String> que = new ArrayList<String>();
        String e = "ERDOS";
        que.add(e);
        Map<String, Integer> ans = new TreeMap<String, Integer>();
        ans.put(e, 0);
        int lo = 0;
        while (lo < que.size()) {
        	String s = que.get(lo);
        	lo++;
        	if (nei.get(s) == null) continue;
        	for (String t : nei.get(s)) {
        		if (ans.containsKey(t))
        			continue;
        		ans.put(t, ans.get(s) + 1);
        		que.add(t);
        	}
        }
        String[] res = new String[all.size()];
        int i = 0;
        for (String s : all) {
        	if (ans.containsKey(s)) {
            	res[i++] = s + " " + ans.get(s);
        	} else {
        		res[i++] = s;
        	}
        }
        return res;
    }

}
