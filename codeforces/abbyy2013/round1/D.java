package codeforces.abbyy2013.round1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class D {
	private static BufferedReader in;
	
	static final int M = 1000000007;

	public void run() throws IOException {
		int n = Integer.parseInt(in.readLine());
		int m = 0;
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i = 0; i < n; i++) {
			if (Integer.parseInt(st.nextToken()) == 1) {
				m++;
			}
		}
		int[] fact = new int[n + 2];
		int[][] cnk = new int[n + 1][n + 1];
		int[][] c1 = new int[n + 1][n + 1];
		int[][] c2 = new int[n + 1][n + 1];
		fact[0] = 1;
		for (int i = 1; i <= n + 1; i++) {
			fact[i] = (int) ((fact[i - 1] * (long) i) % M);
		}
		for (int i = 0; i <= n; i++) {
			cnk[i][0] = cnk[i][i] = 1;
			for (int j = 1; j < i; j++) {
				cnk[i][j] = (cnk[i - 1][j - 1] + cnk[i - 1][j]) % M;
			}
			for (int j = 0; j <= i; j++) {
				c1[i][j] = (int) ((cnk[i][j] * (long) fact[j]) % M);
				c2[i][j] = (int) ((cnk[i][j] * (long) fact[j + 1]) % M);
			}
		}
		int[][] a = new int[n + 1][n + 1];
		a[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= i; j++) {
				int jj = i - j;
				if (j <= 1) {
					a[i][j] = fact[i];
					continue;
				}
				for (int k = 0; k <= jj; k++) {
					long c = (c1[jj][k] * 1L * a[i - k - 1][j - 1]) % M;
					long d = (c2[jj][k] * 1L * a[i - k - 2][j - 2]) % M;
					a[i][j] = (int) ((a[i][j] + c + d * (j - 1)) % M);
				}
			}
		}
		System.out.println(a[n][m]);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new D().run();
		in.close();
	}
}
