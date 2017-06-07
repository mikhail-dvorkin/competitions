#!/usr/bin/env python3

def solve(a):
	a.sort()
	return a[0] + a[3] == a[1] + a[2]

if __name__ == '__main__':
	print("Yes" if solve(list(map(int, input().split()))) else "No")
