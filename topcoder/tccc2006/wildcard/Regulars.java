package topcoder.tccc2006.wildcard;
import java.util.*;

public class Regulars {
    public long stringCt(String s, int maxLen) {
    	RegualarExpression regexp = stupidParse(s);
		EpsNFA epsNFA = regexp.toEpsNFA();
		DFA dfa = epsNFA.determinize();
		long[] count = dfa.acceptedStringCount(maxLen);
		long ans = 0;
		for (long x : count) {
			ans += x;
		}
    	return ans;
    }

	final static int OP_LETTER = 1;
	final static int OP_EMPTY_SET = 2;
	final static int OP_EPS = 3;
	final static int OP_KLEENE_STAR = 4;
	final static int OP_CONCAT = 5;
	final static int OP_UNION = 6;
	static String alphabetString = "01";

	static class RegualarExpression {
		int type;
		RegualarExpression x, y;
		int value;

		RegualarExpression(int value) {
			this.type = OP_LETTER;
			this.x = null;
			this.y = null;
			this.value = value;
		}

		RegualarExpression(int type, RegualarExpression x, RegualarExpression y) {
			this.type = type;
			this.x = x;
			this.y = y;
		}

		@SuppressWarnings("unchecked")
		EpsNFA toEpsNFA() {
			EpsNFA a = new EpsNFA();
			a.alphabet = alphabetString.length();
			a.init = 0;
			if (type == OP_LETTER) {
				a.n = 2;
				a.sigma = new ArrayList[a.n][a.alphabet + 1];
				for (int i = 0; i <= a.alphabet; i++) {
					a.sigma[0][i] = new ArrayList<Integer>();
					a.sigma[1][i] = new ArrayList<Integer>();
				}
				a.sigma[0][value].add(1);
			}
			if (type == OP_EMPTY_SET) {
				a.n = 2;
				a.sigma = new ArrayList[a.n][a.alphabet + 1];
				for (int i = 0; i <= a.alphabet; i++) {
					a.sigma[0][i] = new ArrayList<Integer>();
					a.sigma[1][i] = new ArrayList<Integer>();
				}
			}
			if (type == OP_EPS) {
				a.n = 2;
				a.sigma = new ArrayList[a.n][a.alphabet + 1];
				for (int i = 0; i <= a.alphabet; i++) {
					a.sigma[0][i] = new ArrayList<Integer>();
					a.sigma[1][i] = new ArrayList<Integer>();
				}
				a.sigma[0][a.alphabet].add(1);
			}
			if (type == OP_KLEENE_STAR) {
				EpsNFA b = x.toEpsNFA();
				a.n = b.n + 2;
				a.sigma = new ArrayList[a.n][a.alphabet + 1];
				for (int i = 0; i <= a.alphabet; i++) {
					for (int j = 0; j < a.n; j++) {
						a.sigma[j][i] = new ArrayList<Integer>();
					}
				}
				for (int i = 0; i < b.n; i++) {
					for (int j = 0; j <= a.alphabet; j++) {
						for (int k : b.sigma[i][j]) {
							a.sigma[i + 1][j].add(k + 1);
						}
					}
				}
				a.sigma[0][a.alphabet].add(1);
				a.sigma[0][a.alphabet].add(a.n - 1);
				a.sigma[a.n - 2][a.alphabet].add(1);
				a.sigma[a.n - 2][a.alphabet].add(a.n - 1);
			}
			if (type == OP_CONCAT) {
				EpsNFA b = x.toEpsNFA();
				EpsNFA c = y.toEpsNFA();
				a.n = b.n + c.n;
				a.sigma = new ArrayList[a.n][a.alphabet + 1];
				for (int i = 0; i <= a.alphabet; i++) {
					for (int j = 0; j < a.n; j++) {
						a.sigma[j][i] = new ArrayList<Integer>();
					}
				}
				for (int i = 0; i < b.n; i++) {
					for (int j = 0; j <= a.alphabet; j++) {
						for (int k : b.sigma[i][j]) {
							a.sigma[i][j].add(k);
						}
					}
				}
				for (int i = 0; i < c.n; i++) {
					for (int j = 0; j <= a.alphabet; j++) {
						for (int k : c.sigma[i][j]) {
							a.sigma[i + b.n][j].add(k + b.n);
						}
					}
				}
				a.sigma[b.n - 1][a.alphabet].add(b.n);
			}
			if (type == OP_UNION) {
				EpsNFA b = x.toEpsNFA();
				EpsNFA c = y.toEpsNFA();
				a.n = b.n + c.n + 2;
				a.sigma = new ArrayList[a.n][a.alphabet + 1];
				for (int i = 0; i <= a.alphabet; i++) {
					for (int j = 0; j < a.n; j++) {
						a.sigma[j][i] = new ArrayList<Integer>();
					}
				}
				for (int i = 0; i < b.n; i++) {
					for (int j = 0; j <= a.alphabet; j++) {
						for (int k : b.sigma[i][j]) {
							a.sigma[i + 1][j].add(k + 1);
						}
					}
				}
				for (int i = 0; i < c.n; i++) {
					for (int j = 0; j <= a.alphabet; j++) {
						for (int k : c.sigma[i][j]) {
							a.sigma[i + 1 + b.n][j].add(k + 1 + b.n);
						}
					}
				}
				a.sigma[0][a.alphabet].add(1);
				a.sigma[0][a.alphabet].add(b.n + 1);
				a.sigma[b.n][a.alphabet].add(a.n - 1);
				a.sigma[b.n + c.n][a.alphabet].add(a.n - 1);
			}
			a.terminal = new boolean[a.n];
			a.terminal[a.n - 1] = true;
			a = makeEpsNFAfromDFA(a.determinize());
			return a;
		}
	}

