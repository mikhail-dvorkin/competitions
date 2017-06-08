package dgcj.y2015.practice;

import dgcj.message;
import java.util.*;

public class B_tl {
	static final Object PROBLEM = new majority(); // PROBLEM NAME goes here
	
	public String run() {
		int n = (int) majority.GetN();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		int here = to - from;
		Map<Integer, Integer> votes = new TreeMap<>();
		for (int i = from; i < to; i++) {
			int vote = (int) majority.GetVote(i);
			votes.put(vote, 1 + myGet(votes, vote));
		}
		ArrayList<Result> results = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : votes.entrySet()) {
			results.add(new Result(entry.getKey(), entry.getValue()));
		}
		Collections.sort(results);
		int leader = 0;
		int advantage = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			leader = message.GetInt(ID - 1);
			advantage = message.GetInt(ID - 1);
		}
		int leaderHere = myGet(votes, leader);
		int next = 0;
		int nextHere = 0;
		for (Result result : results) {
			if (result.id == leader) {
				continue;
			}
			next = result.id;
			nextHere = result.count;
			break;
		}
		advantage += leaderHere;
		advantage -= here - leaderHere;
		if (advantage < 0) {
			leader = next;
			advantage += nextHere;
			advantage = nextHere - Math.abs(advantage);
			if (advantage <= 0) {
				advantage = 0;
			}
		}
		if (ID == NODES - 1) {
			message.PutInt(ID - 1, leader);
			message.PutInt(ID - 1, myGet(votes, leader));
			message.Send(ID - 1);
			return null;
		}
		message.PutInt(ID + 1, leader);
		message.PutInt(ID + 1, advantage);
		message.Send(ID + 1);
		message.Receive(ID + 1);
		leader = message.GetInt(ID + 1);
		int leaderCount = message.GetInt(ID + 1);
		leaderCount += myGet(votes, leader);
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
	
	static int myGet(Map<Integer, Integer> votes, int person) {
		Integer get = votes.get(person);
		return (get == null) ? 0 : get;
	}
	
	static class Result implements Comparable<Result> {
		int id;
		int count;
		
		public Result(int id, int count) {
			this.id = id;
			this.count = count;
		}

		@Override
		public int compareTo(Result that) {
			return Integer.compare(that.count, count);
		}
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new B_tl().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}
