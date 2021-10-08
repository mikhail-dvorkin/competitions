/**
 * https://atcoder.jp/contests/ahc001/tasks/ahc001_a
 *
 * A - AtCoder Ad
 *
 * Time Limit: 5 sec / Memory Limit: 1024 MB
 * Problem Statement
 *
 * AtCoder has decided to place web advertisements of n
 *
 * companies on the top page. The space for placing advertisements is a square of size 10000 x 10000. The space for each company must be an axis-parallel rectangle with positive area, and the coordinates of the vertices must be integer values. Different rectangles may touch on their sides, but they must not overlap. In other words, the common area must not have positive area. It is allowed to leave some free space that does not belong to any ad.
 *
 * President Takahashi asked each company for their desired location and area. Company i
 * wants an ad space with area ri including point (xi+0.5,yi+0.5). The satisfaction level pi of company i
 *
 * is determined as follows.
 *
 *     If the ad space for company i
 *
 * does not contain the point (xi+0.5,yi+0.5), then pi=0
 * .
 * If the ad space for company i
 * contains the point (xi+0.5,yi+0.5) and the area is si, then pi=1−(1−min(ri,si)/max(ri,si))2
 *
 *     .
 *
 * Your task is to determine the placement of the ads so that the sum of the satisfaction levels is maximized. You will get a score of 109×∑n−1i=0pi/n
 *
 * rounded to the nearest integer.
 *
 * Input
 *
 * Input is given from Standard Input in the following format:
 *
 * n
 *
 *
 * x0
 *
 *  y0
 *
 *  r0
 *
 *
 * ⋮
 *
 *
 * xn−1
 *
 *  yn−1
 *
 *  rn−1
 *
 *
 *     50≤n≤200
 *
 * xi
 * and yi are integers satisfying 0≤xi≤9999 and 0≤yi≤9999. For any i≠j, (xi,yi)≠(xj,yj)
 * holds.
 * ri
 * is an integer at least one and satisfies ∑n−1i=0ri=10000×10000
 *
 *     .
 *
 * Output
 *
 * Let (ai,bi)
 * and (ci,di) (0≤ai<ci≤10000, 0≤bi<di≤10000) be the coordinates of the two diagonal vertices of the rectangle representing the ad space for company i
 *
 * . Output to standard output in the following format.
 *
 * a0
 *
 *  b0
 *
 *  c0
 *
 *  d0
 *
 *
 * ⋮
 *
 *
 * an−1
 *
 *  bn−1
 *
 *  cn−1
 *
 *  dn−1
 *
 *
 * Input Generation
 *
 * Let rand()
 *
 * be a function that generates a uniformly random double-precision floating point number at least zero and less than one.
 * Generation of n
 *
 * The number of companies n
 * is generated by rounding 50×4rand()
 *
 * to the nearest integer value.
 * Generation of xi
 * and yi
 *
 * The list of desired locations (x1,yi),…,(xn,yn)
 * is generated by randomly sampling n distinct coordinates from {(x,y)∣x∈{0,1,…,9999},y∈{0,1,…,9999}}
 *
 * .
 * Generation of ri
 *
 * Let q1,…,qn−1
 * be a sorted list of n−1 distinct integers randomly sampled from {1,2,…,99999999}. Let q0=0 and qn=100000000. Then ri=qi+1−qi
 *
 * .
 * Number of test cases
 *
 *     Provisional test: 50
 *     System test: 1000. We will publish seeds.txt (md5=8fc1ce3f4beabac6abc1bdb4206d7f7e) after the contest is over.
 *
 * The score of a submission is the total scores for each test case. In the provisional test, if your submission produces illegal output or exceeds the time limit for some test cases, the submission itself will be judged as WA or TLE, and the score of the submission will be zero. In the system test, if your submission produces illegal output or exceeds the time limit for some test cases, only the score for those test cases will be zero.
 * Tools
 *
 * You can download an input generator and visualizer here. To use them, you need a compilation environment of Rust language. Thanks to kenkoooo, You can now use the tools on the web, although they are not officially supported. https://kenkoooo.github.io/ahc001-gen-vis-wasm/
 * Sample Input 1
 * Copy
 *
 * 50
 * 1909 360 6468907
 * 5810 7091 4661329
 * 5407 422 2010076
 * 5767 3140 681477
 * 6659 3234 920591
 * 4206 1620 2487369
 * 7853 9492 440133
 * 7875 432 586159
 * 9048 5059 1805425
 * 7292 9070 509242
 * 7633 2496 1558444
 * 421 4835 1808752
 * 7164 4109 35081
 * 5356 2271 78438
 * 5261 577 971398
 * 3546 5225 1871979
 * 4667 3386 28796
 * 5596 7896 3310195
 * 2518 9813 1739130
 * 9002 3913 334620
 * 8574 8947 1107057
 * 3118 1773 669849
 * 7140 4388 2098247
 * 8544 8196 1742491
 * 8577 4337 4435283
 * 3155 9168 976005
 * 7823 4404 945830
 * 9451 110 569854
 * 7031 1389 787729
 * 1841 2337 942236
 * 76 8364 710110
 * 3543 3931 3840994
 * 3927 8828 2920828
 * 5671 3305 1526349
 * 5542 4587 6285390
 * 4030 7732 3962404
 * 8575 8200 3662259
 * 1139 3739 254000
 * 50 7415 647735
 * 934 4056 1800657
 * 8801 7178 1218595
 * 4499 6207 660560
 * 3096 3375 2695827
 * 5252 3281 1046149
 * 2247 1446 7148429
 * 3347 8501 7546190
 * 5791 8600 3909497
 * 8033 8992 3365971
 * 2297 9254 23830
 * 4312 6176 192104
 *
 * Sample Output 1
 * Copy
 *
 * 0 0 4473 1446
 * 4634 5915 6987 7896
 * 4473 0 7875 577
 * 5260 2633 6274 3305
 * 6274 2722 7172 3747
 * 3174 1446 5238 2651
 * 7522 9161 8185 9824
 * 7875 0 8454 1012
 * 8376 4388 9720 5731
 * 6890 8668 7522 9473
 * 7172 1815 8315 3178
 * 0 4072 1185 5598
 * 7071 4016 7258 4203
 * 5238 2124 5504 2418
 * 4565 577 5958 1274
 * 2862 4541 4230 5909
 * 4582 3302 4752 3471
 * 3348 7896 8049 8600
 * 1351 9255 3685 10000
 * 8713 3624 9291 4202
 * 8185 8373 9149 9521
 * 2480 1446 3174 2411
 * 6105 4203 7823 5424
 * 8049 5731 8575 8373
 * 7823 3178 8713 4388
 * 2410 8600 3900 9255
 * 7823 4388 8376 5731
 * 8969 0 9933 591
 * 6581 940 7481 1815
 * 1356 1852 2327 2822
 * 0 7805 635 8923
 * 1286 3376 4582 4541
 * 3900 8600 5791 10000
 * 4752 3305 6274 4203
 * 4582 4203 6105 5915
 * 2287 6208 4634 7896
 * 8575 7179 10000 8373
 * 849 3449 1286 4030
 * 0 6812 652 7805
 * 0 4030 1286 4072
 * 8575 6182 9797 7179
 * 4313 5915 4634 6208
 * 2327 2651 4582 3376
 * 4752 2651 5260 3305
 * 0 1446 2480 1852
 * 635 7896 3348 8600
 * 5791 8600 6890 10000
 * 7522 8600 8185 9161
 * 2188 9146 2406 9255
 * 3671 5909 4313 6208
 */
package marathons.atcoder.heuristic1_atcoderAd;