	@SuppressWarnings("unchecked")
	static EpsNFA makeEpsNFAfromDFA(DFA b) {
		EpsNFA a = new EpsNFA();
		a.alphabet = b.alphabet;
		a.n = b.n + 2;
		a.init = 0;
		a.terminal = new boolean[a.n];
		a.terminal[a.n - 1] = true;
		a.sigma = new ArrayList[a.n][a.alphabet + 1];
		for (int i = 0; i <= a.alphabet; i++) {
			for (int j = 0; j < a.n; j++) {
				a.sigma[j][i] = new ArrayList<Integer>();
			}
		}
		for (int i = 0; i < b.n; i++) {
			for (int j = 0; j < b.alphabet; j++) {
				a.sigma[i + 1][j].add(b.sigma[i][j] + 1);
			}
			if (b.terminal[i]) {
				a.sigma[i + 1][a.alphabet].add(a.n - 1);
			}
		}
		a.sigma[0][a.alphabet].add(b.init + 1);
		return a;
	}

	public static RegualarExpression stupidParse(String s) {
		int len = s.length();
		RegualarExpression[][] re = new RegualarExpression[len][len + 1];
		for (int ij = 1; ij <= len; ij++) {
			loop:
			for (int i = 0;; i++) {
				int j = i + ij;
				if (j > len)
					break;
				char c = s.charAt(i);
				char d = s.charAt(j - 1);
				if (ij == 1) {
					if (alphabetString.indexOf(c) >= 0) {
						re[i][j] = new RegualarExpression(alphabetString.indexOf(c));
					}
					if (c == '@') {
						re[i][j] = new RegualarExpression(OP_EMPTY_SET, null, null);
					}
					if (c == 'e') {
						re[i][j] = new RegualarExpression(OP_EPS, null, null);
					}
					continue loop;
				}
				if (c == '(' && d == ')' && re[i + 1][j - 1] != null) {
					re[i][j] = re[i + 1][j - 1];
					continue loop;
				}
				for (int k = i + 1; k + 1 < j; k++) {
					if (s.charAt(k) == '|' && re[i][k] != null && re[k + 1][j] != null) {
						re[i][j] = new RegualarExpression(OP_UNION, re[i][k], re[k + 1][j]);
						continue loop;
					}
				}
				for (int k = i + 1; k < j; k++) {
					if (re[i][k] != null && re[k][j] != null) {
						re[i][j] = new RegualarExpression(OP_CONCAT, re[i][k], re[k][j]);
						continue loop;
					}
				}
				if (d == '*' && re[i][j - 1] != null) {
					re[i][j] = new RegualarExpression(OP_KLEENE_STAR, re[i][j - 1], null);
					continue loop;
				}
				re[i][j] = null;
			}
		}
		return re[0][len];
	}

	static class EpsNFA {
		int alphabet;
		int n;
		boolean[] terminal;
		int init;
		ArrayList<Integer>[][] sigma;

		int[][] eps;
		boolean[] mark;

