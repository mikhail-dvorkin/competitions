package topcoder;
public class RepaintTheChessboard {
	public int minimumChanges(String[] board) {
		int hei = board.length;
		int wid = board[0].length();
		int ans = 64;
		for (int i = 0; i + 8 <= hei; i++) {
			for (int j = 0; j + 8 <= wid; j++) {
				int cur = 0;
				for (int ii = 0; ii < 8; ii++) {
					for (int jj = 0; jj < 8; jj++) {
						char c = ((ii + jj) % 2 == 0) ? 'B' : 'W';
						if (board[i + ii].charAt(j + jj) != c)
							cur++;
					}
				}
				ans = Math.min(ans, cur);
				cur = 0;
				for (int ii = 0; ii < 8; ii++) {
					for (int jj = 0; jj < 8; jj++) {
						char c = ((ii + jj) % 2 != 0) ? 'B' : 'W';
						if (board[i + ii].charAt(j + jj) != c)
							cur++;
					}
				}
				ans = Math.min(ans, cur);
			}
		}
		return ans;
	}
}
