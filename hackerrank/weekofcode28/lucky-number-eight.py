#!/usr/bin/env python3

Q = 8
M = 10**9 + 7

def solve(s):
	a = [1] + [0] * (Q - 1)
	for d in map(int, s):
		b = a[:]
		for i in range(Q):
			b[(10 * i + d) % Q] += a[i]
		a = [i % M for i in b]
	return (a[0] + M - 1) % M

def test():
	assert solve('968') == 3
	assert solve('9') == 0
	assert solve('000') == 7
	n = 2 * 10**5
	assert solve('0' * n) == (2**n - 1) % M


if __name__ == '__main__':
	#test()
	input()
	print(solve(input()))
