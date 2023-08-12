import java.util.Arrays;

public class MagicTrickWithCards {
	int solve(String cards, int pos) {
		int n = cards.length();
		double xSum = 0, ySum = 0;
		for (int i = 0; i < n; i++) {
			if (cards.charAt(i) != '-') {
				continue;
			}
			double alpha = 2 * Math.PI * i / n;
			xSum += Math.cos(alpha);
			ySum += Math.sin(alpha);
		}
		double alpha = Math.atan2(ySum, xSum) + Math.PI;
		int start = (int) Math.round(alpha * n / 2 / Math.PI) % n;
		System.out.println(cards.substring(start) + cards.substring(0, start));
		for (int i = 0; i < n; i++) {
			int j = (start + n - 1 - i) % n;
			if (cards.charAt(j) != '-') {
				continue;
			}
			if (pos == 0) {
				return j;
			}
			pos--;
		}
		throw new RuntimeException();
	}

	public int[] findTheirCard(String[] queryCards, int[] queryX) {
		int[] ans = new int[queryCards.length];
		for (int t = 0; t < queryCards.length; t++) {
			ans[t] = solve(queryCards[t], queryX[t]);
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] a = new MagicTrickWithCards().findTheirCard(
				new String[] {"ncmN-z-yG--q-v-K-D-J-B--e----i-p-Q-s-F-d-ZjaxgfbOHAS", "NdyuiS--C-t-Q-D--Ys-n-q-K-r-ex-J-l-AHcVvBmbwagETGLjo", "m-fI-j-Y--u-D-rioGLXWsqdFpwESeBPhTAvU-Q-M-t-V-c--N-C", "MF-l-W-yVc-p-N-b-Y-L--f-s--KqwoeIvJaDuETdRQ-z-m-k-U-", "F-n-b-UqCQZHVBuLcXszgaRoN-J-P-M-Y--Gd--lf--k-v-K-E--", "s-f--c-M-vZHCjLDFgPbndeR-o-N-y-G-O-V-S-TUWYp-h-K--u-", "HAdJblNSWt-c-m-r-Y-p-w-X-eZ-Ky--T-E--FQ--IOsvkVBCMRj", "o-pm-w--S-Ez-K-J-eRubcxXAIVFMlg-i-a--BH-W-Q-k-ZU-C--", "-y-B-A-b-HXGOIacWPELuJTzsUjSQFKlD-n-m-Y-Z-ek--w--x-r", "laHpnNmQIzgRVFYh-M-d-Sb--c-JX-U--T---e-B--u-x-w-o-Z-", "K-O-mG-w-y--H-J-F-B--T-e-I-tQ-qS-x--jNLpMvsEcVfnYuD-", "-y-K-uOEqrPaTeXcwRNpUhf-i-xF-H-v--C-ZM-d-----jB-o-bl", "-L-I--oAF-P-h--S-W-g-U-steEKXafxCbRVluYmGNJjQr-T-c-H", "-B-e--WlzETxGNJVKtrQMHbSwRCPp-L-I-X-f-g-k-F--s-m-y-n", "Q--Vp-s-lUNfHzwFWdOnLrMcAmDkKC-x-P-J--R-j-B-g--hS--a", "C-Ji-m-l-z-BuDK-o--YV--Ae-n-S-v-pTMPGqhZdHIEOaNUr-g-", "-j-w-am-v----c-Xi-R-W-kH-guAFOTlZBJKSnNqUo-M--d-E--s", "-g-W-i-C-O--XRQhKTfYvDewIJFrdxctM-o-j-m--b-pA-y-uZ-B", "V-mw-r-l-k-zy-K-f-pUNxJM--h-W--b-T--t-IYCBQnAsXPdiEa", "D--S-e-J-cv-ZE-P-M-W-lhmRACudQsTpgIwGinBKx-b-z-j-a-H"},
				new int[] {2, 2, 1, 2, 1, 4, 4, 1, 1, 0, 0, 0, 2, 3, 3, 2, 4, 0, 2, 3}
		);
		System.out.println(Arrays.toString(a));
	}
}
/*
 {9,  5, 11, 23, 23, 1,  33, 14, 6,  23, 23, 4,  18, 0 , 1, 27, 14, 51, 34, 16 }
 [36, 30, 11, 23, 3, 1, 33, 14, 6, 51, 35, 4,    18, 0, 1, 27, 14, 11, 34, 14]
 [9, 9, 39, 47, 27, 32, 18, 33, 35, 16, 51, 23, 50, 35, 36, 1, 49, 33, 6, 48]
 */