		public EpsNFA() {
		}

		public boolean accepts(String s) {
			mark = new boolean[n];
			dfs(init);
			boolean[] cur = mark.clone();
			for (char c : s.toCharArray()) {
				boolean[] newCur = new boolean[n];
				for (int i = 0; i < n; i++) {
					if (!cur[i]) {
						continue;
					}
					for (int j : sigma[i][alphabetString.indexOf(c)]) {
						newCur[j] = true;
					}
				}
				cur = newCur;
				for (int i = 0; i < n; i++) {
					if (!cur[i]) {
						continue;
					}
					mark = new boolean[n];
					dfs(i);
					for (int j = 0; j < n; j++) {
						cur[j] |= mark[j];
					}
				}
			}
			for (int i = 0; i < n; i++) {
				if (cur[i] && terminal[i])
					return true;
			}
			return false;
		}

		private void epsClosure() {
			eps = new int[n][];
			for (int i = 0; i < n; i++) {
				mark = new boolean[n];
				dfs(i);
				int reach = 0;
				for (int j = 0; j < n; j++) {
					if (mark[j]) {
						reach++;
					}
				}
				eps[i] = new int[reach];
				int k = 0;
				for (int j = 0; j < n; j++) {
					if (mark[j]) {
						eps[i][k++] = j;
					}
				}
			}
		}

		private void dfs(int v) {
			if (mark[v])
				return;
			mark[v] = true;
			for (int u : sigma[v][alphabet]) {
				dfs(u);
			}
		}

		public DFA determinize() {
			epsClosure();
			DFA a = new DFA();

			BitSet m0 = new BitSet();
			for (int x : eps[init])
				m0.set(x);
			Map<BitSet, Integer> masks = new HashMap<BitSet, Integer>();
			List<Integer> terms = new ArrayList<Integer>();
			List<BitSet> queue = new ArrayList<BitSet>();
			List<int[]> sig = new ArrayList<int[]>();
			masks.put(m0, 0);
			queue.add(m0);
			boolean[] set = new boolean[n];
			boolean[] next = new boolean[n];
			for (int k = 0; k < queue.size(); k++) {
				BitSet mask = queue.get(k);
				sig.add(new int[alphabet]);
				for (int c = 0; c < alphabet; c++) {
					for (int i = 0; i < n; i++) {
						set[i] = mask.get(i);
						if (set[i] && terminal[i])
							terms.add(k);
					}
					Arrays.fill(next, false);
					for (int i = 0; i < n; i++) {
						if (!set[i])
							continue;
						for (int j : sigma[i][c]) {
							for (int kk : eps[j])
								next[kk] = true;
						}
					}
					BitSet nxt = new BitSet();
					for (int i = 0; i < n; i++) {
						if (next[i]) {
							nxt.set(i);
						}
					}
					if (!masks.containsKey(nxt)) {
						masks.put(nxt, masks.size());
						queue.add(nxt);
					}
					sig.get(k)[c] = masks.get(nxt);
				}
			}

			a.alphabet = alphabet;
			a.n = masks.size();
			a.init = 0;
			a.terminal = new boolean[a.n];
			for (int k : terms)
				a.terminal[k] = true;
			a.sigma = new int[a.n][];
			for (int i = 0; i < a.n; i++) {
				a.sigma[i] = sig.get(i);
			}
			return a.minimize();
		}
	}

	static class DFA {
		int alphabet;
		int n;
		boolean[] terminal;
		int init;
		int[][] sigma;

		public DFA() {
		}

