package topcoder.srm777;

import java.util.*;

public class PreviousOccurrence {
	public int findValue(int defaultValue, int query) {
		int[] last = new int[(int) 2e7];
		Arrays.fill(last, -1);
		last[0] = 0;
		if (query == 0) return 0;
		if (defaultValue == 1 && query > 1) return -1;
		int index = 1;
		int next = defaultValue;
		while (true) {
			int cur = next;
			int prev = last[cur];
			next = prev == -1 ? defaultValue : index - prev;
			if (cur == query) return index;
			last[cur] = index++;
		}
	}
}
