package topcoder;
public class NewMagicSquare {
	public String[] completeTheSquare(String[] square) {
		int[][] a = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				String s = square[i].substring(3 * j, 3 * j + 2);
				if (s.equals("??"))
					a[i][j] = 0;
				else
					a[i][j] = Integer.parseInt(s);
			}
		}
		int[][] b = lexFirst(a);
		if (b[0][0] == 566)
			return new String[0];
		String[] ans = new String[5];
		for (int i = 0; i < 5; i++) {
			ans[i] = "";
			for (int j = 0; j < 5; j++) {
				ans[i] += "" + (b[i][j] / 10) + "" + (b[i][j] % 10) + " ";
			}
			ans[i] = ans[i].trim();
		}
		return ans;
	}

	private int[][] lexFirst(int[][] a) {
		int fi = -1;
		int fj = -1;
		loop:
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (a[i][j] == 0) {
					fi = i;
					fj = j;
					break loop;
				}
			}
		}
		if (fi == -1)
			return a;
		for (int val = 1; val <= 25; val++) {
			a[fi][fj] = val;
			if (possible(a)) {
				return lexFirst(a);
			}
		}
		a[0][0] = 566;
		return a;
	}

	private boolean possible(int[][] t) {
		int[][] a = new int[5][5];
		boolean[] were = new boolean[26];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (t[i][j] == 0)
					continue;
				a[i][j] = t[i][j];
				if (were[a[i][j]])
					return false;
				were[a[i][j]] = true;
			}
		}
		for (int p = 1; p <= 25; p++) {
			if (were[p])
				continue;
			int bst = 566;
			int ii = -1;
			int jj = -1;
			for (int i = 0; i < 5; i++) {
				int j = 0;
				while (j < 5 && a[i][j] > 0)
					j++;
				if (j == 5)
					continue;
				if (j > 0 && p <= a[i][j - 1])
					continue;
				int jjj = j;
				while (j < 5 && a[i][j] == 0)
					j++;
				int imp = 26;
				if (j < 5)
					imp = a[i][j];
				if (imp < bst) {
					bst = imp;
					ii = i;
					jj = jjj;
				}
			}
			if (ii < 0)
				return false;
			a[ii][jj] = p;
			were[p] = true;
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (a[i][j] >= a[i][j + 1])
					return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		new NewMagicSquare().completeTheSquare(
				new String[]
//						{"?? ?? 20 ?? ??", 
//							 "?? ?? ?? ?? ??", 
//							 "?? ?? ?? 05 ??", 
//							 "?? ?? ?? ?? ??", 
//							 "?? ?? ?? ?? ??"}
				{"?? ?? ?? ?? ??", 
					 "?? ?? ?? ?? 24", 
					 "?? ?? ?? ?? ??", 
					 "?? ?? ?? ?? ??", 
					 "21 ?? ?? ?? ??"}
				);
	}
}
