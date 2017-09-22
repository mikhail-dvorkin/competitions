package topcoder;
import java.util.*;

public class RussianCheckers {
	ArrayList<String> sim = new ArrayList<String>();
	ArrayList<String> cap = new ArrayList<String>();
	
	int[] dx = new int[]{1,1,-1,-1};
	int[] dy = new int[]{1,-1,1,-1};
	
	private ArrayList<String> capture(char[][] f, int i, int j) {
		if (i == 0)
			return captureKing(f, i, j);
		ArrayList<String> ans = new ArrayList<String>();
		f[i][j] = '.';
		String init = cell(i, j);
		for (int d = 0; d < 4; d++) {
			int ii = i + dx[d];
			int iii = ii + dx[d];
			int jj = j + dy[d];
			int jjj = jj + dy[d];
			if (!out(iii, jjj) && f[ii][jj] == 'b' && f[iii][jjj] == '.') {
				f[ii][jj] = 'w';
				f[iii][jjj] = 'w';
				ArrayList<String> poss = capture(f, iii, jjj);
				if (poss.isEmpty()) {
					ans.add(init + ":" + cell(iii, jjj));
				} else {
					for (String move : poss)
						ans.add(init + ":" + move);
				}
				f[ii][jj] = 'b';
				f[iii][jjj] = '.';
			}
		}
		f[i][j] = (i == 0) ? 'W' : 'w';
		return ans;
	}
	
	private ArrayList<String> captureKing(char[][] f, int i, int j) {
		ArrayList<String> ans = new ArrayList<String>();
		ArrayList<String> moreCapt = new ArrayList<String>();
		f[i][j] = '.';
		dloop:
		for (int d = 0; d < 4; d++) {
			int ii = i;
			int jj = j;
			String init = cell(i, j);
			int ei = -1;
			int ej = -1;
			while (true) {
				ii += dx[d];
				jj += dy[d];
				if (out(ii, jj) || f[ii][jj] == 'w' || f[ii][jj] == 'W')
					continue dloop;
				if (f[ii][jj] == 'b')
					break;
			}
			ei = ii;
			ej = jj;
			f[ei][ej] = 'w'; // :-)
			ii += dx[d];
			jj += dy[d];
			ArrayList<String> poss = new ArrayList<String>();
			while (true) {
				if (out(ii, jj) || f[ii][jj] != '.')
					break;
				f[ii][jj] = 'W';
				poss.addAll(captureKing(f, ii, jj));
				f[ii][jj] = '.';
				ii += dx[d];
				jj += dy[d];
			}
			for (String move : poss) {
				moreCapt.add(init + ":" + move);
			}
			ii = ei;
			jj = ej;
			ii += dx[d];
			jj += dy[d];
			while (true) {
				if (out(ii, jj) || f[ii][jj] != '.')
					break;
				if (poss.isEmpty()) {
					moreCapt.add(init + ":" + cell(ii, jj));
				}
				ans.add(init + ":" + cell(ii, jj));
				ii += dx[d];
				jj += dy[d];
			}
			f[ei][ej] = 'b';
		}
		f[i][j] = 'W';
		if (moreCapt.size() > 0)
			return moreCapt;
		return ans;
	}

	String cell(int i, int j) {
		char c = (char) ('a' + j);
		char d = (char) ('1' + (7 - i));
		return c + "" + d;
	}
	
	boolean out(int i, int j) {
		return (i >= 8) || (j >= 8) || i < 0 || j < 0;
	}
	
	public String[] listMoves(String[] board, String turn) {
		char[][] f = new char[8][];
		for (int i = 0; i < 8; i++) {
			f[i] = board[i].toCharArray();
		}
		if (turn.equals("BLACK")) {
			char[][] g = new char[8][8];
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					int ii = 7 - i;
					int jj = 7 - j;
					g[ii][jj] = '.';
					if (f[i][j] == 'w')
						g[ii][jj] = 'b';
					if (f[i][j] == 'W')
						g[ii][jj] = 'B';
					if (f[i][j] == 'b')
						g[ii][jj] = 'w';
					if (f[i][j] == 'B')
						g[ii][jj] = 'W';
				}
			}
			f = g;
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (f[i][j] == 'B')
					f[i][j] = 'b';
			}
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (f[i][j] == 'w') {
					if (i > 0) {
						if (j > 0 && f[i - 1][j - 1] == '.') {
							sim.add(cell(i, j) + "-" + cell(i - 1, j - 1));
						}
						if (j + 1 < 8 && f[i - 1][j + 1] == '.') {
							sim.add(cell(i, j) + "-" + cell(i - 1, j + 1));
						}
					}
					cap.addAll(capture(f, i, j));
				}
				if (f[i][j] == 'W') {
					for (int d = 0; d < 4; d++) {
						int ii = i;
						int jj = j;
						while (true) {
							ii += dx[d];
							jj += dy[d];
							if (out(ii, jj) || f[ii][jj] != '.')
								break;
							sim.add(cell(i, j) + "-" + cell(ii, jj));
						}
					}
					cap.addAll(captureKing(f, i, j));
				}
			}
		}
		
		if (cap.size() == 0)
			cap = sim;
		if (turn.equals("BLACK")) {
			sim = new ArrayList<String>();
			for (String s : cap) {
				String t = "";
				for (int i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					if (c >= 'a' && c <= 'h')
						c = (char) ('a' + 'h' - c);
					if (c >= '1' && c <= '8')
						c = (char) ('1' + '8' - c);
					t += c;
				}
				sim.add(t);
			}
			cap = sim;
		}
		Collections.sort(cap);
		return cap.toArray(new String[0]);
	}
	
	public static void main(String[] args) {
		String[] a = new RussianCheckers().listMoves(new String[]
{				".W.B.B.W",
				"B.W.w.W.",
				".w.w.B..",
				"w.B.b...",
				".w.w.B.w",
				"w.b...w.",
				".W...w.B",
				"B.W.W.W."},
					"WHITE"
					 );
		System.out.println(Arrays.deepToString(a));
	}
}
