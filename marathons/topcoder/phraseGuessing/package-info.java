/**
 * https://www.topcoder.com/challenges/5fb2b54f-055e-4059-8687-ebaa8dbd8a18
 *
 * Phrase Guessing
 *
 * You and your friend are playing a phrase guessing game. Your friend thinks of a secret phrase with N distinct words selected from a dictionary (words_alpha_filtered.txt). Given N and the length of the phrase P you must make a series of guesses to find the secret phrase as quickly as possible. Your guess must be a phrase of length P with N space-separated words from the dictionary. After each guess, your friend will provide you with feedback of length P, ie., one for each character of your guess. There are three feedback characters: '*' for exact matches, '+' for characters in the wrong place (partial matches) and '.' for characters that do not occur (mismatches). In particular the feedback string is generated with the following pseudocode:
 *
 *     Initialize: for all i, set feedback[i] := '?' and used[i] := false
 *     Find exact matches: for all i, if guess[i] == secret[i], then set feedback[i] := '*' and used[i] := true
 *     Find partial matches: for all i from 1 to P, if feedback[i] == '?' and there exists k such that guess[i] == secret[k] and used[k] == false, then set feedback[i] := '+' and used[k] := true
 *     Remaining feedback are mismatches: for all i, if feedback[i] == '?', then set feedback[i] := '.'
 *
 * Your friend sends you his feedback, but unfortunately it gets corrupted during the process. In particular, each character of the feedback is corrupted with probability C. When a character is corrupted it is replaced with '*', '+' or '.' chosen at random.
 * Input and Output
 *
 * This is an interactive problem, so your code needs to interact with the tester for each guess. Initially your code will receive as input the following values, each on a separate line:
 *
 *     N, the number of secret words.
 *     P, the length of the secret phrase.
 *     C, the corruption probability.
 *
 * The following loop repeats for 10,000 guesses or until you terminate it:
 *
 *     You output a guess phrase with N space-separated words and a total of P characters.
 *     If your guess is correct (all characters match) then the game terminates and you receive the final score (number of guesses used).
 *     Otherwise your guess is compared to the secret phrase and the appropriate feedback is generated.
 *     Each character of the feedback is corrupted with probability C. When a character is corrupted it is replaced with '*', '+' or '.' chosen at random.
 *     The tester outputs the total elapsed time and the corrupted feedback, each on a separate line.
 *     You can terminate the loop by printing the line "-1". You will then receive the score based on your last guess.
 *
 * Example
 *
 * Here is an example solution for seed=1 and secret phrase "SPILLER NONEMOTIVE".
 *
 * seed 1 example
 * Scoring
 *
 * The raw score of your final guess is the number of guesses you made + 100 * number of mismatches ('.') + 20 * number of partial matches ('+'). If your return was invalid, then your raw score on this test case will be -1. Possible reasons include:
 *
 *     Using invalid characters that are not space or capital letters ('A' to 'Z').
 *     Not using exactly N words or using words that are not found in the dictionary.
 *     Guessing a phrase that doesn't have P characters.
 *     Exceeding the time-limit or 10,000 guesses.
 *
 * If your raw score for a test case is negative then your normalized score for that test case is 0. Otherwise, your normalized score for each test case is MIN/YOUR, where YOUR is your raw score and MIN is the smallest positive raw score currently obtained on this test case (considering only the last submission from each competitor). Finally, the sum of all your test scores is normalized to 100.
 * Test Case Generation
 *
 * Please look at the generate() method in visualizer's source code for the exact details about test case generation. Each test case is generated as follows:
 *
 *     The number of secret words N is chosen between 2 and 10, inclusive.
 *     The corruption probability C is chosen between 0.05 and 0.8, inclusive.
 *     N distinct words are chosen from the dictionary to form the secret phrase. Each word is separated by a space.
 *     All values are chosen uniformly at random.
 *
 * Notes
 *
 *     The available words are provided in words_alpha_filtered.txt that your solution can read during runtime.
 *     You are allowed to use a word multiple times in the same guess.
 *     The corrupted feedback character can be identical to the original feedback character at the same location.
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
 * Make sure you name your Source Code file as PhraseGuessing.<appropriate extension>
 * Sample Submissions
 *
 * Here are example solutions for different languages, modified to be executed with the visualizer. Note that this solution does not produce a valid final answer. You may modify and submit these example solutions:
 *
 *     Java Source Code - PhraseGuessing.java
 *     C++ Source Code - PhraseGuessing.cpp
 *     Python3.6 Source Code - PhraseGuessing.py
 *     C# Source Code - PhraseGuessing.cs
 *
 * Tools
 *
 * An offline tester is available below. You can use it to test/debug your solution locally. You can also check its source code for an exact implementation of test case generation and score calculation. You can also find links to useful information and sample solutions in several languages.
 * Downloads
 *
 *     Visualizer Source - PhraseGuessingTester.zip
 *     Visualizer Binary - tester.jar
 *     Dictionary of words - words_alpha_filtered.txt
 *
 * Offline Tester / Visualizer
 *
 * In order to use the offline tester/visualizer tool for testing your solution locally, you'll have to include in your solution the main method that interacts with the tester/visualizer via reading data from standard input and writing data to standard output.
 *
 * To run the tester with your solution, you should run:
 *
 * java -jar tester.jar -exec "<command>" -seed <seed>
 *
 * Here, <command> is the command to execute your program, and <seed> is seed for test case generation. If your compiled solution is an executable file, the command will be the full path to it, for example, "C:\TopCoder\PhraseGuessing.exe" or "~/topcoder/PhraseGuessing". In case your compiled solution is to be run with the help of an interpreter, for example, if you program is in Java, the command will be something like "java -cp C:\TopCoder PhraseGuessing".
 *
 * Additionally you can use the following options:
 *
 *     -seed <seed> Sets the seed used for test case generation, default is seed 1.
 *     -debug. Print debug information.
 *     -N <N> Sets a custom number of secret words.
 *     -C <C> Sets a custom corruption probability.
 *
 * Marathon local testers have many useful options, including running a range of seeds with a single command, running more than one seed at time (multiple threads), controlling time limit, saving input/output/error and loading solution from a file. The usage of these options are described here.
 */
package marathons.topcoder.phraseGuessing;
