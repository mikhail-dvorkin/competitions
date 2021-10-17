/**
 * https://www.topcoder.com/challenges/eab61248-dcf2-4a3b-aa72-62c703d9a7dd
 *
 * Marathon Match 130
 *
 * Overview
 *
 * You are given an undirected graph, consisting of a single connected component with N nodes. Your task is to label the nodes of this graph with distinct non-negative integers, such that the absolute difference between every pair of connected node values is distinct. Your score is the largest node value that you used, the lower the better.
 *
 * Here is an example solution for seed=1. There are 5 nodes and 5 edges. Each node shows its id and assigned value. The edge labels show the absolute difference between node values. Note that all node values are distinct and all edge labels are distinct. The largest node value is 7, hence this solution has a raw score of 7.
 *
 * seed 1 example
 * Input and Output
 *
 * Your code will receive as input the following values, each on a separate line:
 *
 *     N, the number of nodes.
 *     E, the number of edges.
 *     E lines representing the edges of the graph. These will be formatted as "n1 n2" (without the quotes), indicating that node n1 is connected to node n2. All node indices are 0-based.
 *
 * Your solution should output the values of each node as a single line of N space-separated long integers in the range [0, 2^63-1]. The i-th integer represents the value of the i-th node (0-based).
 * Scoring
 *
 * The raw score is the largest node value that you used. If your return was invalid, then your raw score on this test case will be -1. Possible reasons include:
 *
 *     Not outputting exactly N space-separated values.
 *     Using node values outside of the [0, 2^63-1] range.
 *     Using the same node value multiple times.
 *     Using the same edge difference multiple times.
 *     Exceeding the time-limit.
 *
 * If your raw score for a test case is negative then your normalized score for that test case is 0. Otherwise, your normalized score for each test case is MIN/YOUR, where YOUR is your raw score and MIN is the smallest positive raw score currently obtained on this test case (considering only the last submission from each competitor). Finally, the sum of all your test scores is normalized to 100.
 * Test Case Generation
 *
 * Please look at the generate() method in the visualizer's source code for the exact details about test case generation. Each test case is generated as follows:
 *
 *     The grid size N is chosen between 5 and 500, inclusive.
 *     The connectivity constant C is chosen between 0.05 and 1, inclusive.
 *     Each pair of nodes are connected with probability C. The graph is regenerated until it forms a single connected component.
 *     All values are chosen uniformly at random.
 *
 * Notes
 *
 *     A connected component of a graph is a subgraph in which every two nodes are connected to each other by a path.
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
 * Make sure you name your Source Code file as GraphLabeling.<appropriate extension>
 * Sample Submissions
 *
 * Here are example solutions for different languages, modified to be executed with the visualizer. Note that this solution does not produce a valid final answer. You may modify and submit these example solutions:
 *
 *     Java Source Code - GraphLabeling.java
 *     C++ Source Code - GraphLabeling.cpp
 *     Python3.6 Source Code - GraphLabeling.py
 *     C# Source Code - GraphLabeling.cs
 *
 * Tools
 *
 * An offline tester is available below. You can use it to test/debug your solution locally. You can also check its source code for an exact implementation of test case generation and score calculation. You can also find links to useful information and sample solutions in several languages.
 * Downloads
 *
 *     Visualizer Source - tester.zip
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
 * Here, <command> is the command to execute your program, and <seed> is seed for test case generation. If your compiled solution is an executable file, the command will be the full path to it, for example, "C:\TopCoder\GraphLabeling.exe" or "~/topcoder/GraphLabeling". In case your compiled solution is to be run with the help of an interpreter, for example, if you program is in Java, the command will be something like "java -cp C:\TopCoder GraphLabeling".
 *
 * Additionally you can use the following options:
 *
 *     -seed <seed> Sets the seed used for test case generation, default is seed 1.
 *     -debug. Print debug information.
 *     -N <N> Sets a custom number of nodes.
 *
 *     -C <C> Sets a custom connectivity constant.
 *
 * Marathon local testers have many useful options, including running a range of seeds with a single command, running more than one seed at time (multiple threads), controlling time limit, saving input/output/error and loading solution from a file. The usage of these options are described here.
 *
 */
package marathons.topcoder.graphLabeling;
