package topcoder.srm588;
import java.util.*;

public class GUMIAndSongsDiv1 {
    public int maxSongs(int[] duration, int[] tone, int t) {
    	int n = duration.length;
    	Song[] songs = new Song[n];
    	for (int i = 0; i < n; i++) {
    		songs[i] = new Song(duration[i], tone[i], i);
    	}
    	Arrays.sort(songs);
    	int[][] a = new int[n][n];
    	int inf = Integer.MAX_VALUE / 2;
    	for (int i = 0; i < n; i++) {
    		Arrays.fill(a[i], inf);
    	}
    	for (int i = 0; i < n; i++) {
    		a[i][0] = songs[i].duration;
    		for (int j = 0; j < i; j++) {
    			for (int k = 0; k < n; k++) {
    				if (a[j][k] == inf) {
    					continue;
    				}
    				a[i][k + 1] = Math.min(a[i][k + 1], a[j][k] + songs[i].tone - songs[j].tone + songs[i].duration);
    			}
    		}
    	}
    	int ans = 0;
    	for (int i = 0; i < n; i++) {
    		for (int k = 0; k < n; k++) {
    			if (a[i][k] <= t) {
    				ans = Math.max(ans, k + 1);
    			}
    		}
    	}
    	return ans;
    }

    class Song implements Comparable<Song> {
    	int duration;
    	int tone;
    	int id;

		public Song(int duration, int tone, int id) {
			this.duration = duration;
			this.tone = tone;
			this.id = id;
		}

		@Override
		public int compareTo(Song that) {
			return tone - that.tone;
		}
    }

}
