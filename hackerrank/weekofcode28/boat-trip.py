#!/usr/bin/env python3

def solve(c, m, p):
	return c * m >= max(p)

def test():
	assert solve(2, 2, [1, 2, 1, 4, 3])
	assert not solve(1, 2, [1, 2, 1, 4, 1])

def read_ints():
	return list(map(int, input().split()))


if __name__ == '__main__':
	#test()
	_, c, m = read_ints()
	print("Yes" if solve(c, m, read_ints()) else "No")
