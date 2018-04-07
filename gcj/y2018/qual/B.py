#!/usr/bin/python3

def solve():
	n = int(input())
	a = list(map(int, input().split()))
	for i in range(2):
		a[i::2] = sorted(a[i::2])
	for i in range(n - 1):
		if a[i] > a[i + 1]:
			return i
	return 'OK'

tests = int(input())
for t in range(tests):
	print('Case #{}: {}'.format(t + 1, solve()))
