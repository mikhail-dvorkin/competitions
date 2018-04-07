#!/usr/bin/python3
import sys

def solve():
	a = int(input())
	n = 1
	while 9 * n**2 < a:
		n += 1
	for i in range(n):
		for j in range(n):
			x = 3 * i + 2
			y = 3 * j + 2
			ready = set()
			while len(ready) < 9:
				print(x, y)
				sys.stdout.flush()
				point = tuple(map(int, input().split()))
				if point == (0, 0):
					return
				ready.add(point)

tests = int(input())
for t in range(tests):
	solve()
