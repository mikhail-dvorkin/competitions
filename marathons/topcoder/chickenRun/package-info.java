/**
 * https://www.topcoder.com/challenges/c522c9e3-29d3-4788-98db-05748f0842dc
 *
 * Marathon Match 129
 *
 * Important Links
 * Submission-Review You can find your submissions artifacts here. Artifacts will contain output.txt's for both example test cases and provisional test cases with stdout/stderr and individual test case scores.
 * Other Details For other details like Processing Server Specifications, Submission Queue, Example Test Cases Execution.
 *
 * Overview
 * Your chickens have escaped from your barn and you need to catch them quickly! Luckily you have some friends who volunteered to help. You are given an NxN grid representing the layout of your farm. Each cell is either empty, a chicken, a wall or a person. Each turn you can move one or more people into an adjacent cell (horizontally or vertically) that is empty or contains a chicken. If the person lands on a chicken then you get (N*N - turnNumber + 1) points and that chicken is collected from the grid. Now the chickens make their moves. These chickens are clever so they will try to move away from the people. Specifically a chicken will try to move into an empty adjacent cell (horizontally or vertically) that is not adjacent to a person. If there are multiple such cells then one will be selected at random, but if there are no such cells then the chicken will stay where it is. Your task is to score as many points as possible within N*N turns.
 *
 * Here is an example solution for seed=1:
 *
 * animation for seed 1
 *
 * Input and Output
 * This is an interactive problem, so your code needs to interact with the tester for each turn. Initially your code will receive as input the following values, each on a separate line:
 *
 * N, the grid size.
 * N*N lines representing the grid in row-major order. Each cell is either empty ('.'), a chicken ('C'), a wall ('#") or a person ('P').
 * The following loop repeats until you terminate it by printing "-1":
 *
 * You move one or more people. To move K people you print K, followed by K lines formatted as "r1 c1 r2 c2" (without the quotes). Here (r1,c1) is the current location of a person and (r2,c2) is their new location.
 * The tester performs your moves and collects any caught chickens. Now the chickens make their moves in a random order. You will then receive the following:
 * A number representing the total time (in milliseconds) spent in your solution.
 * The updated grid, which is N*N lines representing the grid in row-major order.
 * Scoring
 * The raw score is the total number of points you received for all collected chickens. If your return was invalid, then your raw score on this test case will be -1. Possible reasons include:
 *
 * Not formatting the moves correctly or using indices that are out of bounds.
 * Trying to make moves that are not orthogonally adjacent.
 * Trying to move a person onto another person or a wall.
 * Trying to move a person multiple times.
 * Performing more than N*N turns.
 * Exceeding the time-limit.
 * If your raw score for a test case is negative then your normalized score for that test case is 0. Otherwise, your normalized score for each test case is YOUR/MAX, where YOUR is your raw score and MAX is the largest positive raw score currently obtained on this test case (considering only the last submission from each competitor). Finally, the sum of all your test scores is normalized to 100.
 *
 * Test Case Generation
 * Please look at the generate() method in visualizer's source code for the exact details about test case generation. Each test case is generated as follows:
 *
 * The grid size N is chosen between 6 and 30, inclusive.
 * The chicken ratio ratioC is chosen between 0.03 and 0.1, inclusive.
 * The person ratio ratioP is chosen between 0.01 and 0.03, inclusive.
 * The wall ratio ratioW is chosen between 0.01 and 0.15, inclusive.
 * Each cell of the grid is filled, such that a chicken is chosen with probability ratioC, a person with probability ratioP and a wall with probability ratioW; otherwise the cell is set to empty.
 * The grid generating process is repeated until there is at least 1 chicken, at least 4 people and all non-wall locations are reachable.
 * All values are chosen uniformly at random.
 * Notes
 * Two locations are orthogonally adjacent if they differ only by one row or by one column.
 * The time limit is 10 seconds per test case (this includes only the time spent in your code). The memory limit is 1024 megabytes.
 * The compilation time limit is 30 seconds.
 * There are 10 example test cases and 100 full submission (provisional) test cases. There will be 2000 test cases in the final testing.
 * The match is rated.
 * Languages Supported
 * C#, Java, C++ and Python
 *
 * Submission Format
 * Your submission must be a single ZIP file not larger than 500 MB, with your source code only.
 * Please Note: Please zip only the file. Do not put it inside a folder before zipping, you should directly zip the file.
 *
 * Make sure you name your Source Code file as ChickenRun.<appropriate extension>
 *
 * SAMPLE SUBMISSIONS
 * Here are example solutions for different languages, modified to be executed with the visualizer. Note that this solution does not produce a valid final answer. You may modify and submit these example solutions:
 *
 * Java Source Code - ChickenRun.java
 * C++ Source Code - ChickenRun.cpp
 * Python3.6 Source Code - ChickenRun.py
 * C# Source Code - ChickenRun.cs
 * Tools
 * An offline tester is available below. You can use it to test/debug your solution locally. You can also check its source code for an exact implementation of test case generation and score calculation. You can also find links to useful information and sample solutions in several languages.
 *
 * Downloads
 * Visualizer Source - ChickenRunTester.zip
 * Visualizer Binary - tester.jar
 * Offline Tester / Visualizer
 * In order to use the offline tester/visualizer tool for testing your solution locally, you'll have to include in your solution the main method that interacts with the tester/visualizer via reading data from standard input and writing data to standard output.
 *
 * To run the tester with your solution, you should run:
 *
 * java -jar tester.jar -exec "<command>" -seed <seed>
 *
 * Here, <command> is the command to execute your program, and <seed> is seed for test case generation. If your compiled solution is an executable file, the command will be the full path to it, for example, "C:\TopCoder\ChickenRun.exe" or "~/topcoder/ChickenRun". In case your compiled solution is to be run with the help of an interpreter, for example, if you program is in Java, the command will be something like "java -cp C:\TopCoder ChickenRun".
 *
 * Additionally you can use the following options:
 *
 * -seed <seed> Sets the seed used for test case generation, default is seed 1.
 * -debug. Print debug information.
 * -novis. Turns off visualisation.
 * -pause. Starts the visualizer in paused mode. See more information below.
 * -showAllMoves. Show the move of every chicken and person. Slow, but useful for debugging.
 * -noImages. Do not display images.
 * -delay <delay> Sets the delay (in milliseconds) between visualizing consecutive simulation steps, default is 100.
 * -N <N> Sets a custom grid size.
 * -ratioC <ratioC> Sets a custom chicken ratio.
 * -ratioP <ratioP> Sets a custom person ratio.
 * -ratioW <ratioW> Sets a custom wall ratio.
 *
 * The visualizer works in two modes. In regular mode, steps are visualized one after another with a delay specified with the -delay parameter. In paused mode, the next move will be visualized only when you press any key. The space key can be used to switch between regular and paused modes. The default starting mode is regular. You can use the -pause parameter to start in paused mode.
 *
 * Marathon local testers have many useful options, including running a range of seeds with a single command, running more than one seed at time (multiple threads), controlling time limit, saving input/output/error and loading solution from a file. The usage of these options are described here.
 *
 * Payments
 * Topcoder will compensate members in accordance with our standard payment policies, unless otherwise specified in this challenge. For information on payment policies, setting up your profile to receive payments, and general payment questions, please refer to ‌Payment Policies and Instructions.
 */
package marathons.topcoder.chickenRun;
