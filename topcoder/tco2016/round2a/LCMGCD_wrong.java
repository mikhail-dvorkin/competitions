package topcoder.tco2016.round2a;
import java.util.*;

public class LCMGCD_wrong {
	int n;
	int[] a, b;

	public String isPossible(int[] x, int t) {
		 n = x.length;
		 x = Arrays.copyOf(x, n + 1);
		 x[n] = t;
		 a = new int[n + 1];
		 b = new int[n + 1];
		 for (int i = 0; i <= n; i++) {
			 while (x[i] % 2 == 0) {
				 x[i] /= 2;
				 a[i]++;
			 }
			 while (x[i] % 3 == 0) {
				 x[i] /= 3;
				 b[i]++;
			 }
		 }
		 BitSet bitSet = new BitSet(n);
		 bitSet.set(0, n);
		 search(bitSet);
		 TreeSet<Integer> res = memo.get(bitSet);
		 int v = a[n] * 32 + b[n];
		 boolean ans = res.contains(v);
		 return ans ? "Possible" : "Impossible";
	}

	Map<BitSet, TreeSet<Integer>> memo = new HashMap<>();

	void search(BitSet bitSet) {
		if (memo.containsKey(bitSet)) {
			return;
		}
		TreeSet<Integer> result = new TreeSet<>();
 		int num = bitSet.cardinality();
		if (num == 1) {
			int i = bitSet.nextSetBit(0);
			int v = a[i] * 32 + b[i];
			result.add(v);
		} else {
			boolean heuristics = (num > 3 && num < n - 2);
			if (heuristics) {
				int aMin = 32;
				int bMin = 32;
				int aMax = 0;
				int bMax = 0;
				for (int i = 0; i < n; i++) {
					if (!bitSet.get(i)) {
						continue;
					}
					aMin = Math.min(aMin, a[i]);
					aMax = Math.max(aMax, a[i]);
					bMin = Math.min(bMin, b[i]);
					bMax = Math.max(bMax, b[i]);
				}
				result.add(aMin * 32 + bMin);
				result.add(aMax * 32 + bMax);
			} else {
				for (int i = 0; i < n; i++) {
					if (!bitSet.get(i)) {
						continue;
					}
					for (int j = i; j < n; j++) {
						if (!bitSet.get(j)) {
							continue;
						}
						BitSet two = new BitSet();
						two.set(i);
						two.set(j);
						BitSet others = (BitSet) bitSet.clone();
						others.andNot(two);
						if (others.cardinality() == 0) {
							continue;
						}
						search(two);
						search(others);
						TreeSet<Integer> r1 = memo.get(two);
						TreeSet<Integer> r2 = memo.get(others);
						for (int v1 : r1) {
							int a1 = v1 / 32;
							int b1 = v1 % 32;
							for (int v2 : r2) {
								int a2 = v2 / 32;
								int b2 = v2 % 32;
								int u1 = Math.min(a1, a2) * 32 + Math.min(b1, b2);
								int u2 = Math.max(a1, a2) * 32 + Math.max(b1, b2);
								result.add(u1);
								result.add(u2);
							}
						}
					}
				}
			}
		}
		memo.put(bitSet, result);
	}

	static void test(int[] x, int t, String ans) {
		String res = new LCMGCD_wrong().isPossible(x, t);
		if (!res.equals(ans)) {
			throw new AssertionError();
		}
		System.out.println(res);
	}

	public static void main(String[] args) {
		test(new int[]{2, 3}, 6, "Possible");
		test(new int[]{4, 9}, 6, "Impossible");
		test(new int[]{6,12,24,18,36,72,54,108,216}, 36, "Possible");
		test(new int[]{6,27,81,729}, 6, "Impossible");
		test(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, 1, "Possible");
		test(new int[]{72,16,16,16,16,16,27,27,27,27,27,27,27,27,27}, 72, "Possible");
		test(new int[]{100663296, 544195584, 229582512, 59049}, 60466176, "Possible");
		test(new int[]{2,4,8,16,32,64}, 256, "Impossible");
	}
}
