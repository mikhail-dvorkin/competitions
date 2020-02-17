package topcoder.srm388;
import java.util.*;

public class InformFriends {
	public int maximumGroups(String[] friends) {
		int n = friends.length;
		int max = (1 << n);
		char[][] a = new char[n][];
		int[] c = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = friends[i].toCharArray();
			a[i][i] = 'Y';
			int mask = 0;
			for (int j = 0; j < n; j++) {
				if (a[i][j] == 'Y')
					mask |= (1 << j);
			}
			c[i] = mask;
		}
		boolean[] great = new boolean[max];
		for (int mask = 0; mask < max; mask++) {
			int cover = 0;
			for (int i = 0; i < n; i++) {
				if ((mask & (1 << i)) != 0) {
					cover |= c[i];
				}
			}
			great[mask] = (cover == max - 1);
		}
		int[] good = new int[max];
		for (int m = 0; m < max; m++) {
			int gm = good[m];
			int rest = (max - 1) ^ m;
			for (int t = rest; t > 0; t = (t - 1) & rest) {
				if (great[t]) {
					good[t | m] = Math.max(good[t | m], gm + 1);
				}
			}
		}
		Arrays.sort(good);
		return good[max - 1];
	}

	public static void main(String[] args) {
		new InformFriends().maximumGroups(new String[]{
					"NYYYYYYYYYYYYYY",
					"YNYYYYYYYYYYYYY",
					"YYNYYYYYYYYYYYY",
					"YYYNYYYYYYYYYYY",
					"YYYYNYYYYYYYYYY",
					"YYYYYNYYYYYYYYY",
					"YYYYYYNYYYYYYYY",
					"YYYYYYYNYYYYYYY",
					"YYYYYYYYNYYYYYY",
					"YYYYYYYYYNYYYYY",
					"YYYYYYYYYYNYYYY",
					"YYYYYYYYYYYNYYY",
					"YYYYYYYYYYYYNYY",
					"YYYYYYYYYYYYYNY",
					"YYYYYYYYYYYYYYN"

		});
	}
}
