/**
 * http://www.codecup.nl/rules_blackhole.php
 *
 * Blackhole
 *
 * Introduction
 *
 * The game Black Hole is a two-player game that is played on 36 hexagonal tiles placed in the shape of a triangle.
 *
 * When the game begins all tiles are empty except for 5 tiles where they jury has placed a brown jury stone.
 *
 *
 * One player has 15 red stones and the other player has 15 blue stones. The pieces for each player are numbered from 1 to 15.
 *
 * The players take turns placing a stone of their own colour on the board.
 *
 * The game ends when all stones have been placed on the board, and only a single tile of the board is empty. This tile is called the ‘black hole’.
 *
 * Game play
 *
 * The red player starts. Both players alternate turns, and the game is finished after a total of 30 turns.
 *
 * Each turn the player places one of his remaining stones on an empty tile.
 *
 * When all the stones have been placed, the stones that are adjacent to the black hole fall into the black hole.
 *
 * Score
 *
 * The scoring is based on the tiles that have been placed adjacent to the black hole.
 *
 * Let R be the sum of all the numbers on the red tiles adjacent to the black hole. Let B be the sum of the adjacent blue tiles.
 *
 * The red player receives 75 + R - B points, while the blue player receives 75 - R + B points.
 *
 * In a competition, your player will play every other player twice; once as Red, once as Blue.
 *
 * Your competition is score is the sum of the points you have received for all the games you have played. The final ranking is determined by the competition score. Is multiple players have the same competition score, a ‘sub-competition score’ is determined based on only the results of the matches between players with this same competition score. If the sub-competition score is also the same, those players are tied.
 *
 * Example
 *
 * Consider the following end situation:
 *
 *
 * Red has three stones adjacent to the black hole. These stones have the values 4, 7 and 14, so R = 4 + 7 + 14 = 25.
 *
 * The blue player has two stones, with values 2 and 11, adjacent to the black hole. Therefore B = 2 + 11 = 13.
 *
 * The final scores are:
 *
 * Red:	75 + 25 - 13 = 87 points
 * Blue:	75 + 13 - 25 = 63 points
 * If your program exceed the time limit or makes an invalid move, it will receive zero points and your opponent will finish the game against a player controlled by the jury. See the technical rules for more information. Your opponent will receive a score based on the rule above.
 *
 * Input/Output
 *
 * A communication protocol has been designed for your program to communicate with the judging software. You will read the moves of the other players from stdin (standard in). You must print your own moves to stdout (standard out). You are free to write to stderr (standard error).
 *
 * The following image shows how all the tiles have been labeled.
 *
 *
 * At the start of the game, both players receive 5 lines. Each line contains a position (for example: "B4"), which indicates that this position must be a brown tile.
 *
 * If your player is red, the following line will be "Start", and your program has to print his first move. After this, your program has to keep reading the moves of the other player, and respond with his own move, until it receives "Quit". If your player is blue, your program has to read the move of the other player and respond with its own move until it receives "Quit". The "Quit"- instruction will be sent when the game ends, or if you make an invalid move or run out of time.
 *
 * A move should follow the format <position>=<number>. For example, the move "A3=6" places the number 6 on A3.
 */
package marathons.codecup.y2018_blackhole;
