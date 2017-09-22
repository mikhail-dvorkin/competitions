package topcoder;
public class ShufflingCardsDiv1 {
    public int shuffle(int[] p) {
    	int n = p.length / 2;
    	for (int i = 0; i < 2 * n; i++) {
    		p[i]--;
    	}
    	boolean ok = true;
    	int a = 0;
    	for (int i = 0; i < 2 * n; i++) {
    		if (p[i] != i) {
    			ok = false;
    		}
    		if (i % 2 == 0 && p[i] < n) {
    			a++;
    		}
    	}
    	if (ok) {
    		return 0;
    	}
    	if (a == n) {
    		return 1;
    	}
    	if (a == (n + 1) / 2) {
    		return 2;
    	}
    	if (a == 0 && n % 2 == 1) {
    		return 4;
    	}
    	return 3;
    }

}
