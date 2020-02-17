package topcoder.srm538;
import java.util.*;

public class TurtleSpy {
	public double maxDistance(String[] commands) {
		double forward = 0;
		double backward = 0;
		ArrayList<Integer> turns = new ArrayList<Integer>();
		for (String command : commands) {
			int v = Integer.parseInt(command.split(" ")[1]);
			switch (command.charAt(0)) {
			case 'f':
				forward += v;
				break;
			case 'b':
				backward += v;
				break;
			case 'l':
				turns.add(v);
				break;
			case 'r':
				turns.add(-v);
				break;
			}
		}
		int FULL = 360;
		boolean[] p = new boolean[FULL];
		p[0] = true;
		for (int t : turns) {
			boolean[] pp = p.clone();
			for (int i = 0; i < FULL; i++) {
				if (p[i]) {
					pp[(i + t + FULL) % FULL] = true;
				}
			}
			p = pp;
		}
		double ans = 0;
		for (int i = 0; i < FULL; i++) {
			if (p[i]) {
				ans = Math.max(ans, Math.sqrt(backward * backward + forward * forward - 2 * backward * forward * Math.cos(i * Math.PI / 180)));
			}
		}
		return ans;
	}
}
