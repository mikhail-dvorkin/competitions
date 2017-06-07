#!/usr/bin/env python3

def solve(p, a):
	a.sort()
	ans = 0
	m = 0
	for x in a:
		m = max(m + 1, (x + p - 1) // p)
		ans += m
	return ans

def test():
	assert solve(2, [4, 6, 6]) == 9
	assert solve(3, [4, 5]) == 5

def read_ints():
	return list(map(int, input().split()))


if __name__ == '__main__':
	#test()
	print(solve(read_ints()[1], read_ints()))
