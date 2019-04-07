#!/usr/bin/python3

def solve(forbidden=4):
	n = int(input())
	a = n - 1
	b = 1
	t = 1
	while t <= a:
		if a // t % 10 == forbidden:
			a -= t
			b += t
		t *= 10
	return '{} {}'.format(a, b)

tests = int(input())
for t in range(tests):
	print('Case #{}: {}'.format(t + 1, solve()))
