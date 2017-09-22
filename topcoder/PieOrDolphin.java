package topcoder;
public class PieOrDolphin {
	int m = 50;
	int[][] to = new int[m][];
	int[][] index = new int[m][];
	int[][] dir = new int[m][];
	int[] degree = new int[m];
	
    public int[] Distribute(int[] choice1, int[] choice2) {
    	int edges = choice1.length;
    	for (int i = 0; i < edges; i++) {
    		degree[choice1[i]]++;
    		degree[choice2[i]]++;
    	}
    	for (int i = 0; i < m; i++) {
    		to[i] = new int[degree[i]];
    		index[i] = new int[degree[i]];
    		dir[i] = new int[degree[i]];
    	}
    	int[] d = new int[m];
    	for (int i = 0; i < edges; i++) {
    		int a = choice1[i];
    		int b = choice2[i];
    		to[a][d[a]] = b;
    		index[a][d[a]] = i;
    		dir[a][d[a]] = 1;
    		d[a]++;
    		to[b][d[b]] = a;
    		index[b][d[b]] = i;
    		dir[b][d[b]] = 2;
    		d[b]++;
    	}
    	int[] answer = new int[edges];
    	for (;;) {
    		int v = -1;
    		for (int i = 0; i < m; i++) {
    			if (degree[i] == 0) {
    				continue;
    			}
    			if (degree[i] % 2 == 1 || v == -1) {
    				v = i;
    				continue;
    			}
    		}
    		if (v == -1) {
    			break;
    		}
    		while (degree[v] > 0) {
    			int u = to[v][degree[v] - 1];
    			int ind = index[v][degree[v] - 1];
    			answer[ind] = dir[v][degree[v] - 1];
    			degree[v]--;
    			for (int i = 0; i < degree[u] - 1; i++) {
    				if (index[u][i] == ind) {
    					to[u][i] = to[u][degree[u] - 1];
    					index[u][i] = index[u][degree[u] - 1];
    					dir[u][i] = dir[u][degree[u] - 1];
    					break;
    				}
    			}
    			degree[u]--;
    			v = u;
    		}
    	}
    	return answer;
    }
    
//[0, 2, 4, 6, 8, 11, 12, 15, 17, 19, 21, 22, 24, 26, 28, 31, 33, 34, 36, 39, 40, 42, 45, 46, 48, 49, 2, 3, 6, 7, 9, 12, 13, 15, 18, 19, 21, 23, 25, 28, 30, 32, 34, 35, 38, 39, 42, 43, 46, 48]
//[1, 3, 5, 7, 9, 10, 13, 14, 16, 18, 20, 23, 25, 27, 29, 30, 32, 35, 37, 38, 41, 43, 44, 47, 49, 0, 1, 4, 5, 8, 10, 11, 14, 16, 17, 20, 22, 24, 26, 27, 29, 31, 33, 36, 37, 40, 41, 44, 45, 47]

}
