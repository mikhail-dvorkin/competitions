package yandex.y2014.warmup;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

public class F_tl {
	static class BigRational extends Number implements Comparable<BigRational> {
		private static final long serialVersionUID = -9028505490908693186L;
		
		BigInteger num;
		BigInteger den;
		
		public BigRational(BigInteger num, BigInteger den, boolean safe) {
			this.num = num;
			this.den = den;
			if (!safe) {
				if (den.signum() < 0) {
					this.num = this.num.negate();
					this.den = this.den.negate();
				}
				BigInteger gcd = num.gcd(den);
				this.num = this.num.divide(gcd);
				this.den = this.den.divide(gcd);
			}
		}
		
		public BigRational(BigInteger num, BigInteger den) {
			this(num, den, false);
		}
		
		public BigRational(BigInteger value) {
			this(value, BigInteger.ONE, true);
		}
		
		public BigRational(long num, long den, boolean safe) {
			this(BigInteger.valueOf(num), BigInteger.valueOf(den), safe);
		}

		public BigRational(long num, long den) {
			this(BigInteger.valueOf(num), BigInteger.valueOf(den), false);
		}

		public BigRational(long value) {
			this(BigInteger.valueOf(value), BigInteger.ONE, true);
		}

		@Override
		public String toString() {
			return num + "/" + den;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof BigRational) {
				BigRational that = (BigRational) obj;
				return num.equals(that.num) && den.equals(that.den);
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return (num.hashCode() * 3458937) ^ den.hashCode();
		}
		
		@Override
		public int compareTo(BigRational that) {
			return num.multiply(that.den).compareTo(den.multiply(that.num));
		}

		@Override
		public int intValue() {
			return toBigDecimal(0, RoundingMode.HALF_EVEN).intValue();
		}

		@Override
		public long longValue() {
			return toBigDecimal(0, RoundingMode.HALF_EVEN).longValue();
		}

		@Override
		public float floatValue() {
			return (float) doubleValue();
		}

		@Override
		public double doubleValue() {
			return num.doubleValue() / den.doubleValue();
		}
		
		public BigDecimal toBigDecimal(int scale, RoundingMode roundingMode) {
			return new BigDecimal(num).divide(new BigDecimal(den), scale, roundingMode);
		}
		
		public BigRational negate() {
			return new BigRational(num.negate(), den, true);
		}
		
		public BigRational add(BigRational val) {
			BigInteger d = den.multiply(val.den);
			BigInteger n = num.multiply(val.den).add(den.multiply(val.num));
			return new BigRational(n, d, false);
		}
		
		public BigRational subtract(BigRational val) {
			return add(val.negate());
		}
		
		public BigRational multiply(BigRational val) {
			return new BigRational(num.multiply(val.num), den.multiply(val.den), false);
		}
		
		public BigRational divide(BigRational val) {
			return new BigRational(num.multiply(val.den), den.multiply(val.num), false);
		}
		
		public BigRational pow(int exponent) {
			if (exponent < 0) {
				if (num.signum() == 0) {
		            throw new ArithmeticException("BigRational divide by zero");
				}
				BigInteger n = den.pow(-exponent);
				BigInteger d = num.pow(-exponent);
				if (d.signum() < 0) {
					n = n.negate();
					d = d.negate();
				}
				return new BigRational(n, d, false);
			}
			return new BigRational(num.pow(exponent), den.pow(exponent), false);
		}
	}
	
	static class ContinuedFraction {
		BigInteger[] a;

		public ContinuedFraction(BigInteger[] a) {
			this.a = a;
		}
		
		public ContinuedFraction(long[] a) {
			this.a = new BigInteger[a.length];
			for (int i = 0; i < a.length; i++) {
				this.a[i] = BigInteger.valueOf(a[i]);
			}
		}
		
		public ContinuedFraction(int[] a) {
			this.a = new BigInteger[a.length];
			for (int i = 0; i < a.length; i++) {
				this.a[i] = BigInteger.valueOf(a[i]);
			}
		}
		
		public ContinuedFraction(BigRational val) {
			List<BigInteger> list = new ArrayList<BigInteger>();
			BigInteger num = val.num;
			BigInteger den = val.den;
			while (den.compareTo(BigInteger.ONE) == 1) {
				BigInteger[] dr = num.divideAndRemainder(den);
				BigInteger p = dr[0];
				BigInteger q = dr[1];
				if (q.signum() < 0) {
					p = p.subtract(BigInteger.ONE);
					q = q.add(den);
				}
				list.add(p);
				num = den;
				den = q;
			}
			list.add(num);
			a = list.toArray(new BigInteger[list.size()]);
		}
		
		public BigRational toBigRational() {
			BigInteger num = a[a.length - 1];
			BigInteger den = BigInteger.ONE;
			for (int i = a.length - 2; i >= 0; i--) {
				BigInteger t = num; num = den; den = t;
				num = num.add(a[i].multiply(den));
			}
			return new BigRational(num, den, true);
		}
	}
	
	void run() {
		int n = in.nextInt() + 1;
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		ContinuedFraction f = new ContinuedFraction(a);
		BigRational x = f.toBigRational();
		x = new BigRational(1).subtract(x);
		ContinuedFraction g = new ContinuedFraction(x);
		out.println(g.a.length - 1);
		for (BigInteger v : g.a) {
			out.print(v + " ");
		}
		out.println();
	}

	static boolean stdStreams = true;
	static String fileName = F_tl.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		BufferedReader br;
		if (stdStreams) {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			br = new BufferedReader(new FileReader(inputFileName));
			out = new PrintWriter(outputFileName);
		}
		in = new MyScanner(br);
		new F_tl().run();
		br.close();
		out.close();
	}
	
	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		MyScanner(BufferedReader br) {
			this.br = br;
		}
		
		void findToken() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		String next() {
			findToken();
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		double nextDouble() {
			return Double.parseDouble(next());
		}
	}
}
