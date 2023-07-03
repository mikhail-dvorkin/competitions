/**
 * https://www.topcoder.com/challenges/9e14555a-a853-4137-8902-cd118f7ea5dc?tab=details
 *
 * You are given an N*N grid whose every cell is either a wall or a ball from one of C colours. Each turn you can swap two orthogonally adjacent balls. You cannot swap the location of walls. Your task is to maximize the number of components g containing exactly K balls, while minimizing the number of turns taken t. In particular, your final score will be g*P - t, where P is a provided constant.
 *
 * Here is an example solution for seed=1.
 *
 * seed 1 example
 * Input and Output
 *
 * Your code will receive as input the following values, each on a separate line:
 *
 *     N, the size of the grid.
 *     C, the number of ball colours.
 *     K, the size of the target components.
 *     P, the score multiplier.
 *     N*N lines representing the grid in row major order. Each cell is either a wall (0) or a ball with a colour between 1 and C, inclusive.
 *
 * Your solution needs to output the following:
 *
 *     t, the number of moves.
 *     t lines representing each move, formatted as "r1 c1 r2 c2" (without the quotes). This means that you are swapping the balls located at (r1, c1) and (r2, c2). All locations are zero based and must be orthogonally adjacent.
 *
 * Scoring
 *
 * The raw score of your final grid state is g*P - t, where g is the number of components with exactly K balls, P is a given score multiplier and t is the number of moves taken. If your return was invalid, then your raw score on this test case will be -1. Possible reasons include:
 *
 *     Trying to swap walls or using locations that are outside the grid.
 *     Trying to swap balls that are not orthogonally adjacent.
 *     Using more than 2*N*N*N moves.
 *
 * If your raw score for a test case is negative then your normalized score for that test case is 0. Otherwise, your normalized score for each test case is YOUR/MAX, where YOUR is your raw score and MAX is the largest positive raw score currently obtained on this test case (considering only the last submission from each competitor). Finally, the sum of all your test scores is normalized to 100.
 * Test Case Generation
 *
 * Please look at the generate() method in the visualizer's source code for the exact details about test case generation. Each test case is generated as follows:
 *
 *     The grid size N is chosen between 8 and 30, inclusive.
 *     The number of ball colours C is chosen between 2 and 6, inclusive.
 *     The target component size K is chosen between 2 and 8, inclusive.
 *     The multiplier P is chosen between 4*K and 12*K, inclusive.
 *     The wall fill ratio W is chosen between 0 and 0.3, inclusive.
 *     Generate the grid. With probability W a cell is set to be a wall (0), otherwise it is set to be a ball whose colour is chosen between 1 and C, inclusive. The grid generation process is repeated until there are at least K balls of each colour and the set of all balls forms a single connected component.
 *     All values are chosen uniformly at random.
 *
 * Notes
 *
 *     A component is the maximal set of balls of the same colour, such that there is a path from every ball in the set to every other ball in the set. This path must consist of orthogonal steps via balls in the set.
 *     Two locations (r1, c1) and (r2, c2) are orthogonally adjacent if |r1 - r2| + |c1 - c2| = 1.
 *     The time limit is 10 seconds per test case (this includes only the time spent in your code). The memory limit is 1024 megabytes.
 *     The compilation time limit is 30 seconds.
 *     There are 10 example test cases and 100 full submission (provisional) test cases. There will be 2000 test cases in the final testing.
 *     The match is rated.
 *
 * Languages Supported
 *
 * C#, Java, C++ and Python
 * Submission Format
 *
 * Your submission must be a single ZIP file not larger than 500 MB, with your source code only.
 * Please Note: Please zip only the file. Do not put it inside a folder before zipping, you should directly zip the file.
 *
 * Make sure you name your Source Code file as HappyGrid*.<appropriate extension>*
 * Sample Submissions
 *
 * Here are example solutions for different languages, modified to be executed with the visualizer. You may modify and submit these example solutions:
 *
 *     Java Source Code - HappyGrid.java
 *     C++ Source Code - HappyGrid.cpp
 *     Python3.6 Source Code - HappyGrid.py
 *     C# Source Code - HappyGrid.cs
 *
 * Tools
 *
 * An offline tester is available below. You can use it to test/debug your solution locally. You can also check its source code for an exact implementation of test case generation and score calculation. You can also find links to useful information and sample solutions in several languages.
 * Downloads
 *
 *     Visualizer Source - HappyGridTester.zip
 *     Visualizer Binary - tester.jar
 *
 * Offline Tester / Visualizer
 *
 * Your solution should interact with the tester/visualizer by reading data from standard input and writing data to standard output.
 *
 * To run the tester with your solution, you should run:
 *
 * java -jar tester.jar -exec "<command>" -seed <seed>
 *
 * Here, <command> is the command to execute your program, and <seed> is seed for test case generation.
 * If your compiled solution is an executable file, the command will be the full path to it, for example, "C:\TopCoder\HappyGrid.exe" or "~/topcoder/HappyGrid".
 * In case your compiled solution is to be run with the help of an interpreter, for example, if your program is in Java, the command will be something like "java -cp C:\TopCoder HappyGrid".
 *
 * Additionally you can use the following options:
 *
 *     -seed <seed> Sets the seed used for test case generation, default is seed 1.
 *     -debug. Print debug information.
 *     -novis. Turns off visualisation.
 *     -noanimate. Turns off animation and only displays the final grid.
 *     -pause. Starts the visualizer in paused mode. See more information below.
 *     -delay <delay> Sets the delay (in milliseconds) between visualizing consecutive simulation steps, default is 1000.
 *     -manual. Allows you to play in the manual mode. Use the left mouse click to select and swap balls. Use the right mouse click to undo moves.
 *     -N <N> Sets a custom grid size.
 *     -C <C> Sets a custom number of ball colours.
 *     -K <K> Sets a custom target component size.
 *     -P <P> Sets a custom score multiplier.
 *     -W <W> Sets a custom wall fill ratio.
 *
 * The visualizer works in two modes. In regular mode, steps are visualized one after another with a delay specified with the -delay parameter. In paused mode, the next move will be visualized only when you press any key. The space key can be used to switch between regular and paused modes. The default starting mode is regular. You can use the -pause parameter to start in paused mode.
 *
 * Marathon local testers have many useful options, including running a range of seeds with a single command, running more than one seed at time (multiple threads), controlling time limit, saving input/output/error and loading solution from a file. The usage of these options are described here.
 */
package marathons.topcoder.happyGrid;
