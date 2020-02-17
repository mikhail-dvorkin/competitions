package topcoder.srm621;
import java.util.*;

public class TreesAnalysis {
	int n;
	ArrayList<Integer>[] nei1, nei2;
	long ans = 0;

	@SuppressWarnings("unchecked")
	public long treeSimilarity(int[] tree1, int[] tree2) {
    	n = tree1.length + 1;
    	nei1 = new ArrayList[n];
    	nei2 = new ArrayList[n];
    	size = new int[n];
    	for (int i = 0; i < n; i++) {
    		nei1[i] = new ArrayList<Integer>();
    		nei2[i] = new ArrayList<Integer>();
    	}
    	for (int i = 0; i < n - 1; i++) {
    		int j = tree1[i];
    		nei1[i].add(j);
    		nei1[j].add(i);
    		j = tree2[i];
    		nei2[i].add(j);
    		nei2[j].add(i);
    	}
    	labelsList = new BitSet[n];
    	dfs1(0, -1);
    	for (int v = 1; v < n; v++) {
    		labels = labelsList[v];
    		card = labels.cardinality();
    		dfs2(0, -1);
    	}
    	return ans;
    }

	BitSet[] labelsList;

    void dfs1(int v, int p) {
    	BitSet res = new BitSet();
    	res.set(v);
		for (int u : nei1[v]) {
			if (u == p) {
				continue;
			}
			dfs1(u, v);
			res.or(labelsList[u]);
		}
		labelsList[v] = res;
	}

    BitSet labels;
    int card;
    int[] size;

	int dfs2(int v, int p) {
		int res = labels.get(v) ? 1 : 0;
		size[v] = 1;
		for (int u : nei2[v]) {
			if (u == p) {
				continue;
			}
			res += dfs2(u, v);
			size[v] += size[u];
		}
		if (p >= 0) {
			int val = Math.max(Math.max(res, card - res), Math.max(size[v] - res, n - card - size[v] + res));
			ans += val * val;
		}
		return res;
	}

}
