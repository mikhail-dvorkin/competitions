#!/usr/bin/env python3

def solve(n):
	for i in range(2, n + 2):
		if n % i:
			return i

if __name__ == '__main__':
	print(solve(int(input())))
