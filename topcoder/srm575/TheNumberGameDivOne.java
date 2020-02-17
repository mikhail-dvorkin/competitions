package topcoder;
public class TheNumberGameDivOne {
	String first = "John";
	String second = "Brus";
	
	public String find(long n) {
		long m = n;
		while (m % 4 == 0) {
			m /= 4;
		}
		return (n % 2 == 0 && m != 2) ? first : second;
	}

}
