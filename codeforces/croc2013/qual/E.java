package codeforces.croc2013.qual;
import java.io.*;
import java.util.*;

public class E {
	private static BufferedReader in;
	
	int[] parent;
	List<Integer>[] kids;
	String[] string;
	int[] p;
	char[] c;
	int tLength;
	int ans;

	@SuppressWarnings("unchecked")
	public void run() throws IOException {
		int n = Integer.parseInt(in.readLine());
		parent = new int[n];
		kids = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			kids[i] = new ArrayList<Integer>();
		}
		string = new String[n];
		parent[0] = -1;
		string[0] = "";
		int sumLen = 0;
		for (int i = 1; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			parent[i] = Integer.parseInt(st.nextToken()) - 1;
			kids[parent[i]].add(i);
			string[i] = st.nextToken();
			sumLen += string[i].length();
		}
		String t = in.readLine();
		tLength = t.length();
		p = new int[tLength + sumLen + 2];
		c = new char[p.length - 1];
		p[0] = -1;
		ans = 0;
		append(0, t + "$");
		dfs(0, tLength + 1);
		System.out.println(ans);
	}
	
	void dfs(int v, int pos) {
		append(pos, string[v]);
		pos += string[v].length();
		for (int u : kids[v]) {
			dfs(u, pos);
		}
	}

	void append(int pos, String s) {
		for (int i = 0; i < s.length(); i++) {
			int k = p[pos + i];
			char curC = s.charAt(i);
			c[pos + i] = curC;
			while ((k >= 0) && (c[k] != curC)) {
				k = p[k];
			}
			p[pos + i + 1] = k + 1;
			if (k + 1 == tLength) {
				ans++;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new E().run();
		in.close();
	}
}
