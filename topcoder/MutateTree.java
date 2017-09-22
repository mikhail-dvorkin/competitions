package topcoder;
public class MutateTree {
	char[] left = new char[260];
	char[] right = new char[260];
	char root;
	
	boolean isLeaf(char c) {
		return c >= 'A' && c <= 'Z';
	}
	
	public String newTree(String tree, int root1, int root2) {
		int n = tree.length();
		char[] stack = new char[n];
		int ss = 0;
		for (int i = 0; i < n; i++) {
			char c = tree.charAt(i);
			if (isLeaf(c)) {
				stack[ss] = c;
				ss++;
				continue;
			}
			if (ss < 2)
				return "BADTREE";
			ss--;
			char c1 = stack[ss];
			ss--;
			char c2 = stack[ss];
			left[c] = c1;
			right[c] = c2;
			stack[ss] = c;
			ss++;
		}
		if (ss != 1)
			return "BADTREE";
		root = stack[0];
		char rr1 = tree.charAt(root1); 
		char rr2 = tree.charAt(root2);
		if (dfs(rr1, rr2) || dfs(rr2, rr1))
			return "OVERLAP";
		return trav(root, rr1, rr2);
	}

	private String trav(char v, char rr1, char rr2) {
		if (v == rr1 || v == rr2)
			v ^= rr1 ^ rr2;
		if (isLeaf(v))
			return "" + v;
		return trav(right[v], rr1, rr2) + trav(left[v], rr1, rr2) + v;
	}

	private boolean dfs(char v, char find) {
		if (v == find)
			return true;
		if (isLeaf(v))
			return false;
		return dfs(left[v], find) || dfs(right[v], find);
	}
	
	public static void main(String[] args) {
		System.out.println(new MutateTree().newTree("ABcCbEZDfzy", 4, 8));
	}
}
