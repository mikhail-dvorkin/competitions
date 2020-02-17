package topcoder.srm350;
import java.util.*;

public class SumsOfPerfectPowers {
	public int howMany(int lowerBound, int upperBound) {
//		return upto(upperBound) - upto(lowerBound - 1);
		int MAX = 5000000;
		boolean[] isPow = new boolean[MAX + 1];
		boolean[] isSum = new boolean[MAX + 1];
		isPow[0] = true;
		isPow[1] = true;
		for (int i = 2; i * i <= MAX; i++) {
			long j = i * i;
			while (j <= MAX) {
				isPow[(int) j] = true;
				j *= i;
			}
		}
		ArrayList<Integer> ps = new ArrayList<Integer>();
		for (int i = 0; i <= MAX; i++)
			if (isPow[i])
				ps.add(i);
		System.out.println(ps.size());
		for (int i : ps)
			for (int j : ps) {
				if (i + j > MAX)
					break;
				isSum[i + j] = true;
			}
		int ans = 0;
		for (int i = lowerBound; i <= upperBound; i++)
			if (isSum[i])
				ans++;
		return ans;
	}

	public static void main(String[] args) {
		System.out.println(new SumsOfPerfectPowers().howMany(0, 100000));
	}
}
