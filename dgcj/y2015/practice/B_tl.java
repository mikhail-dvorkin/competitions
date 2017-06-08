package dgcj.y2015.practice;

import java.util.*;

import dgcj.message;

public class B_tl {
	static final Object PROBLEM = new majority(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) majority.GetN();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		int here = to - from;
		int[] votes = new int[here];
		for (int i = from; i < to; i++) {
			votes[i - from] = (int) majority.GetVote(i);
		}
		Arrays.sort(votes);
		int max1 = 0;
		int max1arg = 0;
		int max2 = 0;
		int max2arg = 0;
		for (int i = 0, j = 0; i < here; i++) {
			int v = votes[i];
			if (i == here - 1 || v != votes[i + 1]) {
				int val = i + 1 - j;
				if (val >= max1) {
					max2 = max1;
					max2arg = max1arg;
					max1 = val;
					max1arg = v;
				} else if (val >= max2) {
					max2 = val;
					max2arg = v;
				}
				j = i + 1;
			}
		}
		int leader = 0;
		int advantage = 0;

		if (ID > 0) {
			message.Receive(ID - 1);
			leader = message.GetInt(ID - 1);
			advantage = message.GetInt(ID - 1);
		}
		int leaderHere = count(votes, leader);
		int next, nextHere;
		if (max1arg != leader) {
			next = max1arg;
			nextHere = max1;
		} else {
			next = max2arg;
			nextHere = max2;
		}
		advantage += leaderHere;
		advantage -= here - leaderHere;
		if (advantage < 0) {
			leader = next;
			advantage += nextHere;
			advantage = nextHere - Math.abs(advantage);
			advantage = Math.max(advantage, 0);
		}
		int leaderCount = 0;
		if (ID < NODES - 1) {
			message.PutInt(ID + 1, leader);
			message.PutInt(ID + 1, advantage);
			message.Send(ID + 1);

			message.Receive(ID + 1);
			leader = message.GetInt(ID + 1);
			leaderCount = message.GetInt(ID + 1);
		}
		leaderCount += count(votes, leader);
		if (ID > 0) {
			message.PutInt(ID - 1, leader);
			message.PutInt(ID - 1, leaderCount);
			message.Send(ID - 1);
			return null;
		}

		if (leaderCount * 2 > n) {
			return "" + leader;
		}
		return "NO WINNER";
	}

	static int count(int[] votes, int person) {
		return binarySearch(votes, person + 1) - binarySearch(votes, person);
	}

	static int binarySearch(int[] votes, int person) {
		int low = -1;
		int high = votes.length;
		while (low + 1 < high) {
			int mid = (low + high) / 2;
			if (votes[mid] < person) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return high;
	}

	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new B_tl().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}
