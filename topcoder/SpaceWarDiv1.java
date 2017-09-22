package topcoder;
import java.util.*;

public class SpaceWarDiv1 {
	public long minimalFatigue(int[] magicalGirlStrength, int[] enemyStrength, long[] enemyCount) {
		int n = magicalGirlStrength.length;
		int m = enemyStrength.length;
		Arrays.sort(magicalGirlStrength);
		Enemy[] enemies = new Enemy[m];
		for (int i = 0; i < m; i++) {
			enemies[i] = new Enemy(enemyStrength[i], enemyCount[i]);
		}
		Arrays.sort(enemies);
		if (enemies[0].strength > magicalGirlStrength[n - 1]) {
			return -1;
		}
		long inf = Long.MAX_VALUE / 2;
		long left = 0;
		long right = inf;
		while (left + 1 < right) {
			long fatigue = (left + right) / 2;
			long[] f = new long[n];
			Arrays.fill(f, fatigue);
			int i = n - 1;
			int j = 0;
			long count = enemies[0].count;
			int strength = enemies[0].strength;
			boolean win = false;
			while (j < m) {
				if (count == 0) {
					j++;
					if (j == m) {
						win = true;
						break;
					}
					count = enemies[j].count;
					strength = enemies[j].strength;
					continue;
				}
				while (i >= 0 && f[i] == 0) {
					i--;
				}
				if (i < 0) {
					break;
				}
				if (magicalGirlStrength[i] < strength) {
					break;
				}
				long c = Math.min(f[i], count);
				count -= c;
				f[i] -= c;
			}
			if (win) {
				right = fatigue;
			} else {
				left = fatigue;
			}
		}
		return right;
	}
	
	class Enemy implements Comparable<Enemy> {
		int strength;
		long count;
		
		public Enemy(int strength, long count) {
			this.strength = strength;
			this.count = count;
		}

		@Override
		public int compareTo(Enemy o) {
			return o.strength - strength;
		}
	}
}
