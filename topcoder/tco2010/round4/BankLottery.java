package topcoder;
public class BankLottery {
	public double expectedAmount(int[] accountBalance, int weeklyJackpot, int weekCount) {
		double sum = 0;
		for (int a : accountBalance) {
			sum += a;
		}
		double my = accountBalance[0];
		for (int i = 0; i < weekCount; i++) {
			my += (my / sum) * weeklyJackpot;
			sum += weeklyJackpot;
		}
		return my;
	}
}
