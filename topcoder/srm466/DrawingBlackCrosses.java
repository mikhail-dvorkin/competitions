package topcoder.srm466;
import java.util.*;

public class DrawingBlackCrosses {
	final int modulo = 1000000007;

	public int count(String[] field) {
		int hei = field.length;
		int wid = field[0].length();
		int ans = 0;
		TreeSet<Integer> bis = new TreeSet<Integer>();
		TreeSet<Integer> bjs = new TreeSet<Integer>();
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (field[i].charAt(j) == 'B') {
					bis.add(i);
					bjs.add(j);
				}
			}
		}
		ArrayList<Integer> ilist = new ArrayList<Integer>(bis);
		ArrayList<Integer> jlist = new ArrayList<Integer>(bjs);
		int bi = bis.size();
		int bj = bjs.size();
		boolean[][] fld = new boolean[bi][bj];
		for (int i = 0; i < bi; i++) {
			for (int j = 0; j < bj; j++) {
				fld[i][j] = field[ilist.get(i)].charAt(jlist.get(j)) == 'B';
			}
		}
		int ci = hei - bi;
		int cj = wid - bj;
		int MASK = 1 << (bi + bj);
		int[][][] a = new int[MASK][ci + 1][cj + 1];
		a[0][0][0] = 1;
		for (int mask = 0; mask < MASK; mask++) {
			for (int ii = 0; ii <= ci; ii++) {
				for (int jj = 0; jj <= cj; jj++) {
					if (a[mask][ii][jj] == 0) {
						continue;
					}
					int aa = a[mask][ii][jj];
					int aai = (int) (((long) aa) * (ci - ii) % modulo);
					int aaj = (int) (((long) aa) * (cj - jj) % modulo);
					int aaij = (int) (((long) aa) * (ci - ii) * (cj - jj) % modulo);
					boolean whites = false;
					for (int i = 0; i <= bi; i++) {
						for (int j = 0; j <= bj; j++) {
							int newmask = mask;
							int newii = ii;
							int newjj = jj;
							if (i < bi) {
								if ((mask & (1 << (i))) != 0) {
									continue;
								}
								newmask |= (1 << (i));
							} else {
								if (ii == ci) {
									continue;
								}
								newii++;
							}
							if (j < bj) {
								if ((mask & (1 << (bi + j))) != 0) {
									continue;
								}
								newmask |= (1 << (bi + j));
							} else {
								if (jj == cj) {
									continue;
								}
								newjj++;
							}
							if (i < bi && j < bj && fld[i][j]) {
								continue;
							}
							whites = true;
							int an = a[newmask][newii][newjj];
							if (i < bi) {
								if (j < bj) {
									an += aa;
								} else {
									an += aaj;
								}
							} else {
								if (j < bj) {
									an += aai;
								} else {
									an += aaij;
								}
							}
							if (an >= modulo) an -= modulo;
							a[newmask][newii][newjj] = an;
						}
					}
					if (!whites) {
						ans = (ans + aa) % modulo;
					}
				}
			}
		}
		return ans;
	}
}
