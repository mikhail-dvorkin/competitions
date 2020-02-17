package topcoder.srm380;
public class CompilingDecksWithJokers {
	public int maxCompleteDecks(int[] cards, int jokers) {
		long le = 0;
		long ri = cards[0] + jokers + 1;
		int n = cards.length;
		while (le + 1 < ri) {
			long mi = (le + ri) / 2;
			long need = 0;
			for (int i = 0; i < n; i++) {
				need += Math.max(mi - cards[i], 0);
			}
			if (need <= jokers && need <= mi)
				le = mi;
			else
				ri = mi;
		}
		return (int) le;
	}

	public static void main(String[] args) {
		int a = new CompilingDecksWithJokers().maxCompleteDecks(new int[]{400000000, 400000000, 400000000, 400000000, 400000000}, 400000000);
		System.out.println(a);
	}
}
