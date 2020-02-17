package topcoder.tccc2007.round3;
public class TristripeBacteria {
	int hei, wid;
	int[][] mark, m2;
	int part = 0;
	int ans = 0;
	int[] dx = new int[]{-1, 0, 1, 0};
	int[] dy = new int[]{0, 1, 0, -1};
	int[] size = new int[2500];
	String[] p;

	public int howMany(String[] photo) {
		p = photo;
		hei = photo.length;
		wid = photo[0].length();
		mark = new int[hei][wid];
		m2 = new int[wid][hei];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (p[i].charAt(j) == '*' && mark[i][j] == 0) {
					part++;
					dfs(i, j);
				}
			}
		}
		m2 = new int[wid][hei];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				m2[j][i] = mark[i][j];
			}
		}
		for (int i = 1; i <= part; i++) {
			if (size[i] <= 4) {
				ans++;
				continue;
			}
			if (good(i, mark, hei, wid) || good(i, m2, wid, hei)) {
				ans++;
			}
		}
		return ans;
	}

	private boolean good(int prt, int[][] mark_, int hei_, int wid_) {
		secloop:
		for (int sec = -1; sec < wid_; sec++) {
			int hors = 0;
			for (int i = 0; i < hei_; i++) {
				int jmin = 100;
				int jmax = -100;
				for (int j = 0; j < wid_; j++) {
					if (j == sec)
						continue;
					if (mark_[i][j] == prt) {
						if (jmin == 100)
							jmin = j;
						jmax = j;
					}
				}
				if (jmin == 100)
					continue;
				for (int j = jmin; j <= jmax; j++) {
					if (mark_[i][j] != prt)
						continue secloop;
				}
				hors++;
			}
			if (sec == -1 && hors <= 3)
				return true;
			if (hors <= 2)
				return true;
		}
		return false;
	}

	private void dfs(int i, int j) {
		if (i < 0 || j < 0 || i >= hei || j >= wid)
			return;
		if (p[i].charAt(j) == '.' || mark[i][j] == part)
			return;
		mark[i][j] = part;
		size[part]++;
		for (int d = 0; d < 4; d++) {
			dfs(i + dx[d], j + dy[d]);
		}
	}

	public static void main(String[] args) {
		int a = new TristripeBacteria().howMany(new String[]{".*..***.*.***...*..*.**...***********.*..***.*.***", "**..********.**.*******.******...****.***..******."});
		System.out.println(a);
	}
}

//{"aawfhriejlcmbvbhxjuisvsheimgvhmdwhuzmxvaddnapauqus", "ofrpyqzgrhcumwnoyriaszutfdxupwrhcqhwehoyeegeomhept", "tcbbcuujpltmivvdcbbfrfogjviirrximhttoskopwrcmkcbor", "cxvryhstmrefyjdzhbcwoscraixnilxjjmbwtexmdxkfupcqyo", "ilferzqroctdgwslzebeajkjclnlrvogjpwpgsowjjqulfsvtx", "eughslzeafxiwtzhkvagbnkadsikogdhbsiuroxagxraeekkda", "ntogwbmbjrfunkbzebbcuycftzmapuxnrztsxztuajibugwxbx", "ymrjktsiqnzczaqjnqvqymmzgflrtapumeqbzuwlafpwbmywws", "zferveavomlduwcpufmknsuiaphbmfcrwihmxskcvgajxwlgkj", "qlqtvlnqigksreyaixmnwrkpdzmdipvmdmdirvweethigbxrcr", "aqpuexvqmutejxnwshpcijkoxrtzjckhxgkcnnnffkznpjjuxr", "onyfazmzfvclbzjswvkrhwejkczaecbqhudskmncyiyhuwknhs", "illzjpsmeikmjmbezolxvpkhvphqwgqbmvwfzrsgxgmjvpakfm", "ngopiotseylsoontxopvlayatxgqyubcjwfypzukaapumntyhe", "dzdiekmcjhhbhvgjpbgkbtkasnlsacgnuqgvmlbrrlgpjcwpva", "rrwjvuxcgntulkexmdyqpclasdfcjqlohskcszfulszlatcppx", "puiqwhgbjaixebwjegmxkddqhkowvettrejhykfgawvaenhqoo", "muesgewxcgxrdklluxkxoawpkyxpqkdcznzhkrmbctdwfoepqy", "zgagfzyvdnhpniiucrxbkxmkucrwhdxvcgykwuycahsgkcrarz", "ssoirojikchunoarnihofnxlqhosfmripnldnusflztvkayomm", "ovcbqeasxvbdwayaczhyabbghukvtzjufyjyhjyogkhswnwbfm", "tetturrrhiggkslfgrgntvtzxteleeybdxvxgcrwvsatnhatka", "iszojhynzmkidcasiqjgddsxobiyhqzbptkwiphblntacwppvw", "eycqqnbvdseupckbxtxmvelofgwlkrbbbcmyebryacccgrorqj", "jylltsjpyxkmghklnyyshirdffkqbhztdixfzjnakddrwkayiz", "aawfhriejlcmbvbhxjuisvsheimgvhmdwhuzmxvaddnapauqus", "ofrpyqzgrhcumwnoyriaszutfdxupwrhcqhwehoyeegeomhept", "tcbbcuujpltmivvdcbbfrfogjviirrximhttoskopwrcmkcbor", "cxvryhstmrefyjdzhbcwoscraixnilxjjmbwtexmdxkfupcqyo", "ilferzqroctdgwslzebeajkjclnlrvogjpwpgsowjjqulfsvtx", "eughslzeafxiwtzhkvagbnkadsikogdhbsiuroxagxraeekkda", "ntogwbmbjrfunkbzebbcuycftzmapuxnrztsxztuajibugwxbx", "ymrjktsiqnzczaqjnqvqymmzgflrtapumeqbzuwlafpwbmywws", "zferveavomlduwcpufmknsuiaphbmfcrwihmxskcvgajxwlgkj", "qlqtvlnqigksreyaixmnwrkpdzmdipvmdmdirvweethigbxrcr", "aqpuexvqmutejxnwshpcijkoxrtzjckhxgkcnnnffkznpjjuxr", "onyfazmzfvclbzjswvkrhwejkczaecbqhudskmncyiyhuwknhs", "illzjpsmeikmjmbezolxvpkhvphqwgqbmvwfzrsgxgmjvpakfm", "ngopiotseylsoontxopvlayatxgqyubcjwfypzukaapumntyhe", "dzdiekmcjhhbhvgjpbgkbtkasnlsacgnuqgvmlbrrlgpjcwpva", "rrwjvuxcgntulkexmdyqpclasdfcjqlohskcszfulszlatcppx", "puiqwhgbjaixebwjegmxkddqhkowvettrejhykfgawvaenhqoo", "muesgewxcgxrdklluxkxoawpkyxpqkdcznzhkrmbctdwfoepqy", "zgagfzyvdnhpniiucrxbkxmkucrwhdxvcgykwuycahsgkcrarz", "ssoirojikchunoarnihofnxlqhosfmripnldnusflztvkayomm", "ovcbqeasxvbdwayaczhyabbghukvtzjufyjyhjyogkhswnwbfm", "tetturrrhiggkslfgrgntvtzxteleeybdxvxgcrwvsatnhatka", "iszojhynzmkidcasiqjgddsxobiyhqzbptkwiphblntacwppvw", "eycqqnbvdseupckbxtxmvelofgwlkrbbbcmyebryacccgrorqj", "jylltsjpyxkmghklnyyshirdffkqbhztdixfzjnakddrwkayiz"}
