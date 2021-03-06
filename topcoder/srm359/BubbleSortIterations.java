package topcoder.srm359;
public class BubbleSortIterations {
	public int numIterations(int[] X, int[] Y, int L0, int M, int n) {
		//L[0] = L0
		//for i := 1 to n-1
		//    L[i] = (L[i-1] * X[i % length(X)] + Y[i % length(Y)]) MOD M
		int[] a = new int[n];
		a[0] = L0;
		for (int i = 1; i < n; i++) {
			long li = a[i - 1];
			li *= X[i % X.length];
			li += Y[i % Y.length];
			li %= M;
			a[i] = (int) li;
		}
		int ans = 0;

		int[] t = new int[M + 10];
		int all = 0;
		for (int k : a) {
	        int sum = 0;
	        int b = k;
	        for (; b >= 0; b = (b & (b + 1)) - 1)
	          sum += t[b];
	        ans = Math.max(ans, all - sum);
			for (; k <= M; k |= k + 1) t[k] ++;
			all++;
		}
		return ans + 1;
	}

	public static void main(String[] args) {
		int a = new BubbleSortIterations().numIterations(
				new int[]{234, 56, 567, 3147, 23464, 57128, 234, 2346, 457, 1346, 1254, 45, 23247, 1347, 145, 123},
				new int[]{235, 3413, 171, 58, 569, 40, 262, 467, 2456, 246, 2684, 3456, 5, 61, 8, 258, 24524, 2, 58, 245, 674},
				1, 999997, 100000
				);
		System.out.println(a);
		int[] x = new int[50];
		int[] y = new int[50];
		for (int i = 0; i < 50; i++) {
			double alpha = 2 * Math.PI * i / 196;
			x[i] = (int) (500 * Math.cos(alpha));
			y[i] = (int) (500 * Math.sin(alpha));
		}
		System.out.println();
	}
}
//{500, 498, 492, 482, 469, 452, 432, 409, 383, 356, 327, 296, 265, 234, 203, 172, 143, 116, 90, 67, 47, 30, 17, 7, 1, 0, 1, 7, 17, 30, 47, 67, 90, 116, 143, 172, 203, 234, 265, 296, 327, 356, 383, 409, 432, 452, 469, 482, 492, 498}
//{250, 281, 312, 342, 370, 396, 421, 442, 461, 476, 487, 495, 499, 499, 495, 487, 476, 461, 442, 421, 396, 370, 342, 312, 281, 250, 218, 187, 157, 129, 103, 78, 57, 38, 23, 12, 4, 0, 0, 4, 12, 23, 38, 57, 78, 103, 129, 157, 187, 218}

//{500, 499, 498, 497, 495, 493, 490, 487, 483, 479, 474, 469, 463, 457, 450, 443, 435, 427, 419, 410, 400, 390, 380, 370, 359, 347, 336, 324, 311, 299, 286, 272, 259, 245, 231, 216, 202, 187, 172, 157, 142, 126, 111, 95, 79, 63, 48, 32, 16, 0}
//{0, 16, 32, 48, 63, 79, 95, 111, 126, 142, 157, 172, 187, 202, 216, 231, 245, 259, 272, 286, 299, 311, 324, 336, 347, 359, 370, 380, 390, 400, 410, 419, 427, 435, 443, 450, 457, 463, 469, 474, 479, 483, 487, 490, 493, 495, 497, 498, 499, 500}
