package topcoder.srm777;

import java.util.*;

public class PreviousOccurrence_research {
	static final int MAX_QUERY = 7000;
	static final int MAX_VALUE = 1000;
	int[] last = new int[(int) 2e7];

	public void findValue(int defaultValue) {
		Arrays.fill(last, -1);
		last[0] = 0;
		int index = 1;
		int next = defaultValue;
		int bad = MAX_QUERY;
		while (bad > 0) {
			int cur = next;
			int prev = last[cur];
			next = prev == -1 ? defaultValue : index - prev;
			if (cur <= MAX_QUERY && last[cur] == -1) bad--;
			last[cur] = index++;
			if (index == last.length) {
				System.out.println(defaultValue + " " + Arrays.toString(Arrays.copyOf(last, MAX_QUERY + 1)));
				return;
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i <= MAX_VALUE; i++) {
			new PreviousOccurrence_research().findValue(i);
		}
	}
}
