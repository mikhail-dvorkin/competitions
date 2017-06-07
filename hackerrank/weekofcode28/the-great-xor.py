#!/usr/bin/env python3

def solve(x):
	return ((1 << x.bit_length()) - 1) ^ x

def solve_slow(x):
	return len([a for a in range(x) if (a ^ x) > x])

def test():
	assert solve(2) == 1
	assert solve(10) == 5
	for x in range(10**4):
		assert solve(x) == solve_slow(x)


if __name__ == '__main__':
	#test()
	for _ in range(int(input())):
		print(solve(int(input())))
