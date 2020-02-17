package topcoder.srm426;
import java.util.Arrays;


public class ShufflingMachine {
	public double stackDeck(int[] shuffle, int maxShuffles, int[] cardsReceived, int K) {
		int n = shuffle.length;
		int[] a = new int[n];
		int[] get = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = i;
		}
		for (int j = 0; j < maxShuffles; j++) {
			int[] b = new int[n];
			for (int i = 0; i < n; i++) {
				b[shuffle[i]] = a[i];
			}
			a = b;
			for (int x : cardsReceived)
				get[a[x]]++;
		}
		Arrays.sort(get);
		int ans = 0;
		for (int i = n - K; i < n; i++) {
			ans += get[i];
		}
		return ans * 1d / maxShuffles;
	}

	public static void main(String[] args) {
		new ShufflingMachine().stackDeck(new int[]{1,0}, 3, new int[]{0}, 1);
	}
}
