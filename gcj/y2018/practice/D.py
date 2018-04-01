#!/usr/bin/python3
import collections


def solve():
	n, k = map(int, input().split())
	a = collections.Counter({n: 1})
	while True:
		space = max(a.keys())
		num = a[space]
		del a[space]
		space -= 1
		parts = space - space // 2, space // 2
		for part in parts:
			a[part] += num
		k -= num
		if k <= 0:
			return parts


tests = int(input())
for t in range(tests):
	print('Case #{}: {} {}'.format(t + 1, *solve()))
