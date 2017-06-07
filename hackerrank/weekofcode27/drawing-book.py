#!/usr/bin/env python3

def solve(n, p):
	p //= 2
	n //= 2
	return min(p, n - p)

def test():
	assert solve(6, 2) == 1
	assert solve(5, 4) == 0
	assert [[solve(n, x + 1) for x in range(n)] for n in [5, 6]] == [[0, 1, 1, 0, 0], [0, 1, 1, 1, 1, 0]]


if __name__ == '__main__':
	#test()
	print(solve(int(input()), int(input())))
