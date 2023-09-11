AvgScore = 825480 (Place: 39th)

* Use heuristic algorithms for graph cover, MST, and Steiner problem to select a "corridor"
* Split the rest to "groups" hanging from it
* Greedily donate cells from group of size S to group of size <= S - 2
* Use DP to make use of groups, from largest to smallest
* Optimize corridor: fill in crops that fit perfectly, then fill in anything that fits at all
