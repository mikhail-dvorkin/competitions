package topcoder.srm560;
import java.util.*;

public class TomekPhone {
	public int minKeystrokes(int[] occurences, int[] keySizes) {
		int n = occurences.length;
		Arrays.sort(occurences);
		ArrayList<Integer> pos = new ArrayList<Integer>();
		for (int k : keySizes) {
			for (int i = 1; i <= k; i++) {
				pos.add(i);
			}
		}
		if (pos.size() < n) {
			return -1;
		}
		Collections.sort(pos);
		int answer = 0;
		for (int i = 0; i < n; i++) {
			answer += occurences[n - 1 - i] * pos.get(i);
		}
		return answer;
	}

}
