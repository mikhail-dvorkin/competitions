package topcoder;
public class RepeatingFreeStrings {
  int n, m;
  
  public int count(int len, int end, int beg) {
    int ans = 1 << (m - len);
    prefcycle:
    for (int pref = 1; pref <= m; pref++) {
      int ww = beg >> (m - pref);
      for (int p = 1; p < pref; p++) {
        int w = beg >> (m - p);
        int wwend = ww & ((1 << (pref - p)) - 1);
        if (w == wwend)
          continue prefcycle;
      }
      if (len + pref > m) {
        int over = len + pref - m;
        int aa = ww >> (pref - over);
        int bb = end & ((1 << (over)) - 1);
        if (aa != bb) {
          continue prefcycle;
        } else {
          ans -= 1;
          continue prefcycle;
        }
      } else {
        ans -= 1 << (m - len - pref);
      }
    }
    return ans;
  }
  
  public String kthString(int nn, int k) {
    n = nn;
    m = n / 2;
    boolean odd = (n % 2 == 1);
    int max = 1 << m;
    for (int beg = 0; beg < max; beg++) {
      int here = count(0, 0, beg);
      if (odd)
        here *= 2;
      if (k >= here) {
        k -= here;
        continue;
      }
      boolean mone = false;
      if (odd) {
        here /= 2;
        if (k >= here) {
          mone = true;
          k -= here;
        }
      }
      int end = 0;
      for (int len = 1; len <= m; len++) {
        end *= 2;
        int hh = count(len, end, beg);
        if (k >= hh) {
          end++;
          k -= hh;
        }
      }
      if (odd) {
        beg *= 2;
        if (mone)
          beg++;
      }
      long ans = (((long) beg) << m) | end;
      String an = "";
      for (int i = 0; i < n; i++) {
        int bit = (int) (ans & 1);
        ans /= 2;
        an = bit + an;
      }
      return an;
    }
    return "";
  }
}