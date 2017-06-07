#!/usr/bin/env python3

def solve(a):
	n = len(a)
	a.sort()
	i = 0
	ans = 0
	while i < n:
		ans += 1
		if i + 1 < n and a[i + 1] <= a[i] + 2:
			i += 2
			continue
		i += 1
	return ans

if __name__ == '__main__':
	input()
	print(solve(list(map(int, input().split()))))
