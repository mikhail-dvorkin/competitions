#!/usr/bin/python3

def solve():
	input()
	return input().translate({ord('S'): 'E', ord('E'): 'S'})

tests = int(input())
for t in range(tests):
	print('Case #{}: {}'.format(t + 1, solve()))
