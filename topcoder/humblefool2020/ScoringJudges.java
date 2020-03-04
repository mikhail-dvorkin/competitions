package topcoder.humblefool2020;

import java.util.*;

public class ScoringJudges {
	public double overallScore(int[] individualScores) {
		int n = individualScores.length;
		int m = n / 3;
		Arrays.sort(individualScores);
		return avg(individualScores, 0, m) + avg(individualScores, m, n - m) + avg(individualScores, n - m, n);
	}

	private double avg(int[] individualScores, int from, int to) {
		double sum = 0;
		for (int i = from; i < to; i++) {
			sum += individualScores[i];
		}
		return sum / (to - from);
	}
}
