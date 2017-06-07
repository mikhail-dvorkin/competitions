#!/usr/bin/env python3

def solve(a):
	n = len(a)
	a = [x % 2 != 0 for x in a]
	for p in range(2):
		des = p == 0
		best = n + 1
		for q in range(2):
			b = a[:]
			ops = 0
			if q:
				b[0] ^= True
				if 1 < n:
					b[1] ^= True
				ops += 1
			for i in range(n - 1):
				if b[i] == (des > 0):
					continue
				ops += 1
				for j in range(i, i + 3):
					if j == n:
						break
					b[j] ^= True
			if b[n - 1] == des:
				best = min(best, ops)
		yield -1 if best > n else best


if __name__ == '__main__':
	input()
	print(*solve(list(map(int, input().split()))))
