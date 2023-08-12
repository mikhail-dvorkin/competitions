package topcoder.tco2021.round3;
public class AnnoyingPasswords {
	public int count(int U, int L, int D) {
		if (U + L + D == 0) return 1;
		int M = 1_000_000_007;
		int[][][] lastU = new int[U + 1][L + 1][D + 1];
		int[][][] lastL = new int[U + 1][L + 1][D + 1];
		int[][][] lastD = new int[U + 1][L + 1][D + 1];
		if (U > 0) lastU[1][0][0] = 1;
		if (L > 0) lastL[0][1][0] = 1;
		if (D > 0) lastD[0][0][1] = 1;
		for (int u = 0; u <= U; u++) {
			for (int l = 0; l <= L; l++) {
				for (int d = 0; d <= D; d++) {
					if (u < U) lastU[u + 1][l][d] = (int) (((long) lastD[u][l][d] + lastL[u][l][d] + lastU[u + 1][l][d]) * (26 - u) % M);
					if (l < L) lastL[u][l + 1][d] = (int) (((long) lastD[u][l][d] + lastU[u][l][d] + lastL[u][l + 1][d]) * (26 - l) % M);
					if (d < D) lastD[u][l][d + 1] = (int) (((long) lastL[u][l][d] + lastU[u][l][d] + lastD[u][l][d + 1]) * (10 - d) % M);
				}
			}
		}
		int ans = (int) (((long) lastU[U][L][D] + lastL[U][L][D] + lastD[U][L][D]) % M);
		return ans;
	}

	public static void main(String[] args) {
		System.out.println(new AnnoyingPasswords().count(4, 1, 1));
		System.out.println(new AnnoyingPasswords().count(5, 0, 4));
	}
}
