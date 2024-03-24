/**
 * https://codeforces.com/group/WfpD43Y7Ig/contest/497688/problem/A
 */
package marathons.oxfordCompSoc.y2024ht_robotMazeSolvingCompetition;
/*
1105.09	first working code
1109.52	fix finish on ?		→ 204792.632, 1st
1104.45 fix finish
1137.95	sort cells
1132.14	fix division by 0
1135.28	score = (cellsNew.size.compareTo(cells.size) + 1) * 1e9 + cellsNew.size * 1e4 + max * 1e6 + maxDistToFinish * 1e2 + sum / (count + 0.1)
1220.96 search depth = 2, TL
1195.25 depth = 1…2 to fit into TL	→ 214063.29, 1st
 */
