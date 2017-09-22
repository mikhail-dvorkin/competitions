package topcoder;
import java.util.*;

public class SortMaterials {
	public int totalVolume(String[] data, String[] requirements) {
		int ans = 0;
		sloop:
		for (String s : data) {
			Scanner in = new Scanner(s);
			int edge = in.nextInt();
			int qual = in.nextInt();
			String col = in.next().trim();
			in.close();
			for (String r : requirements) {
				if (r.startsWith("EDGE=")) {
					r = r.substring(5);
					int e = Integer.parseInt(r);
					if (edge != e)
						continue sloop;
					continue;
				}
				if (r.startsWith("QUALITY=")) {
					r = r.substring(8);
					int q = Integer.parseInt(r);
					if (qual < q)
						continue sloop;
					continue;
				}
				r = r.substring(6);
				if (!col.equals(r))
					continue sloop;
			}
			ans += edge * edge * edge;
		}
		return ans;
	}
}
