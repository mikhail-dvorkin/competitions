#!/usr/bin/python3
import collections


def solve():
	low, high = map(int, input().split())
	high += 1
	input()
	while True:
		mid = (low + high) // 2
		print(mid)
		resp = input()
		if resp == 'CORRECT':
			return
		if resp == 'TOO_BIG':
			high = mid
		else:
			low = mid


tests = int(input())
for t in range(tests):
	solve()
