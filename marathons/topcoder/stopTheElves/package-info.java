/**
 * https://www.topcoder.com/challenges/00b375b6-1f64-4702-baeb-28a7b00b64d1
 *
 * Marathon Match 131
 *
 * Christmas presents got lost in the forest. It is your task to try and protect them from being stolen by the elves. The forest is represented by a N*N grid. The initial grid consists of trees, presents, and empty locations. You start with an amount of money. You can buy a box for C units of money and place it on an empty cell in the grid. You are not allowed to place a box on the border of the grid. A present is worth 100 units of money. After each turn you receive 1 unit of money. Elves spawn randomly along the border each turn with the probability elfP. An elf will take the shortest path to the nearest present, pick it up, and take the shortest path to the edge of the grid. Once an elf carries a present out of the grid, that present is stolen and lost forever, the elf will also luckily never return. In case no path to a present exists, the elf will use the shortest path to a box, pick it up, and take the shortest path to the edge of the grid. The box and elf will be gone forever. In case no path to a present, a box or to the edge can be found, the elf will remain idle at its current location. Only one elf can occupy each cell. When more than one shortest path exists, the first one found will be used, please look at the visualizer source code for the exact implementation. Each turn you can place zero or more boxes.
 *
 * The simulation runs up to N*N turns or until all the presents are stolen.
 *
 * Here is an example solution for seed=1:
 *
 * seed 1 example
 * Input and Output
 *
 * This is an interactive problem, so your code needs to interact with the tester for each turn. Initially your code will receive as input the following values, each on a separate line:
 *
 *     N, the size of the grid.
 *     C, the cost of a box.
 *     elfP, the spawning probability of an elf.
 *     money, the starting money.
 *     N*N lines representing the starting grid in row-major order. Each cell is either empty ('.'), a tree ('T'), a present ('P'), a box ('b'), an elf ('e'), an elf carrying a present ('E') or an elf carrying a box ('B').
 *
 * The following loop repeats until all presents are stolen or until N*N turns have been performed:
 *
 *     You place boxes by writing one line with box coordinates separated by spaces. The coordinates should be in row column order, for example '2 3 4 5' will place a box at row 2, column 3 and another box at row 4, column 5. If you want to place no boxes, output any single integer on the line such as '-1'. All coordinates are 0-based where coordinate (0,0) is at the top left side of the grid.
 *     All the elves on the grid make a move.
 *     New elf is spawned.
 *     You get 1 more unit of money.
 *     The tester sends you the following, each on a separate line: the total elapsed time, your updated money amount, and the updated grid on N*N lines.
 *
 * Scoring
 *
 * The raw score is the amount of money you have when the simulation ends plus 100 times the number of presents still on the grid. This includes presents held by elves on the grid. If your return was invalid, then your raw score on this test case will be -1. Possible reasons include:
 *
 *     Buying more boxes than what you can afford.
 *     Using an invalid box location.
 *     Exceeding the time-limit.
 *
 * If your raw score for a test case is negative then your normalized score for that test case is 0. Otherwise, your normalized score for each test case is YOUR/MAX, where YOUR is your raw score and MAX is the largest positive raw score currently obtained on this test case (considering only the last submission from each competitor). Finally, the sum of all your test scores is normalized to 100.
 * Test Case Generation
 *
 * Please look at the generate() method in the visualizer's source code for the exact details about test case generation. Each test case is generated as follows:
 *
 *     The grid size N is chosen between 10 and 30, inclusive.
 *     The cost of a box C is chosen between 1 and 10, inclusive.
 *     The elf spawn probability elfP is chosen between 0.1 and 0.2, inclusive.
 *     The present probability presentP is chosen between 0.1 and 0.3, inclusive.
 *     The tree probability treeP is chosen between 0.1 and 0.2, inclusive.
 *     The starting money is chosen between C * (1 and 10, inclusive).
 *     Each cell of the grid is filled, such that a tree is chosen with probability treeP and a present with probability presentP; otherwise the cell is set to empty.
 *     The grid generating process is repeated until there is at least 1 present.
 *     All values are chosen uniformly at random.
 *
 * Notes
 *
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
 * Make sure you name your Source Code file as StopTheElves.<appropriate extension>
 * Sample Submissions
 *
 * Here are example solutions for different languages, modified to be executed with the visualizer. Note that this solution does not produce a valid final answer. You may modify and submit these example solutions:
 *
 *     Java Source Code - StopTheElves.java
 *     C++ Source Code - StopTheElves.cpp
 *     Python3.6 Source Code - StopTheElves.py
 *     C# Source Code - StopTheElves.cs
 *
 * Tools
 *
 * An offline tester is available below. You can use it to test/debug your solution locally. You can also check its source code for an exact implementation of test case generation and score calculation. You can also find links to useful information and sample solutions in several languages.
 * Downloads
 *
 *     Visualizer Source - StopTheElvesTester.zip
 *     Visualizer Binary - tester.jar
 *
 * Offline Tester / Visualizer
 *
 * In order to use the offline tester/visualizer tool for testing your solution locally, you'll have to include in your solution the main method that interacts with the tester/visualizer via reading data from standard input and writing data to standard output.
 *
 * To run the tester with your solution, you should run:
 *
 * java -jar tester.jar -exec "<command>" -seed <seed>
 *
 * Here, <command> is the command to execute your program, and <seed> is seed for test case generation. If your compiled solution is an executable file, the command will be the full path to it, for example, "C:\TopCoder\StopTheElves.exe" or "~/topcoder/StopTheElves". In case your compiled solution is to be run with the help of an interpreter, for example, if you program is in Java, the command will be something like "java -cp C:\TopCoder StopTheElves".
 *
 * Additionally you can use the following options:
 *
 *     -seed <seed> Sets the seed used for test case generation, default is seed 1.
 *     -debug. Print debug information.
 *     -novis. Turns off visualisation.
 *     -pause. Starts the visualizer in paused mode. See more information below.
 *     -noImages. Do not display images.
 *     -delay <delay> Sets the delay (in milliseconds) between visualizing consecutive simulation steps, default is 100.
 *     -N <N> Sets a custom size of the grid.
 *     -C <C> Sets a custom cost of a box.
 *     -money <money> Sets a custom amount of starting money.
 *     -elfP <elfP> Sets a custom elf spawn probability.
 *     -presentP <presentP> Sets a custom present probability.
 *
 *     -treeP <treeP> Sets a custom tree probability.
 *
 * The visualizer works in two modes. In regular mode, steps are visualized one after another with a delay specified with the -delay parameter. In paused mode, the next move will be visualized only when you press any key. The space key can be used to switch between regular and paused modes. The default starting mode is regular. You can use the -pause parameter to start in paused mode.
 *
 * Marathon local testers have many useful options, including running a range of seeds with a single command, running more than one seed at time (multiple threads), controlling time limit, saving input/output/error and loading solution from a file. The usage of these options are described here.
 */
package marathons.topcoder.stopTheElves;
