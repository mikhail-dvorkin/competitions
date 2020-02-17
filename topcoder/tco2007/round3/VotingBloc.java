package topcoder.tco2007.round3;
import java.util.*;

public class VotingBloc {
	boolean[] yes;
	boolean[] cov;
	boolean[][] e;
	int[] proh;
	boolean[] took;
	int n, lo;

	public int[] abstainers(String[] voter) {
		n = voter.length;
		yes = new boolean[n];
		cov = new boolean[n];
		e = new boolean[n][n];
		proh = new int[n];
		were = new boolean[n];
		took = new boolean[n];
		lm = new int[n];
		rm = new int[n];
		for (int i = 0; i < n; i++) {
			Scanner in = new Scanner(voter[i]);
			yes[i] = in.next().equals("Y");
			while (in.hasNextInt()) {
				int j = in.nextInt() - 1;
				e[i][j] = e[j][i] = true;
			}
			in.close();
		}
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				e[i][j] = e[i][j] && (yes[i] ^ yes[j]);
		ArrayList<Integer> ans = new ArrayList<Integer>();
		int cover = cover(0);
		for (int i = 0; i < n; i++) {
//			for (int j = i + 1; j < n; j++) {
//				if (e[i][j])
//					proh[j]++;
//			}
			if (took[i])
				continue;
			int nc = cover(i + 1);
			if (nc < cover) {
				cover = nc;
				ans.add(i + 1);
			} else {
				for (int j = i + 1; j < n; j++) {
					if (e[i][j]) {
						if (!took[j])
							ans.add(j + 1);
						took[j] = true;
					}
				}
				cover = cover(i + 1);
			}
		}
		int[] a = new int[ans.size()];
		Collections.sort(ans);
		for (int i = 0; i < a.length; i++) {
			a[i] = ans.get(i);
			System.out.println("take " + a[i]);
		}
		return a;
	}

	boolean[] were;
	int[] lm, rm;

	private int cover(int low) {
		lo = low;
		int siz = 0;
		Arrays.fill(lm, -1);
		Arrays.fill(rm, -1);
		for (int i = lo; i < n; i++) {
			if (took[i])
				continue;
			if (yes[i])
				continue;
			Arrays.fill(were, false);
			if (dfs(i)) {
				siz++;
			}
		}
		return siz;
	}

	private boolean dfs(int v) {
		if (were[v])
			return false;
		were[v] = true;
		for (int i = lo; i < n; i++) {
			if (took[i])
				continue;
			if (e[v][i]) {
				if (lm[i] < 0) {
					lm[i] = v;
					rm[v] = i;
					return true;
				}
				if (dfs(lm[i])) {
					lm[i] = v;
					rm[v] = i;
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
//		new VotingBloc().abstainers(new String[]{"N 2 3 4","N 3 4","Y 4","N"});
		new VotingBloc().abstainers(new String[]{"N"});
//		new VotingBloc().abstainers(new String[]{"N 7", "N 7", "N 7 8 9", "N 9", "N 9 10", "N 9 10", "Y", "Y", "Y", "Y", "Y"});
	}

}
