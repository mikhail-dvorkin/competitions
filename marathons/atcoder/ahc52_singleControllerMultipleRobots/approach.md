[Statement](https://atcoder.jp/contests/ahc052/tasks/ahc052_a)

[Visualization](https://disk.yandex.ru/d/juR6HCflpqZl0A/reports/ahc052/vis_singleControllerMultipleRobots.gif)

During the last half hour, I dropped to 16th place, before that I was in 8th. I'm very pleased!! :)

I wouldn't say my approach is particularly genius. Rather, I was lucky that I managed to write everything I wanted without any bugs in just two hours, and it worked right away.

I'm decreasing the parameter p from 15 (half the side of a square) downwards.

For each cell and each of the 8 (understand why) directions, I precompute: if you start in that cell and move in that direction as a snake, which set of cells will be visited. A snake is p large moves of length p.

Then, for each cell that hasn't been covered yet, in order from the corners to the center, I choose a robot and a snake that will cover it. Among all such snakes, I choose the one that covers as many other uncovered cells as possible. Among all the robots, I choose either a random one or the closest one.

But how do you deliver a robot to the necessary start of the optimal snake?

To do this, initially, [8, 8, 7, 7, 6, 6, 5] delivery buttons will be pressed! (You need 3 control buttons for the snake path, so you have 7 buttons for delivery to the snake starting positions.)

Using dynamic programming, I precompute for each robot which cells it can reach with this kind of delivery procedure. And, naturally, I only choose the pair: the optimal snake and the robot that can be delivered to its starting position.

It seems my intuition worked: this array is really good :) Because in the 4-hour rounds, you must get AC on all tests, otherwise, it's not counted, and there's no feedback. So here's the thing: this array + the parameter p equal to 15 immediately got all the ACs and a lot of points. When I tried to "improve" something, WA occurred on two tests. So I had to fix that during all parameter adjustments, I should start with this array and p=15.