		public DFA(Scanner in) {
			alphabet = in.next().length();
			n = in.nextInt();
			init = in.nextInt() - 1;
			terminal = new boolean[n];
			for (int i = in.nextInt(); i > 0; i--) {
				terminal[in.nextInt() - 1] = true;
			}
			sigma = new int[n][alphabet];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < alphabet; j++) {
					sigma[i][j] = in.nextInt() - 1;
				}
			}
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(n);
			sb.append("\n");
			sb.append(init + 1);
			sb.append(" ");
			int terms = 0;
			for (int i = 0; i < n; i++) {
				if (terminal[i])
					terms++;
			}
			sb.append(terms);
			for (int i = 0; i < n; i++) {
				if (terminal[i]) {
					sb.append(" ");
					sb.append(i + 1);
				}
			}
			sb.append("\n");
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < alphabet; j++) {
					if (j > 0)
						sb.append(" ");
					sb.append(sigma[i][j] + 1);
				}
				sb.append("\n");
			}
			return sb.toString();
		}

		private ArrayList<Integer> from[][];
		private ArrayList<Integer> queueA;
		private ArrayList<Integer> queueB;
		private boolean[][] diff;
		private boolean[] needed;

		@SuppressWarnings("unchecked")
		public DFA minimize() {
			DFA a = new DFA();
			a.alphabet = alphabet;
			from = new ArrayList[n][alphabet];
			queueA = new ArrayList<Integer>();
			queueB = new ArrayList<Integer>();
			diff = new boolean[n][n];
			needed = new boolean[n];
			dfs(init);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < alphabet; j++) {
					from[i][j] = new ArrayList<Integer>();
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < alphabet; j++) {
					from[sigma[i][j]][j].add(i);
				}
			}
			for (int i = 0; i < n; i++) {
				if (!needed[i])
					continue;
				for (int j = 0; j < n; j++) {
					if (!needed[j])
						continue;
					if (terminal[i] != terminal[j]) {
						queueA.add(i);
						queueB.add(j);
						diff[i][j] = true;
					}
				}
			}
			for (int cur = 0; cur < queueA.size(); cur++) {
				int x = queueA.get(cur);
				int y = queueB.get(cur);
				for (int c = 0; c < alphabet; c++) {
					for (int xx : from[x][c]) {
						if (!needed[xx])
							continue;
						for (int yy : from[y][c]) {
							if (!needed[yy])
								continue;
							if (!diff[xx][yy]) {
								queueA.add(xx);
								queueB.add(yy);
								diff[xx][yy] = true;
							}
						}
					}
				}
			}
			int[] newname = new int[n];
			boolean[] main = new boolean[n];
			for (int i = 0; i < n; i++) {
				if (!needed[i])
					continue;
				boolean good = true;
				for (int j = 0; j < i; j++) {
					if (!needed[j])
						continue;
					if (!diff[i][j]) {
						good = false;
						newname[i] = newname[j];
						break;
					}
				}
				if (good) {
					newname[i] = a.n++;
					main[i] = true;
				}
			}
			a.terminal = new boolean[a.n];
			for (int i = 0; i < n; i++) {
				if (!needed[i])
					continue;
				if (terminal[i])
					a.terminal[newname[i]] = true;
			}
			a.init = newname[init];
			a.sigma = new int[a.n][a.alphabet];
			for (int i = 0; i < n; i++) {
				if (!needed[i])
					continue;
				 for (int c = 0; c < a.alphabet; c++) {
					 a.sigma[newname[i]][c] = newname[sigma[i][c]];
				 }
			}
			return a;
		}

		public long[][] perStateStringCount(int maxLength) {
			long[][] count = new long[maxLength + 1][n];
			count[0][init] = 1;
			for (int len = 0; len < maxLength; len++) {
				for (int v = 0; v < n; v++) {
					for (int c = 0; c < alphabet; c++) {
						count[len + 1][sigma[v][c]] += count[len][v];
					}
				}
			}
			return count;
		}

		public long[] acceptedStringCount(int maxLength) {
			long[][] count = perStateStringCount(maxLength);
			long[] res = new long[maxLength + 1];
			for (int len = 0; len <= maxLength; len++) {
				for (int v = 0; v < n; v++) {
					if (terminal[v]) {
						res[len] += count[len][v];
					}
				}
			}
			return res;
		}

		private void dfs(int v) {
			if (needed[v])
				return;
			needed[v] = true;
			for (int c = 0; c < alphabet; c++) {
				dfs(sigma[v][c]);
			}
		}

		@SuppressWarnings("unchecked")
		public EpsNFA reverse() {
			EpsNFA a = new EpsNFA();
			a.alphabet = alphabet;
			a.n = n + 1;
			a.init = n;
			a.terminal = new boolean[n + 1];
			a.terminal[init] = true;
			a.sigma = new ArrayList[n + 1][alphabet + 1];
			for (int i = 0; i < n + 1; i++) {
				for (int j = 0; j < alphabet + 1; j++) {
					a.sigma[i][j] = new ArrayList<Integer>();
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < alphabet; j++) {
					a.sigma[sigma[i][j]][j].add(i);
				}
				if (terminal[i]) {
					a.sigma[n][alphabet].add(i);
				}
			}
			return a;
		}
	}
}
