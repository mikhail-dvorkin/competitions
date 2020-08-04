/**
 * https://www.topcoder.com/challenges/30134026
 *
 * Overview
 * You want to create a maze to test the navigating ability of robot vacuum cleaners. The maze is to be designed on an NxN grid floor, whose cells are either empty ('.') or contain a wall ('#'). There are R robots and each robot must visit T target locations. In particular, each robot r begins in location Starts[r] and must visit every location given in Targets[r]. The robots do not need to return to their starting location at the end of their journey. The robots can only move through adjacent (horizontally or vertically) empty cells. The robots can see the entire maze and will choose the shortest path that visits all their target locations. Some paths of the robot may overlap and these are all counted towards the total distance. Your goal is to make the maze as hard as possible, ie., to maximize the total distance travelled by all the robots.
 * For example, here is a solution for seed=1. There are 2 robots (crosses) and each must visit 2 target locations (circles). The shortest path for each robot is shown with a different colour. Note that some paths may overlap - this is ok as robots do not interfere with each other.
 *
 * Input
 * Your code will receive as input the following values, each on a separate line:
 *     N, the size of the grid.
 *     R, the number of robots.
 *     T, the number of targets for each robot.
 * This is followed by R blocks, representing the Starts and Targets locations. The r-th block contains information about robot r. The first line is its starting location (formatted as [row column]), while the next T lines are its target locations (formatted as [row column]). All indices are zero-based.
 *
 * Output
 * Your code should write to output the following:
 *     On the first line, the number of cells in the maze, ie., N*N.
 *     N*N lines, each representing a single cell of the maze in row-major order. The cells must be either empty ('.') or a wall ('#').
 *
 * Scoring
 * The scorer will compute the length of the shortest path required for each robot to visit its targets. The raw score will be the sum of all these lengths.
 * If your return was invalid, then your raw score on this test case will be -1. Possible reasons include:
 *     Not outputing exactly N*N cells.
 *     Using characters other than '.' and '#'.
 *     Having any unreachable targets or starting locations blocked by walls.
 * If your raw score for a test case is negative then your normalized score for that test case is 0. Otherwise, your normalized score for each test case is YOUR/MAX, where YOUR is your raw score and MAX is the largest positive raw score currently obtained on this test case (considering only the last submission from each competitor). Finally, the sum of all your test scores is normalized to 100.
 *
 * Test Case Generation
 * Please look at the generate() method in visualizer's source code for the exact details about test case generation. Each test case is generated as follows:
 *     The size of the grid N is chosen between 10 and 40, inclusive.
 *     The number of robots R is chosen between 1 and 6, inclusive.
 *     The number of targets per robot T is chosen between 2 and 6, inclusive.
 *     Starts and Targets are chosen to be distinct locations on the grid.
 *     All values are chosen uniformly at random.
 *
 * Notes
 *     The time limit is 10 seconds per test case (this includes only the time spent in your code). The memory limit is 1024 megabytes.
 *     The compilation time limit is 30 seconds.
 *     There are 10 example test cases and 100 full submission (provisional) test cases. There will be 2000 test cases in the final testing.
 *     The match is rated.
 */
package marathons.topcoder.hardestMaze;
