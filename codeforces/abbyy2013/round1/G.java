package codeforces.abbyy2013.round1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class G {
	private static BufferedReader in;

	public void run() throws IOException {
		String original = in.readLine();
		int n = Integer.parseInt(in.readLine());
		String[] s = new String[n];
		int[] left = new int[n];
		int[] right = new int[n];
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			s[i] = st.nextToken();
			left[i] = Integer.parseInt(st.nextToken());
			right[i] = Integer.parseInt(st.nextToken());
		}
		Set<String> nice = new HashSet<String>();
		for (int i = 0; i < original.length(); i++) {
			for (int j = i + 1; j <= original.length(); j++) {
				String t = original.substring(i, j);
				boolean good = true;
				for (int k = 0; k < n; k++) {
					int[] kmp = kmp(t + "$" + s[k]);
					int count = 0;
					for (int z : kmp) {
						if (z == t.length()) {
							count++;
						}
					}
					if (count < left[k] || count > right[k]) {
						good = false;
						break;
					}
				}
				if (good) {
					nice.add(t);
				}
			}
		}
		System.out.println(nice.size());
	}
	
	public static int[] kmp(String s) {
		int n = s.length();
		int[] p = new int[n + 1];
		p[0] = -1;
		for (int i = 0; i < n; i++) {
			int k = p[i];
			char c = s.charAt(i);
			while ((k >= 0) && (s.charAt(k) != c)) {
				k = p[k];
			}
			p[i + 1] = k + 1;
		}
		return p;
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new G().run();
		in.close();
	}
}
