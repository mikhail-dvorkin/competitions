#!/usr/bin/env python3

def solve(a):
	xor = 0
	for x in a:
		xor ^= ((x - 1) ^ 1) + 1
	return "W" if xor else "L"

def test():
	assert solve([1, 2]) == "W"
	assert solve([2, 2]) == "L"

def read_ints():
	return list(map(int, input().split()))


if __name__ == '__main__':
	test()
	for _ in range(int(input())):
		input()
		print(solve(read_ints()))
