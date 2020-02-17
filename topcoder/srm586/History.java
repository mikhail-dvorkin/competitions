package topcoder.srm586;
public class History {
    public String verifyClaims(String[] dynasties, String[] battles, String[] queries) {
    	int d = dynasties.length;
    	int[][] dyn = new int[d][];
    	for (int i = 0; i < d; i++) {
    		String[] s = dynasties[i].split("\\s");
    		dyn[i] = new int[s.length];
    		for (int j = 0; j < s.length; j++) {
    			dyn[i][j] = Integer.parseInt(s[j]);
    		}
    	}
    	StringBuilder bat = new StringBuilder();
    	for (String s : battles) {
    		bat.append(s);
    	}
    	String ans = "";
    	for (String s : queries) {
        	int[][] min = new int[d][d];
        	int[][] max = new int[d][d];
        	int inf = Integer.MAX_VALUE / 3;
        	for (int i = 0; i < d; i++) {
        		min[i][i] = max[i][i] = 0;
        		for (int j = 0; j < d; j++) {
        			min[i][j] = -inf;
        			max[i][j] = inf;
        		}
        	}
        	for (String t : bat.toString().split("\\s")) {
        		int d1 = t.charAt(0) - 'A';
        		int d2 = t.charAt(3) - 'A';
        		int n1 = t.charAt(1) - '0';
        		int n2 = t.charAt(4) - '0';
        		min[d1][d2] = Math.max(min[d1][d2], dyn[d2][n2] + 1 - dyn[d1][n1 + 1]);
        		max[d1][d2] = Math.min(max[d1][d2], dyn[d2][n2 + 1] - 1 - dyn[d1][n1]);
        		max[d2][d1] = -min[d1][d2];
        		min[d2][d1] = -max[d1][d2];
        	}


    		int d1 = s.charAt(0) - 'A';
    		int d2 = s.charAt(3) - 'A';
    		int n1 = s.charAt(1) - '0';
    		int n2 = s.charAt(4) - '0';
    		min[d1][d2] = Math.max(min[d1][d2], dyn[d2][n2] + 1 - dyn[d1][n1 + 1]);
    		max[d1][d2] = Math.min(max[d1][d2], dyn[d2][n2 + 1] - 1 - dyn[d1][n1]);
    		max[d2][d1] = -min[d1][d2];
    		min[d2][d1] = -max[d1][d2];
    		for (int j = 0; j < d; j++) {
    			for (int i = 0; i < d; i++) {
    				for (int k = 0; k < d; k++) {
    					min[i][k] = Math.max(min[i][k], min[i][j] + min[j][k]);
    					max[i][k] = Math.min(max[i][k], max[i][j] + max[j][k]);
    				}
    			}
    		}
    		boolean ok = true;
			for (int i = 0; i < d; i++) {
				for (int j = 0; j < d; j++) {
					if (min[i][j] > max[i][j]) {
						ok = false;
					}
				}
			}
			ans += ok ? "Y" : "N";
    	}
    	return ans;
    }

}
