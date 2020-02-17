package topcoder;
import java.math.BigInteger;
import java.util.*;

public class ArithmeticProgressions {
	public String[] maxAptitude(String[] numbers) {
		int n = numbers.length;
		BigInteger[] a = new BigInteger[n];
		for (int i = 0; i < n; i++) {
			a[i] = new BigInteger(numbers[i]);
		}
		Arrays.sort(a);
		if (n < 3)
			return new String[]{"0", "1"};
		BigInteger min = a[0];
		BigInteger max = a[n - 1];
		BigInteger numb = BigInteger.ZERO;
		BigInteger denb = BigInteger.ONE;
		HashSet<BigInteger> difs = new HashSet<BigInteger>();
		for (int i = 0; i < n; i++) {
			BigInteger maxa = max.subtract(a[i]);
			BigInteger mina = a[i].subtract(min);
			for (int j = i + 1; j < n; j++) {
				BigInteger ij = a[j].subtract(a[i]);
				difs.clear();
				for (int k = j + 1; k < n; k++) {
					for (int l = k; l < n; l++) {
						BigInteger kl = a[l].subtract(a[k]);
						BigInteger diff = ij.gcd(kl);
						if (difs.contains(diff))
							continue;
						difs.add(diff);
						int take = 0;
						for (int x = 0; x < n; x++) {
							BigInteger ix = a[x].subtract(a[i]);
							if (ix.remainder(diff).signum() == 0)
								take++;
						}
						if (take < 3)
							continue;
						BigInteger num = BigInteger.valueOf(take);
						BigInteger jj = maxa.divide(diff);
						BigInteger ii = mina.divide(diff);
						BigInteger den = ii.add(jj).add(BigInteger.ONE);
						if (num.multiply(denb).compareTo(den.multiply(numb)) > 0) {
							numb = num;
							denb = den;
						}
					}
				}
			}
		}
		BigInteger gcd = numb.gcd(denb);
		numb = numb.divide(gcd);
		denb = denb.divide(gcd);
		return new String[]{numb.toString(), denb.toString()};
	}
	
	public static void main(String[] args) {
		String[] a = new String[50];
		for (int i = 0; i < 50; i++) {
			a[i] = "" + (i + 1);
		}
//		{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"}
		new ArithmeticProgressions().maxAptitude(a);
	}
}
