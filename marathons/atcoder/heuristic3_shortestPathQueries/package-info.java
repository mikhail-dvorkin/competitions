/**
 * https://atcoder.jp/contests/ahc003/tasks/ahc003_a
 *
 * Shortest Path Queries
 *
 * Time Limit: 2 sec / Memory Limit: 1024 MB
 * Story
 *
 * AtCoder is developing a route navigation application that utilizes shortest path algorithms. The service area is represented as a road network of 30x30 vertices connected in a grid. When a user specifies the vertex of the current location and the vertex of the destination, the app will output the shortest path between them. The trouble is that, even though the scheduled release date is approaching, the measurement of the length of each edge, which is essential for shortest path computations, is not finished at all. Therefore, AtCoder decided to give up measuring the edge length in advance and allows the app to output paths that are not the shortest. It should be possible to gradually improve the performance by estimating the length of each edge based on the information about the actual time users take to arrive at their destinations.
 * Problem Statement
 *
 * There is an undirected grid graph with 30x30 vertices with unknown edge lengths. Let
 * (0,0) denote the top-left vertex, and (i,j) denote the vertex at the i-th row from the top and
 *
 * j-th column from the left. Your task is to process the following query 1000 times.
 *
 * In the
 * k-th query, your program first receives the vertices sk=(sik,sjk) and
 *
 * tk=(tik,tjk) from Standard Input in the following format:
 *
 * sik
 *
 * sjk
 *
 * tik
 *
 * tjk
 *
 * Then, your program should compute a path
 * Pk from sk to tk. Let U, D, L, and R represent the movement from (i,j) to (i−1,j), (i+1,j), (i,j−1), and (i,j+1), respectively. Output a string representing the path
 *
 * Pk to Standard Output in one line. After the output, you have to flush Standard Output. Otherwise, the submission might be judged as TLE.
 *
 * After your program outputs a path, the judge program calculates the length
 * bk of the path, generates a uniform random number ek between 0.9 and 1.1, and gives an integer value round(bk×ek) to Standard Input. By reading that integer, the k-th query completes, and you should proceed to the
 *
 * k+1-th query.
 * Examples
 * Input 	Output
 *
 * 3 19 16 17
 *
 *
 *
 *
 * DDDDDDDDDDDDDLL
 *
 * 99561
 *
 *
 *
 * 26 18 13 18
 *
 *
 *
 *
 * UUUUUUUUUUUUU
 *
 * 72947
 *
 *
 * Scoring
 *
 * Let
 * ak and bk be the lengths of the shortest path and the output path for the k-th query (
 *
 * 1≤k≤1000), respectively. Then the score for the test case is
 *
 * round(2312311×∑k=110000.9981000−kbkak)
 *
 * The score of a submission is the total score for each test case. If your program outputs an illegal path (visiting the same vertex multiple times, going outside of 30x30, or not a path from
 * s to
 *
 * t), it is judged as WA. After the contest is over, the final ranking will be determined by system tests against the last submission.
 *
 *     Provisional tests consist of 100 test cases. If you get a result other than AC for one or more test cases, the score of the submission will be zero.
 *     System tests consist of 3000 test cases. If you get a result other than AC for some test cases, only the score for those test cases will be zero. We will publish seeds.txt (md5=0cf5051d586e7f62c0b3527f6f7fbb1c) after the contest is over.
 *
 * Input Generation
 *
 * Let
 * rand(L,U) be a function that generates a uniformly random integer between L and U, inclusive. We first generate two parameters D=rand(100,2000) and M=rand(1,2). Let hi,j be the length of the edge between (i,j) and (i,j+1), and let vi,j be the length of the edge between (i,j) and
 *
 * (i+1,j).
 * Generation of
 * hi,j
 *
 *     For each
 *
 * i∈{0,…,29} and p∈{0,…,M−1}, we independently generate a random integer
 * Hi,p=rand(1000+D,9000−D).
 * For each
 * i∈{0,…,29} and j∈{0,…,28}, we independently generate a random integer
 * δi,j=rand(−D,D).
 * If
 * M=1, for each i∈{0,…,29} and j∈{0,…,28}, we set
 * hi,j=Hi,0+δi,j.
 * If
 * M=2, for each i∈{0,…,29}, we generate a random integer xi=rand(1,28), and then for each j∈{0,…,xi−1}, we set hi,j=Hi,0+δi,j, and for each j∈{xi,…,28}, we set
 *
 *     hi,j=Hi,1+δi,j.
 *
 * Generation of
 * vi,j
 *
 *     For each
 *
 * j∈{0,…,29} and p∈{0,…,M−1}, we independently generate a random integer
 * Vj,p=rand(1000+D,9000−D).
 * For each
 * i∈{0,…,28} and j∈{0,…,29}, we independently generate a random integer
 * γi,j=rand(−D,D).
 * If
 * M=1, for each j∈{0,…,29} and i∈{0,…,28}, we set
 * vi,j=Vj,0+γi,j.
 * If
 * M=2, for each j∈{0,…,29}, we generate a random integer yj=rand(1,28), and then for each i∈{0,…,yj−1}, we set vi,j=Vj,0+γi,j, and for each i∈{yj,…,28}, we set
 *
 *     vi,j=Vj,1+γi,j.
 *
 * Generation of
 * sk,
 * tk
 *
 * The vertices
 * sk and tk given in the query are chosen uniformly at random among all the vertices. If the Manhattan distance between sk and tk (
 *
 * ∣sik−tik∣+∣sjk−tjk∣) is strictly less than 10, we repeat the random selection until the distance becomes at least 10.
 * Tools
 *
 *     Local tester: You need a compilation environment of Rust language.
 *     Visualizer
 *     Inputs: If you don't use the above local tester, you can instead use these 100 inputs (seed 0-99) for local testing. These inputs are different from the actual test cases. The inputs are in the following format, and you can use them by writing a judge program by yourself.
 *
 * h0,0
 *
 * …
 *
 * h0,28
 *
 * ⋮
 *
 * h29,0
 *
 * …
 *
 * h29,28
 *
 * v0,0
 *
 * …
 *
 * v0,29
 *
 * ⋮
 *
 * v28,0
 *
 * …
 *
 * v28,29
 *
 * si1
 *
 * sj1
 *
 * ti1
 *
 * tj1
 *
 * a1
 *
 * e1
 *
 * ⋮
 *
 * si1000
 *
 * sj1000
 *
 * ti1000
 *
 * tj1000
 *
 * a1000
 *
 * e1000
 *
 * Example of judge program (pseudo code)
 *
 * string query(s, t, prev_result) {
 *     // WRITE YOUR SOLUTION HERE
 * }
 *
 * int main() {
 *     if (LOCAL_TEST) {
 *         read_h_v();
 *     }
 *     prev_result = 0;
 *     score = 0.0;
 *     for (int k = 0; k < 1000; k++) {
 *         if (LOCAL_TEST) {
 *             read_s_t_a_e();
 *         } else {
 *             read_s_t();
 *         }
 *         path = query(s, t, prev_result);
 *         print(path);
 *         if (LOCAL_TEST) {
 *             b = compute_path_length(path);
 *             score = score * 0.998 + a / b;
 *             prev_result = round(b * e);
 *         } else {
 *             prev_result = read_result();
 *         }
 *     }
 *     if (LOCAL_TEST) {
 *         print(round(2312311 * score));
 *     }
 *     return 0;
 * }
 */
package marathons.atcoder.ahc3_shortestPathQueries;
