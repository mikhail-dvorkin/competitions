package topcoder.srm351;
public class CoinsExchange {
	public int countExchanges(int G1, int S1, int B1, int G2, int S2, int B2) {
		int op = 0;
		while (G1 < G2) {
			if (S1 >= 11) {
				S1 -= 11;
				G1++;
				op++;
			} else if (B1 >= 11) {
				B1 -= 11;
				S1++;
				op++;
			} else
				return -1;
		}
		while (B1 < B2) {
			if (S1 >= 1) {
				S1 -= 1;
				B1 += 9;
				op++;
			} else if (G1 > G2) {
				G1 -= 1;
				S1 += 9;
				op++;
			} else
				return -1;
		}
		while (S1 < S2) {
			if (G1 > G2) {
				G1 -= 1;
				S1 += 9;
				op++;
			} else if (B1 >= B2 + 11) {
				B1 -= 11;
				S1++;
				op++;
			} else
				return -1;
		}
		return op;
	}
}
