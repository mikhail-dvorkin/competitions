/**
 * Statement: https://atcoder.jp/contests/ahc025/tasks/ahc025_a
 *
 * Tools: https://img.atcoder.jp/ahc025/tNvZmDfV.zip
 */
package marathons.atcoder.ahc25_balancingByBalance;
/*
23247567
3136644 greedy, list
2858232 binary search in sorting of greedy
2337961 splitMore()
2272411 ↑ improve
2054867 improveA() for left and right greedy group, move 1
1904875 ↑ several times
1867242 ↑ move 1, than swap 1 to 1
1727459 ↑ several times
1698053 not only left and right
//2707625   @100
1676020 sort by weight level
1674590 only small differences of weight level
1245891 simplify greedy when 0 questions left           → 168303330 — 168th
836093  bugfix in hash code                             → 159408067 — 168th
//1932191   @1000
820998  pGreedyUsage = 0.4
//1929970   @1000
793230  pWantedGroup = sqrt(n)
//1922928   @1000
802751  pWantedGroup = 0.8 * sqrt(n)                    → 156500206 — 172nd→168th
//1919262   @1000

greedyUsage	0.5686896501565399
 */
