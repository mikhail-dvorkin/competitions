package topcoder;
import java.util.*;

public class CropCircles {
	final double eps = 1e-8;
	
	class Pair implements Comparable<Pair> {
		double ang;
		int num;

		public Pair(double a, int k) {
			ang = a;
			num = k;
		}

		@Override
		public int compareTo(Pair o) {
			if (ang > o.ang + eps)
				return 1;
			if (ang < o.ang - eps)
				return -1;
			return 0;
		}
	}
	
	public int countCircles(int[] x, int[] y) {
		int n = x.length;
		int answer = 0;
		ArrayList<Pair> al = new ArrayList<Pair>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				al.clear();
				for (int k = 0; k < n; k++) {
					int ori = x[i] * y[j] - x[j] * y[i] +
							  x[j] * y[k] - x[k] * y[j] +
							  x[k] * y[i] - x[i] * y[k];
					if (ori == 0)
						continue;
					double a1 = Math.atan2(y[i] - y[k], x[i] - x[k]);
					double a2 = Math.atan2(y[j] - y[k], x[j] - x[k]);
					double a = Math.abs(a1 - a2);
					if (a > Math.PI)
						a = 2 * Math.PI - a;
					if (ori < 0)
						a = Math.PI - a;
					al.add(new Pair(a, k));
				}
				Collections.sort(al);
				for (int k = 0; k < al.size(); k++) {
					Pair p = al.get(k);
					if (k > 0 && Math.abs(p.ang - al.get(k - 1).ang) < eps)
						continue;
					int min = p.num;
					for (int l = k + 1; l < al.size(); l++) {
						if (Math.abs(p.ang - al.get(l).ang) < eps) {
							min = Math.min(min, al.get(l).num);
						} else
							break;
					}
					if (min > j)
						answer++;
				}
			}
		}
		return answer;
	}
	
	public static void main(String[] args) {
//		int[] x = new int[]{500, 498, 492, 482, 469, 452, 432, 409, 383, 356, 327, 296, 265, 234, 203, 172, 143, 116, 90, 67, 47, 30, 17, 7, 1, 0, 1, 7, 17, 30, 47, 67, 90, 116, 143, 172, 203, 234, 265, 296, 327, 356, 383, 409, 432, 452, 469, 482, 492, 498};
//		int[] y = new int[]{250, 281, 312, 342, 370, 396, 421, 442, 461, 476, 487, 495, 499, 499, 495, 487, 476, 461, 442, 421, 396, 370, 342, 312, 281, 250, 218, 187, 157, 129, 103, 78, 57, 38, 23, 12, 4, 0, 0, 4, 12, 23, 38, 57, 78, 103, 129, 157, 187, 218};
		int[] x = new int[]{500, 499, 498, 497, 495, 493, 490, 487, 483, 479, 474, 469, 463, 457, 450, 443, 435, 427, 419, 410, 400, 390, 380, 370, 359, 347, 336, 324, 311, 299, 286, 272, 259, 245, 231, 216, 202, 187, 172, 157, 142, 126, 111, 95, 79, 63, 48, 32, 16, 0};
		int[] y = new int[]{0, 16, 32, 48, 63, 79, 95, 111, 126, 142, 157, 172, 187, 202, 216, 231, 245, 259, 272, 286, 299, 311, 324, 336, 347, 359, 370, 380, 390, 400, 410, 419, 427, 435, 443, 450, 457, 463, 469, 474, 479, 483, 487, 490, 493, 495, 497, 498, 499, 500};
		System.out.println(new CropCircles().countCircles(x, y));
	}
}
